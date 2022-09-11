def start():
    run_tests()
    print("Hi!")
    numbers = initialise_numbers()
    execute_functionalities(numbers)


def initialise_numbers():
    '''
    Description: initialises the list "numbers" with 10 complex numbers.
    Input: numbers
    preconditions: numbers - a list of tuples with the elements (realPart, imaginaryPart) representing  a complex number
    Output: numbers'
    postconditions: numbers' - a list of 10 tuples with the elements (realPart, imaginaryPart) representing  a complex number
    '''

    numbers = [(2.0, 6.0), (4.0, -67.0), (90.0, 3.0), (80.0, -7.0), (100.0, 1.0), (43.0, -12.0), (3.0, 0.0),
               (45.0, -71.0), (1.0, 5.0), (-1.0, 100.0)]
    return numbers


def execute_functionalities(numbers):
    '''
    Description: reads the codes of the functionalities and executes them.
    Input: numbers
    preconditions: numbers - a list of tuples with the elements (realPart, imaginaryPart) representing  a complex number
    Output: -
    '''

    functionality = -1
    while functionality != '0':
        print_menu()
        functionality = input()
        if functionality == '1':
            read_numbers(numbers)
        elif functionality == '2':
            print_numbers(numbers)
        elif functionality == '3':
            PRINT_longest_sequence_strictly_increasing_part(numbers)
        elif functionality == '4':
            PRINT_longest_sequence_real_part_mountain(numbers)
        elif functionality == '0':
            exit_application()
        else:
            print("Invalid code!")


def print_menu():
    '''
    Description: prints the menu on the screen.
    Input: -
    Output:-
    '''

    print("Please type the code of the functionality you want to use:")
    print("1. read a list of complex numbers;")
    print("2. print the entire list of numbers;")
    print("3. print the longest sequence of numbers with a strictly incresing real part;")
    print(
        "4. print the longest sequence of numbers whose real part is in the form of a mountain (first the values increase, then they decrease);")
    print("0. exit the application.")


def exit_application():
    '''
    Description: exits the application.
    Input: -
    Output: -
    '''

    print("Goodbye!")


def read_numbers(numbers):
    '''
    Description: reads a list of complex numbers.
    Input: numbers
    preconditions: numbers - list of complex numbers
    Output: numbers'
    postconditions: numbers' = list "numbers" with read numbers added
    '''

    stop = False
    while stop == False:
        print("Add a number? (yes/no): ")
        answer = input()
        if answer == 'yes':
            complexNr = get_complex_number()
            if complexNr == None:
                print("Invalid number!")
            else:
                numbers.append(complexNr)
        elif answer == 'no':
            stop = True
        else:
            print("Invalid answer!")


def get_complex_number():
    '''
    Description: reads a complex numbers and returns it if it's valid.
    Input: -
    Output: a complex number as a tuple in the form (real_part, imaginary_part)
            none, if the number is invalid
    '''

    print("real part: ")
    realPart = input()
    print("imaginary part: ")
    imaginaryPart = input()
    try:
        realPart = float(realPart)
        imaginaryPart = float(imaginaryPart)
    except ValueError:
        return None
    return (realPart, imaginaryPart)


def print_numbers(numbers):
    '''
    Description: prints the list of complex numbers.
    Input: numbers
    Preconditions: numbers - a list of tuples with the elements (realPart, imaginaryPart) representing  a complex number
    Output: -
    '''

    length = len(numbers)
    if len == 0:
        print("Void list!")
    else:
        print("List of numbers: ")
        for element in range(length):
            print_complex_number(numbers[element])


def print_complex_number(number):
    '''
    Description: prints a complex number.
    Input: number
    preconditions: number - a tuple with the elements (realPart, imaginaryPart) representing  a complex number
    Output: -
    '''

    if number[1] >= 0:
        print(number[0], '+', number[1], '*i')
    else:
        print(number[0], number[1], '*i')


def longest_sequence_strictly_increasing_part(numbers):
    '''
    Description: finds the longest sequence in which the numbers have a strictly increasing real part.
    Input: numbers
    preconditions: numbers - a list of tuples with the elements (realPart, imaginaryPart) representing  a complex number
    Output: (startPosition, maximumLength) / None
    postconditions: startPosition - the index of the element in "numbers" where the sequence starts
                    maximumLength - the length of the sequence above representing the number of elements that are contained
                    returns None if such a sequence doesn't exist
    '''

    startPosition = 0
    maximumLength = 0
    currentStart = 0
    currentLength = 0
    lengthNumbers = len(numbers)
    currentPosition = 1
    while currentPosition < lengthNumbers:
        if numbers[currentPosition - 1][0] < numbers[currentPosition][0]:
            currentStart = currentPosition - 1
            currentLength = 2
            currentPosition += 1
            while currentPosition < lengthNumbers and numbers[currentPosition - 1][0] < numbers[currentPosition][0]:
                currentLength += 1
                currentPosition += 1
            if currentLength > maximumLength:
                maximumLength = currentLength
                startPosition = currentStart
        else:
            currentPosition += 1
    if maximumLength == 0:
        return None
    return (startPosition, maximumLength)


