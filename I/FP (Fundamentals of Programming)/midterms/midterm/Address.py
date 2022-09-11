class Address(object):
    
    
    def __init__(self, ID, name, x, y):
        # Constructor for Address class    
        
        self.__ID = ID
        self.__name = name
        self.__x = x
        self.__y = y
        
    def __str__(self):
        return "address {} -  name: {}, position: x={}, y={}".format(self.__ID, self.__name, self.__x, self.__y)
        
    
    @property
    def x(self):
        return self.__x
    
    
    @property
    def y(self):
        return self.__y
    

