# local dep
import sys
sys.path.append("../utils")
import gpio

class pcs_control:

    """
    __init__:
        @params:
            None
    """
    def __init__(
        self
    ):
        # TODO

    """
    record_audio:
        @params:
            filename(str)   : where to store audio input
            channel(int)    : which gpio(see GPIO_REGISTER)
        @ret:
            flag(bool)      : whether record audio successfully
    """
    def record_audio(
        self,
        filename,
        channel
    ):
        # TODO

    """
    play_audio:
        @params:
            filename(str)   : which audio file to play
        @ret:
            flag(bool)      : whether play audio successfully
    """
    def play_audio(
        self,
        filename
    ):
        # TODO

    """
    capture_image:
        @params:
            filename(str)       : where to store image input
            resolution(tuple)   : (int, int) to identify resolution
        @ret:
            flag(bool)          : whether capture image successfully
    """
    def capture_image(
        self,
        filename,
        resolution
    ):
        # TODO

    """
    get_bluetooth:
        @params:
            None
        @ret:
            data(str)   : data from bluetooth
    """
    def get_bluetooth(
        self
    ):
        # TODO

    """
    separate_tablet:
        @params:
            storehouse(int) : No. of drug storehouse
            number(int)     : number of drug
        @ret:
            flag(bool)      : whether separate tablets successfully
    """
    def separate_tablet(
        self,
        storehouse,
        number
    ):
        # TODO
        # storehouse is related to (see GPIO_REGISTER)

    """
    recycle_tablet:
        @params:
            None
        @ret:
            flag(bool)      : whether recycle tablet successfully
    """
    def recycle_tablet(
        self
    ):
        # TODO
        # recycle tablets(not eaten by user) to recycle box

if __name__ == "__main__":
    pcs_control_inst = pcs_control()
    # testcases
