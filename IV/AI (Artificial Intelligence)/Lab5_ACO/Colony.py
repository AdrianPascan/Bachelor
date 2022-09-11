from Lab5.Ant import Ant
from Lab5.Position import Position
from random import randint
from texttable import Texttable


class Colony:
    def __init__(self, squareMatrixOrder, noOfAnts):
        self.__n = squareMatrixOrder
        self.__valueMatrix = [[[0, 0] for column in range(self.__n)] for row in range(self.__n)]

        self.__ants = [Ant(squareMatrixOrder) for _ in range(noOfAnts)]
        for ant in self.__ants:
            position = ant.lastPosition
            self.__valueMatrix[position.row][position.column] = [randint(1, self.__n),
                                                                 randint(1, self.__n)]

        self.__traceMatrix = [[ dict() for column in range(self.__n) ] for row in range(self.__n)]
        variations = [Position(-1, -1), Position(-1, 0), Position(-1, 1), Position(0, 1), Position(1, 1),
                      Position(1, 0), Position(1, -1), Position(0, -1)]
        for row in range(self.__n):
            for column in range(self.__n):
                for variation in variations:
                    newRow = row + variation.row
                    newColumn = column + variation.column
                    if 0 <= newRow < self.__n and 0 <= newColumn < self.__n:
                        self.__traceMatrix[row][column] [Position(newRow, newColumn)] = 1

    @property
    def ants(self):
        return self.__ants

    @ants.setter
    def ants(self, newAnts):
        self.__ants = newAnts

    @property
    def valueMatrix(self):
        return self.__valueMatrix

    @valueMatrix.setter
    def valueMatrix(self, newValueMatrix):
        self.__valueMatrix = newValueMatrix

    @property
    def traceMatrix(self):
        return self.__traceMatrix

    @traceMatrix.setter
    def traceMatrix(self, newTraceMatrix):
        self.__traceMatrix = newTraceMatrix

    def __str__(self):
        table = Texttable()
        for row in self.__valueMatrix:
            tableRow = []
            for value in row:
                tableRow.append(str(value[0]) + ", " + str(value[1]))
            table.add_row(tableRow)
        return table.draw()

