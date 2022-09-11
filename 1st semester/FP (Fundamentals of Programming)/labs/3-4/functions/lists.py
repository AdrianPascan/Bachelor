from functions.complex_number import *
from functions.comparison import *

def listToString (listNumbers):
    # Converts a list of complex numbers into a string.
    if len(listNumbers) == 0:
        return 'Void list!'
    string = ''
    for number in listNumbers:
        string += str(complexNumberToString(number)) + ' '
    return string
    
def addNumber (listNumbers, number):
    # Adds a number at the end of a list of complex numbers.
    listNumbers.append(number)
    
def insertNumberAtPosition (listNumbers, number, position):
    # Inserts a number at a given position in a list of complex numbers.
    listNumbers.insert(position, number)

def removePosition (listNumbers, position):
    # Removes the number at a given position of a list of complex numbers.
    del listNumbers[position]

def removeBetweenPositions (listNumbers, startPositionition, stopPosition):
    # Removes the numbers between two given positions from a list of complex numbers.
    currentPosition = startPositionition
    while currentPosition <= stopPosition :
        del listNumbers[startPositionition]
        currentPosition += 1

def replace (listNumbers, oldNumber, newNumber):
    # Replaces every occurrence of a given number in a list of complex numbers with a new given one.
    length = len(listNumbers)
    for currentPosition in range(length):
        if equalComplexNumbers(listNumbers[currentPosition], oldNumber):
            listNumbers[currentPosition] = newNumber

def filterByRealBetweenPositions (listNumbers, startPositionition, stopPosition):
    # Creates a list of the real numbers between two given positions of a list of complex numbers.
    listReal = filterByReal (listNumbers[startPositionition:stopPosition+1])
    return listReal

def filterByReal (listNumbers):
    # Creates a list of the real numbers of a list of complex numbers.
    listReal = []
    for number in listNumbers:
        if isReal(number):
            listReal.append(number)
    return listReal
            
def filterByModulo (listNumbers, operator, compareWith):
    # Creates a list of the complex numbers which have modulo <, = or > than a given number based on a list of complex numbers.
    listModulo = []
    for number in listNumbers:
        if compareNumbers(modulo(number), compareWith, operator):
            listModulo.append(number)
    return listModulo

def sumBetweenPositions (listNumbers, startPositionition, stopPosition):
    # Calculates the sum of the numbers between two given positions in a list of complex numbers.
    sumNumbers = [0, 0]
    currentPosition = startPositionition
    while currentPosition <= stopPosition:
        sumNumbers = addTwoComplexNumbers(sumNumbers, listNumbers[currentPosition])
        currentPosition += 1
    return sumNumbers

def productBetweenPositions (listNumbers, startPositionition, stopPosition):
    # Calculates the product of the numbers between two given positions in a list of complex numbers.
    productNumbers = [1,0]
    currentPosition = startPositionition
    while currentPosition <= stopPosition:
        productNumbers = multiplyTwoComplexNumbers(productNumbers, listNumbers[currentPosition])
        currentPosition += 1
    return productNumbers