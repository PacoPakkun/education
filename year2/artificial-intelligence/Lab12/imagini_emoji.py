import os
import csv

from sklearn.cluster import KMeans

from utils import createData, read_images
from sklearn.linear_model import SGDClassifier
from sklearn.metrics import accuracy_score


def load_data():
    crtDir = os.getcwd()
    fileName = os.path.join(crtDir, 'data', 'faces.csv')

    data = []
    with open(fileName) as csv_file:
        csv_reader = csv.reader(csv_file, delimiter=',')
        line_count = 0
        for row in csv_reader:
            if line_count == 0:
                data_names = row
            else:
                data.append(row)
            line_count += 1

    inputs_code = [data[i][1].replace("U+", "") for i in range(len(data))]
    outputs = [data[i][3] for i in range(len(data))]

    return inputs_code, outputs


def SGD(trainInputs, trainOutputs, testInputs, testOutputs):
    classifier = SGDClassifier(max_iter=1000)
    classifier.fit(trainInputs, trainOutputs)
    computed = classifier.predict(testInputs)
    acc = accuracy_score(testOutputs, computed)
    print("Acuratete pentru emoji(happy vs sad): " + str(acc))


def kMeans_tool(trainFeatures, testFeatures, labelNames, testOutputs, k):
    unsupervisedClassifier = KMeans(n_clusters=k, random_state=0)
    unsupervisedClassifier.fit(trainFeatures)
    computedTestIndexes = unsupervisedClassifier.predict(testFeatures)
    computedTestOutputs = [labelNames[value] for value in computedTestIndexes]
    print("Acuratete kMeans: ", accuracy_score(testOutputs, computedTestOutputs))


def solve_emoji():
    inputs_code, outputs = load_data()
    trainInputs, trainOutputs, testInputs, testOutputs = createData(inputs_code, outputs)
    trainInputs = read_images("data/train/*.jpg")
    testInputs = read_images("data/test/*.jpg")
    SGD(trainInputs, trainOutputs, testInputs, testOutputs)

    # labelNames = list(set(trainOutputs))
    # kMeans_tool(trainInputs, testInputs, labelNames, testOutputs, len(labelNames))
