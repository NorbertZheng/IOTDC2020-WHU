# local dep
import sys
sys.path.append("../utils")
import http_api

class connect_server:
    # relative url according to host
    SERVER_REGISTER = {
        "audio": "/audio",
        "image": "/image"
    }

    """
    __init__:
        @params:
            host(str)   : our host server(ip / name)
        @ret:
            None
    """
    def __init__(
        self,
        host
    ):
        # TODO

    """
    get_medicalplan:
        @params:
            username(str)   : user name
            userpwd(str)    : user password
        @ret:
            data(str)       : medical plan
    """
    def get_medicalplan(
        self,
        username,
        userpwd
    ):
        # TODO
        # return "今天的用药规则是: 3片阿司匹林，用药时间是上午九点，下午2点和晚上7点。"
        # should be orgnized as csv

    """
    send_emergency:
        @params:
            username(str)   : user name
            userpwd(str)    : user password
        @ret:
            flag(bool)      : whether send emergency successfully
    """
    def get_emergency():
        # TODO
        # return "已经联系您的家人了，他们正在赶来的途中，请耐心等待."

if __name__ == "__main__":
    connect_server_inst = connect_server(
        host = "testhost"
    )
    # testcases

