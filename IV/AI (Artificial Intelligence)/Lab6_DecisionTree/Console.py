from Lab6.Controller import Controller
from Lab6.MyException import MyException
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

    def __exitCommand(self):
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def __decisionTreeCommand(self):
        testSizeStr = input("Test size (between 0 and 1):\n\t(0.1 by default)\n\t\t>> ")
        if not testSizeStr:
            testSizeStr = "0.1"
        withShuffleStr = input("With shuffle? (true / false):\n\t(false by default)\n\t\t>> ")
        if not withShuffleStr:
            withShuffleStr = "false"

        self.__controller.run(testSizeStr, withShuffleStr)

    @classmethod
    def __getMenu(self):
        return "1. Decision Tree Algorithm\n" \
               "0. EXIT"

    def __getCommands(self):
        return {"0": self.__exitCommand,
                "1": self.__decisionTreeCommand}
