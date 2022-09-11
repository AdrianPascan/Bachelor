from math import sqrt

def createComplexNumber (realPart, imaginaryPart = 0):
    # Creates a complex number.
    return [realPart, imaginaryPart]

def getRealPart (number):
    # Returns the real part of a complex number.
    return number[0]

def getImaginaryPart (number):
    # Returns the real part of a complex number.
    return number[1]

def isReal (number):
    # Verifies if a complex number is real.
    return getImaginaryPart(number) == 0

def complexNumberToString (number):
    # Converts a complex number into  a string.
    string = ""
    realPart = getRealPart(number)
    imaginaryPart = getImaginaryPart(number)
    if realPart == 0 and imaginaryPart == 0: 
        return "0"
    if realPart != 0:
        string += str(realPart)
    if realPart != 0 and imaginaryPart > 0:
        string += "+"
    if imaginaryPart != 0:
        if imaginaryPart == 1:
            string += "i"
        elif imaginaryPart == -1:
            string += "-i"
        else:
            string += str(imaginaryPart) + "i"
    return string

def equalComplexNumbers (number1, number2):
    # Verifies if two complex numbers are equal.
    return getRealPart(number1) == getRealPart(number2) and getImaginaryPart(number1) == getImaginaryPart(number2)

def modulo (number):
    # Calculates the modulo of a complex number.
    return sqrt(getRealPart(number)**2 + getImaginaryPart(number)**2)

def addTwoComplexNumbers (number1, number2):
    # Calculates the sum of two complex numbers.
    real1=getRealPart(number1)
    real2=getRealPart(number2)
    imaginary1=getImaginaryPart(number1)
    imaginary2=getImaginaryPart(number2)
    return [real1 + real2, imaginary1 + imaginary2]

def multiplyTwoComplexNumbers (number1, number2):
    # Calculates the product of two complex numbers. 
    real1 = getRealPart(number1)
    imaginary1 = getImaginaryPart(number1)
    real2 = getRealPart(number2)
    imaginary2 = getImaginaryPart(number2)
    return [real1*real2-imaginary1*imaginary2, real1*imaginary2+real2*imaginary1]