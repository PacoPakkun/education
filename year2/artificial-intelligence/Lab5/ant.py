from random import randint


class Ant:
    def __init__(self, n):
        self.solution = [randint(1, n)]
        self.distance = 0