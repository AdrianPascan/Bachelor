from texttable import Texttable


class Board():
    
    
    def __init__(self):
        
        self.matrix = [ [" " for index in range(15)] for index in range(15)] 
    
    
    def __str__(self):
        
        table = Texttable()
        
        header = [""]
        header.extend(range(15))
        table.header(header)
        
        for numberOfRow in range(15):
            row = [ str(numberOfRow) + ")"]
            row.extend(self.matrix[numberOfRow])
            table.add_row(row)
            
        return table.draw()
            