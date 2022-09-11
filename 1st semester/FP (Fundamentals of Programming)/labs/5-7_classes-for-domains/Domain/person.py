class Person ():

    def __init__(self, personID, name, phoneNumber, address):
        # Constructor for Person class
        self.__ID = personID
        self.__name = name
        self.__phoneNumber = phoneNumber
        self.__address = address

    
    def get_id(self):
        return self.__ID


    def get_name(self):
        return self.__name


    def get_phone_number(self):
        return self.__phoneNumber


    def get_address(self):
        return self.__address


    def set_id(self, value):
        if type(value) != int or value <= 0:
            raise ValueError('ID must be a strictly positive integer.')
        self.__ID = value


    def set_name(self, value):
        if type(value) != str or len(value.split('_')) != 2:
            raise ValueError('Name must be in this form: LastName_FirstName1-FirstName2-....')
        self.__name = value


    def set_phone_number(self, value):
        if type(value) != str or len(value)!= 10 or value[0] != '0':
            raise ValueError('Phone number must be in this form: 0xxxxxxxxx.')
        try:
            int(value)
        except ValueError:
            raise ValueError('Phone number must contain only digits.')
        self.__phoneNumber = value


    def set_address(self, value):
        if type(value) != str or len(value) == 0 or value != value.strip():
            raise ValueError('Address must be a nonempty string.')
        self.__address = value

    
    def __str__(self):
        # Converts the Person object into a string type.
        return '{} : {} : {} : {}'.format(self.__ID, self.__name, self.__phoneNumber, self.__address)
    
    
    def __eq__(self, person):
        # Checks if two instances are refering to the same person.
        if self.get_id() == person.get_id():
            return True
        return False
    
    
    ID = property(get_id, set_id, None, None)
    name = property(get_name, set_name, None, None)
    phoneNumber = property(get_phone_number, set_phone_number, None, None)
    address = property(get_address, set_address, None, None)
    

def test_person ():
    person = Person(None, None, None, None)
    person.set_id(25)
    person.set_name('Popescu_Ioan')
    person.set_phone_number('0740801221')
    person.set_address('Aleea_Peana_10')
    assert person.get_id() == 25
    assert person.get_name() == 'Popescu_Ioan'
    assert person.get_phone_number() == '0740801221'
    assert person.get_address() == 'Aleea_Peana_10'
    
    assert person.__str__() == '25 : Popescu_Ioan : 0740801221 : Aleea_Peana_10'
    
    person1 = Person(25, None, None, None)
    assert person.__eq__(person1) == True
    person1.set_id(20)
    assert person.__eq__(person1) == False