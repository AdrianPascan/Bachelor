from functions.complex_number import *
from functions.lists import *
from functions.initialise import initialiseList
from user_interface.validation import validateComplexNumber

def test_equalComplexNumber ():
    assert equalComplexNumbers(createComplexNumber(0, 0), createComplexNumber(0, 0)) == True
    assert equalComplexNumbers(createComplexNumber(0, 0), createComplexNumber(0, 1)) == False

def test_validateComplexNumber ():
    assert validateComplexNumber('0') == [0,0]
    assert validateComplexNumber('i') == [0,1]
    assert validateComplexNumber('-i') == [0,-1]
    assert validateComplexNumber('12') == [12,0]
    assert validateComplexNumber('-12') == [-12,0]
    assert validateComplexNumber('5i') == [0,5]
    assert validateComplexNumber('-5i') == [0,-5]
    assert validateComplexNumber('12+5i') == [12,5]
    assert validateComplexNumber('12-5i') == [12,-5]
    assert validateComplexNumber('-12+5i') == [-12,5]
    assert validateComplexNumber('-12-5i') == [-12,-5]
    assert validateComplexNumber('5+i') == [5,1]
    assert validateComplexNumber('5-i') == [5,-1]
    assert validateComplexNumber('0-0i') == [0,0]
    try: 
        validateComplexNumber('+-12')
        assert False
    except ValueError:
        try:
            validateComplexNumber('12+5i1')
            assert False
        except ValueError:
                assert True

def test_complexNumberToString ():
    assert complexNumberToString(createComplexNumber(0, 0)) == "0"
    assert complexNumberToString(createComplexNumber(1, 0)) == "1"
    assert complexNumberToString(createComplexNumber(-1, 0)) == "-1"
    assert complexNumberToString(createComplexNumber(8, 8)) == "8+8i"
    assert complexNumberToString(createComplexNumber(1, 1)) == "1+i"
    assert complexNumberToString(createComplexNumber(1, -1)) == "1-i"
    assert complexNumberToString(createComplexNumber(-2, -7)) == "-2-7i"
    assert complexNumberToString(createComplexNumber(-99, -11)) == "-99-11i"

def test_addNumber ():
    listNumbers = []
    addNumber(listNumbers, createComplexNumber(1, 2))
    assert len(listNumbers) == 1 and getRealPart(listNumbers[0]) == 1 and getImaginaryPart(listNumbers[0]) == 2
    addNumber(listNumbers, createComplexNumber(3, 4))
    assert len(listNumbers) == 2 and getRealPart(listNumbers[1]) == 3 and getImaginaryPart(listNumbers[1]) == 4

def test_insertNumberAtPosition ():
    listNumbers = [createComplexNumber(0, 0), createComplexNumber(1, 1)]
    insertNumberAtPosition(listNumbers, createComplexNumber(2, 2), 1)
    assert listNumbers == []

def test_removePosition ():
    listNumbers = initialiseList()
    removePosition(listNumbers, 2)
    assert len(listNumbers)==9 and listNumbers[2] == [-12, -9]

def test_removeBetweenPositions ():
    listNumbers = initialiseList()
    removeBetweenPositions(listNumbers, 1, 5)
    assert len(listNumbers) == 5 and listNumbers[1] == [-4,11]

def test_replace ():
    listNumbers = initialiseList()
    replace(listNumbers, [0,0], [-1,-1])
    assert len(listNumbers) == 10 and listNumbers[1] == [-1,-1]

def test_filterByReal ():
    listNumbers = initialiseList()
    listReal = filterByReal(listNumbers)
    assert len(listReal) == 2 and listReal == [[0,0], [15,0]]

def test_filterByRealBetweenPositions ():
    listNumbers = initialiseList()
    listReal = filterByRealBetweenPositions(listNumbers, 0, 8)
    assert len(listReal)==1 and listReal == [[0,0]]
    
def test_filterByModulo ():
    listNumbers = initialiseList()
    listModulo = filterByModulo(listNumbers, '<', 2)
    assert len(listModulo)==2 and listModulo == [[1,1], [0,0]]

def test_addTwoComplexNumbers ():
    assert addTwoComplexNumbers(createComplexNumber(-1, 0), createComplexNumber(1, 2)) == [0,2]

def test_sumBetweenPositions ():
    listNumbers = initialiseList()
    assert sumBetweenPositions(listNumbers, 0, 2) == [20,-9]

def test_multilpyTwoComplexNumbers ():
    assert multiplyTwoComplexNumbers(createComplexNumber(0, 1), createComplexNumber(2, -3)) == [3, 2]

def test_productBetweenPositions ():
    listNumbers = initialiseList()
    assert productBetweenPositions(listNumbers, 7, 9) == [720,210]

def runTests ():
    test_equalComplexNumber()
    test_validateComplexNumber()
    test_complexNumberToString()
    test_addNumber()
    test_removePosition()
    test_removeBetweenPositions()
    test_replace()
    test_filterByReal()
    test_filterByRealBetweenPositions()
    test_filterByModulo()
    test_addTwoComplexNumbers()
    test_sumBetweenPositions()
    test_multilpyTwoComplexNumbers()
    test_productBetweenPositions()
