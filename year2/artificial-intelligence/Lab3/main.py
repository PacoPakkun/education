from graph import Graph
from service import Service
from utils import modularity


def main():
    graph = Graph("bin/word.gml", "bin/out.txt")
    srv = Service(graph)
    params = {'fitness': modularity,
              'pop_size': 100,
              'no_gens': 100,
              'min': 0,
              'max': graph.n,
              'size': graph.n}
    srv.run(params)


main()
