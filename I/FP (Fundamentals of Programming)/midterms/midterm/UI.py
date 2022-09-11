from lib2to3.fixer_util import Comma



class Console(object):
    
    
    def __init__(self, controller):
        
        self.__controller = controller
        
    
    def __readCommand(self):
        
        print("1. Display the addresses and drivers")
        print("2. Drivers sorted by the distance to a given address")
        print("3. Pair of drivers who are closest to each other")
        command = input("\nGive command: >>> ")
        return command
     
    
    def __one(self):
        
        print("\nADDRESSES:")
        addresses = self.__controller.getAllAddresses()
        for current in addresses:
            print(str(current))
            
        print("\nDRIVERS:")
        
        drivers = self.__controller.getAllDrivers()
        for current in drivers:
            print(str(current))
        
        print("\n")
        
    
    def __two(self):
        address = input("Give address: >>> ")
        
        
        drivers = self.__controller.distanceToAddress(address)
        print("\nDRIVERS:")
        for current in drivers:
            print(str(current))
         
        print("\n")
    
    
    def __three(self):
        pass
    
    
    def start(self):
                
        while True:
            command = self.__readCommand()
            if command == '0':
                print("Application has been closed.")
                return
            elif command == '1':
                self.__one()
            elif command == '2':
                self.__two()
            elif command == '3':
                self.__three()
            else:
                print("Invalid command.")
        
        


