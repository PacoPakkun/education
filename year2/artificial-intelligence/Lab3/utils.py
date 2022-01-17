import matplotlib.pyplot as plot
import networkx as nx
import numpy as np
import collections

from random import choice


def draw_network(g, color=None):
    matrix = np.matrix(g.matrix)
    graph = nx.from_numpy_matrix(matrix)
    pos = nx.spring_layout(graph)
    plot.figure(figsize=(4, 4))
    if color is None:
        nx.draw(graph, with_labels=True)
    else:
        nx.draw_networkx_nodes(graph, pos, node_size=200, cmap=plot.cm.RdYlBu, node_color=color)
        nx.draw_networkx_edges(graph, pos, alpha=0.3)
    plot.show()


def modularity(communities, graph):
    n = graph.n
    m = graph.m
    matrix = graph.matrix
    grad = graph.grad
    M = 2 * m
    Q = 0.0
    for i in range(n):
        for j in range(n):
            if communities[i] == communities[j]:
                Q += (matrix[i][j] - grad[i] * grad[j] / M)
    return Q / M


def random_neighbour(node, matrix):
    neighbours = []
    l = matrix[node]
    for i in range(len(l)):
        if l[i] == 1:
            neighbours.append(i + 1)
    selected = choice(neighbours)
    return selected


def node_communities(chromosome):
    def dfs(node):
        communities[node - 1] = count
        visited.add(node)
        for n in neighbour[node]:
            if n not in visited:
                dfs(n)

    edges = []
    for i in range(len(chromosome)):
        edges.append([i, chromosome[i]])
    neighbour = collections.defaultdict(list)
    for e in edges:
        u, v = e[0], e[1]
        neighbour[u].append(v)
        neighbour[v].append(u)
    visited = set()
    count = 0
    communities = [0 for _ in range(len(chromosome))]
    for i in range(1, len(chromosome) + 1):
        if i not in visited:
            count += 1
            dfs(i)
    return communities


def get_communities(nodes):
    result = {}
    for i in range(0, len(nodes)):
        if nodes[i] in result:
            result[nodes[i]].append(i)
        else:
            result[nodes[i]] = [i]
    return result