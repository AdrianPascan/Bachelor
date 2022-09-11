from Domain.Person import Person
from Domain.StrictlyPositiveIntegerValidator import validateStrictlyPositiveInteger
from MyDataStructure import filterBy


class PersonController:
    
    def __init__(self, personValidator, personRepository, repositoryDTO):
        
        self.__validator = personValidator
        self.__repository = personRepository
        self.__repositoryDTO = repositoryDTO
    
    
    def populate(self):
        
        self.__repository.populate()
    

    def create(self, ID, name, phoneNumber, address):
        
        person = Person(ID, name, phoneNumber, address)
        
        self.__validator.validate(person)
        self.__repository.store(person)
        
    
    def remove(self, ID):
        
        ID = validateStrictlyPositiveInteger(ID)
        self.__repositoryDTO.removePerson(ID)
    
    
    def update(self, ID, name, phoneNumber, address):
        
        new = Person(ID, name, phoneNumber, address)
        
        self.__validator.validate(new)
        
        self.__repository.update(new)
    
    
    def persons(self):
        
        return self.__repository._getAll()
    
    
    def search(self, string):
        
        persons = self.__repository._getAll()
        
        return filterBy(persons, lambda person: string.lower() in person.name.lower() or string in person.phoneNumber)
    
    
    def personActivities(self):
        
        return self.__repositoryDTO.personActivities()
    