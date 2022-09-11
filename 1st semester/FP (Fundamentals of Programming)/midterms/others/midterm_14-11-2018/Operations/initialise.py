from Operations.functions import createProduct


def createProductList():
    productList = []
    productList.append(createProduct('flour_1kg', 5, 10))
    productList.append(createProduct('sugar_1kg', 7, 100))
    productList.append(createProduct('bread', 1, 50))
    productList.append(createProduct('butter_200g', 2, 25))
    productList.append(createProduct('milk_1l', 2, 140))
    return productList
    