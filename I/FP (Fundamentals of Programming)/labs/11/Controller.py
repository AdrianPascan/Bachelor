'''
Created on Jan 12, 2019

@author: Adrian
'''
from Domain import Integer


class Controller():
    
    def __init__(self, validator, repository):
        
        self.__validator = validator
        self.__repository = repository
        
    
    def create(self, string):
        
        self.__validator.validate(string)
        integer = Integer(int(string))
        self.__repository.store(integer)
        
        
    def subsetsPropertyIterative(self):
        
        data = sorted(self.__repository.data, key = lambda integer: integer.value) 
        subsets = []
        
        for _first in range(len(data)-1):
            
            subset = [data[_first]]
            
            for _next in range(_first+1, len(data)):
                
                if self.__commonDigit(subset[-1], data[_next]):
                    
                    subset.append(data[_next])
                    subsets.append(subset[:])
                    
        return subsets
            
    
    
    def subsetsPropertyRecursive(self):
        
        data = sorted(self.__repository.data, key = lambda integer: integer.value) 
        subsets = []
        self.__recursive(data, 0, subsets)
        
        return subsets
          
        
    def __recursive(self, data, _first, subsets):
        
        if _first >= len(data)-1:
            return
        
        subset = [data[_first]]
        
        for _next in range(_first+1, len(data)):
            
            if self.__commonDigit(subset[-1], data[_next]):
                    
                    subset.append(data[_next])
                    subsets.append(subset[:])
        
        return self.__recursive(data, _first+1, subsets)    
    
    
    def __commonDigit(self, integer1, integer2):
        
        for digit in range(10):
            if integer1.digits[digit] and integer2.digits[digit]:
                return True
            
        return False
        
        