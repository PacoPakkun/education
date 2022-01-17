import csv


class Reader:
    def __init__(self, file):
        self.file = file

    def read(self, inputs, outputs):
        data = []
        labels = []
        with open(self.file) as csv_file:
            reader = csv.reader(csv_file, delimiter=',')
            count = 0
            for row in reader:
                if count == 0:
                    labels = row
                else:
                    data.append(row)
                count += 1
        var = []
        for label in inputs:
            var.append(labels.index(label))
        inputs = [[float(data[i][j]) for j in var] for i in range(len(data))]
        var = []
        for label in outputs:
            var.append(labels.index(label))
        outputs = [[float(data[i][j]) for j in var] for i in range(len(data))]

        return inputs, outputs
