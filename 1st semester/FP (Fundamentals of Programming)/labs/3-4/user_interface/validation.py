def validateComplexNumber (string):
    # Verifies if a string can be converted into a complex number and returns it.
    try:
        if string[-1] != 'i' :
            return [int(string),0]
        signPos = string.find ('+', (string[0]=='-')+1)
        if signPos == -1: 
            signPos = string.find ('-', (string[0]=='-')+1)
        if signPos == -1 :
            if string == 'i':
                return [0,1]
            if string == '-i': 
                return [0,-1]
            return [0,int(string[:len(string)-1])]
        realPart = int(string[:signPos])
        if signPos+1 == len(string)-1:
            if string[signPos] == '+':
                return [realPart, 1]
            else:
                return [realPart, -1]
        imaginaryPart = int(string[signPos+1:len(string)-1])
        if string[signPos] == '-':
            imaginaryPart = -imaginaryPart
        return [realPart,imaginaryPart]
    except ValueError:
        raise ValueError('Invalid complex number!')

def validateReal (string):
    # Checks if a string is a positive real number and returns it if so.
    try:
        number = float (string)
        return number
    except ValueError:
        raise ValueError('Invalid real number!')
    
def validatePosition (listNumbers, string):
    # Checks if a string can represent a position in a list and returns it if so.
    try:
        pos = int (string)
        if pos < 0  or pos >= len(listNumbers):
            raise ValueError
        return pos
    except ValueError:
        raise ValueError('Invalid position!')

def validateSequence (startPositionition, stopPosition):
    # Verifies if two positions represent the beginning and the end of a sequence in a list.
    if not (startPositionition <= stopPosition):
        raise ValueError('Such a sequence does not exist!')
    
def validateOperator (operator):
    # Verifies if the operator is <, =, >.
    if not(operator == '<' or operator == '=' or operator == '>'):
        raise ValueError('Invalid operator!')