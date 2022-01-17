from graph import Graph
from service import Service
from utils import fitness


def main():
    graph = Graph("in\\att48.tsp", "out.txt")
    srv = Service(graph)
    params = {'fitness': fitness,
              'pop_size': 150,
              'no_gens': 150,
              'prob': 0.2,
              'size': graph.n}
    srv.run(params)


main()
