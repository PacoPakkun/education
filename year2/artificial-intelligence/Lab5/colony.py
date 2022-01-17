from random import uniform, randint

from ant import Ant


class Colony:
    def __init__(self, graph, params):
        self.graph = graph
        self.params = params
        self.population = []
        self.pheromone_matrix = [[0.0] * graph.n] * graph.n

    def construct_solution(self):
        # init
        self.population = []
        for _ in range(self.params['colSize']):
            a = Ant(self.graph.n)
            self.population.append(a)

        for i in range(1, self.graph.n):
            for k in range(self.params['colSize']):
                neighbour = self.select_most_attractive_by_wheel(k)

                # adding node to solution
                self.population[k].solution = self.population[k].solution + [neighbour]

                # local pheromone update
                pos1 = self.population[k].solution[-2]
                pos2 = self.population[k].solution[-1]
                self.pheromone_matrix[pos1 - 1][pos2 - 1] = (1.0 - self.params['ro']) * self.pheromone_matrix[pos1 - 1][
                    pos2 - 1] + self.params['ro']
                self.pheromone_matrix[pos2 - 1][pos1 - 1] = (1.0 - self.params['ro']) * self.pheromone_matrix[pos2 - 1][
                    pos1 - 1] + self.params['ro']

        # calculate distance
        for i in range(self.params['colSize']):
            self.population[i].distance = self.calculate_distance(self.population[i].solution)

    def select_most_attractive_by_wheel(self, k):
        # find neighbours
        node = self.population[k].solution[-1]
        neighbours = []
        for i in range(self.graph.n):
            if self.graph.matrix[node - 1][i] != 0 and i + 1 not in self.population[k].solution:
                neighbours.append(i + 1)

        # select by wheel
        node = self.population[k].solution[-1]
        pheromone_sum = 0.0
        for i in neighbours:
            pheromone_sum += (self.pheromone_matrix[i - 1][node - 1] ** self.params['alpha']) * (
                    (1.0 / self.graph.matrix[i - 1][node - 1]) ** self.params['beta'])
        pick = uniform(0, pheromone_sum)
        current = 0.0
        for i in neighbours:
            current += (self.pheromone_matrix[i - 1][node - 1] ** self.params['alpha']) * (
                    (1.0 / self.graph.matrix[i - 1][node - 1]) ** self.params['beta'])
            if current > pick:
                return i
        return neighbours[0]

    def best_solution(self):
        minim = self.population[0].distance
        index = 0
        for i in range(1, self.params['colSize']):
            if self.population[i].distance < minim:
                minim = self.population[i].distance
                index = i
        return self.population[index]

    def calculate_distance(self, solution):
        distance = 0
        for i in range(len(solution) - 1):
            distance += self.graph.matrix[solution[i] - 1][solution[i + 1] - 1]
        distance += self.graph.matrix[solution[-1] - 1][solution[0] - 1]
        return distance

    def dynamic(self):
        for _ in range(2):
            value = randint(1, 50)
            node1 = 0
            node2 = 0
            while node1 == node2:
                node1 = randint(0, self.graph.n - 1)
                node2 = randint(0, self.graph.n - 1)
            self.graph.matrix[node1][node2] = value
            self.graph.matrix[node2][node1] = value

            print("Updated edge [" + str(node1) + "," + str(node2) + "] to " + str(value))
