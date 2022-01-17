from math import sqrt


class Regression:

    def __init__(self, real, computed):
        self.real_outputs = real
        self.computed_outputs = computed

    def abs_dif_regression(self):
        result = []
        i = 0

        while i < len(self.real_outputs):
            result.append(
                sum(abs(real - computed) for real, computed in
                    zip(self.real_outputs[i], self.computed_outputs[i])) / len(
                    self.real_outputs[0]))
            i += 1

        return result

    def dif_squares_regression(self):
        result = []
        i = 0

        while i < len(self.real_outputs):
            result.append(sqrt(
                sum(((real - computed) ** 2) for real, computed in
                    zip(self.real_outputs[i], self.computed_outputs[i])) / len(
                    self.real_outputs[0])))
            i += 1

        return result

    def run(self):
        print('\nMulti-Target Regression:')
        print('     Mean Absolute Error:    ' + str(
            self.abs_dif_regression()))
        print('     Root Mean Square Error: ' + str(
            self.dif_squares_regression()))
