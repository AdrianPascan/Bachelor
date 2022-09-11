class Activity(object):
    
    def __init__(self, activityID, personIDs, date, time, description):
        # Constructor for Activity class
        self.__activityID = activityID
        self.__personIDs = personIDs
        self.__date = date
        self.__time = time
        self.__description = description

    
    @property
    def ID(self):
        return self.__activityID
    
    @ID.setter
    def ID(self, value):
        self.__activityID = value

    
    @property
    def personIDs(self):
        return self.__personIDs
    
    @personIDs.setter
    def personIDs(self, value):
        self.__personIDs = value

    
    @property
    def date(self):
        return self.__date

    
    @property
    def time(self):
        return self.__time

    
    @property
    def description(self):
        return self.__description

    
    def __str__(self):
        return "activity {} - persons_IDs: {}, date: {}, time: {}, description: {}"\
            .format(self.ID, self.personIDs, self.date, self.time, self.description)
    