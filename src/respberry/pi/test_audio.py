import os
import sys
import time
import wave
import pyaudio
# raspberry GPIO
import RPi.GPIO as GPIO
# local dep
import analysis
sys.path.append("./utils")
import gpio
import audio
sys.path.append("./utils/alist_api")
import alist_api

SOUND_INPUT_FILE = "s2t.wav"
SOUND_INPUT_CNV_FILE = "s2t_cnv.wav"
SOUND_OUTPUT_FILE = "t2s.wav"
SOUND_OUTPUT_CNV_FILE = "t2s_cnv.wav"

if __name__ == "__main__":
    # reset gpio
    gpio.reset_gpio()
    while True:
        while not GPIO.input(gpio.GPIO_REGISTER["voice"]):
            time.sleep(1)
        # record audio
        audio.record_audio(
            filename = SOUND_INPUT_FILE,
            channel = gpio.GPIO_REGISTER["voice"]
        )
        # get text
        text = alist_api.API_LIST["sound2text"](
            src = SOUND_INPUT_FILE,
            dst = SOUND_INPUT_CNV_FILE
        )
        print(text)
        # analysis text
        text = analysis.analysis(
            text = text
        )
        print(text)
        # get sound
        alist_api.API_LIST["text2sound"](
            text = text,
            src = SOUND_OUTPUT_FILE,
            dst = SOUND_OUTPUT_CNV_FILE
        )
        # play audio
        audio.play_audio(
            filename = SOUND_OUTPUT_CNV_FILE
        )

