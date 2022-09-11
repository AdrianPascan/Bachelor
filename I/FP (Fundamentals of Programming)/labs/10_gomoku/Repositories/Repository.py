from Domain.Board import Board
from Repositories.RepositoryException import RepositoryError
from Domain.Square import Square


class Repository():
    
    def __init__(self):
        
        self.board = Board()
        
        
    def store(self, square, symbol):
        
        if self.board.matrix[square.row][square.column] != " ":
            raise RepositoryError("There exist a stone already on this place.")
        
        row = square.row
        column = square.column
        self.board.matrix[row][column] = symbol
    
    
    def getAll(self):
        
        return self.board.matrix[:]
    
    
    def wins(self, square, symbol):
        
        directions = [(0, 1, "horizontal"), (1, 0, "vertical"), (1, 1, "first diagonal"), (1, -1, "second diagonal")]
        
        for direction in directions:            
            for possible in range(5):
                
                border1 = Square(square.row + direction[0] * possible, square.column + direction[1] * possible)
                border2 = Square(border1.row - 4 * direction[0], border1.column - 4 * direction[1])
                                
                if any(index not in range(15) for index in [border1.row, border1.column, border2.row, border2.column]):
                    continue
                
                if len([ Square(border1.row - index * direction[0], border1.column - index * direction[1]) for index in range(5) if self.board.matrix[border1.row - index * direction[0]][border1.column - index * direction[1]] == symbol ]) == 5:
                    return "border 1 = " + str(border2) + " ; " + "border 2 = " + str(border1) + " ; " + direction[2]
                                
        return None
    
    
    def move (self, square, symbol):
        
        directions = [(0, 1), (1, 0), (1, 1), (1, -1)]
        maximumLength = 0
        gap = None
        
        for direction in directions:
            for possible in range(5):
                
                border1 = Square(square.row + direction[0] * possible, square.column + direction[1] * possible)
                border2 = Square(border1.row - 4 * direction[0], border1.column - 4 * direction[1])
                
                if any(index not in range(15) for index in [border1.row, border1.column, border2.row, border2.column]):
                    continue
                
                squares = [ Square(border1.row - index * direction[0], border1.column - index * direction[1]) for index in range(5) ]
                matching = [ current for current in squares if self.board.matrix[current.row][current.column] == symbol ]
                remaining = [ current for current in squares if current not in matching ]
                length = len(matching)
                
                if length > maximumLength and len( [current for current in remaining if self.board.matrix[current.row][current.column] != " "] ) == 0 : # and len(remaining) == len( [current for current in remaining if self.board.matrix[current.row][current.column] != " " ] ):
                    
                    maximumLength = length
                    gap = remaining[0]
        
        return (gap, maximumLength)
    
    
    def firstEmptySquare(self):
        
        for row in range(15):
            for column in range(15):
                if self.board.matrix[row][column] == " ":
                    return Square(row, column)
    
    
    def __str__(self):
        
        return str(self.board)
        
        