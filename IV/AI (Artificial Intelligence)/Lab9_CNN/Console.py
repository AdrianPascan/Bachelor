from Lab9.Controller import Controller
from Lab9.MyException import MyException
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

    def __cnnImageClassification(self):
        trainCountStr = input("No. of train examples (min. 1, max. 60000):\n\t(60000 by default)\n\t\t>> ")
        if not trainCountStr:
            trainCountStr = "60000"

        testCountStr = input("No. of test examples (min. 1, max. 10000):\n\t(10000 by default)\n\t\t>> ")
        if not testCountStr:
            testCountStr = "10000"

        noOfEpochsStr = input("No. of epochs:\n\t(3 by default)\n\t\t>> ")
        if not noOfEpochsStr:
            noOfEpochsStr = "3"

        learningRateStr = input("Learning rate (between 0 and 1):\n\t(0.005 by default)\n\t\t>> ")
        if not learningRateStr:
            learningRateStr = "0.005"

        self.__controller.runWithValidation(trainCountStr, testCountStr, noOfEpochsStr, learningRateStr)

    @staticmethod
    def __getMenu():
        return "__________________________________________________________________\n" \
               " 1. CNN: Image Classification using MNIST Database and TensorFlow\n" \
               " 0. EXIT"

    def __getCommands(self):
        return {"0": self.__exitCommand,
                "1": self.__cnnImageClassification}
