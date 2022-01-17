import np
import matplotlib.pyplot as plt


class Graphics:

    def __init__(self, manual_regressor, train_inputs, train_outputs, test_inputs, test_outputs):
        self.manual_regressor = manual_regressor
        self.train_inputs = train_inputs
        self.train_outputs = train_outputs
        self.test_inputs = test_inputs
        self.test_outputs = test_outputs

    # @staticmethod
    # def plotDataHistogram(x, variableName):
    #     n, bins, patches = plt.hist(x, 10)
    #     plt.title('Histogram of ' + variableName)
    #     plt.show()

    @staticmethod
    def plotData(trainC, trainF, trainO, coef, name):
        noOfPoints = 1000
        xref = []
        val = min(trainC)
        step = (max(trainC) - min(trainF)) / noOfPoints
        xref1 = []
        val1 = min(trainF)
        step1 = (max(trainF) - min((trainF))) / noOfPoints
        for i in range(1, noOfPoints):
            xref.append(val)
            xref1.append(val1)
            val += step
            val1 += step1
        yref = [[coef[0] + coef[1] * x1 + coef[2] * y1] for x1, y1 in zip(xref, xref1)]

        ax = plt.axes(projection='3d')
        ax.scatter3D(trainC, trainF, trainO, c='b', marker='o')
        ax.plot_surface(np.array(xref), np.array(xref1), np.array(yref), color='green')
        ax.set_xlabel('Capital')
        ax.set_ylabel('Freedom')
        ax.set_zlabel('Happiness')
        plt.title(name)
        plt.show()

    @staticmethod
    def predictedPlot(capital, freedom, output, computed_output, name):
        ax = plt.axes(projection='3d')
        ax.scatter3D(capital, freedom, output, c='b', marker='o')
        ax.scatter3D(capital, freedom, computed_output, c='g', marker='^')
        ax.set_xlabel('Capital')
        ax.set_ylabel('Freedom')
        ax.set_zlabel('Happiness')
        plt.title(name)
        plt.show()

    def draw(self):
        # self.plotDataHistogram([self.train_inputs[i][0] for i in range(0, len(self.train_inputs))], 'capita GDP')
        # self.plotDataHistogram([self.train_inputs[i][1] for i in range(0, len(self.train_inputs))], 'freedom')
        # self.plotDataHistogram(self.train_outputs, 'Happiness score')

        self.plotData([self.train_inputs[i][0] for i in range(0, len(self.train_inputs))],
                      [self.train_inputs[i][1] for i in range(0, len(self.train_outputs))], self.train_outputs,
                      [self.manual_regressor.intercept_, self.manual_regressor.coef_[0], self.manual_regressor.coef_[1]],
                      'TRAIN DATA VS MODEL')

        self.plotData([self.test_inputs[i][0] for i in range(0, len(self.test_inputs))],
                      [self.test_inputs[i][1] for i in range(0, len(self.test_inputs))], self.test_outputs,
                      [self.manual_regressor.intercept_, self.manual_regressor.coef_[0], self.manual_regressor.coef_[1]],
                      'TEST DATA VS MODEL')

        self.predictedPlot([self.test_inputs[i][0] for i in range(0, len(self.test_inputs))],
                           [self.test_inputs[i][1] for i in range(0, len(self.test_inputs))], self.test_outputs,
                           self.manual_regressor.predict(self.test_inputs), 'PREDICTED VS REAL')
