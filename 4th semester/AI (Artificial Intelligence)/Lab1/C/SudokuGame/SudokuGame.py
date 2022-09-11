from texttable import Texttable
from math import sqrt, floor
from Lab1.C.SudokuGame.SudokuSquare import SudokuSquare
from Lab1.C.MyException import MyException


class SudokuGame:

    def __init__(self, n, noOfAttempts):
        self.__n = n
        self.__nSquare = int(sqrt(n))
        self.__board = self.__initialiseBoard(n)
        self.__noOfAttempts = noOfAttempts

    def play(self):
        try:
            for attempt in range(self.__noOfAttempts):
                print("Attempt no. " + str(attempt + 1) + ":\n")
                self.__round()
                input("Press any key to continue..")
        except MyException as exception:
            print(str(self) + "\n" +
                  str(exception) + "(" + str(attempt + 1) + " st/nd/th attempt)")


    def __round(self):
        for row in self.__board:
            for square in row:
                square.shuffleSquare()

        try:
            for row in range(self.__n):
                boardRow = self.getRow(row)
                for number in range(1, self.__n + 1):
                    boardRow.index(number)
            for column in range(self.__n):
                boardColumn = self.getColumn(column)
                for number in range(1, self.__n + 1):
                    boardColumn.index(number)


            raise MyException("Solution found!")
        except ValueError:
            print(str(self) + "\n It is not a solution.")

    def __str__(self):
        table = Texttable()

        header = [""]
        header.extend(range(self.__n))
        table.header(header)

        for noOfRow in range(self.__n):
            row = [str(noOfRow) + ")"]
            row.extend(self.getRow(noOfRow))
            table.add_row(row)

        return table.draw()

    def getRow(self, row):
        boardRow = []
        noOfBoardRow = floor(row / self.__nSquare)
        noOfSquareRow = row - self.__nSquare * noOfBoardRow
        for square in self.__board[noOfBoardRow]:
            boardRow.extend(square.getRow(noOfSquareRow))
        return boardRow

    def getColumn(self, column):
        boardColumn = []
        noOfBoardColumn = floor(column / self.__nSquare)
        noOfSquareColumn = column - self.__nSquare * noOfBoardColumn
        for noOfBoardRow in range(self.__nSquare):
            square = self.__board[noOfBoardRow][noOfBoardColumn]
            boardColumn.extend(square.getColumn(noOfSquareColumn))
        return boardColumn

    def __initialiseBoard(self, n):
        board = []
        nSquare = int(sqrt(n))
        for row in range(nSquare):
            board.append([])
            for column in range(nSquare):
                board[row].append(SudokuSquare(nSquare))
        return board
