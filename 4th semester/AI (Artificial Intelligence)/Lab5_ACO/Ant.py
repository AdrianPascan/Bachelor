from random import randint
from Lab5.Position import Position


class Ant:
    def __init__(self, squareMatrixOrder):
        self.__n = squareMatrixOrder
        self.__path = [Position(randint(0, self.__n - 1),
                                randint(0, self.__n - 1))]

    @property
    def path(self):
        return self.__path

    @path.setter
    def path(self, newPath):
        self.__path = newPath

    @property
    def pathLength(self):
        return len(self.__path)

    @property
    def lastPosition(self):
        return self.__path[-1]
