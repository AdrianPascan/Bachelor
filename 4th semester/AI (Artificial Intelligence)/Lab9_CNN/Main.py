from Lab9.Repository import Repository
from Lab9.Controller import Controller
from Lab9.Console import Console

if __name__ == '__main__':
    repository = Repository()
    controller = Controller(repository)
    console = Console(controller)

    console.start()