def TEST_longest_sequence_strictly_increasing_part():
    numbers = [(1, -1), (2, 3), (3, 1)]
    assert longest_sequence_strictly_increasing_part(numbers) == (0, 3)
    numbers = [(1, 1), (0, 5)]
    assert longest_sequence_strictly_increasing_part(numbers) == None
    numbers = [(-1, 5), (5, 6), (1, 5)]
    assert longest_sequence_strictly_increasing_part(numbers) == (0, 2)
    numbers = [(6, 5), (5, 6), (7, 5)]
    assert longest_sequence_strictly_increasing_part(numbers) == (1, 2)
    numbers = [(-1, 5), (-5, 6), (1, 5), (0, 5)]
    assert longest_sequence_strictly_increasing_part(numbers) == (1, 2)


def PRINT_longest_sequence_strictly_increasing_part(numbers):
    sequenceDetails = longest_sequence_strictly_increasing_part(numbers)
    if sequenceDetails == None:
        print("There is no squence of numbers with strictly an increasing part!")
    else:
        print("The longest sequence in which the numbers have a strictly increasing real part is: ")
        startPosition = sequenceDetails[0]
        stopPosition = startPosition + sequenceDetails[1]
        for currentNumber in range(startPosition, stopPosition):
            print_complex_number(numbers[currentNumber])


def longest_sequence_real_part_mountain(numbers):
    '''
    Description: finds the longest sequence in which the real part of the numbers are in the form of a mountain (first the values increase, then they decrease).
    Input: numbers
    preconditions: numbers - a list of tuples with the elements (realPart, imaginaryPart) representing  a complex number
    Output: (startPosition, maximumLength) / None
    postconditions: startPosition - the index of the element in "numbers" where the sequence starts
                    maximumLength - the length of the sequence above representing the number of elements that are contained
                    returns None if such a sequence doesn't exist
    '''

    startPosition = 0
    maximumLength = 0
    currentStart = 0
    currentLength = 0
    lengthNumbers = len(numbers)
    currentPosition = 1
    while currentPosition < lengthNumbers:
        if numbers[currentPosition - 1][0] < numbers[currentPosition][0]:
            currentStart = currentPosition - 1
            currentLength = 2
            currentPosition += 1
            while currentPosition < lengthNumbers and numbers[currentPosition - 1][0] < numbers[currentPosition][0]:
                currentLength += 1
                currentPosition += 1
            peak = currentPosition - 1
            while currentPosition < lengthNumbers and numbers[currentPosition - 1][0] > numbers[currentPosition][0]:
                currentLength += 1
                currentPosition += 1
            if peak != currentPosition - 1:
                if currentLength > maximumLength:
                    maximumLength = currentLength
                    startPosition = currentStart
        else:
            currentPosition += 1
    if maximumLength == 0:
        return None
    return (startPosition, maximumLength)


def TEST_longest_sequence_real_part_mountain():
    numbers = [(1, -1), (2, 6), (4, -67), (90, 3), (80, -7), (76, 1), (43, -12), (3, 0)]
    assert longest_sequence_real_part_mountain(numbers) == (0, 8)
    numbers = [(1, 1), (5, 5), (6, 6)]
    assert longest_sequence_real_part_mountain(numbers) == None
    numbers = [(-1, 5), (-5, 6), (-7, 5)]
    assert longest_sequence_real_part_mountain(numbers) == None
    numbers = [(6, -1), (2, 6), (1, -67), (90, 3), (80, -7), (76, 1), (43, -12), (70, 0)]
    assert longest_sequence_real_part_mountain(numbers) == (2, 5)
    numbers = [(1, -1), (2, 6), (4, -67), (90, 3), (80, -7), (76, 1), (43, -12), (70, 0)]
    assert longest_sequence_real_part_mountain(numbers) == (0, 7)
    numbers = [(8, -1), (2, 6), (4, -67), (90, 3), (80, -7), (76, 1), (43, -12), (3, 0)]
    assert longest_sequence_real_part_mountain(numbers) == (1, 7)


def PRINT_longest_sequence_real_part_mountain(numbers):
    sequenceDetails = longest_sequence_real_part_mountain(numbers)
    if sequenceDetails == None:
        print("There is no squence of numbers whose real part is in the form of a mountain!")
    else:
        print("The longest sequence in which the real part of the numbers are in the form of a mountain is: ")
        startPosition = sequenceDetails[0]
        stopPosition = startPosition + sequenceDetails[1]
        for currentNumber in range(startPosition, stopPosition):
            print_complex_number(numbers[currentNumber])


def run_tests():
    TEST_longest_sequence_strictly_increasing_part()
    TEST_longest_sequence_real_part_mountain()


start()
