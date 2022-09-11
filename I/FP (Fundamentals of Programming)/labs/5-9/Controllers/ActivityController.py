from Domain.Activity import Activity
from Domain.StrictlyPositiveIntegerValidator import validateStrictlyPositiveInteger
from MyDataStructure import filterBy, shellSort
from datetime import date, datetime


class ActivityController:
    
    def __init__(self, activityValidator, activityRepository, repositoryDTO):
        self.__validator = activityValidator
        self.__repository = activityRepository
        self.__repositoryDTO = repositoryDTO
        
        
    def populate(self):
        
        self.__repository.populate()
    

    def create (self, ID, personIDs, date, time, description):
        
        activity = Activity(ID, personIDs, date, time, description)
        
        self.__validator.validate(activity)
        self.__repositoryDTO.storeActivity(activity)
        
            
    def remove (self, ID):
        
        ID = validateStrictlyPositiveInteger(ID)
        self.__repository.remove(ID)
        
    
    def update (self, ID, personIDs, date, time, description):
        
        new = Activity(ID, personIDs, date, time, description)
        
        self.__validator.validate(new)
        
        self.__repositoryDTO.updateActivity(new)
    
    
    def activities (self):
        
        return self.__repository._getAll()
    
    
    def search (self, string):
        
        activities = self.__repository._getAll()
        
        return filterBy(activities, lambda activity: any(string in data for data in [activity.date, activity.time, activity.date + " " + activity.time, activity.description]))
    
    
    def day (self, date):
        
        activities = self.__repository._getAll()
        
        matching = filterBy(activities, lambda activity: activity.date == date)
        shellSort(matching, lambda activity1, activity2: datetime.strptime(activity1.time, '%H:%M') <= datetime.strptime(activity2.time, '%H:%M') )
        
        return matching
    
    
    def week (self, date):
        
        try:
            date = datetime.strptime(date, "%d.%m.%Y")
            year = date.year
            week = date.isocalendar()[1]
        except ValueError:
            return []
        
        activities = self.__repository._getAll()
        
        matching = filterBy(activities, lambda activity: year == datetime.strptime(activity.date, "%d.%m.%Y").year and week == datetime.strptime(activity.date, "%d.%m.%Y").isocalendar()[1] )
        shellSort(matching, lambda activity1, activity2: ( datetime.strptime(activity1.date, "%d.%m.%Y") < datetime.strptime(activity2.date, "%d.%m.%Y") ) or ( activity1.date == activity2.date and datetime.strptime(activity1.time, '%H:%M') <= datetime.strptime(activity2.time, '%H:%M') ) )
        
        return matching
    
    
    def busiest (self):
        
        activities = self.__repository._getAll()
        
        upcoming = filterBy(activities, lambda activity: self.__repositoryDTO._upcoming(activity.date) )
        
        days = {}
        for activity in upcoming:
            if activity.date not in days.keys():
                days[activity.date] = 0
            days[activity.date] += 1
            
        shellSort(upcoming, lambda activity1, activity2: days[activity1.date] > days[activity2.date] ) 
        
        return upcoming
    
    
    def participates (self, ID):
        
        activities = self.__repository._getAll()
        
        return filterBy(activities, lambda activity: self.__repositoryDTO._upcoming(activity.date) and any( ID == str(personID) for personID in activity.personIDs ) )
    
    