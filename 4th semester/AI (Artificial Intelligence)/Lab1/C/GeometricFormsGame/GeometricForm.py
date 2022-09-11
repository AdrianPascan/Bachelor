class GeometricForm:

    def __init__(self, symbol, length, width, cells):
        self.__symbol = symbol
        self.__length = length
        self.__width = width
        self.__cells = cells

    def getSymbol(self):
        return self.__symbol

    def getLength(self):
        return self.__length

    def getWidth(self):
        return self.__width

    def getCells(self):
        return self.__cells