import sys
import matplotlib.pyplot as plt
import random
import math
from Lab1.B.MyException import MyException


class Console:

    def __init__(self):
        self.__menu = self.__getMenu()
        self.__commands = self.__initialiseCommands()

    def start(self):
        while (True):
            print(self.__menu)
            command = self.__readCommand()
            try:
                self.__commands[command]()
            except KeyError as error:
                print("Invalid command.")

    def __uniformDistribution(self):
        try:
            minimum = int(input("Min. Value: >> "))
            maximum = int(input("Max. Value: >> "))
            if minimum == maximum:
                raise MyException("Numbers cannot be equal.")
            if minimum > maximum:
                (maximum, minimum) = (minimum, maximum)

            random.seed(1)
            numbersToBeGenerated = 10
            numbersList = [minimum, maximum]
            numbersGenerated = 0
            while numbersGenerated < numbersToBeGenerated and numbersGenerated < maximum - minimum - 1:
                number = random.randint(minimum, maximum)
                if number not in numbersList:
                    numbersGenerated += 1
                    numbersList.append(number)
            pdfNumbersList = [1 / (maximum - minimum)] * len(numbersList)

            plt.plot(numbersList, pdfNumbersList)
            plt.scatter(numbersList, pdfNumbersList)
            plt.xlabel("x\n(random number)")
            plt.ylabel("f(x)\n(pdf of x)")
            plt.show()
        except MyException as error:
            print(str(error))
        except ValueError:
            print("Invalid integer number.")

    def __standardUniformDistribution(self):
        random.seed(1)

        maximum = 10
        numbersList = [-maximum, maximum]
        numbersGenerated = 0
        numbersToBeGenerated = 15
        while numbersGenerated < numbersToBeGenerated and numbersGenerated < 2 * maximum - 1:
            number = random.randint(-maximum, maximum)
            if number not in numbersList:
                numbersGenerated += 1
                numbersList.append(number)
        numbersList.sort()

        pdfNumbersList = []
        for number in numbersList:
            pdfNumbersList.append(
                (math.e ** (-(number ** 2) / 2)) / math.sqrt(2 * math.pi))  # f(x) = (1/sqrt(2*Pi)) * E ** (- x**2 / 2)

        plt.plot(numbersList, pdfNumbersList)
        plt.scatter(numbersList, pdfNumbersList)
        plt.xlabel("x\n(random number)")
        plt.ylabel("f(x)\n(pdf of x)")
        plt.show()

    def __exitCommand(self):
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __initialiseCommands(self):
        commands = {"0": self.__exitCommand,
                    "1": self.__uniformDistribution,
                    "2": self.__standardUniformDistribution}
        return commands

    def __getMenu(self):
        menu = "DISTRIBUTIONS:\n"
        menu += "1. Uniform Distribution\n"
        menu += "2. Standard Normal Distribution\n"
        menu += "0. EXIT\n"
        return menu

    def __readCommand(self):
        return input("Your Choice: >> ").strip(" ")
