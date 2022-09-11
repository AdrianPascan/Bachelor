from functions.lists import *
from user_interface.validation import *
from functions.initialise import getAvailableCommands

startPosition = 0
stopPosition = 0

def printList (listNumbers):
    # Prints a list of complex numbers.
    print(listToString(listNumbers))

def UI_executeCommmands (listNumbers):
    print (getAvailableCommands())
    listUndo = []
    currentCommand = ''
    while currentCommand.lower() != 'exit':
        currentCommand = input('\nInput command:\n\t > ')
        splitCommand = currentCommand.split(' ')
        lengthCommand = len(splitCommand)
        if splitCommand[0] == 'add' and lengthCommand == 2:
            UI_addNumber(listNumbers, splitCommand, listUndo)
        elif splitCommand[0] == 'insert' and lengthCommand == 4:
            UI_insertNumberAtPosition(listNumbers, splitCommand, listUndo)
        elif splitCommand[0] == 'remove':
            if lengthCommand == 2:
                UI_removePosition(listNumbers, splitCommand, listUndo)
            elif lengthCommand == 4:
                UI_removeBetweenPositions(listNumbers, splitCommand, listUndo)
        elif splitCommand[0] == 'replace' and lengthCommand == 4:
            UI_replace(listNumbers, splitCommand, listUndo)
        elif splitCommand[0] == 'list':
            if lengthCommand == 1:
                UI_list(listNumbers, splitCommand)
            elif lengthCommand == 5:
                UI_listReal(listNumbers, splitCommand)
            elif lengthCommand == 4:
                UI_listModulo(listNumbers, splitCommand)
        elif splitCommand[0] == 'sum' and lengthCommand == 4:
            UI_sumBetweenPositions (listNumbers, splitCommand)
        elif splitCommand[0] == 'product' and lengthCommand == 4:
            UI_productBetweenPositions(listNumbers, splitCommand)
        elif splitCommand[0] == 'filter':
            if lengthCommand == 2:
                UI_filterByReal(listNumbers, splitCommand, listUndo)
            elif lengthCommand == 4:
                UI_filterByModulo(listNumbers, splitCommand, listUndo)
        elif splitCommand[0] == 'undo' and lengthCommand == 1:
            UI_undo(listNumbers, listUndo)
        elif splitCommand[0].lower() == 'exit' and len(splitCommand) == 1:
            UI_exit()
        else: 
            print('Invalid command!')
            

def UI_exit ():
    print('Application has been closed!')

def UI_addNumber (listNumbers, splitCommand, listUndo):
    try:
        number = validateComplexNumber(splitCommand[1])
        listUndo.append(listNumbers[:])
        addNumber(listNumbers, number)
    except ValueError as error:
        print (error)

def UI_insertNumberAtPosition (listNumbers, splitCommand, listUndo):
    try:
        if splitCommand[2] != 'at':
            raise ValueError('Invalid command!')
        number = validateComplexNumber(splitCommand[1])
        position = validatePosition(listNumbers, splitCommand[3])
        listUndo.append(listNumbers[:])
        insertNumberAtPosition(listNumbers, number, position)
    except ValueError as error:
        print (error)

def UI_removePosition (listNumbers, splitCommand, listUndo):
    try:
        position = validatePosition(listNumbers, splitCommand[1])
        listUndo.append(listNumbers[:])
        removePosition(listNumbers, position)
    except ValueError as error:
        print(error)

def UI_removeBetweenPositions (listNumbers, splitCommand, listUndo):
    try:
        if splitCommand[2] != 'to':
            raise ValueError('Invalid command!')
        startPositionition = validatePosition(listNumbers, splitCommand[1])
        stopPosition = validatePosition(listNumbers, splitCommand[3])
        validateSequence(startPositionition, stopPosition)
        listUndo.append(listNumbers[:])
        removeBetweenPositions(listNumbers, startPositionition, stopPosition)
    except ValueError as error:
        print(error)

def UI_replace (listNumbers, splitCommand, listUndo):
    try:
        if splitCommand[2] != 'with':
            raise ValueError('Invalid command!')
        oldNumber = validateComplexNumber(splitCommand[1])
        newNumber = validateComplexNumber(splitCommand[3])
        listUndo.append(listNumbers[:])
        replace(listNumbers, oldNumber, newNumber)
    except ValueError as error:
        print(error)

def UI_list (listNumbers, splitCommand):
    printList(listNumbers)

def UI_listReal (listNumbers, splitCommand):
    try: 
        if splitCommand[1] != 'real' and splitCommand[3] != 'to':
            raise ValueError('Invalid command!')
        startPositionition = validatePosition(listNumbers, splitCommand[2])
        stopPosition = validatePosition(listNumbers, splitCommand[4])
        validateSequence(startPositionition, stopPosition)
        listReal = filterByRealBetweenPositions(listNumbers, startPositionition, stopPosition)
        printList(listReal)
    except ValueError as error:
        print(error)

def UI_listModulo (listNumbers, splitCommand):
    try: 
        if splitCommand[1] != 'modulo' :
            raise ValueError('Invalid command!')
        validateOperator(splitCommand[2])
        compareWith = validateReal(splitCommand[3])
        listModulo = filterByModulo(listNumbers, splitCommand[2], compareWith)
        printList(listModulo)
    except ValueError as error:
        print(error)
        
def UI_sumBetweenPositions (listNumbers, splitCommand):
    try:
        if splitCommand[2] != 'to':
            raise ValueError('Invalid command!')
        startPosition = validatePosition(listNumbers, splitCommand[1])
        stopPosition = validatePosition(listNumbers, splitCommand[3])
        validateSequence(startPosition, stopPosition)
        print(complexNumberToString(sumBetweenPositions(listNumbers, startPosition, stopPosition)))
    except ValueError as error:
        print(error)
        
def UI_productBetweenPositions (listNumbers, splitCommand):
    try:
        if splitCommand[2] != 'to':
            raise ValueError('Invalid command!')
        startPositionition = validatePosition(listNumbers, splitCommand[1])
        stopPosition = validatePosition(listNumbers, splitCommand[3])
        validateSequence(startPositionition, stopPosition)
        print(complexNumberToString(productBetweenPositions(listNumbers, startPositionition, stopPosition)))
    except ValueError as error:
        print(error)

def UI_filterByReal (listNumbers, splitCommand, listUndo):
    if splitCommand[1] != 'real':
        print('Invalid command!')
    else:
        listUndo.append(listNumbers[:])
        listNumbers[:]= filterByReal(listNumbers)

def UI_filterByModulo (listNumbers, splitCommand, listUndo):
    try:
        if splitCommand[1] != 'modulo':
            raise ValueError('Invalid command!')
        validateOperator(splitCommand[2])
        compareWith = validateReal(splitCommand[3])
        listUndo.append(listNumbers[:])
        listNumbers[:] = filterByModulo(listNumbers, splitCommand[2], compareWith)
    except ValueError as error:
        print(error)

def UI_undo (listNumbers, listUndo):
    if len(listUndo) == 0:
        print('No undos available!')
    else:
        listNumbers[:] = listUndo[len(listUndo)-1]
        del listUndo[len(listUndo)-1]
