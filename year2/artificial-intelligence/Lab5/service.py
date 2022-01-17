from ant import Ant
from colony import Colony


class Service:
    def __init__(self, graph):
        self.graph = graph

    def run(self, params):
        colony = Colony(self.graph, params)

        best_solution = Ant(self.graph.n)
        best_solution.distance = 2000000000

        for i in range(params['noIter']):
            colony.construct_solution()
            solution = colony.best_solution()
            print("Generation " + str(i) + ": " + str(solution.solution) + ", cost: " + str(
                solution.distance))
            if solution.distance < best_solution.distance:
                best_solution = solution
            # colony.pheromone_update()
            colony.dynamic()

        print("\nBest ant: " + str(best_solution.solution) + ", cost: " + str(best_solution.distance))
