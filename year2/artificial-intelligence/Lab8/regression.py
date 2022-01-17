class Regression:
    def __init__(self):
        self.intercept = 0.0
        self.coef = []

    def fit(self, x, y):
        learning_rate = 0.005
        nr_epochs = 1000
        self.coef = [0.0 for _ in range(len(x[0]) + 1)]
        for epoch in range(nr_epochs):
            errors = [0.0 for _ in range(len(x[0]) + 1)]
            for i in range(len(x)):
                guess = self.eval(x[i])
                error = guess - y[i]
                for j in range(0, len(x[0])):
                    errors[j] += error * x[i][j]
                errors[-1] += error
            for i in range(0, len(x[0]) + 1):
                self.coef[i] -= (learning_rate * (errors[i] / len(x)))
        self.intercept = self.coef[-1]
        self.coef = self.coef[:-1]

    def eval(self, x):
        y = self.coef[-1]
        for j in range(len(x)):
            y += self.coef[j] * x[j]
        return y

    def predict(self, x):
        computed = [self.eval(t) for t in x]
        return computed

    def mean_square_error(self, myinput, myoutput):
        error = 0.0
        for t1, t2 in zip(self.predict(myinput), myoutput):
            error += (t1 - t2) ** 2
        error = error / len(myoutput)
        return error
