class Square():
    
    def __init__(self, row, column):
        
        self._row = row
        self._column = column
        
    
    @property
    def row(self):
        return self._row
    
    @row.setter
    def row(self, value):
        self._row = value
    
    
    @property
    def column(self):
        return self._column
    
    @column.setter
    def column(self, value):
        self._column = value
        
    
    def __str__(self):
        
        return "<" + str(self.row) + "," + str(self.column) + ">"
    
    
class SquareValidator():
    
    def validate(self, square):
        
        try:
            square.row = int(square.row)
            square.column = int(square.column)
        except:
            raise SquareValidatorError("Indexes of the square must be integers.")
        
        if any(index not in range(15) for index in [square.row, square.column]):
            raise SquareValidatorError("Not pointing on the board: row or/and column not in range [0,15) .")
               
               
class SquareValidatorError(Exception):
    pass