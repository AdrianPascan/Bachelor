from Lab8.Repository import Repository
from Lab8.Controller import Controller
from Lab8.Console import Console


if __name__ == '__main__':
    repository = Repository()
    controller = Controller(repository)
    console = Console(controller)

    console.start()

