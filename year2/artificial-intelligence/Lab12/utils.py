import glob

import cv2
import numpy as np
import matplotlib.pyplot as plt


def createData(inputs, outputs):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    return trainInputs, trainOutputs, testInputs, testOutputs


def flatten(mat):
    x = []
    for line in mat:
        for el in line:
            el = el / 255
            x.append(el)
    return x


def read_images(path):
    size = 0
    image_list = []
    for fileName in glob.glob(path):
        print(fileName)
        size = size + 1
        image = cv2.imread(fileName)
        image = image.transpose((2, 0, 1))
        image = image.reshape(image.shape[0], (image.shape[1] * image.shape[2]))
        image = flatten(image)
        image_list.append(image)
        if size == 200:
            break
    return image_list


def read_img():
    path = 'data/facialEmotions/test/'
    emotions = ['angry', 'disgust', 'fear', 'happy', 'neutral', 'sad', 'surprise']
    image_list = []
    for emo in emotions:
        fullpath = path + emo + '/*.jpg'
        size = 0
        emolist = []
        for fileName in glob.glob(fullpath):
            print(fileName)
            size += 1
            image = cv2.imread(fileName)
            image = cv2.resize(image, (48, 48))
            image = cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
            image = np.reshape(image, [1, image.shape[0], image.shape[1], 1])
            emolist.append(image)
            # image = plt.imread(fileName)
            # emolist.append(fileName)
            if size == 50:
                break
        image_list.append(emolist)
    return image_list
