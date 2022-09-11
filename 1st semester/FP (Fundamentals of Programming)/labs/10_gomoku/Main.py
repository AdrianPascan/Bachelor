from Repositories.Repository import Repository
from Controller.Controller import Controller
from UI.Console import Console
from Domain.Square import SquareValidator

if __name__ == '__main__':
	validator = SquareValidator()
	repository = Repository()
	controller = Controller(validator, repository)
	console = Console(controller)

	console.start()