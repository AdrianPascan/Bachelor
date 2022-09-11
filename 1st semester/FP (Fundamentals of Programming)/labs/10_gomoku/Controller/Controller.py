from Domain.Square import Square
from Controller.ControllerException import GameOver


class Controller():
    
    def __init__(self, validator, repository):
        
        self.validator = validator
        self.repository = repository
        self.moves = 0
        self.computerSquare = None
        
    
    def round (self, row, column):
        
        userSquare = self.create(row, column, "B", True)
        
        message = self.repository.wins(userSquare, "B")
        if message is not None:
            raise GameOver("User wins: " + message + " .")
        
        if self.tie():
            raise GameOver("It's a tie: no more available squares to place stones.")
        
        if self.moves > 1:
            (gap, length) = self.repository.move(self.computerSquare, "W")
            if length == 4:
                self.create(gap.row, gap.column, "W")
                raise GameOver("Computer wins: " + self.repository.wins(self.computerSquare, "W") + " .")
        
        (gap, length) = self.repository.move(userSquare, "B")
        if length >= 3 or self.moves == 1:
            self.computerSquare = gap
        else:
            (self.computerSquare, length) = self.repository.move(self.computerSquare, "W")
        
        self.create(self.computerSquare.row, self.computerSquare.column, "W", False)
                
        message = self.repository.wins(self.computerSquare, "W")
        if message is not None:
            raise GameOver("Computer wins: " + message + " .")
    
    
    def create (self, row, column, symbol, validate = False):
        
        square = Square(row, column)
        if validate:
            self.validator.validate(square)
        self.repository.store(square, symbol)
        self.moves += 1
        return square
    
    
    def tie(self):
        
        if self.moves == 225:
            return True
        
        return False
    
    
    def stringBoard(self):
        
        return str(self.repository)
        
        
                
                
                    
                
    