class Controller():
    
    def __init__(self, repository):
    # Constructor for Controller class
        
        self.__repository = repository
        
        
    def getAllAddresses(self):
        # Returns a list of all addresses stored in repository
        
        return self.__repository.getAllAddresses().values()

    
    def getAllDrivers(self):
        # Returns a list of all drivers stored in repository
        
        return self.__repository.getAllDrivers().values()
    
    
    def distanceToAddress(self, address):
        
        try:
            address = self.__repository.getAllAddresses()[int(address)]
        except Exception:
            print("No address for ID: ", address)
            return
        
        print(str(address))
        
        drivers = [x[1] for x in self.__repository.getAllDrivers().items()]
        print(drivers)
        
        drivers = sorted(drivers, key = lambda driv: abs(driv.x - address.x) + abs(driv.y-address.y))
        
        return drivers
    
    
    