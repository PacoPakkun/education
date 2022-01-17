class Classification:

    def __init__(self, labels, real, computed, prob):
        self.labels = labels
        self.real_labels = real
        self.computed_labels = computed
        self.probabilities = prob

    def classification(self, positive, negative):
        TP = sum(
            [1 if (self.real_labels[i] == positive and self.computed_labels[i] == positive) else 0 for i in
             range(len(self.real_labels))])
        FP = sum(
            [1 if (self.real_labels[i] in negative and self.computed_labels[i] == positive) else 0 for i in
             range(len(self.real_labels))])
        TN = sum(
            [1 if (self.real_labels[i] in negative and self.computed_labels[i] in negative) else 0 for i in
             range(len(self.real_labels))])
        FN = sum(
            [1 if (self.real_labels[i] == positive and self.computed_labels[i] in negative) else 0 for i in
             range(len(self.real_labels))])

        return TP, FP, TN, FN

    def accuracy(self):
        return sum([1 if self.real_labels[i] == self.computed_labels[i] else 0 for i in range(len(self.real_labels))])

    def calculate(self, a, b):
        try:
            return a / (a + b)
        except:
            print("div by 0")

    def run(self):
        print("\nMulti-Label Classification:")

        print('     Overall accuracy is ' + str(self.accuracy()) + '%')

        for i in range(len(self.labels)):
            label = self.labels[i]
            print('\n     ----- ' + label + ' -----')
            TP, FP, TN, FN = self.classification(label, self.labels[i:] + self.labels[:i + 1])
            pos_precision = self.calculate(TP, FP)
            neg_precision = self.calculate(TN, FN)
            pos_recall = self.calculate(TP, FN)
            neg_recall = self.calculate(TN, FP)
            print('     Positive Precision: ' + str(pos_precision))
            print('     Negative Precision: ' + str(neg_precision))
            print('     Positive Recall: ' + str(pos_recall))
            print('     Negative Recall: ' + str(neg_recall))
