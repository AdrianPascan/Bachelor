from Address import Address
from Driver import Driver


class Repository(object):
    
    
    def __init__(self):
        # Constructor for Repository class    
        
        self.__address = {}
        self.__driver = {}
        
        with open("addresses", "r") as file:
            data = file.read()
            data = data.split("\n")
            if len(data) != 1: 
                for address in data:
                    address = address.split(",")
                    self.__address[int(address[0].strip())] = Address(int(address[0].strip()), address[1].strip(), int(address[2].strip()), int(address[3].strip()) )
        
        with open("drivers", "r") as file:
            data = file.read()
            data = data.split("\n")
            if len(data) != 1: 
                for driver in data:
                    driver = driver.split(",")
                    self.__driver[driver[0].strip()] = Driver(driver[0].strip(), int(driver[1].strip()), int(driver[2].strip()) )
                    
                
#         print(self.__address)
#         print(self.__driver)    
                
    
    def getAllAddresses(self):
        return self.__address
    
    def getAllDrivers(self):
        return self.__driver
    