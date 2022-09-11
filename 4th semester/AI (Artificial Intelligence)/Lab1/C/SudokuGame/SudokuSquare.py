import random
from texttable import Texttable


class SudokuSquare:

    def __init__(self, n):
        self.__n = n
        self.__square = self.__initialiseSquare(n)

    def shuffleSquare(self):
        random.shuffle(self.__square)

    def getRow(self, row):
        return self.__square[(row * self.__n): ((row + 1) * self.__n)]

    def getColumn(self, column):
        columnList = []
        for row in range(self.__n):
            columnList.append(self.__square[row * self.__n + column])
        return columnList

    def __initialiseSquare(self, n):
        square = []
        square.extend(range(1, n**2+1))
        return square

    def __str__(self):
        print(self.__square)

        table = Texttable()

        for noOfRow in range(self.__n):
            row = []
            for noOfColumn in range(self.__n):
                row.append(self.__square[noOfRow * self.__n + noOfColumn])
            table.add_row(row)

        return table.draw()

