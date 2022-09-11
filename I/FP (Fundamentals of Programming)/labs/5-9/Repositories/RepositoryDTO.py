from Exceptions.RepositoryException import RepositoryError
from MyDataStructure import filterBy, shellSort
from datetime import datetime


class RepositoryDTO:
    
    def __init__(self, personRepository, activityRepository):
        
        self._personRepository = personRepository
        self._activityRepository = activityRepository
        
    
    def removePerson(self, ID):
        
        self._personRepository.remove(ID)
        
        for activity in self._activityRepository._data:
            if ID in activity.personIDs:
                activity.personIDs.remove(ID)
       
       
    def storeActivity(self, activity):
        
        if self._activityRepository._find(activity.ID) != None:
            raise RepositoryError("Activity with ID " + activity.ID + " already exists.")
        
        for ID in activity.personIDs:
            if self._personRepository._find(ID) == None :
                raise RepositoryError("At least one person who participates to the activity doesn't exist (ID: " + str(ID) + ").\n")
    
        self._activityRepository._data.data.append(activity)
        
    
    def updateActivity(self, new):
        
        self._activityRepository.remove(new.ID)
        self.storeActivity(new)
        
        
    def personActivities (self):
        
        upcoming = filterBy( self._activityRepository._getAll(), lambda activity: self._upcoming(activity.date) )
        persons = self._personRepository._getAll()
        
        count = {}
        for person in persons:
            count[person.ID] = 0
        for activity in upcoming:
            for ID in activity.personIDs:
                count[ID] += 1
        
        shellSort( persons, lambda person1, person2: count[person1.ID] >= count[person2.ID] )
        
        return persons
    
    
    def _upcoming (self, dateString):
        # Checks whether a date is an upcoming one
        
        today = datetime.now()
        
        splitDate = dateString.split('.')
        year = int(splitDate[2])
        month = int(splitDate[1])
        day = int(splitDate[0])    
        
        if year > today.year or (year == today.year and month > today.month) or (year == today.year and month == today.month and day >= today.day):
            return True
        return False
                
