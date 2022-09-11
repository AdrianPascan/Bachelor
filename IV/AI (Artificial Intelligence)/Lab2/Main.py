from Lab2.Console import Console
from Lab2.Controller import Controller


if __name__ == '__main__':
    controller = Controller()
    console = Console(controller)
    console.start()