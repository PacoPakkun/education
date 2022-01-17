from utils import distance_between_two_nodes

class Graph:
    def __init__(self, input, output):
        self.matrix = []
        self.n = 0
        self.m = 0
        self.input = input
        self.output = output
        self.load()
        # self.load_xoy()

    def load(self):
        f = open(self.input, "r")
        self.n = int(f.readline())
        for i in range(self.n):
            self.matrix.append([])
            line = f.readline()
            elems = line.split(",")
            for j in range(self.n):
                self.matrix[-1].append(int(elems[j]))
        f.close()

    def load_xoy(self):
        f = open(self.input, "r")
        n = f.readline()
        while not n[0].isnumeric():
            n = f.readline()
        line = n.split(" ")
        coordinates_list = [[float(line[1]), float(line[2])]]
        n = f.readline()
        while n[0].isnumeric():
            line = n.split(" ")
            coordinates_list.append([float(line[1]), float(line[2])])
            n = f.readline()
        self.n = len(coordinates_list)
        mat = []
        for i in range(self.n):
            line = [0 for _ in range(self.n)]
            for j in range(self.n):
                if i != j:
                    distance = distance_between_two_nodes(coordinates_list[i], coordinates_list[j])
                    line[j] = distance
            mat.append(line)
        self.matrix = mat
