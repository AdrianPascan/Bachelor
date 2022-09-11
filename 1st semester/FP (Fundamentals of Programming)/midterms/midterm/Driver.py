class Driver():
    
    
    def __init__(self, name, x, y):
    # Constructor for Driver class    
    
        self.__name = name
        self.__x = x
        self.__y = y
        
    
    def __str__(self):
        return "driver: {}, position: x={}, y={}".format(self.__name, self.__x, self.__y)

    
    @property
    def x(self):
        return self.__x
    
    
    @property
    def y(self):
        return self.__y