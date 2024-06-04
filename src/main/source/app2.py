from flask import Flask, Response, render_template, jsonify
from flask_cors import CORS
import cv2
import torch
import sys
import pathlib
import json

if sys.platform == 'win32':
    pathlib.PosixPath = pathlib.WindowsPath

app = Flask(__name__)
CORS(app)

model = torch.hub.load("ultralytics/yolov5", 'custom', 'lighter2.pt', force_reload=True, skip_validation=True)

def gen_frames():  # 카메라로부터 프레임을 캡처
    cap = cv2.VideoCapture(0)
    while True:
        success, frame = cap.read()
        if not success:
            break
        else:
            # YOLOv5 모델로 객체 탐지
            results = model(frame)
            frame = results.render()[0]  # 탐지 결과로 프레임 렌더
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
    cap.release()

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/detect_objects', methods=['POST'])
def detect_objects():
    cap = cv2.VideoCapture(0)
    success, frame = cap.read()
    cap.release()  # 웹캠 리소스를 해제
    if success:
        results = model(frame)
        # 인식된 객체들 중에서 이름 정보만 추출
        names = [obj['name'] for obj in results.pandas().xyxy[0].to_dict(orient="records")]
        # 스프링부트 서버의 특정 API로 JSON 데이터를 POST
        try:
            response = requests.post('http://localhost:8080/receive_objects', json={'names': names})
            return response.text  # 스프링부트 서버로부터의 응답을 반환
        except requests.exceptions.RequestException as e:
            return str(e), 500
    return jsonify([]), 404


# 기본적인 127.0.0.1:5000번 포트로 접속
@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)