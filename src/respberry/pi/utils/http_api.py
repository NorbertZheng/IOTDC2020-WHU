import json
import requests

def send_post(
    url,
    data    # dict
):
    res = requests.post(
        url = url,
        data = data
    )
    return res

def send_get(
    url
):
    res = requests.get(
        url = url
    )
    return res

if __name__ == "__main__":
    url = "http://115.159.3.231:8000/get_medi/"
    data = {
        "user_name": "zhenwei",
        "password": "123456"
    }
    # print(data)
    res = send_post(
        url = url,
        data = data
    )
    print(res.text)
