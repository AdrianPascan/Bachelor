from Domain.Person import Person
from MyDataStructure import MyDataStructure
from Exceptions.RepositoryException import RepositoryError
import random

class PersonRepository:
    
    def __init__(self):
        
        self._data = MyDataStructure([])
        
    
    def _getAll(self):
        
        return self._data.getAll()
    
    
    def __len__(self):
        return len(self._data)
   
    
    def _find(self, ID):
         
        for index in range(len(self._data)):
            if self._data[index].ID == ID:
                return index
            
        return None
    
    
    def store(self, person):
        
        if self._find(person.ID) != None:
            raise RepositoryError("Person with ID " + str(person.ID) + " exists already.")
        
        self._data.data.append(person)
    
    
    def remove(self, ID):
        
        index = self._find(ID)
        
        if index == None:
            raise RepositoryError("Person with ID " + str(ID) + " does not exist.")
        
        del self._data[index]
    
    
    def update(self, new):
        
        self.remove(new.ID)
        self.store(new)
        
    
    def populate(self):
        
        self._data.data = self.__populate100()
    
    
    def __populate100 (self):
    # Creates a random list of 100 persons
        personList = []
        lastNameList = ['Popescu', 'Aelenei', 'Iuga', 'Pascan', 'Bulai', 'Candrea', 'Doroftiese', 'Morosanu', 'Danea', 'Florea']
        firstNameList = ['Adrian', 'Diana', 'Ruxandra', 'Olga', 'Maricica', 'Mircea', 'Luca', 'Ciprian', 'Bogdan', 'Iulian'] 
        numbersList = ['48', '75', '14', '10', '99', '66', '70', '39', '00', '55']
        addressList = [['Trandafirilor', 'Peana', 'Kogalniceanu', 'Primaverii', 'Petreni', 'Transilvaniei', 'Chilia', 'Oltului', 'Rucar', 'Islazului'],\
                       ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '']]
        for ID in range(1,101):
            name = random.choice(lastNameList) + '_' + random.choice(firstNameList)
            phoneNumber = '07' + random.choice(numbersList) + random.choice(numbersList) + random.choice(numbersList) + random.choice(numbersList)
            address = random.choice(addressList[0]) + ' ' + random.choice(addressList[1][1:10]) + random.choice(addressList[1])
            personList.append(Person(ID, name, phoneNumber, address))
        return personList
    
    
    def __populate10 (self):
        
        personList = []
        personList.append(Person(1, 'Pop_Adrian', '0745739772', 'Peana 13'))
        personList.append(Person(2, 'Luca_Iulian', '0743877389', 'Rucar 2'))
        personList.append(Person(3, 'Pindelea_Ionela', '0748966321', 'Primaverii 14'))
        personList.append(Person(4, 'Ignat_Olga', '0714243435', 'Motilor 78'))
        personList.append(Person(5, 'Cioata_Viorel', '0741235896', 'Zorilor 8'))
        personList.append(Person(6, 'Nituca_Razvan', '0741725869', 'Campului 105'))
        personList.append(Person(7, 'Florea_Raluca', '0796855875', 'Traian 52'))
        personList.append(Person(8, 'Todasca_Rebeca', '0722568696', 'Baladei 47'))
        personList.append(Person(9, 'Pascanu_Mircea', '0733002001', 'Dostoievski 21'))
        personList.append(Person(10, 'Lerda_Maricica', '0758693210', 'Repulicii 140'))
        return personList
    
    