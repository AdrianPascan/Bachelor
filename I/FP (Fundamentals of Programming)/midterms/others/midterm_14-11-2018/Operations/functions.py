def createProduct (name, price, quantity):
    '''
    Returns a product as a list based on the given information
    '''
    return [name, price, quantity]


def addProductToList (productList, product):
    '''
    Adds a new product to the list
    '''
    productList.append(product)


def findProductInList (productList, name):
    '''
    Find a product by name in the list and returns its index (-1 if it does not exist)
    '''
    for index in range(len(productList)):
        if productList[index][0] == name:
            return index
    return -1
    

def removeProductFromList (productList, name):
    '''
    Removes a product with a given name from the list
    '''
    index = findProductInList(productList, name)
    if index == -1:
        raise ValueError('Product does not exist.')
    productList.pop(index)
    

def stringProductList (productList):
    '''
    Converts the list of products into a string and returns it
    '''
    if len(productList) == 0:
        return 'Product list is void.'
    string = 'Product list:'
    for index in range(len(productList)):
        string += '\n' + str(productList[index][0]) + ': ' + str(productList[index][1]) + '$, ' + str(productList[index][2]) + ' items' 
    return string


def calculateTotalValue (productList):
    ''' 
    Calculates the total value of the products
    '''
    total = 0
    for index in range(len(productList)):
        total += productList[index][1] * productList[index][2]
    return total