from functions.lists import listToString, addNumber, insertNumberAtPosition,\
    removePosition, removeBetweenPositions, replace,\
    filterByRealBetweenPositions, filterByModulo, sumBetweenPositions,\
    productBetweenPositions, filterByReal
from functions.initialise import getAvailableCommandsMenu
from user_interface.console import UI_exit, UI_undo
from user_interface.validation import validateComplexNumber, validatePosition,\
    validateSequence, validateOperator, validateReal
from functions.complex_number import complexNumberToString

def printList (listNumbers):
    # Prints a list of complex numbers.
    print(listToString(listNumbers))
    
def commandForNumberInput ():
    command = {'0': UI_menuExit, '1': UI_menuAddNumber, '2': UI_menuInsertNumberAtPosition, '3': UI_menuRemovePosition, '4': UI_menuRemoveBetweenPositions, '5': UI_menuReplace, '6': UI_menuList, '7': UI_menuListRealBetweenPositions, '8': UI_menuListModulo, '9': UI_menuSumBetweenPositions, '10': UI_menuProductBetweenPositions, '11': UI_menuFilterByReal, '12': UI_menuFilterByModulo, '13': UI_menuUndo}
    return command

def UI_readComplexNumber (atribute):
    if atribute == '':
        print('Input complex number: ')
    elif atribute == 'old':
        print('Input old complex number: ')
    else: 
        print('Input new complex number: ')
    number = []
    while number == [] :
        try: 
            string = input('\t > ')
            number = validateComplexNumber(string)
        except ValueError as error: 
            print(error)
    return number

def UI_readPosition (listNumbers, atribute):
    if atribute == '':
        print('Input position: ')
    elif atribute == 'start':
        print('Input start position: ')
    else:
        print('Input stop position: ')
    position = -1
    while position == -1 :
        try: 
            string = input('\t > ')
            position = validatePosition(listNumbers, string)
        except ValueError as error: 
            print(error)
    return position


def UI_readSequence (listNumbers):
    startPositionition = 0
    stopPositionition = -1
    while startPositionition > stopPositionition :
        try:
            startPositionition = UI_readPosition(listNumbers, 'start')
            stopPositionition = UI_readPosition(listNumbers, 'stop')
            validateSequence(startPositionition, stopPositionition)
            return (startPositionition, stopPositionition)
        except ValueError as error:
            print(error)

def UI_readOperator():
    print('Input operator: ')
    while True:
        try:
            operator = input('\t > ')
            validateOperator(operator)
            return operator
        except ValueError as error: 
            print(error) 

def UI_readReal():
    print('Input real number: ')
    number = None
    while number == None:
        try:
            number = validateReal(input('\t > '))
            return number
        except ValueError as error:
            print(error)
    
def UI_menuExecuteCommands (listNumbers):
    print(getAvailableCommandsMenu())
    command = commandForNumberInput()
    listUndo = []
    answer = ''
    while answer != '0':
        answer = input('\nInput command:\n\t > ')
        try:
            command[answer](listNumbers, listUndo)
        except KeyError:
            print('Invalid command!')

def UI_menuExit (listNumbers, listUndo):
    UI_exit()
    
def UI_menuAddNumber (listNumbers, listUndo):
    number = UI_readComplexNumber('')
    listUndo.append(listNumbers[:])
    addNumber(listNumbers, number)
    
def UI_menuInsertNumberAtPosition (listNumbers, listUndo):
    number = UI_readComplexNumber('')
    position = UI_readPosition(listNumbers, '')
    listUndo.append(listNumbers[:])
    insertNumberAtPosition(listNumbers, number, position)
    
def UI_menuRemovePosition (listNumbers, listUndo):
    position = UI_readPosition(listNumbers, '')
    listUndo.append(listNumbers[:])
    removePosition(listNumbers, position)
    
def UI_menuRemoveBetweenPositions (listNumbers, listUndo):
    (startPositionition, stopPositionition) = UI_readSequence(listNumbers)
    listUndo.append(listNumbers[:])
    removeBetweenPositions(listNumbers, startPositionition, stopPositionition)
    
def UI_menuReplace (listNumbers, listUndo):
    oldNumber = UI_readComplexNumber('old')
    newNumber = UI_readComplexNumber('new')
    listUndo.append(listNumbers[:])
    replace(listNumbers, oldNumber, newNumber)
    
def UI_menuList (listNumbers, listUndo):
    printList(listNumbers)
    
def UI_menuListRealBetweenPositions (listNumbers, listUndo):
    (startPositionition, stopPositionition) = UI_readSequence(listNumbers)
    listReal = filterByRealBetweenPositions(listNumbers, startPositionition, stopPositionition)
    printList(listReal)

def UI_menuListModulo (listNumbers, listUndo):
    operator = UI_readOperator()
    compareWith = UI_readReal()
    listModulo = filterByModulo(listNumbers, operator, compareWith)
    printList(listModulo)

def UI_menuSumBetweenPositions (listNumbers, listUndo):
    (startPositionition, stopPositionition) = UI_readSequence(listNumbers)
    print(complexNumberToString(sumBetweenPositions(listNumbers, startPositionition, stopPositionition)))

def UI_menuProductBetweenPositions (listNumbers, listUndo):
    (startPositionition, stopPositionition) = UI_readSequence(listNumbers)
    print(complexNumberToString(productBetweenPositions(listNumbers, startPositionition, stopPositionition)))
    
def UI_menuFilterByReal (listNumbers, listUndo):
    listUndo.append(listNumbers[:])
    listNumbers[:]= filterByReal(listNumbers)
    
def UI_menuFilterByModulo (listNumbers, listUndo):
    operator = UI_readOperator()
    compareWith = UI_readReal()
    listUndo.append(listNumbers[:])
    listNumbers[:] = filterByModulo(listNumbers, operator, compareWith)
    
def UI_menuUndo (listNumbers, listUndo):
    UI_undo(listNumbers, listUndo)
