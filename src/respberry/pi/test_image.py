import os
import picamera
import requests
from time import sleep

def post_req(url, data, files):
    resp = requests.request(
        "POST",
        url,
        data = data,
        files = files
    )
    return resp

def test_image(url):
    camera = picamera.PiCamera()
    camera.resolution = (1024, 768)
    camera.capture("image.png")
    # os.system("raspistill -o image.png")
    # post req
    files = {"image": open("image.png", "rb")}
    print(post_req(
        url = url,
        data = None,
        files = files
    ))

if __name__ == "__main__":
    url = "http://192.168.137.1:8000/testserver/test_image/"
    test_image(
        url = url
    )

