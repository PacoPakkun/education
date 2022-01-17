from ga import GA


class Service:
    def __init__(self, graph):
        self.graph = graph

    def run(self, params):
        ga = GA(params, self.graph)
        ga.initialisation()
        ga.evaluation()

        print("Population: " + str(ga.population.__repr__()))

        for generation in range(params['no_gens']):
            ga.next_generation_steady_state()

            best_chromo = ga.best_chromosome()

            print('Generation ' + str(generation) + ': ' + str(
                best_chromo.rep))
            print('   fitness = ' + str(best_chromo.fitness))

        best_chromo = ga.best_chromosome()
        print('Best solution: ' + str(best_chromo.rep))
        print('   fitness = ' + str(best_chromo.fitness))
