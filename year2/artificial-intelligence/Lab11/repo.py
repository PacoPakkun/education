import csv


class Reader:
    def __init__(self):
        self.file_iris = 'data/iris.txt'
        self.file_emotions = 'data/reviews.csv'

    def read_iris(self):
        f = open(self.file_iris, 'r')
        inputs = []
        output = []

        while True:
            line = f.readline()
            if line:
                array = line.split(',')
                inputs.append([float(array[0]), float(array[1]), float(array[2]), float(array[3])])
                output.append(array[4].split('\n')[0])
            else:
                break

        f.close()
        return inputs, output

    def read_text(self):
        data = []

        with open(self.file_emotions) as csv_file:
            csv_reader = csv.reader(csv_file, delimiter=',')
            for row in csv_reader:
                data.append(row)

        data = data[1:]
        inputs = [data[i][0] for i in range(len(data))]
        output = [data[i][1] for i in range(len(data))]
        return inputs, output
