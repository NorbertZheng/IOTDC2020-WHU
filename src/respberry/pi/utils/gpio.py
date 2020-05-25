import time
# raspberry GPIO
import RPi.GPIO as GPIO

# init as BOARD index
GPIO.setmode(GPIO.BOARD)

GPIO_REGISTER = {
    "voice": 11     # GPIO.0
}

def reset_gpio():
    # reset voice gpio as input
    GPIO.setup(GPIO_REGISTER["voice"], GPIO.IN)

def test_gpio_in(
    channel = 11    # GPIO.0
):
    # setup gpio
    GPIO.setup(channel, GPIO.IN)
    while True:
        print(GPIO.input(channel))
        time.sleep(1)
    GPIO.cleanup()

if __name__ == "__main__":
    test_gpio_in()
