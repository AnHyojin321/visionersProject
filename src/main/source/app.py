from flask import Flask, Response, render_template, jsonify, request
import cv2
import torch
import sys
import pathlib
import json
import requests
import mysql.connector
from flask_cors import CORS

if sys.platform == 'win32':
    pathlib.PosixPath = pathlib.WindowsPath

app = Flask(__name__)
CORS(app, resources={r"/*": {"origins": "*"}})

try:
    model = torch.hub.load("ultralytics/yolov5", 'custom', path='train_5.pt', force_reload=True, skip_validation=True)
    print("Model loaded successfully.")
except Exception as e:
    print(f"Error loading model: {e}")

def get_prohibited_items():
    conn = mysql.connector.connect(
        host="localhost",
        user="root",
        password="anhyojin321!",
        database="visioners"
    )
    cursor = conn.cursor()
    cursor.execute("SELECT name FROM prohibited_items")
    items = [row[0] for row in cursor.fetchall()]
    conn.close()
    return items

def gen_frames():
    cap = cv2.VideoCapture(0)
    while True:
        success, frame = cap.read()
        if not success:
            break
        else:
            try:
                # YOLOv5 모델로 객체 탐지
                results = model(frame)
                frame = results.render()[0]
                ret, buffer = cv2.imencode('.jpg', frame)
                frame = buffer.tobytes()
                yield (b'--frame\r\n'
                       b'Content-Type: image/jpeg\r\n\r\n' + frame + b'\r\n')
            except Exception as e:
                print(f"Error processing frame: {e}")
    cap.release()

@app.route('/video_feed')
def video_feed():
    return Response(gen_frames(), mimetype='multipart/x-mixed-replace; boundary=frame')

@app.route('/detect_objects', methods=['POST'])
def detect_objects():
    try:
        cap = cv2.VideoCapture(0)
        success, frame = cap.read()
        cap.release()
        if success:
            try:
                results = model(frame)
                detected_names = [obj['name'] for obj in results.pandas().xyxy[0].to_dict(orient="records")]
                print(f"Detected objects: {detected_names}")
                prohibited_items = get_prohibited_items()
                prohibited_detected = [item for item in detected_names if item in prohibited_items]

                if prohibited_detected:
                    message = f"Detected prohibited items: {', '.join(prohibited_detected)}. 반입 불가능합니다."
                else:
                    message = "No prohibited items detected. 반입 가능합니다."

                return jsonify({
                    'message': message,
                    'detected': detected_names,
                    'prohibited_detected': prohibited_detected
                })
            except Exception as e:
                print(f"Error during detection: {e}")
                return jsonify({'message': f"Error during detection: {e}"}), 500
        else:
            print("Error capturing frame.")
            return jsonify({'message': 'Error capturing frame.'}), 500
    except Exception as e:
        print(f"Error in detect_objects endpoint: {e}")
        return jsonify({'message': f"Internal server error: {e}"}), 500

@app.route('/')
def index():
    return render_template('index.html')

if __name__ == '__main__':
    app.run(debug=True)
