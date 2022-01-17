import numpy as np
from PIL import Image


class Reader:
    def __init__(self):
        self.__inputs = []
        self.__outputs = []
        self.__output_names = []
        self.load_images()

    def get_inputs(self):
        return self.__inputs

    def get_outputs(self):
        return self.__outputs

    def get_output_names(self):
        return self.__output_names

    def load_images(self):
        for i in range(4101, 4300):
            img = Image.open("cats/cat." + str(i) + ".jpg")
            pixels = np.array(img.getdata())
            self.__inputs.append(pixels.reshape(-1).tolist())
            if i < 4202:
                self.__outputs.append(0)
            else:
                self.__outputs.append(1)
        self.__output_names = [0, 1]
