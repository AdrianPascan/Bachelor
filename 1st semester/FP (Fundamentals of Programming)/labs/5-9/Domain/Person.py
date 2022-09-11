class Person(object):
    
    def __init__(self, personID, name, phoneNumber, address):
        # Constructor for Person class
        self.__ID = personID
        self.__name = name
        self.__phoneNumber = phoneNumber
        self.__address = address
        
    @property
    def ID(self):
        return self.__ID

    @ID.setter
    def ID(self, value):
        self.__ID = value
    
    @property
    def name(self):
        return self.__name
    
    
    @property
    def phoneNumber(self):
        return self.__phoneNumber

    
    @property
    def address(self):
        return self.__address
    
    
    def __str__(self):
        # Converts the Person object into a string type.
        return 'person {} - name: {} , phone_number: {} , address: {}'.format(self.__ID, self.__name, self.__phoneNumber, self.__address)
    