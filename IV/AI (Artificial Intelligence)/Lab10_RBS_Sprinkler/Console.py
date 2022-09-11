from Lab10_RBS_Sprinkler.MyException import MyException
import sys


class Console:
    '''
        Run RBS in fuzzy environment with validation
    '''

    def __init__(self, controller):
        self.controller = controller

    def start(self):
        menu = self._getMenu()
        commands = self._getCommands()
        while True:
            try:
                print(menu)
                command = input("\t >>> ").strip(" ")
                commands[command]()
            except MyException as exception:
                print(str(exception))
            except KeyError:
                print("Invalid command..")

    @staticmethod
    def _exitCommand():
        print("Closing application..")
        print("Application has been closed.")
        sys.exit()

    def _rbsFuzzyEnvironment(self):
        stringTemperature = input("Temperature (min. -30, max. 35):\n\t>> ")
        stringHumidity = input("Humidity (min. 0, max. 100):\n\t>> ")
        stringInputs = {'temperature': stringTemperature,
                        'humidity': stringHumidity}
        time = self.controller.computeWithValidation(stringInputs)

        print("For temperature={} and humidity={}, the estimated operating time is {:.2f}"
              .format(stringTemperature, stringHumidity, time))

    @staticmethod
    def _getMenu():
        return "_____________________________\n" \
               " 1. RBS in Fuzzy Environment\n" \
               " 0. EXIT"

    def _getCommands(self):
        return {"0": self._exitCommand,
                "1": self._rbsFuzzyEnvironment}
