class ConsoleUI:
    
    def __init__(self, personController, activityController):
        self.__personController = personController
        self.__activityController = activityController
    
    
    def start(self):
        
        self.__personController.populate()
        self.__activityController.populate()
        
        commands = {"1": self.__addPerson, "2": self.__addActivity, "3": self.__removePerson, "4": self.__removeActivity, \
                    "5": self.__updatePerson, "6": self.__updateActivity, "7": self.__listPersons, "8": self.__listActivities, \
                    "9": self.__searchPerson, "10": self.__searchActivity, "11": self.__activitiesDay, "12": self.__activitiesWeek, \
                    "13": self.__busiest, "14": self.__personParticipates, "15": self.__personActivities}
        
        while True:
            
            command = self.__readCommand()
            
            if command == "0":
                print("\nApplication has been closed.")
                return
            elif command in commands.keys():
#                 try:
                commands[command]()
#                 except Exception as errors:
#                     print ("\nSome errors occured:\n" + str(errors))
            else:
                print("\nInvalid command.")
        
        
    def __readCommand(self):
        
        print("______________________________________________________________________________________________________________")
        print("Menu:")
        print("\t 1 - Add person")
        print("\t 2 - Add activity")
        print("\t 3 - Remove person")
        print("\t 4 - Remove activity")
        print("\t 5 - Update person")
        print("\t 6 - Update activities")
        print("\t 7 - List persons")
        print("\t 8 - List activities")
        print("\t 9 - Search for person by name / phone number (or a part of these 2)")
        print("\t 10 - Search for activity by date / time (date time) or description (or a part of these 3)")
        print("\t 11 - Activities for a day")
        print("\t 12 - Activities for a week")
        print("\t 13 - Busiest upcoming days")
        print("\t 14 - Upcoming activities to which a person will participate")
        print("\t 15 - Persons in descending order of the number of upcoming activities to which they will participate")
        print("\t 0 - Exit")
        
        return input("Give command code: >> ")
    
    
    def __addPerson(self):
        
        print("\nPerson:")
        ID = input("\t > ID: ")
        name = input("\t > name (LastName_FirstName1-FirstName2-...) : ")
        phoneNumber = input("\t > phone_number (07xxxxxxxx) : ")
        address = input("\t > address: ")
        
        self.__personController.create(ID, name, phoneNumber, address)
    
    
    def __addActivity(self):
        
        print("\nActivity: ")
        ID = input("\t > ID: ")
        #
        print("\t > person_IDs: (input as many as you want; when you finish, type '0')")
        personIDs= []
        while True:
            personIDs.append( input("\t\t >> ") )
            if personIDs[len(personIDs)-1] == "0":
                personIDs.pop()
                break
        #
        date = input("\t > date (dd.mm.yyyy) : ")
        time = input("\t > time (hh:mm) : ")
        description = input("\t > description: ")
            
        self.__activityController.create(ID, personIDs, date, time, description)
    
    
    def __removePerson(self):
        
        ID = input("\nPerson's ID: ")
        
        self.__personController.remove(ID)
    
    
    def __removeActivity(self):
        
        ID = input("\nID of activity: ")
        
        self.__activityController.remove(ID)
    
    
    def __updatePerson(self):
        
        print("\nPerson:")
        ID = input("\t > ID: ")
        name = input("\t > name (LastName_FirstName1-FirstName2-...) : ")
        phoneNumber = input("\t > phone_number (07xxxxxxxx) : ")
        address = input("\t > address: ")
        
        self.__personController.update(ID, name, phoneNumber, address)
    
    
    def __updateActivity(self):
        
        print("\nActivity: ")
        ID = input("\t > ID: ")
        #
        print("\t > person_IDs: (input as many as you want; when you finish, type '0')")
        personIDs= []
        while True:
            personIDs.append( input("\t\t >> ") )
            if personIDs[len(personIDs)-1] == "0":
                personIDs.pop()
                break
        #
        date = input("\t > date (dd.mm.yyyy) : ")
        time = input("\t > time (hh:mm) : ")
        description = input("\t > description: ")
        
        self.__activityController.update(ID, personIDs, date, time, description)
    
    
    def __listPersons(self):
        
        self.__list(self.__personController.persons(), "Empty person repository.", "PERSONS")
    
    
    def __listActivities(self):
        
        self.__list(self.__activityController.activities(), "Empty activity repository.", "ACTIVITIES")
    
    
    def __searchPerson(self):
        
        string = input("Search by: >> ")
        
        self.__list(self.__personController.search(string), "No matching persons found.", "Matching PERSONS")
    
    
    def __searchActivity(self):
        
        string = input("Search by: >> ")
        
        self.__list(self.__activityController.search(string), "No matching activities found.", "Matching ACTIVITIES")
    
    
    def __activitiesDay(self):
        
        date = input("Date (dd.mm.yyyy) : >> ")
        
        self.__list(self.__activityController.day(date), "No matching activities found.", "ACTIVITIES for " + date)
    
    
    def __activitiesWeek(self):
        
        date = input("Date (dd.mm.yyyy) : >> ")
        
        self.__list(self.__activityController.week(date), "No matching activities found.", "ACTIVITIES for the week corresponding to " + date)
    
    
    def __busiest(self):
        
        self.__list(self.__activityController.busiest(), "Empty activity repository.", "ACTIVITIES")
    
    
    def __personParticipates(self):
        
        ID = input("Person's ID: >> ")
        
        self.__list(self.__activityController.participates(ID), "No matching activities found.", "ACTIVITIES to which the person will partcipate")
    
    
    def __personActivities(self):
        
        self.__list(self.__personController.personActivities(), "Empty person repository.", "PERSONS in descending order by activities")
    
    
    def __list (self, data, emptyMessage, message):
        
        if len(data) == 0:
            print("\n" + emptyMessage)
        else:
            print("\n" + message + ":\n")
            for element in data:
                print("\t" + str(element))
            
    
