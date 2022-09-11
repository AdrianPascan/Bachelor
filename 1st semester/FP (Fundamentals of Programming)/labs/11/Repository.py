'''
Created on Jan 12, 2019

@author: Adrian
'''


class Repository():
    
    def __init__(self):
        
        self.__data = []
        
    
    @property
    def data(self):
        return self.__data[:]
    
    
    def store(self, integer):
        
        if integer.value in [integer.value for integer in self.__data]:
            raise RepositoryError(str(integer) + " already exists: numbers must be distinct.")
        
        self.__data.append(integer)
        
        
class RepositoryError(Exception):
    pass