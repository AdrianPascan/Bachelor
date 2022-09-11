from Lab5.Service import Service
from Lab5.MyException import MyException
import sys


class ConsoleUI:
    def __init__(self):
        pass

    def start(self):
        menu = self.__getMenu()
        commands = self.__getCommands()
        while True:
            try:
                print(menu)
                command = input("\t >>> ").strip(" ")
                commands[command]()
            except MyException as exception:
                print(str(exception))
            except KeyError:
                print("Invalid command..")

    def __exitCommand(self):
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __acoCommand(self):
        squareMatrixOrder = input("Square Matrix Order: >> ")
        noOfColonies = input("No. of Colonies: >> ")
        noOfAnts = input("No. of Ants: >> ")
        alpha = input("Alpha (default = 1.9): >> ")
        beta = input("Beta (default = 0.9): >> ")
        evaporationCoefficient = input("Evaporation Coefficient (default = 0.05): >> ")

        service = Service(squareMatrixOrder, noOfColonies, noOfAnts, alpha, beta, evaporationCoefficient)
        bestColonyFitness, bestColony = service.run()
        print("BEST COLONY:")
        print("fitness:", bestColonyFitness)
        print("colony:")
        print(str(bestColony))

    def __getMenu(self):
        return "1. Euler's Square using ACO algorithm\n" \
               "0. EXIT"

    def __getCommands(self):
        return {"0": self.__exitCommand,
                "1": self.__acoCommand}