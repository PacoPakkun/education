from graph import Graph
from service import Service


def main():
    graph = Graph("data\\easy.txt", "out.txt")
    # graph = Graph("data\\medium.txt", "out.txt")
    # graph = Graph("data\\att48.tsp", "out.txt")
    srv = Service(graph)
    params = {'colSize': 10, 'noIter': 100,
              'alpha': 2, 'beta': 5, 'ro': 0.1,
              'q0': 0.5}
    srv.run(params)


if __name__ == '__main__':
    main()
