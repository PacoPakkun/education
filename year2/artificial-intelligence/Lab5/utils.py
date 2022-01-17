from math import sqrt


def distance_between_two_nodes(node1, node2):
    return sqrt((node1[0] - node2[0]) ** 2 + (node1[1] - node2[1]) ** 2)
