import matplotlib.pyplot as plot

from ga import GA
from utils import draw_network, node_communities, get_communities


class Service:
    def __init__(self, graph):
        self.graph = graph

    def run(self, params):
        draw_network(self.graph)

        ga = GA(params, self.graph)
        ga.initialisation()
        ga.evaluation()

        best_fitness = []
        best_community_no = []
        best_chromosome = ga.best_chromosome()

        for generation in range(params['no_gens']):
            ga.next_generation_elitism()
            best_chromosome = ga.best_chromosome()
            communities = node_communities(best_chromosome.rep)
            best_community_no.append(max(communities))
            best_fitness.append(best_chromosome.fitness)

            print('Best solution in generation no ' + str(generation) + ' has: communities: ' + str(
                best_community_no[-1]) + ', fitness: f(x) = ' + str(
                best_chromosome.fitness) + ', best chromosome is: x = ' + str(
                best_chromosome.rep))

        print("\nFinal result:")
        print('Generation no ' + str(params['no_gens']) + ' has: communities: ' + str(
            best_community_no[-1]) + ', fitness: f(x) = ' + str(
            best_chromosome.fitness) + ', best chromosome is: x = ' + str(
            best_chromosome.rep))

        communities = node_communities(best_chromosome.rep)
        print("Community number per node where position is node")
        print(communities)
        print("Communities:")
        for commune in get_communities(communities).values():
            print(commune)
        print("Number of communities per generation:")
        print(best_community_no)
        print("Fitness function per generation:")
        print(best_fitness)

        self.graph.save(get_communities(communities), best_fitness, best_community_no)

        plot.ylabel('Fitness')
        plot.plot(best_fitness)
        plot.xlabel('Generation')
        plot.show()

        draw_network(self.graph, color=best_chromosome.rep)
