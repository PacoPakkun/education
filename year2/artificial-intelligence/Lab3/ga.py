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
            chromosome.fitness = self.params['fitness'](chromosome.rep, self.graph)

    def best_chromosome(self):
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

    def next_generation_elitism(self):
        new_pop = [self.best_chromosome()]
        for _ in range(self.params['pop_size'] - 1):
            p1 = self.population[self.selection()]
            p2 = self.population[self.selection()]
            off = p1.crossover(p2)
            off.mutation(self.graph.matrix)
            new_pop.append(off)
        self.population = new_pop
        self.evaluation()

