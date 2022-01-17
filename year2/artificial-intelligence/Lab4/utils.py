from random import uniform
from math import sqrt


def fitness(chromosome, matrix):
    res = 0
    for i in range(len(chromosome) - 1):
        res += matrix[chromosome[i] - 1][chromosome[i + 1] - 1]
    return res


def generate_new_value(lim1, lim2):
    return uniform(lim1, lim2)

def distanceBetweenTwoNodes(node1, node2):
    return sqrt((node1[0] - node2[0]) ** 2 + (node1[1] - node2[1]) ** 2)
