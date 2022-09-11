from Lab6.Repository import Repository
from Lab6.Controller import Controller
from Lab6.Console import Console

if __name__ == '__main__':
    repository = Repository()
    controller = Controller(repository)
    console = Console(controller)

    console.start()