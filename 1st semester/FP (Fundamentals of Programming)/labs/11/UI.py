'''
Created on Jan 12, 2019

@author: Adrian
'''
from Validator import ValidatorError
from Repository import RepositoryError


class Console():
    
    def __init__(self, controller):
        
        self.__controller = controller
        
    
    def run(self):
        
        self.__read()
        
        print("\nThe subsets of at least two elemets which are in increasing order and have at least one common digit for any two consecutive elements are:")
        
        print("\nIterative:")
        data = self.__controller.subsetsPropertyIterative()
        if len(data) == 0:
            print("There are no such subsets.")
        else:
            for index in range(len(data)):
                print(str(index+1) + ". " + str( [ integer.value for integer in data[index] ] ) )
        
        print("\nRecursive:")
        data = self.__controller.subsetsPropertyRecursive()
        if len(data) == 0:
            print("There are no such subsets.")
        else:
            for index in range(len(data)):
                print(str(index+1) + ". " + str( [ integer.value for integer in data[index] ] ) )
    
    
    def __read(self):
        
        print("Input as many distinct integers as you want; when you finished, type 'stop'.")
        
        while True:
            
            answer = input("\t>> ")
            
            if answer == "stop":
                break
            
            try:
                self.__controller.create(answer)
            except (ValidatorError, RepositoryError) as message:
                print("\t\t" + str(message))
        
        
        