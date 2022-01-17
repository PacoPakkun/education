import random

import matplotlib.pyplot as plt
import numpy as np


def sigmoid(x):
    return 1 / (1 + np.exp(-x))


def der(x):
    return x * (1 - x)


def softmax(y):
    exp = [np.exp(i) for i in y]
    s = sum(exp)
    return [i / s for i in exp]


class NeuralNetwork:
    def __init__(self, nr_input, nr_output, hidden_layers=(2,), learning_rate=0.1, iterations=100):
        self.learning_rate = learning_rate
        self.iterations = iterations
        self.nr_input = nr_input
        self.nr_output = nr_output

        self.weights = []
        self.weights.append(np.random.rand(hidden_layers[0], nr_input))
        self.biases = []
        self.biases.append(np.random.rand(hidden_layers[0], 1))

        for i in range(1, len(hidden_layers)):
            mat = np.random.rand(hidden_layers[i], hidden_layers[i - 1])
            bias = np.random.rand(hidden_layers[i], 1)
            self.weights.append(mat)
            self.biases.append(bias)
        self.weights.append(np.random.rand(nr_output, hidden_layers[len(hidden_layers) - 1]))
        self.biases.append(np.random.rand(nr_output, 1))

    def activate(self, func, mat):
        vfunc = np.vectorize(func)
        return vfunc(mat)

    def train(self, inputs, outputs):
        # feed forward
        input_mat = np.array(inputs)
        layer_results = []
        layer_input = input_mat.reshape((input_mat.shape[0], 1))
        input_mat = layer_input
        for i in range(len(self.weights)):
            mat = self.weights[i]
            bias = self.biases[i]
            result_mat = mat.dot(layer_input)
            result_mat = result_mat + bias
            if i == len(self.weights) - 1:
                result_mat = np.array(softmax(result_mat.flatten()))
                result_mat = result_mat.reshape((result_mat.shape[0], 1))
            else:
                result_mat = self.activate(sigmoid, result_mat)
            layer_input = result_mat
            layer_results.append(result_mat)
        predicted = layer_input.flatten()

        # error
        out = np.array(outputs)
        layer_error = predicted - out
        layer_error = layer_error.reshape((layer_error.shape[0], 1))

        # backpropagation
        errors = self.compute_errors(layer_error)
        self.update_weights(errors, layer_results, input_mat)

    def compute_errors(self, layer_error):
        errors = []
        layer_nr = len(self.weights)
        for i in range(layer_nr - 1, -1, -1):
            error_index = layer_nr - 1 - i
            # compute layer error
            if i == layer_nr - 1:
                errors.append(layer_error)
            else:
                tran = self.weights[i + 1].transpose()
                errors.append(tran.dot(errors[error_index - 1]))
        return errors

    def update_weights(self, errors, layer_results, input_mat):
        layer_nr = len(self.weights)
        for i in range(layer_nr - 1, -1, -1):
            error_index = layer_nr - 1 - i
            # update layer weights
            if i == layer_nr - 1:
                gradients = errors[error_index]
            else:
                gradients = self.activate(der, layer_results[i])
                gradients = gradients * errors[error_index]
            gradients = gradients * self.learning_rate
            self.biases[i] = self.biases[i] - gradients
            if i != 0:
                input_tran = layer_results[i - 1].transpose()
            else:
                input_tran = input_mat.transpose()
            gradients = gradients.dot(input_tran)
            self.weights[i] = self.weights[i] - gradients

    def fit(self, inputs, outputs):
        progress = []
        prev_error = 1
        not_improving = 0
        indexes = [i for i in range(len(inputs))]
        for i in range(self.iterations):
            train_indexes = random.sample(indexes, len(indexes))
            train_sample_inp = [inputs[i] for i in train_indexes]
            train_sample_out = [outputs[i] for i in train_indexes]
            for j in range(len(train_sample_inp)):
                self.train(train_sample_inp[j], train_sample_out[j])

            iteration_error = self.mean_square_error(train_sample_out, self.predict_probab(train_sample_inp))
            # iteration_error = self.cross_entropy_loss(train_sample_out, self.predict_probab(train_sample_inp))
            if prev_error - iteration_error < 0.00001:
                not_improving += 1
            else:
                not_improving = 0
            prev_error = iteration_error
            print("Iteration {} error: {}".format(i, iteration_error))
            progress.append(iteration_error)
        plt.plot(progress)
        plt.ylabel('Cost')
        plt.xlabel('Iteration')
        plt.show()

    def square_error(self, real, computed):
        return sum([(computed[i] - real[i]) ** 2 for i in range(len(computed))]) / len(computed)

    def mean_square_error(self, real, computed):
        rmse = []
        for i in range(len(real[0])):
            r = [real[j][i] for j in range(len(real))]
            c = [computed[j][i] for j in range(len(computed))]
            val = self.square_error(r, c)
            rmse.append(val)
        return sum([i for i in rmse]) / len(rmse)

    def predict_probab(self, inputs):
        result = []
        for i in range(len(inputs)):
            result.append(self.predict_sample(inputs[i]))
        return result

    def predict_sample(self, inputs_sample):
        input_mat = np.array(inputs_sample)
        layer_input = input_mat.reshape((input_mat.shape[0], 1))
        for i in range(len(self.weights)):
            mat = self.weights[i]
            bias = self.biases[i]
            result_mat = mat.dot(layer_input)
            result_mat = result_mat + bias
            if i == len(self.weights) - 1:
                result_mat = np.array(softmax(result_mat.flatten()))
                result_mat = result_mat.reshape((result_mat.shape[0], 1))
            else:
                result_mat = self.activate(sigmoid, result_mat)
            layer_input = result_mat
        return layer_input.flatten()

    def predict(self, inputs):
        probab = self.predict_probab(inputs)
        results = []
        for i in range(len(probab)):
            index = np.where(probab[i] == (max(probab[i])))[0]
            results.append(index.tolist()[0])
        return results
