import os
import sys
import sox
import wave
import audioop
import pyaudio
# raspberry GPIO
import RPi.GPIO as GPIO
# local dep
sys.path.append("./utils")
import gpio

# macro
CHUNK = 512
FORMAT = pyaudio.paInt16
CHANNELS = 1
RATE = 44100
RECORD_SECONDS = 1
WAVE_OUTPUT_FILENAME = "audio.wav"

"""
audio play & record
"""
def play_audio(filename):
    wf = wave.open(filename, "rb")
    # print(wf.getframerate(), wf.getnchannels(), wf.getsampwidth())
    p = pyaudio.PyAudio()
    stream = p.open(
        format = p.get_format_from_width(wf.getsampwidth()),
        channels = wf.getnchannels(),
        rate = wf.getframerate(),
        # set output device
        output = True,
        output_device_index = 1
    )
    # read data
    data = wf.readframes(CHUNK)
    # play stream
    while len(data) > 0:
        stream.write(data)
        data = wf.readframes(CHUNK)

    stream.stop_stream()
    stream.close()
    p.terminate()

def record_audio(filename, channel = gpio.GPIO_REGISTER["voice"]):
    p = pyaudio.PyAudio()

    stream = p.open(
        format = FORMAT,
        channels = CHANNELS,
        rate = RATE,
        # set input device
        input = True,
        input_device_index = 2,
        frames_per_buffer = CHUNK
    )

    print("recording...")

    frames = []

    while GPIO.input(channel):
        for i in range(0, int(RATE / CHUNK * RECORD_SECONDS)):
            data = stream.read(CHUNK)
            frames.append(data)
        print(len(frames) // int(RATE / CHUNK * RECORD_SECONDS))

    print("done")

    stream.stop_stream()
    stream.close()
    p.terminate()

    wf = wave.open(filename, "wb")
    wf.setnchannels(CHANNELS)
    wf.setsampwidth(p.get_sample_size(FORMAT))
    wf.setframerate(RATE)
    wf.writeframes(b''.join(frames))
    wf.close()

"""
Tools for audio
"""
def upsample_wav(src, dst, rate):
    tfm = sox.Transformer()
    tfm.rate(rate)
    tfm.build(src, dst)
    return True
