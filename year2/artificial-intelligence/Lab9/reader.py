class Reader:
    def __init__(self, file):
        self.file = file

    def read(self):
        f = open(self.file, 'r')
        inputs = []
        output = []

        while True:
            line = f.readline()
            if line:
                array = line.split(",")
                inputs.append([float(array[0]), float(array[1]), float(array[2]), float(array[3])])
                output.append(array[4].split('\n')[0])
            else:
                break

        f.close();
        return inputs, output
