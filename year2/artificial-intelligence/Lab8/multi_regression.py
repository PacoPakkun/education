class MultiTargetRegression:
    def __init__(self):
        self.coef = []
        self.intercept = []

    @staticmethod
    def initialize_matrix(lx, ly):
        intercept = []
        coef = []
        for i in range(ly):
            intercept.append(0.0)
            row = []
            for j in range(lx):
                row.append(0.0)
            coef.append(row)
        return coef, intercept

    def fit(self, x, y):
        learning_rate = 0.005
        no_epochs = 1000
        self.coef, self.intercept = self.initialize_matrix(len(x[0]), len(y[0]))
        for epoch in range(no_epochs):
            errors = self.initialize_matrix(len(x[0]) + 1, len(y[0]))[0]
            for i in range(len(x)):
                guess = self.eval(x[i])
                error = [guess[j] - y[i][j] for j in range(len(y[0]))]
                for k in range(len(y[0])):
                    for j in range(len(x[0])):
                        errors[k][j] += (1 / len(x)) * error[k] * x[i][j]
                    errors[k][-1] += (1 / len(x)) * error[k]
            for i in range(len(y[0])):
                for j in range(len(x[0])):
                    self.coef[i][j] -= errors[i][j] * learning_rate
                self.intercept[i] -= errors[i][-1] * learning_rate

    def prediction_error_multi_target(self, real, computed):
        rmse = []
        for i in range(len(real[0])):
            r = [real[j][i] for j in range(len(real))]
            c = [computed[j][i] for j in range(len(computed))]
            val = sum([(c[i] - r[i]) ** 2 for i in range(len(c))]) / len(c)
            rmse.append(val)
        return sum([i for i in rmse]) / len(rmse)

    def predict(self, x):
        y = []
        for i in range(len(x)):
            s = self.eval(x[i])
            y.append(s)
        return y

    def eval(self, x):
        result = []
        for i in range(len(self.intercept)):
            s = self.intercept[i]
            for j in range(len(x)):
                s += x[j] * self.coef[i][j]
            result.append(s)
        return result
