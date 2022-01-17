from math import exp


class Classification:
    def __init__(self):
        self.intercept = 0.0
        self.coef = []

    def fit(self, x, y):
        learning_rate = 2
        # learning_rate = 0.1
        noEpochs = 100
        self.coef = [0.0 for _ in range(len(x[0]) + 1)]
        for epoch in range(noEpochs):
            errors = [0.0 for _ in range(len(x[0]) + 1)]
            for i in range(len(x)):
                guess = self.sigmoid(self.eval(x[i]))
                error = guess - y[i]
                for j in range(0, len(x[0])):
                    errors[j] += error * x[i][j]
                errors[-1] += error
            for i in range(0, len(x[0]) + 1):
                self.coef[i] -= (learning_rate * (errors[i] / len(x)))

        self.intercept = self.coef[-1]
        self.coef = self.coef[:-1]

    @staticmethod
    def sigmoid(x):
        return 1 / (1 + exp(-x))

    def eval(self, x):
        y = self.coef[-1]
        for j in range(len(x)):
            y += self.coef[j] * x[j]
        return y

    def predict(self, test_input):
        self.coef.append(self.intercept)
        computed = [self.sigmoid(self.eval(sample)) for sample in test_input]
        return computed
