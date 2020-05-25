# -*- coding: utf-8 -*-
import os
import sys
import time
import threading
import ali_speech
from ali_speech.callbacks import SpeechRecognizerCallback
from ali_speech.constant import ASRFormat
from ali_speech.constant import ASRSampleRate
# local dep
sys.path.append("./utils")
import audio

# macro
APPKEY = 'iVo54C7qeKynn2Qr'
TOKEN  = '20a8a377a7734603a59fc5e4ea7e250c'

# result
S2T_RESULT = ""

class MyCallback(SpeechRecognizerCallback):
    """
    parameters to the constructor are not required and can be added as needed.
    the name parameter in the example can be used as the name of the audio file
    to be identified for differentiation in multiple threads.
    """
    def __init__(self, name='default', silent = True):
        self._name  = name
        self.silent = silent

    def on_started(self, message):
        if not self.silent:
            print('MyCallback.OnRecognitionStarted: %s' % message)

    def on_result_changed(self, message):
        if not self.silent:
            print('MyCallback.OnRecognitionResultChanged: file: %s, task_id: %s, result: %s' % (
                self._name, message['header']['task_id'], message['payload']['result']))

    def on_completed(self, message):
        global S2T_RESULT
        if not self.silent:
            print('MyCallback.OnRecognitionCompleted: file: %s, task_id:%s, result:%s' % (
                self._name, message['header']['task_id'], message['payload']['result']))
        S2T_RESULT = message['payload']['result']

    def on_task_failed(self, message):
        if not self.silent:
            print('MyCallback.OnRecognitionTaskFailed: %s' % message)

    def on_channel_closed(self):
        if not self.silent:
            print('MyCallback.OnRecognitionChannelClosed')

"""
    process sound
"""
# process
def process(client, appkey, token, src = "s2t_cnv.wav"):
    audio_name = src
    callback = MyCallback(audio_name)
    recognizer = client.create_recognizer(callback)
    recognizer.set_appkey(appkey)
    recognizer.set_token(token)
    recognizer.set_format(ASRFormat.PCM)
    recognizer.set_sample_rate(ASRSampleRate.SAMPLE_RATE_16K)
    recognizer.set_enable_intermediate_result(False)
    recognizer.set_enable_punctuation_prediction(True)
    recognizer.set_enable_inverse_text_normalization(True)
    try:
        ret = recognizer.start()
        if ret < 0:
            return ret
        print('sending audio...')
        with open(audio_name, 'rb') as f:
            audio = f.read(3200)
            while audio:
                ret = recognizer.send(audio)
                if ret < 0:
                    break
                time.sleep(0.1)
                audio = f.read(3200)
        recognizer.stop()
    except Exception as e:
        print(e)
    finally:
        recognizer.close()

# multithread process
def process_multithread(client, appkey, token, number):
    thread_list = []
    for i in range(0, number):
        thread = threading.Thread(target=process, args=(client, appkey, token))
        thread_list.append(thread)
        thread.start()
    for thread in thread_list:
        thread.join()

# def sound2text
def sound2text(src = "s2t.wav", dst = "s2t_cnv.wav"):
    audio.upsample_wav(
        src = src,
        dst = dst,
        rate = ASRSampleRate.SAMPLE_RATE_16K
    )
    client = ali_speech.NlsClient()
    # set the level of output log information: DEBUG, INFO, WARNING, ERROR
    client.set_log_level('INFO')
    appkey = APPKEY
    token = TOKEN
    process(
        client = client,
        appkey = appkey,
        token = token,
        src = dst
    )
    # multithread example
    # process_multithread(client, appkey, token, 2)
    return S2T_RESULT