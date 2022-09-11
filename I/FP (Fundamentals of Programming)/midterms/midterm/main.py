from UI import Console
from Repository import Repository
from Controller import Controller

repository = Repository()
controller = Controller(repository)
console = Console(controller)

console.start()