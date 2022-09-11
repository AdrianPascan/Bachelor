from random import random, shuffle


class Individual:
    def __init__(self, n):
        self.__first = self.__initialisePiece(n)
        self.__second = self.__initialisePiece(n)
        shuffle(self.__first)
        shuffle(self.__second)

    @property
    def first(self):
        return self.__first

    @first.setter
    def first(self, newFirst):
        self.__first = newFirst

    @property
    def second(self):
        return self.__second

    @second.setter
    def second(self, newSecond):
        self.__second = newSecond

    def mutate(self, probabilityOfMutation):
        if probabilityOfMutation > random():
            shuffle(self.__first)
            shuffle(self.__second)
        return self

    def __initialisePiece(self, n):
        piece = []
        piece.extend(range(1, n + 1))
        return piece

    def __len__(self):
        return len(self.__first)

    def fitness(self):
        fitness = 0

        firstHorizontalPermutation = list(range(1, len(self.__first) + 1))
        try:
            for current in self.__first:
                firstHorizontalPermutation.remove(current)
        except ValueError:
            fitness += 1

        secondHorizontalPermutation = list(range(1, len(self.__second) + 1))
        try:
            for current in self.__second:
                secondHorizontalPermutation.remove(current)
        except ValueError:
            fitness += 1

        return fitness
