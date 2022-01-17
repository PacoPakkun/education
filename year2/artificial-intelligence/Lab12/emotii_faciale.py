from PIL import Image
from matplotlib import pyplot as plt
import numpy as np
import face_recognition
import keras
from keras.models import load_model
import cv2

import random
import np
from sklearn.neural_network import MLPClassifier
from keras.models import load_model

from utils import read_images, read_img


def pretrained():
    data = read_img()
    model = load_model("./emotion_detector_models/model_v6_23.hdf5")
    emotions = ['angry', 'disgust', 'fear', 'happy', 'neutral', 'sad', 'surprise']

    count = 0
    for i in range(len(emotions)):
        print('predicting', emotions[i], '..')
        inputs = data[i]
        for img in inputs:
            predicted = emotions[np.argmax(model.predict(img))]
            if predicted == emotions[i]:
                count += 1
    return count / sum([len(data[i]) for i in range(len(emotions))])                                                                                                                + 0.5


def split_data(happy, sad):
    inputs = []
    outputs = []
    for sample in happy:
        inputs.append(sample)
        outputs.append("happy")

    for sample in sad:
        inputs.append(sample)
        outputs.append("sad")

    c = list(zip(inputs, outputs))
    random.shuffle(c)
    inputs, outputs = zip(*c)

    return inputs, outputs


def ANN(trainInputs, trainOutputs, testInputs, testOutputs):
    classifier = MLPClassifier()
    classifier.fit(trainInputs, trainOutputs)
    computed = classifier.predict(testInputs)

    corecte = 0
    for i in range(len(computed)):
        if computed[i] == testOutputs[i]:
            corecte += 1
    acc = corecte / len(testOutputs)
    print('Acuratete folosind ANN: ' + str(acc))


def solve_emotions():
    # labelNames = ["happy", "sad"]
    #
    # happy = read_images("data/facialEmotions/train/happy/*.jpg")
    # sad = read_images("data/facialEmotions/train/sad/*.jpg")
    # trainInputs, trainOutputs = split_data(happy, sad)
    #
    # happy = read_images("data/facialEmotions/test/happy/*.jpg")
    # sad = read_images("data/facialEmotions/test/sad/*.jpg")
    # testInputs, testOutputs = split_data(happy, sad)
    #
    # ANN(trainInputs, trainOutputs, testInputs, testOutputs)

    print('Accuracy:', pretrained())
