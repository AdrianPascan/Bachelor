def validateStrictlyPositiveInteger(string):
    
    try:
        number = int(string)
    except ValueError:
        raise ValueError("string cannot be converted into integer data type")
    
    if number <= 0:
        raise ValueError("integer not strictly positive")
    
    return number