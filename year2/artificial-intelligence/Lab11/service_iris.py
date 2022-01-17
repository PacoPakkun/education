import np
from math import sqrt
import matplotlib.pyplot as plt


def distance_euclidian(a, b):
    return sqrt(sum([(a[i] - b[i]) ** 2 for i in range(len(a))]))


class ServiceIris:
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

    def run(self):
        # initial centroid
        sums = [sum([i[j] for j in range(4)]) for i in self.train_input]
        sorted_sums = sums.copy()
        sorted_sums.sort()
        centroids = [self.train_input[sums.index(sorted_sums[0])],
                     self.train_input[sums.index(sorted_sums[int(len(sorted_sums) / 3)])],
                     self.train_input[sums.index(sorted_sums[int(len(sorted_sums) / 3) * 2])]]

        # kmeans training
        for iteration in range(1000):

            # assign to clusters
            clusters = [[0 for _ in range(4)] for _ in range(3)]
            clusters_count = [0, 0, 0]
            for i in self.train_input:
                distances = [distance_euclidian(centroids[j], i) for j in range(3)]
                cluster = distances.index(min(distances))
                clusters[cluster] = [sum(x) for x in zip(clusters[cluster], i)]
                clusters_count[cluster] += 1

            # reassign centroids
            centroids = [[x / clusters_count[i] for x in clusters[i] if clusters_count[i] != 0] for i in range(3)]

        # plot data
        self.plot(centroids)

    def plot(self, centroids):
        # plot computed values
        plt.plot(centroids[0][0], centroids[0][1], 'bs')
        plt.plot(centroids[1][0], centroids[1][1], 'rs')
        plt.plot(centroids[2][0], centroids[2][1], 'gs')
        for i in self.train_input:
            distances = [distance_euclidian(centroids[j], i) for j in range(3)]
            cluster = distances.index(min(distances))
            if cluster == 0:
                plt.plot(i[0], i[1], 'b.')
            if cluster == 1:
                plt.plot(i[0], i[1], 'r.')
            if cluster == 2:
                plt.plot(i[0], i[1], 'g.')
        computed = []
        for i in self.test_input:
            distances = [distance_euclidian(centroids[j], i) for j in range(3)]
            cluster = distances.index(min(distances))
            if cluster == 0:
                computed.append('Iris-setosa')
                plt.plot(i[0], i[1], 'bx')
            if cluster == 1:
                computed.append('Iris-versicolor')
                plt.plot(i[0], i[1], 'rx')
            if cluster == 2:
                computed.append('Iris-virginica')
                plt.plot(i[0], i[1], 'gx')
        plt.title('Computed clusters')
        plt.show()

        # plot real values
        for i in range(len(self.train_input)):
            if self.train_output[i] == 'Iris-setosa':
                plt.plot(self.train_input[i][0], self.train_input[i][1], 'b.')
            if self.train_output[i] == 'Iris-versicolor':
                plt.plot(self.train_input[i][0], self.train_input[i][1], 'r.')
            if self.train_output[i] == 'Iris-virginica':
                plt.plot(self.train_input[i][0], self.train_input[i][1], 'g.')
        for i in range(len(self.test_input)):
            if self.test_output[i] == 'Iris-setosa':
                plt.plot(self.test_input[i][0], self.test_input[i][1], 'bx')
            if self.test_output[i] == 'Iris-versicolor':
                plt.plot(self.test_input[i][0], self.test_input[i][1], 'rx')
            if self.test_output[i] == 'Iris-virginica':
                plt.plot(self.test_input[i][0], self.test_input[i][1], 'gx')
        plt.title('Real clusters')
        plt.show()

        self.accuracy(computed)

    def accuracy(self, computed):
        result = sum([1 if computed[i] == self.test_output[i] else 0 for i in range(len(computed))]) / len(computed)
        print('Accuracy: ', result)
