import np
from sklearn.feature_extraction.text import CountVectorizer
from sklearn.cluster import KMeans
from sklearn.metrics import accuracy_score
from sklearn import neural_network


class ServiceEmotions:
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

        self.accuracies = []

    def run(self):
        train, test = self.bag_of_words()
        self.unsupervised(train, test)
        self.supervised(train, test)
        self.hybrid(train, test)
        self.accuracy()

    def bag_of_words(self):
        # transform input data in features
        vectorizer = CountVectorizer(max_features=150)
        vectorizer.fit_transform(self.train_input)
        print('\nBag of Words:\n\t', vectorizer.get_feature_names()[:25], ' ...')

        train = []
        for sentence in self.train_input:
            feature = vectorizer.transform([sentence]).toarray()
            train.append(feature.tolist()[0])
        test = []
        for sentence in self.test_input:
            feature = vectorizer.transform([sentence]).toarray()
            test.append(feature.tolist()[0])
        return train, test

    def unsupervised(self, train, test):
        # kmeans prediction
        print('\n---- Unsupervised clustering ----\n')

        kmeans = KMeans(n_clusters=2)
        kmeans.fit(train)
        computed = kmeans.predict(test)
        real = [1 if self.test_output[i] == 'negative' else 0 for i in range(len(self.test_output))]
        computed_labels = ['negative' if computed[i] == 1 else 'positive' for i in range(len(computed))]

        print("Results:")
        print("\t Computed\tReal    \tSentence")
        for i in range(len(self.test_input)):
            print('\t', computed_labels[i], ' ', self.test_output[i], ' ', self.test_input[i])

        accuracy = accuracy_score(real, computed)
        self.accuracies.append(accuracy)
        # print("Accuracy:\n\t", accuracy_score(real, computed))

    def supervised(self, train, test):
        # neural network prediction
        print('\n---- Supervised clustering ----\n')

        classifier = neural_network.MLPClassifier(max_iter=425)
        classifier.fit(train, self.train_output)
        computed = classifier.predict(test)

        print("Results:")
        print("\t Computed\tReal    \tSentence")
        for i in range(len(self.test_input)):
            print('\t', computed[i], ' ', self.test_output[i], ' ', self.test_input[i])

        accuracy = accuracy_score(self.test_output, computed)
        self.accuracies.append(accuracy)
        # print("Accuracy:\n\t", accuracy_score(self.test_output, computed))

    def hybrid(self, train, test):
        # hybrid kmeans prediction
        print('\n---- Hybrid clustering ----\n')

        # build positive/negative bag of words
        positive, negative = [], []
        for i in range(len(self.train_input)):
            if self.train_output[i] == 'positive':
                positive.append(self.train_input[i])
            else:
                negative.append(self.train_input[i])

        vectorizer_positive = CountVectorizer(max_features=30)
        vectorizer_positive.fit_transform(positive)
        positive_dict = vectorizer_positive.get_feature_names()
        print('Positive bag of words:\n\t', vectorizer_positive.get_feature_names()[:20], '..')

        vectorizer_negative = CountVectorizer(max_features=30)
        vectorizer_negative.fit_transform(negative)
        negative_dict = vectorizer_negative.get_feature_names()
        print('Negative bag of words:\n\t', vectorizer_negative.get_feature_names()[:20], '..')

        # transform input data based on specific positive/negative features
        k = 2
        for i in range(len(self.train_input)):
            count_pos, count_neg = 0, 0
            words = self.train_input[i].split(' ')
            for word in words:
                if word in positive_dict:
                    count_pos += k
                if word in negative_dict:
                    count_neg += k
            train[i].append(count_pos)
            train[i].append(count_neg)
        for i in range(len(self.test_input)):
            count_pos, count_neg = 0, 0
            words = self.test_input[i].split(' ')
            for word in words:
                if word in positive_dict:
                    count_pos += k
                if word in negative_dict:
                    count_neg += k
            test[i].append(count_pos)
            test[i].append(count_neg)

        # kmeans prediction
        kmeans = KMeans(n_clusters=2)
        kmeans.fit(train)
        computed = kmeans.predict(train)
        real = [0 if self.train_output[i] == 'negative' else 1 for i in range(len(self.train_output))]
        computed_labels = ['negative' if computed[i] == 0 else 'positive' for i in range(len(computed))]

        print("Results:")
        print("\t Computed\tReal    \tSentence")
        for i in range(len(self.train_input)):
            print('\t', computed_labels[i], ' ', self.train_output[i], ' ', self.train_input[i])
        accuracy = accuracy_score(real, computed)
        self.accuracies.append(accuracy)
        print("Accuracy:\n\t", accuracy_score(real, computed))

        # kmeans = KMeans(n_clusters=2)
        # kmeans.fit(train)
        # computed = kmeans.predict(test)
        # real = [0 if self.test_output[i] == 'negative' else 1 for i in range(len(self.test_output))]
        # computed_labels = ['negative' if computed[i] == 0 else 'positive' for i in range(len(computed))]
        #
        # print("Results:")
        # print("\t Computed\tReal    \tSentence")
        # for i in range(len(self.test_input)):
        #     print('\t', computed_labels[i], ' ', self.test_output[i], ' ', self.test_input[i])
        # print("Accuracy:\n\t", accuracy_score(real, computed))

    def accuracy(self):
        print('\n---- Accuracy ----\n')
        print('\tUnsupervised:', self.accuracies[0])
        print('\tSupervised:', self.accuracies[1])
        print('\tHybrid:', self.accuracies[2])
