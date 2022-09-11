from MyDataStructure import MyDataStructure
from Exceptions.RepositoryException import RepositoryError
import random
from Domain.Activity import Activity

class ActivityRepository():
    
    def __init__(self):
        
        self._data = MyDataStructure([])
        
        
    def _getAll (self):
        return self._data.getAll()
    
    
    def __len__(self):
        return len(self._data)
    
    
    def _find(self, ID):
        
        for index in range(len(self._data)):
            if self._data[index].ID == ID:
                return index
            
        return None
    
    
    def remove(self, ID):
        
        index = self._find(ID)
        
        if index == None:
            raise RepositoryError("Activity with ID " + str(ID) + " does not exist.")
        
        del self._data[index]
                    
    
    def populate(self):
        
        self._data.data = self.__populate100()
    
    
    def __populate100 (self):
    # Creates a random list of 100 activities
        activityList = []
        dateList = ["01.01.2019", "10.10.2020", "15.12.2019", "08.10.2020", "25.05.2021"]
        numbersList = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
        descriptionList = ['rugby', 'volleyball', 'basketball', 'football', 'cricket', 'hokey', 'baseball', 'tennis', 'bowling', 'golf']
        for ID in range(1, 101):
            personIDs = [0, 0, 0]
            while personIDs[0] == personIDs[1] or personIDs[0] == personIDs[2] or personIDs[1] == personIDs[2]:
                personIDs = [random.randrange(1, 100), random.randrange(1, 100), random.randrange(1, 100)]
            date = random.choice(dateList)
            time = random.choice(numbersList[:2]) + random.choice(numbersList) + ':' + random.choice(numbersList[0:6]) + random.choice(numbersList)
            description = random.choice(descriptionList)
            activityList.append(Activity(ID, personIDs, date, time, description))
        return activityList
    
    
    def __populate10 (self):
    
        activityList = []
        activityList.append(Activity(1, [1,2], '11.11.2018', '16:00', 'rugby'))
        activityList.append(Activity(2, [2,8], '10.12.2018', '08:00', 'tennis'))
        activityList.append(Activity(3, [1,8,9,10], '09.08.2018', '12:30', 'basketball'))
        activityList.append(Activity(4, [4,5,10], '06.01.2019', '14:45', 'football'))
        activityList.append(Activity(5, [3,6,7], '10.01.2019', '16:15', 'swimming'))
        activityList.append(Activity(6, [2,3,4], '20.02.2019', '18:55', 'baseball'))
        activityList.append(Activity(7, [1,2,3,4,5,6,7,8,9], '13.03.2019', '20:30', 'bowling'))
        activityList.append(Activity(8, [5,6,7,8], '17.01.2019', '22:00', 'football'))
        activityList.append(Activity(9, [1,8,9,10], '09.11.2018', '23:10', 'tennis'))
        activityList.append(Activity(10, [4,5,8,9], '14.04.2019', '17:00', 'golf'))
        return activityList
