from Controller.ControllerException import GameOver
from Repositories.RepositoryException import RepositoryError
from Domain.Square import SquareValidatorError


class Console():
    
    def __init__(self, controller):
        
        self.controller = controller
        
    
    def start(self):
                
        try:
            while True:
                try:
                    print("\n" + self.controller.stringBoard())
                    print("\nWhere do you want to put your next piece? (If you want to quit the current game, type 'exit' for row.)")
                    row = input("\t>> row : ")
                    if row == "exit":
                        print("\nApplication closed...")
                        return
                    column = input("\t>> column : ")
                    self.controller.round(row, column)
                except (RepositoryError, SquareValidatorError) as message:
                    print("\n" + str(message))
        
        except GameOver as message:
            print("\n" + str(message))
            print("\n" + self.controller.stringBoard())
    
    
    
        
    
    