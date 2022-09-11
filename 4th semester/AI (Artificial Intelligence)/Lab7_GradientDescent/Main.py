from Lab7.Repository import Repository
from Lab7.Controller import Controller
from Lab7.Console import Console


if __name__ == '__main__':
    repository = Repository()
    controller = Controller(repository)
    console = Console(controller)

    console.start()
