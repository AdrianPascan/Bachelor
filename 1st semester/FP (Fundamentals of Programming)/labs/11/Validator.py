'''
Created on Jan 12, 2019

@author: Adrian
'''


class Validator():
    
    def validate(self, string):
                
        try:
            int(string)
        except ValueError:
            raise ValidatorError("Data must be of type <int>.")
        
        
class ValidatorError(Exception):
    pass