from functions.initialise import initialiseList
from functions.tests import runTests
from user_interface.console import UI_executeCommmands
from user_interface.menu import UI_menuExecuteCommands

def start ():
    runTests()
    listNumbers = initialiseList()
    print ("Which UI would you like to use? command-based / menu-based?\nType 'command' or 'menu':")
    answer = ''
    while answer != 'command' and answer != 'menu':
        answer = input('\t > ')
        if answer == 'command':
            UI_executeCommmands(listNumbers)
        elif answer == 'menu':
            UI_menuExecuteCommands(listNumbers)
        else:
            print('Invalid answer!')
    
start()
