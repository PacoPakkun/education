import np
from sklearn import linear_model
from sklearn.metrics import mean_squared_error

from graphics import Graphics
from regression import Regression


class Service:
    def __init__(self, inputs, output):
        np.random.seed(5)
        indexes = [i for i in range(len(inputs))]
        trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
        testSample = [i for i in indexes if not i in trainSample]

        self.train_inputs = [inputs[i] for i in trainSample]
        self.train_outputs = [output[i] for i in trainSample]

        self.test_inputs = [inputs[i] for i in testSample]
        self.test_outputs = [output[i] for i in testSample]

    def tool_regression(self, train_inputs, train_outputs):
        regressor = linear_model.LinearRegression()
        regressor.fit(train_inputs, train_outputs)
        w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
        print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')
        return regressor

    def manual_regression(self, train_inputs, train_outputs):
        regressor = Regression()
        regressor.fit(train_inputs, train_outputs)
        w0, w1, w2 = regressor.intercept_, regressor.coef_[0], regressor.coef_[1]
        print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')
        return regressor

    def run(self):
        print('\n      SKLEARN MODEL')
        tool_regressor = self.tool_regression(self.train_inputs, self.train_outputs)

        print('\n\n      MY MODEL')
        manual_regressor = self.manual_regression(self.train_inputs, self.train_outputs)

        print('\n\n      PERFORMANCE')
        print('Tool prediction error:   ',
              mean_squared_error(self.test_outputs, tool_regressor.predict(self.test_inputs)))
        print('Manual prediction error: ', manual_regressor.meanSquareError(self.test_inputs, self.test_outputs))

        Graphics(manual_regressor, self.train_inputs, self.train_outputs, self.test_inputs, self.test_outputs).draw()
