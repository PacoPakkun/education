import math
import np
import random
from sklearn import linear_model

from classification import Classification


class Service:
    def __init__(self, inputs, output):
        # split 80/20
        np.random.seed(5)
        indexes = [i for i in range(len(inputs))]
        train_sample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
        test_sample = [i for i in indexes if i not in train_sample]

        self.train_input = [inputs[i] for i in train_sample]
        self.train_output = [output[i] for i in train_sample]

        self.test_input = [inputs[i] for i in test_sample]
        self.test_output = [output[i] for i in test_sample]

        self.execute_normalization()

        # k-fold cross-validation
        self.folds = []
        groups = []
        random.shuffle(indexes)
        k = 10
        for i in range(k):
            groups.append(indexes[i * int(len(inputs) / k):(i + 1) * int(len(inputs) / k)])
        for group in groups:
            test_input = [inputs[i] for i in group]
            test_output = [output[i] for i in group]
            train = [i for i in indexes if i not in group]
            train_input = [inputs[i] for i in train]
            train_output = [output[i] for i in train]
            self.folds.append([train_input, train_output, test_input, test_output])

    @staticmethod
    def normalization(x):
        # average
        avg = 0
        for i in range(0, len(x)):
            avg += x[i]
        avg /= len(x)
        # deviation
        dev = 0
        for i in range(0, len(x)):
            dev += (x[i] - avg) ** 2
        dev /= len(x)
        dev = math.sqrt(dev)

        norm = [(value - avg) / dev for value in x]
        return norm

    def execute_normalization(self):
        train, test = [], []
        for j in range(len(self.train_input[0])):
            train.append(
                self.normalization([self.train_input[i][j] for i in range(len(self.train_input))]))
            test.append(
                self.normalization([self.test_input[i][j] for i in range(len(self.test_input))]))

        self.train_input = [[train[i][j] for i in range(len(train))] for j in range(len(train[0]))]
        self.test_input = [[test[i][j] for i in range(len(test))] for j in range(len(test[0]))]

    @staticmethod
    def cross_entropy_loss(computed):
        suma = 0
        for list in computed:
            suma += np.log(max(list))
        suma = -suma
        return suma

    def tool_regression(self):
        print('\n-------- TOOL CLASSIFICATION --------')
        classifier = linear_model.LogisticRegression(max_iter=1000)
        classifier.fit(self.train_input, self.train_output)
        skpred = classifier.predict(self.test_input)

        print("  SKLEARN              REAL")
        for i in range(0, len(skpred)):
            print(skpred[i], "        ", self.test_output[i])

        print("\nAccuracy: ",
              sum([1 if self.test_output[i] == skpred[i] else 0 for i in range(len(self.test_output))]) / len(
                  self.test_output))

    def manual_regression(self):
        print('\n-------- MANUAL CLASSIFICATION --------')
        labels = ["Iris-setosa", "Iris-versicolor", "Iris-virginica"]
        pred = []
        for label in labels:
            classifier = Classification()
            outputs = [1 if self.train_output[i] == label else 0 for i in range(len(self.train_output))]
            classifier.fit(self.train_input, outputs)
            pred.append(classifier.predict(self.test_input))
        outputs = [[pred[i][j] for i in range(len(pred))] for j in range(len(pred[0]))]

        computed = []
        print("  COMPUTED             REAL")
        for i in range(0, len(pred[0])):
            computed.append(labels[outputs[i].index(max(outputs[i]))])
            print(computed[i], "      ", self.test_output[i])

        print("\nAccuracy: ",
              sum([1 if self.test_output[i] == computed[i] else 0 for i in range(len(self.test_output))]) / len(
                  self.test_output))

        print('\nLoss: ',
              self.cross_entropy_loss(outputs))

    def run(self):
        self.tool_regression()
        self.manual_regression()

        # for fold in self.folds:
        #     self.train_input, self.train_output, self.test_input, self.test_output = \
        #         fold[0], fold[1], fold[2], fold[3]
        #     self.manual_regression()
