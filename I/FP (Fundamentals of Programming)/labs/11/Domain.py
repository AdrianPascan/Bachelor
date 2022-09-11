'''
Created on Jan 12, 2019

@author: Adrian
'''


class Integer():
    
    def __init__(self, number):
        
        self.__value = number
        self.__digits = self.__digitsOfNumber(number)
    
    
    @property
    def value(self):
        return self.__value
    
    @value.setter
    def value(self, number):
        self.__value = number
    
    
    @property
    def digits(self):
        return self.__digits
    
    
    def __digitsOfNumber(self, number):
        
        digits = {0: 0, 1: 0, 2: 0, 3: 0, 4: 0, 5: 0, 6: 0, 7: 0, 8: 0, 9: 0}
        
        if number == 0:
            digits[0] = 1
        else:
            while number > 0:
                digits[number % 10] += 1
                number //= 10
                
        return digits
     
        
    def __str__(self):
        
        return str(self.__value)