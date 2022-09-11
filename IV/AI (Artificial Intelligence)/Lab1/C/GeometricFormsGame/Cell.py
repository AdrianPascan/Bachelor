class Cell:

    def __init__(self, rowIdent, columnIdent):
        self.__rowIdent = rowIdent
        self.__columnIdent = columnIdent

    def getRowIdent(self):
        return self.__rowIdent

    def getColumnIdent(self):
        return self.__columnIdent