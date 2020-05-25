# -*- coding: utf-8 -*-
import sys
import threading
import ali_speech
from ali_speech.callbacks import SpeechSynthesizerCallback
from ali_speech.constant import TTSFormat
from ali_speech.constant import TTSSampleRate
# local dep
sys.path.append("./utils")
import audio

# macro
APPKEY = 'eDzBYtHFvOBiZeLO'
TOKEN = '20a8a377a7734603a59fc5e4ea7e250c'

# define MyCallback
class MyCallback(SpeechSynthesizerCallback):

    # parameter name is used to specify the file to save the audio
    def __init__(self, name, silent = True):
        self._name  = name
        self._fout  = open(name, 'wb')
        self.silent = silent

    def on_binary_data_received(self, raw):
        if not self.silent:
            print('MyCallback.on_binary_data_received: %s' % len(raw))
        self._fout.write(raw)

    def on_completed(self, message):
        if not self.silent:
            print('MyCallback.OnRecognitionCompleted: %s' % message)
        self._fout.close()

    def on_task_failed(self, message):
        if not self.silent:
            print('MyCallback.OnRecognitionTaskFailed-task_id:%s, status_text:%s' % (
                message['header']['task_id'], message['header']['status_text']))
        self._fout.close()

    def on_channel_closed(self):
        if not self.silent:
            print('MyCallback.OnRecognitionChannelClosed')

"""
    process text
"""
# process
def process(client, appkey, token, text, audio_name):
    callback = MyCallback(audio_name)
    synthesizer = client.create_synthesizer(callback)
    synthesizer.set_appkey(appkey)
    synthesizer.set_token(token)
    synthesizer.set_voice('xiaoyun')
    synthesizer.set_text(text)
    synthesizer.set_format(TTSFormat.WAV)
    synthesizer.set_sample_rate(TTSSampleRate.SAMPLE_RATE_16K)
    synthesizer.set_volume(50)
    synthesizer.set_speech_rate(0)
    synthesizer.set_pitch_rate(0)
    try:
        ret = synthesizer.start()
        if ret < 0:
            return ret
        synthesizer.wait_completed()
    except Exception as e:
        print(e)
    finally:
        synthesizer.close()

# multithread process
def process_multithread(client, appkey, token, number):
    thread_list = []
    for i in range(0, number):
        text = "这是线程" + str(i) + "的合成。"
        audio_name = "test_" + str(i) + ".wav"
        thread = threading.Thread(
            target = process,
            args = (
                client,
                appkey,
                token,
                text,
                audio_name
            )
        )
        thread_list.append(thread)
        thread.start()
    for thread in thread_list:
        thread.join()

# def text2sound
def text2sound(text, src = "./t2s.wav", dst = "./t2s_cnv.wav"):
    client = ali_speech.NlsClient()
    # set the level of output log information: DEBUG, INFO, WARNING, ERROR
    client.set_log_level('INFO')
    appkey = APPKEY
    token = TOKEN
    process(client, appkey, token, text, src)
    audio.upsample_wav(
        src = src,
        dst = dst,
        rate = 44100
    )
    # multithread example
    # process_multithread(client, appkey, token, 2)
