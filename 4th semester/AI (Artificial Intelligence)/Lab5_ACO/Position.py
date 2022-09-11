class Position:
    def __init__(self, row, column):
        self.__row = row
        self.__column = column

    @property
    def row(self):
        return self.__row

    @row.setter
    def row(self, newRow):
        self.__row = newRow

    @property
    def column(self):
        return self.__column

    @column.setter
    def column(self, newColumn):
        self.__column = newColumn

    def __eq__(self, other):
        if isinstance(other, Position) and self.__row == other.row and self.__column == other.column:
            return True
        return False

    def __hash__(self):
        return hash(self.__row + self.__column)
