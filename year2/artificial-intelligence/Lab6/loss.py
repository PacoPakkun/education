from sklearn.metrics import log_loss


class Loss:

    def regression_loss(self):
        print("\nRegression loss:")

        real = [6, 7, 6, 12, 5, 15]
        computed = [6.12, 8.9, 6.11, 12.12, 4.9, 15.5]

        cost = sum(abs(r - c) for r, c in zip(real, computed)) / len(real)
        print("      " + str(cost))

    def classification_loss(self):
        print("\nClassification loss:")

        real = ["cat", "dog", "dog", "cat", "cat"]
        computed = [[0.6, 0.4], [0.5, 0.5], [0.3, 0.7], [0.4, 0.6], [0.7, 0.3]]

        print("      " + str(log_loss(real, computed)))

    def multi_labels_loss(self):
        print("\nMulti label loss:")

        real = [[1, 0, 1, 0], [0, 1, 0, 1], [1, 0, 0, 1], [0, 1, 1, 0], [1, 0, 0, 0]]
        computed = [[1, 0, 0, 1], [0, 1, 0, 1], [1, 0, 0, 1], [0, 1, 0, 0], [1, 0, 0, 1]]

        print("      " + str(log_loss(real, computed)))

    def multi_class_loss(self):
        print("\nMulti class loss:")

        real = ["cat", "dog", "mouse", "cat", "mouse"]
        computed = [[0.7, 0.2, 0.1], [0.2, 0.3, 0.5], [0.2, 0.2, 0.6], [0.1, 0.8, 0.1], [0.1, 0.6, 0.3]]

        print("      " + str(log_loss(real, computed)))

    def run(self):
        self.regression_loss()
        self.classification_loss()
        self.multi_labels_loss()
        self.multi_class_loss()
