from Operations.initialise import createProductList
from Operations.functions import createProduct, addProductToList,\
    removeProductFromList, stringProductList, calculateTotalValue
from builtins import int


def getMenu():
    menu = 'Available commands:\n'
    menu += 'add <product_name> <quantity> <item_price>\n'
    menu += 'remove <product_name>\n'
    menu += 'list all\n'
    menu += 'list total\n'
    menu += 'exit\n'
    return menu


def validateStrictlyPositiveInteger (string):
    number = int(string)
    if number <= 0:
        raise ValueError
    return number


def UI_addProductToList(productList, name, price, quantity):
    try:
        price = validateStrictlyPositiveInteger(price)
        quantity = validateStrictlyPositiveInteger(quantity)
        addProductToList(productList, createProduct(name, price, quantity))
    except ValueError:
        print('Price / Quantity must be strictly positive integers.')


def UI_removeProductFromList(productList, name):
    try:
        removeProductFromList(productList, name)
    except ValueError as error:
        print(error)


def UI_listProductList(productList):
    print(stringProductList(productList))


def UI_listTotalValue(productList):
    print(calculateTotalValue(productList))


def UI_runConsole ():
    productList = createProductList()
    print(getMenu())
    command = ''
    while command != 'exit':
        command = input('Input command:\n\t > ')
        splitCommand = command.split(' ')
        if len(splitCommand) == 4 and splitCommand[0] == 'add':
            UI_addProductToList(productList, splitCommand[1], splitCommand[2], splitCommand[3])
        elif len(splitCommand) == 2 and splitCommand[0] == 'remove':
            UI_removeProductFromList(productList, splitCommand[1])
        elif len(splitCommand) == 2 and splitCommand[0] == 'list' and splitCommand[1] == 'all':
            UI_listProductList(productList)
        elif len(splitCommand) == 2 and splitCommand[0] == 'list' and splitCommand[1] == 'total':
            UI_listTotalValue(productList)
        elif len(splitCommand) == 1 and splitCommand[0] == 'exit':
            print('Application has been closed.')
        else:
            print('Invalid command.')