# local dep
import sys

class pi_top:

    """
    __init__:
        @params:
            reminder_path(str)  : where to store reminder file(get from server, csv)
    """
    def __init__(
        self,
        reminder_path
    ):
        self.reminder_path = reminder_path
        # TODO
        # 1. init pcs_control & connect_server
        # 2. check whether reminder is updated to today, otherwise get_medicalplan and store it into reminder_path

    """
    registe_timerint:
        @params:
            time(datetime)  : when to int
            func(func ptr)  : callback func
    """
    def registe_timerint(
        self,
        time,
        func
    ):
        # TODO
        # registe timer int & func according to medical plan

    """
    可能写的不太全，但是希望可以通过中断的方式对用户的请求进行处理。
    大致流程为：
    1、pi开机，初始化pi_top，注册中断函数，然后待机
    2、遇到语音请求，通过终端唤醒，pi_top调用connect_server、pcs_control和utils进行处理
    3、遇到时间中断，分为两种情况，一种是第一次提醒吃药，一种是非第一次提醒吃药
        3.1、第一次提醒吃药：
            通过pcs_control进行分药，然后调用pcs_control语音模块播报吃药
        3.2、非第一次提醒吃药
            3.2.1、拍照片，通过connect_server发送给服务器判定吃药与否，如果吃了无需进行3.2.2
            3.2.2、说明本次用药老人为按时进行，不用分药，先检查是否超出提醒次数，如果超出调用pcs_control将用药仓的药回收，否则调用语音模块提醒
    4、概括不全，感觉有必要的添加，可以增加函数
    """

if __name__ == "__main__":
    pi_top_inst = pi_top(
        reminder_path = "testpath"
    )
    # testcases
