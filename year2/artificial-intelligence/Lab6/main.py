from loss import Loss
from regression import Regression
from classification import Classification


def main():
    # multi-target regression
    real_outputs = [[1.1, 2, 1, 0.9, 3, 2.6, 2.3, 2], [3, 9.5, 4, 5.95, 6, 7.2, 2, 1]]
    computed_outputs = [[1.05, 1.9, 2, 1.2, 2.96, 2.8, 2, 1.9], [2, 7, 4.5, 6, 3, 8, 3, 1.2]]
    Regression(real_outputs, computed_outputs).run()

    # multi-label classification
    sun, rain, snow = 'Sun', 'Rain', 'Snow'
    labels = [sun, rain, snow]
    real_labels = [sun, rain, sun, snow, snow, sun, rain, rain, sun, snow, snow, snow, sun, rain, sun, sun, sun, sun, rain, snow]
    computed_labels = [sun, snow, sun, snow, snow, sun, rain, snow, sun, snow, snow, sun, rain, rain, rain, sun, sun, sun, rain, snow]
    probabilities = [[0.7, 0.2, 0.1], [0.2, 0.3, 0.5], [0.2, 0.2, 0.6], [0.1, 0.8, 0.1], [0.1, 0.6, 0.3],
                     [0.4, 0.3, 0.3], [0.6, 0.2, 0.2], [0.2, 0.2, 0.6], [0.2, 0.2, 0.6], [0.2, 0.6, 0.2],
                     [0.6, 0.2, 0.2], [0.2, 0.2, 0.6], [0.2, 0.6, 0.2], [0.2, 0.2, 0.6], [0.2, 0.2, 0.6],
                     [0.6, 0.2, 0.2], [0.2, 0.2, 0.6], [0.2, 0.2, 0.6], [0.2, 0.6, 0.2], [0.2, 0.6, 0.2]]
    Classification(labels, real_labels, computed_labels, probabilities).run()

    # loss functions
    Loss().run()


if __name__ == '__main__':
    main()
