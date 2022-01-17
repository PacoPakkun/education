import np
import sklearn.datasets
from sklearn import linear_model
from sklearn.metrics import mean_squared_error
from regression import Regression
from multi_regression import MultiTargetRegression
from math import sqrt


class Service:
    def __init__(self, inputs, outputs):
        np.random.seed(5)
        indexes = [i for i in range(len(inputs))]
        trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
        testSample = [i for i in indexes if i not in trainSample]

        self.train_inputs = [inputs[i] for i in trainSample]
        self.train_outputs = [outputs[i][0] for i in trainSample]

        self.test_inputs = [inputs[i] for i in testSample]
        self.test_outputs = [outputs[i][0] for i in testSample]

        self.train_outputs_multi = [outputs[i] for i in trainSample]
        self.test_outputs_multi = [outputs[i] for i in testSample]

    @staticmethod
    def unit_vector_normalization(x):
        m = 0
        for index in x:
            m += index ** 2;
        m = sqrt(m)
        for index in range(0, len(x)):
            x[index] /= m
        return x

    def execute_normalization(self, trainInput, testInput, trainOutput, testOutput):
        GDPTrainInputs = self.unit_vector_normalization([trainInput[i][0] for i in range(len(trainInput))])
        GDPTestInputs = self.unit_vector_normalization([testInput[i][0] for i in range(len(testInput))])
        FreedomTrainInputs = self.unit_vector_normalization([trainInput[i][1] for i in range(len(trainInput))])
        FreedomTestInputs = self.unit_vector_normalization([testInput[i][1] for i in range(len(testInput))])
        trainInput = [[GDPTrainInputs[i], FreedomTrainInputs[i]] for i in range(0, len(GDPTrainInputs))]
        testInput = [[GDPTestInputs[i], FreedomTestInputs[i]] for i in range(0, len(GDPTestInputs))]
        trainOutput = self.unit_vector_normalization(trainOutput)
        testOutput = self.unit_vector_normalization(testOutput)

        return trainInput, testInput, trainOutput, testOutput

    def uni_tool_regression(self):
        train_inputs = [self.train_inputs[0] for _ in range(len(self.train_inputs))]
        test_inputs = [self.test_inputs[0] for _ in range(len(self.test_inputs))]

        print("\nSKLEARN UNIVARIATE REGRESSION")
        regressor = linear_model.SGDRegressor(alpha=0.005, max_iter=1000, average=len(self.train_inputs))
        regressor.fit(train_inputs, self.train_outputs)
        w = [regressor.intercept_[0], regressor.coef_[0]]
        print("Learnt model is: f(x) = " + str(w[0]) + " + " + str(w[1]) + " * x")

        print("ERROR:   ", mean_squared_error(self.test_outputs, regressor.predict(test_inputs)))

    def uni_manual_regression(self):
        train_inputs = [self.train_inputs[0] for _ in range(len(self.train_inputs))]
        test_inputs = [self.test_inputs[0] for _ in range(len(self.test_inputs))]

        print("\nMANUAL UNIVARIATE REGRESSION")
        regressor = Regression()
        regressor.fit(train_inputs, self.train_outputs)
        w = [regressor.intercept, regressor.coef[0]]
        print("Learnt model is: f(x) = " + str(w[0]) + " + " + str(w[1]) + " * x")

        print("ERROR: ", regressor.mean_square_error(test_inputs, self.test_outputs))

    def bi_tool_regression(self):
        print("\nSKLEARN BIVARIATE REGRESSION")
        regressor = linear_model.SGDRegressor(alpha=0.005, max_iter=1000, average=len(self.train_inputs))
        regressor.fit(self.train_inputs, self.train_outputs)
        w0, w1, w2 = regressor.intercept_[0], regressor.coef_[0], regressor.coef_[1]
        print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')

        print("ERROR:    ",
              mean_squared_error(self.test_outputs, regressor.predict(self.test_inputs)))

    def bi_manual_regression(self):
        print("\nMANUAL BIVARIATE REGRESSION")
        regressor = Regression()
        regressor.fit(self.train_inputs, self.train_outputs)
        w0, w1, w2 = regressor.intercept, regressor.coef[0], regressor.coef[1]
        print('The learnt model is: f(x) = ', w0, ' + ', w1, ' * x1', ' + ', w2, ' * x2')

        print("ERROR:  ", regressor.mean_square_error(self.test_inputs, self.test_outputs))

    def multi_target_regression(self):
        print("\nMULTI TARGET REGRESSION")
        regressor = MultiTargetRegression()
        # inputs, outputs = sklearn.datasets.load_linnerud(return_X_y=True)
        # regressor.fit(inputs, outputs)

        regressor.fit(self.train_inputs, self.train_outputs_multi)
        w0, w1, w2 = regressor.intercept, regressor.coef[0], regressor.coef[1]
        print('The learnt model is:\nf1(x) = ', w0[0], ' + ', w1[0], ' * x1', ' + ', w2[0], ' * x2\nf2(x) = ', w0[1],
              ' + ', w1[1], ' * x1', ' + ', w2[1], ' * x2')

        print("ERROR:  ", regressor.prediction_error_multi_target(self.test_inputs, self.test_outputs_multi))

    def run(self):
        # data normalization
        self.train_inputs, self.test_inputs, self.train_outputs, self.test_outputs = self.execute_normalization(
            self.train_inputs,
            self.test_inputs,
            self.train_outputs,
            self.test_outputs)

        self.uni_tool_regression()
        self.uni_manual_regression()

        self.bi_tool_regression()
        self.bi_manual_regression()

        self.multi_target_regression()
