from utils import random_neighbour
from random import randint, random


class Chromosome:
    def __init__(self, params, graph):
        self.params = params
        self.graph = graph
        self.rep = [0] * self.params['size']
        self.fitness = 0.0

    def random(self):
        self.rep = [random_neighbour(i, self.graph.matrix) for i in range(self.params['size'])]

    def crossover(self, other):
        mask = [randint(0, 1) for _ in range(self.params['size'])]
        off_rep = []
        for i in range(len(mask)):
            if mask[i] == 1:
                off_rep.append(self.rep[i])
            else:
                off_rep.append(other.rep[i])
        offspring = Chromosome(other.params, other.graph)
        offspring.rep = off_rep
        return offspring

    def mutation(self, matrix):
        pos = randint(0, len(self.rep) - 1)
        self.rep[pos] = random_neighbour(pos, matrix)

    def __str__(self):
        return 'Chromosome: ' + str(self.rep) + ' has fitness: ' + str(self.fitness)
