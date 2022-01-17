import networkx as nx


class Graph:
    def __init__(self, input, output):
        self.matrix = []
        self.n = 0
        self.m = 0
        self.grad = []
        self.input = input
        self.output = output
        self.load()

    def load(self):
        graph = nx.read_gml(self.input, label='id')
        self.n = len(graph.nodes)
        self.m = len(graph.edges)
        for i in range(self.n):
            line = []
            for j in range(self.n):
                line.append(0)
            self.matrix.append(line)
        for edge in graph.edges:
            self.matrix[edge[0] - 1][edge[1] - 1] = 1
            self.matrix[edge[1] - 1][edge[0] - 1] = 1
        for i in range(self.n):
            if i in graph.adj:
                self.grad.append(len(graph.adj[i]))
            else:
                self.grad.append(0)

    def save(self, communities, best_fitness, best_community_no):
        file = open(self.output, "w")
        result = str(best_community_no[-1]) + "\n"
        for node in communities.values():
            for value in node:
                result += str(value) + " "
            result += '\n'
        for fitness in best_fitness:
            result += str(fitness) + " "
        result += "\n"
        for community_no in best_community_no:
            result += str(community_no) + " "
        file.writelines(result)
        file.close()
