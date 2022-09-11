from Operations.functions import createProduct, addProductToList,\
    removeProductFromList, stringProductList, calculateTotalValue
from Operations.initialise import createProductList


def test_createProduct ():
    product = createProduct('flour', 5, 10)
    assert product[0] == 'flour'
    assert product[1] == 5
    assert product[2] == 10


def test_addProductToList ():
    productList = []
    addProductToList(productList, createProduct('flour', 5, 10))
    assert len(productList) == 1 and productList[0][0] == 'flour'


def test_removeProductFromList ():
    productList = createProductList()
    removeProductFromList(productList, 'flour_1kg')
    assert len(productList) == 4 and productList[0][0] == 'sugar_1kg'


def test_stringProductList():
    productList = []
    assert stringProductList(productList) == 'Product list is void.'
    addProductToList(productList, createProduct('flour', 5, 10))
    assert stringProductList(productList) == 'Product list:\nflour: 5$, 10 items'


def test_calculateTotalValue():
    productList = createProductList()
    assert calculateTotalValue(productList) == 1130


def runTests ():
    test_createProduct()
    test_addProductToList()
    test_removeProductFromList()
    test_stringProductList()
    test_calculateTotalValue()