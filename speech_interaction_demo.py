# -*- coding: utf-8 -*-

from huaweicloud_sis.client.asr_client import AsrCustomizationClient
from huaweicloud_sis.bean.asr_request import AsrCustomShortRequest
from huaweicloud_sis.utils import io_utils
import json
from huaweicloud_sis.client.tts_client import TtsCustomizationClient
from huaweicloud_sis.bean.tts_request import TtsCustomRequest
from huaweicloud_sis.bean.sis_config import SisConfig
from huaweicloud_sis.exception.exceptions import ClientException
from huaweicloud_sis.exception.exceptions import ServerException

# 鉴权参数
sys_ak = '1HYJCSYF2PKVMFRWRHBP'  # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
sys_sk = '321gMCTHf2pou1PiLzMeZV1PW4osIoHIG23uhaxX'  # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
sys_region = 'cn-north-4'  # region，如cn-north-4
sys_project_id = '07a919eae80025272f94c019ddb71922'  # 同region一一对应，参考https://support.huaweicloud.com/api-sis/sis_03_0008.html

"""
    todo 请正确填写音频格式和模型属性字符串
    1. 音频格式一定要相匹配.
         例如文件或者obs url是xx.wav, 则在一句话识别是wav格式，在录音文件识别是auto。具体参考api文档。
         例如音频是pcm格式，并且采样率为8k，则格式填写pcm8k16bit。
         如果返回audio_format is invalid 说明该文件格式不支持。具体支持哪些音频格式，需要参考api文档。

    2. 音频采样率要与属性字符串的采样率要匹配。
         例如格式选择pcm16k16bit，属性字符串却选择chinese_8k_common, 则会返回'audio_format' is not match model
         例如wav本身是16k采样率，属性选择chinese_8k_common, 同样会返回'audio_format' is not match model
"""

# 可改动部分
input_path = 'E:/speech_test/input5.wav'
output_path = 'E:/speech_test/output.wav'
# 指令列表 新增指令需与其反馈一一对应
order_list = ['打开药盒。', '关闭药盒。']
# 反馈列表 新增反馈需与其指令一一对应
feedback_list = ['打开成功', '关闭成功']
of_dict = dict((order, feedback) for order, feedback in zip(order_list, feedback_list))

'''
order_set = set(order_list)
feedback_set = set(feedback_list)
# 指令-反馈（OF）字典
of_dict = dict((order, feedback) for order, feedback in zip(order_set, feedback_set))
'''

err_output = '我不明白您的意思，请再说一遍'
input_result = ''
input_text = ''
output_text = ''
def asrc_short_example():
    ak = sys_ak  # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
    sk = sys_sk  # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
    region = sys_region  # region，如cn-north-4
    project_id = sys_project_id  # 同region一一对应，参考https://support.huaweicloud.com/api-sis/sis_03_0008.html
    path = input_path  # 文件位置, 需要具体到文件，如D:/test.wav
    # 音频格式，默认不改动
    path_audio_format = 'wav'  # 音频格式，如wav等，详见api文档
    path_property = 'chinese_16k_common'  # language_sampleRate_domain, 如chinese_8k_common，详见api文档

    # step1 初始化客户端
    config = SisConfig()
    config.set_connect_timeout(5)  # 设置连接超时
    config.set_read_timeout(10)  # 设置读取超时
    # 设置代理，使用代理前一定要确保代理可用。 代理格式可为[host, port] 或 [host, port, username, password]
    # config.set_proxy(proxy)
    asr_client = AsrCustomizationClient(ak, sk, region, project_id, sis_config=config)

    # step2 构造请求
    data = io_utils.encode_file(path)
    asr_request = AsrCustomShortRequest(path_audio_format, path_property, data)
    # 所有参数均可不设置，使用默认值
    # 设置是否添加标点，yes or no，默认no
    asr_request.set_add_punc('yes')
    # 设置是否添加热词表id，没有则不填
    # asr_request.set_vocabulary_id(None)

    # step3 发送请求，返回结果,返回结果为json格式
    result = asr_client.get_short_response(asr_request)
    # print(json.dumps(result))
    return(json.dumps(result, indent=2, ensure_ascii=False))



def ttsc_example():
    """ 定制语音合成demo """
    ak = sys_ak             # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
    sk = sys_sk            # 参考https://support.huaweicloud.com/sdkreference-sis/sis_05_0003.html
    region = sys_region         # region，如cn-north-4
    project_id = sys_project_id     # 同region一一对应，参考https://support.huaweicloud.com/api-sis/sis_03_0008.html
    text = output_text           # 待合成文本，不超过500字
    path = output_path          # 保存路径，如D:/test.wav。 可在设置中选择不保存本地

    # step1 初始化客户端
    config = SisConfig()
    config.set_connect_timeout(5)       # 设置连接超时，单位s
    config.set_read_timeout(10)         # 设置读取超时，单位s
    # 设置代理，使用代理前一定要确保代理可用。 代理格式可为[host, port] 或 [host, port, username, password]
    # config.set_proxy(proxy)
    ttsc_client = TtsCustomizationClient(ak, sk, region, project_id, sis_config=config)

    # step2 构造请求
    ttsc_request = TtsCustomRequest(text)
    # 设置请求，所有参数均可不设置，使用默认参数
    # 设置属性字符串， language_speaker_domain, 默认chinese_xiaoyan_common, 参考api文档
    ttsc_request.set_property('chinese_xiaoyan_common')
    # 设置音频格式，默认wav，可选mp3和pcm
    ttsc_request.set_audio_format('wav')
    # 设置采样率，8000 or 16000, 默认8000
    ttsc_request.set_sample_rate('8000')
    # 设置音量，[0, 100]，默认50
    ttsc_request.set_volume(50)
    # 设置音高, [-500, 500], 默认0
    ttsc_request.set_pitch(0)
    # 设置音速, [-500, 500], 默认0
    ttsc_request.set_speed(0)
    # 设置是否保存，默认False
    ttsc_request.set_saved(True)
    # 设置保存路径，只有设置保存，此参数才生效
    ttsc_request.set_saved_path(path)

    # step3 发送请求，返回结果。如果设置保存，可在指定路径里查看保存的音频。
    result = ttsc_client.get_ttsc_response(ttsc_request)
    # print(json.dumps(result, indent=2, ensure_ascii=False))

if __name__ == '__main__':
    input_result = asrc_short_example()
    input_text = json.loads(input_result)['result']['text']
    # print(input_text)
    if (input_text in order_list):
        output_text = of_dict[input_text]
    else:
        output_text = err_output
    # print(output_text)
    ttsc_example()
