from Lab1.C.MyException import MyException
from Lab1.C.GeometricFormsGame.GeometricForm import GeometricForm
from Lab1.C.GeometricFormsGame.Cell import Cell
import random
from texttable import Texttable


class GeometricFormsGame:

    def __init__(self, noOfAttempts):
        self.__noOfAttempts = noOfAttempts
        self.__geometricForms = self.__initiateGeometricForms()
        (self.__numberTable, self.__symbolTable) = ([], [])

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
        (self.__numberTable, self.__symbolTable) = self.__initiateTables()
        self.__placeGeometricForms()
        if self.__isSolution():
            raise MyException("Solution found!")
        print(str(self) + "\n\nIt is not a solution.")

    def __placeGeometricForms(self):
        for geometricForm in self.__geometricForms:
            startRow = random.randint(0, 5 - geometricForm.getLength())
            startColumn = random.randint(0, 6 - geometricForm.getWidth())
            for cell in geometricForm.getCells():
                row = startRow + cell.getRowIdent()
                column = startColumn + cell.getColumnIdent()
                self.__numberTable[row][column] += 1
                self.__symbolTable[row][column] = geometricForm.getSymbol()

    def __isSolution(self):
        for row in self.__numberTable:
            for number in row:
                if number > 1:
                    return False
        return True

    def __str__(self):
        numberTextTable = Texttable()
        for row in self.__numberTable:
            numberTextTable.add_row(row)

        symbolTextTable = Texttable()
        for row in self.__symbolTable:
            symbolTextTable.add_row(row)

        return "Number Table:\n" + numberTextTable.draw() + "\n" + \
                "Symbol Table:\n" + symbolTextTable.draw()

    def __initiateGeometricForms(self):
        geometricForms = []
        geometricForms.append(GeometricForm("A", 1, 4, [Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(0, 3)]))
        geometricForms.append(GeometricForm("B", 2, 3, [Cell(0, 0), Cell(1, 0), Cell(1, 1), Cell(1, 2), Cell(0, 2)]))
        geometricForms.append(GeometricForm("C", 2, 3, [Cell(0, 0), Cell(1, 0), Cell(1, 1), Cell(1, 2)]))
        geometricForms.append(GeometricForm("D", 2, 3, [Cell(0, 0), Cell(0, 1), Cell(0, 2), Cell(1, 2)]))
        geometricForms.append(GeometricForm("E", 2, 3, [Cell(0, 1), Cell(1, 0), Cell(1, 1), Cell(1, 2)]))
        return geometricForms

    def __initiateTables(self):
        numberTable = []
        for row in range(5):
            numberTable.append([0] * 6)

        symbolTable = []
        for row in range(5):
            symbolTable.append([" "] * 6)

        return numberTable, symbolTable
