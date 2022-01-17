from random import randint
from utils import generate_new_value


class Chromosome:
    def __init__(self, params, graph):
        self.params = params
        self.graph = graph
        self.rep = []
        self.fitness = 0.0

    def random(self):
        res = [1 for _ in range(self.params['size'] + 1)]
        for i in range(1, len(res) - 1):
            ok = 0
            while ok == 0:
                x = randint(2, self.params['size'])
                if x not in res:
                    res[i] = x
                    ok = 1
        self.rep = res

    def crossover(self, other):
        pos1 = randint(1, self.params['size'] - 1)
        pos2 = randint(1, self.params['size'] - 1)
        if pos2 < pos1:
            pos1, pos2 = pos2, pos1
        k = 0
        new_rep = self.rep[pos1: pos2]
        for el in other.rep[pos2:] + other.rep[:pos2]:
            if el not in new_rep:
                if len(new_rep) < self.params['size'] - pos1:
                    new_rep.append(el)
                else:
                    new_rep.insert(k, el)
                    k += 1
        offspring = Chromosome(self.params, self.graph)
        offspring.rep = new_rep
        return offspring

    def mutation(self, probability):
        for i in range(1, self.params['size']):
            p = generate_new_value(0, 1)
            if p < probability:
                pos = randint(1, self.params['size'] - 1)
                self.rep[i], self.rep[pos] = self.rep[pos], self.rep[i]

    def __str__(self):
        return 'Chromosome: ' + str(self.rep) + ' has fitness: ' + str(self.fitness)
