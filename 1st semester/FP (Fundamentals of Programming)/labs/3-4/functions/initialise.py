from functions.complex_number import createComplexNumber

def initialiseList ():
    # Creates a list of 10 arbitrarily chosen complex numbers and returns it.
    listNumbers = [createComplexNumber(1, 1), createComplexNumber(0), createComplexNumber(19, -10), createComplexNumber(-12, -9), createComplexNumber(0, 17), createComplexNumber(7, -5), createComplexNumber(-4, 11), createComplexNumber(3, 4), createComplexNumber(8, -6), createComplexNumber(15)]
    return listNumbers

def getAvailableCommands ():
    # Creates a menu of the available commands as a string and returns it.
    commands = 'Available commands: \n'
    commands += '\t add <number> \n'
    commands += '\t insert <number> at <position> \n'
    commands += '\t remove <position> \n'
    commands += '\t remove <start position> to <end position> \n'
    commands += '\t replace <old number> with <new number> \n'
    commands += '\t list \n'
    commands += '\t list real <start position> to <end position> \n'
    commands += '\t list modulo [ < | = | > ] <number> \n'
    commands += '\t sum <start position> to <end position> \n'
    commands += '\t product <start position> to <end position> \n'
    commands += '\t filter real \n'
    commands += '\t filter modulo [ < | = | > ] <number> \n'
    commands += '\t undo \n'
    commands += '\t EXIT'
    return commands

def getAvailableCommandsMenu ():
    # Creates a menu of the available commands as a string and returns it.
    commands = 'Available commands: \n'
    commands += '\t 1. add <number> \n'
    commands += '\t 2. insert <number> at <position> \n'
    commands += '\t 3. remove <position> \n'
    commands += '\t 4. remove <start position> to <end position> \n'
    commands += '\t 5. replace <old number> with <new number> \n'
    commands += '\t 6. list \n'
    commands += '\t 7. list real <start position> to <end position> \n'
    commands += '\t 8. list modulo [ < | = | > ] <number> \n'
    commands += '\t 9. sum <start position> to <end position> \n'
    commands += '\t 10. product <start position> to <end position> \n'
    commands += '\t 11. filter real \n'
    commands += '\t 12. filter modulo [ < | = | > ] <number> \n'
    commands += '\t 13. undo \n'
    commands += '\t 0. EXIT'
    return commands