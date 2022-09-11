def compareNumbers (number1, number2, operator) :
    # Compares two real numbers, where the operator can be <, =, >.
    if operator == '<' :
        return number1 < number2
    elif operator == '=' :
        return number1 == number2
    else:
        return number1 > number2
