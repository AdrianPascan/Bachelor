'''
Created on Mar 12, 2019

@author: Adrian
'''


# -*- coding: utf-8 -*-
"""
Created on Tue Feb 19 15:48:58 2019

@author: Zsu
"""


class Bag:

    # creates a new, empty Bag
    # O(1)
    def __init__(self):
        
        self.__elements = []
        self.__frequency = []

    # adds a new element to the Bag
    # O(len(self.__elements))
    def add(self, e):
        try:
            index = self.__elements.index(e)
            self.__frequency[index] += 1
        except ValueError:
            self.__elements.append(e)
            self.__frequency.append(1)

    # removes one occurrence of an element from a Bag
    # returns True if an element was actually removed (the Bag contained the element e), or False if nothing was removed
    # O(len(self.__elements))
    def remove(self, e):
        
        try:
            index = self.__elements.index(e)
            
            if self.__frequency[index] <= 1:
                self.__elements.pop(index)
                self.__frequency.pop(index)
            else:
                self.__frequency[index] -= 1
                
            return True
        except ValueError:
            return False

    # searches for an element e in the Bag
    # returns True if the Bag contains the element, False otherwise
    # O(len(self.__elements))
    def search(self, e):
        if e in self.__elements:
            return True
        
        return False

    # counts and returns the number of times the element e appears in the bag
    # O(len(self.__elements))
    def nrOccurrences(self, e):
        
        try:
            index = self.__elements.index(e)
            return self.__frequency[index]
        except ValueError:
            return 0

    # returns the size of the Bag (the number of elements)
    # O(len(self.__frequency)
    def size(self):
        
        length = 0
        
        for frequency in self.__frequency:
            length += frequency
            
        return length

    # returns True if the Bag is empty, False otherwise
    # O(1)
    def isEmpty(self):
        
        if len(self.__elements) == 0:
            return True
        
        return False

    # returns a BagIterator for the Bag
    # O(1)
    def iterator(self):
        return BagIterator(self)


class BagIterator:

    #creates an iterator for the Bag b, set to the first element of the bag, or invalid if the Bag is empty
    # O(1)
    def __init__(self, b):
        self.__bag = b
        
        self.__position = 0
        if self.__bag.isEmpty():
            self.__frequency = 0
        else:
            self.__frequency = self.__bag._Bag__frequency[0]

    # returns True if the iterator is valid
    # O(1)
    def valid(self):
        if self.__position >= len(self.__bag._Bag__elements) or self.__frequency <= 0:
            return False
        
        return True
    

    # returns the current element from the iterator.
    # throws ValueError if the iterator is not valid
    # O(1)
    def getCurrent(self):
        if not self.valid():
            raise ValueError
        
        return self.__bag._Bag__elements[self.__position]

    # moves the iterator to the next element
    #throws ValueError if the iterator is not valid
    # O(1)
    def next(self):
        if not self.valid():
                raise ValueError
            
        if self.__frequency == 1:
            self.__position += 1
            
            if self.__position < len(self.__bag._Bag__elements):
                self.__frequency = self.__bag._Bag__frequency[self.__position]
                
        else:       
            self.__frequency -= 1
                    

    # sets the iterator to the first element from the Bag
    # O(1)
    def first(self):
        self.__position = 0
        if self.__bag.isEmpty():
            self.__frequency = 0
        else:
            self.__frequency = self.__bag._Bag__frequency[0]
        