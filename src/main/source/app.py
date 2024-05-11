from flask import Flask, Response, render_template
import cv2
import torch
from flask_cors import CORS
import os


app = Flask(__name__, template_folder='../resources/templates')
CORS(app)  # CORS를 전체 앱에 적용
model = torch.hub.load('ultralytics/yolov5', 'yolov5s', pretrained=True)

def gen_frames():  # 카메라로부터 프레임을 캡처
    cap = cv2.VideoCapture(0)
    while True:
        success, frame = cap.read()
        if not success:
            break
        else:
            frame = cv2.resize(frame, (640, 480))  # iframe에 넣기 위해 프레임 크기를 640x480으로 조정
            results = model(frame)  # YOLOv5 모델로 객체 탐지
            frame = results.render()[0]  # 탐지 결과로 프레임 렌더
            ret, buffer = cv2.imencode('.jpg', frame)
            frame = buffer.tobytes()
            yield (b'--frame\r\n'
                   b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/domestic')
def index():
    return render_template('domestic.html')

if __name__ == '__main__':
    app.run(debug=True)
