import sys
from Lab1.C.MyException import MyException
from Lab1.C.SudokuGame.SudokuGame import SudokuGame
from Lab1.C.CryptarithmeticGame.CryptarithmeticGame import CryptarithmeticGame
from Lab1.C.GeometricFormsGame.GeometricFormsGame import GeometricFormsGame
import matplotlib.pyplot as plt
import random
from texttable import Texttable


class Console:

    def __init__(self):
        self.__menu = self.__getMenu()
        self.__commands = self.__initialiseCommands()

    def start(self):
        while (True):
            print(self.__menu)
            try:
                (choice, noOfAttempts) = self.__readCommand()
                if choice == "0":
                    self.__exitCommand()

                self.__commands[choice](noOfAttempts)
            except MyException as error:
                print(str(error))
            except KeyError as error:
                print("Invalid command.")

    def __sudokuGameCommand(self, noOfAttempts):
        sudokuGame = SudokuGame(9, noOfAttempts)
        sudokuGame.play()

    def __cryptarithmeticGame(self, noOfAttempts):
        operand = input("Operand (+, -): >> ")
        if operand != "+" and operand != "-":
            raise MyException("Operand must be one of: +, -")
        firstWord = input("First Word: >> ").strip(" ").upper()
        secondWord = input("Second Word: >> ").strip(" ").upper()
        resultWord = input("Result Word: >> ").strip(" ").upper()

        cryptarithmeticGame = CryptarithmeticGame(noOfAttempts, operand, firstWord, secondWord, resultWord)
        cryptarithmeticGame.play()

    def __geometricFormsGame(self, noOfAttempts):
        geometricFormsGame = GeometricFormsGame(noOfAttempts)
        geometricFormsGame.play()

    def __exitCommand(self):
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __initialiseCommands(self):
        commands = {"0": self.__exitCommand,
                    "1": self.__sudokuGameCommand,
                    "2": self.__cryptarithmeticGame,
                    "3": self.__geometricFormsGame}
        return commands

    def __getMenu(self):
        menu = "GAMES:\n"
        menu += "1. Sudoku Game\n"
        menu += "2. Cryptarithmetic Game\n"
        menu += "3. Geometric Forms Game\n"
        menu += "0. EXIT\n"
        return menu

    def __readCommand(self):
        try:
            choice = input("Your Choice: >> ").strip(" ")
            if choice == "0":
                return "0", None

            noOfAttempts = int(input("No. of Attempts: >> "))
            if noOfAttempts <= 0 or noOfAttempts > 50:
                raise MyException("No. of attempts must be in range [1,50].")
            return choice, noOfAttempts
        except ValueError:
            raise MyException("No. of attempts must be an integer number.")


