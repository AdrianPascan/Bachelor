from Lab8.Controller import Controller
from Lab8.MyException import MyException
import sys


class Console:
    def __init__(self, controller: Controller):
        self.__controller = controller

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
            except OverflowError:
                print("OverflowError: learning rate is too big.")

    @staticmethod
    def __exitCommand():
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __regressionCommand(self):
        testSizeStr = input("Test size (between 0 and 1):\n\t(0.1 by default)\n\t\t>> ")
        if not testSizeStr:
            testSizeStr = "0.1"

        withShuffleStr = input("With shuffle? (true / false):\n\t(false by default)\n\t\t>> ")
        if not withShuffleStr:
            withShuffleStr = "false"

        noOfEpochsStr = input("No. of epochs:\n\t(100 by default)\n\t\t>> ")
        if not noOfEpochsStr:
            noOfEpochsStr = "100"

        learningRateStr = input("Learning rate (between 0 and 1):\n\t(0.00003 by default)\n\t\t>> ")
        if not learningRateStr:
            learningRateStr = "0.00003"

        self.__controller.run(testSizeStr, withShuffleStr, noOfEpochsStr, learningRateStr)

    @staticmethod
    def __getMenu():
        return "1. Regression Algorithm: ANN with One Hidden Layer + Linear Function\n" \
               "0. EXIT"

    def __getCommands(self):
        return {"0": self.__exitCommand,
                "1": self.__regressionCommand}
