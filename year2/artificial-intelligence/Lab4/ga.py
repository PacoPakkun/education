from random import randint
from chromosome import Chromosome


class GA:
    def __init__(self, params, graph):
        self.params = params
        self.graph = graph
        self.population = []

    def initialisation(self):
        for _ in range(0, self.params['pop_size']):
            chromosome = Chromosome(self.params, self.graph)
            chromosome.random()
            self.population.append(chromosome)

    def evaluation(self):
        for chromosome in self.population:
            chromosome.fitness = self.params['fitness'](chromosome.rep, self.graph.matrix)

    def best_chromosome(self):
        best = self.population[0]
        for chromosome in self.population:
            if chromosome.fitness > best.fitness:
                best = chromosome
        return best

    def worst_chromosome(self):
        best = self.population[0]
        for chromosome in self.population:
            if chromosome.fitness > best.fitness:
                best = chromosome
        return best

    def selection(self):
        pos1 = randint(0, self.params['pop_size'] - 1)
        pos2 = randint(0, self.params['pop_size'] - 1)
        if self.population[pos1].fitness > self.population[pos2].fitness:
            return pos1
        else:
            return pos2

    def next_generation_steady_state(self):
        for _ in range(self.params['pop_size']):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.params['prob'])
            off.fitness = self.params['fitness'](off.rep, self.graph.matrix)
            worst = self.worst_chromosome()
            if off.fitness < worst.fitness:
                for i in range(self.params['pop_size']):
                    if self.population[i] == worst:
                        self.population[i] = off
                        break
