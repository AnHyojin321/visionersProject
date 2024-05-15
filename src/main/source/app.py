from flask import Flask, Response, render_template
import cv2
import torch
import sys
import pathlib

if sys.platform == 'win32':
    pathlib.PosixPath = pathlib.WindowsPath

app = Flask(__name__)

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

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(),
                    mimetype='multipart/x-mixed-replace; boundary=frame')

# 기본적인 127.0.0.1:5000번 포트로 접속
@app.route('/')
def index():
    return render_template('index.html')


if __name__ == '__main__':
    app.run(debug=True)