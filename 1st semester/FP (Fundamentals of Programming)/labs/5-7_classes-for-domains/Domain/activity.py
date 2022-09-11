import datetime

class Activity ():
    
    def __init__(self, activityID, personIDs, date, time, description):
        # Constructor for Activity class
        self.__activityID = activityID
        self.__personIDs = personIDs
        self.__date = date
        self.__time = time
        self.__description = description


    def get_activity_id(self):
        return self.__activityID


    def get_person_ids(self):
        return self.__personIDs


    def get_date(self):
        return self.__date


    def get_time(self):
        return self.__time


    def get_description(self):
        return self.__description


    def set_activity_id(self, value):
        if type(value) != int or value <= 0:
            raise ValueError('ID must be a strictly positive integer.')
        self.__activityID = value


    def set_person_ids(self, value):
        if not(isinstance(value, list)):
            raise ValueError('PersonIDs must be a list.')
        for ID in value:
            if type(ID) != int or ID < 0:
                raise ValueError('IDs must be positive integers.')
        if len(value) > len(set(value)):
            raise ValueError('IDs must be unique.')
        self.__personIDs = value


    def set_date(self, value):
        try:
            datetime.datetime.strptime(value, '%d.%m.%Y')
        except ValueError: 
            raise ValueError('Date must be in this form: day.month.year .')
        splitDate = value.split('.')
        if len(splitDate[0]) == 1 or len(splitDate[1]) == 1:
            raise ValueError('If day/month is composed of one digit, it must be written in this form: 0x .')
        self.__date = value


    def set_time(self, value):
        try:
            datetime.datetime.strptime(value, '%H:%M')
        except ValueError:
            raise ValueError('Time must be in this form: hour:minute .')
        splitTime = value.split(':')
        if len(splitTime[0]) == 1 or len(splitTime[1]) == 1:
            raise ValueError('If hour/minute is composed of one digit, it must be written in this form: 0x .')
        self.__time = value


    def set_description(self, value):
        if type(value) != str or len(value) == 0 or value != value.strip():
            raise ValueError('Description must be a nonempty string.')
        self.__description = value

        
    def __str__(self):
        # Converts the Activity object into a string type.
        return '{} : {} : {} : {} : {}'.format(self.__activityID, self.__personIDs, self.__date, self.__time, self.__description)
    
    
    def __eq__(self, activity):
        # Checks if two instances are refering to the same activity.
        if self.get_activity_id() == activity.get_activity_id():
            return True
        return False
    
    
    activityID = property(get_activity_id, set_activity_id, None, None)
    personIDs = property(get_person_ids, set_person_ids, None, None)
    date = property(get_date, set_date, None, None)
    time = property(get_time, set_time, None, None)
    description = property(get_description, set_description, None, None)
    
def test_activity ():
    activity = Activity(None, None, None, None, None)
    activity.set_activity_id(14)
    activity.set_person_ids([1,5,7])
    activity.set_date('02.05.2017')
    activity.set_time('22:01')
    activity.set_description('football')
    assert activity.get_activity_id() == 14
    assert activity.get_person_ids() == [1,5,7]
    assert activity.get_date() == '02.05.2017'
    assert activity.get_time() == '22:01'
    assert activity.get_description() == 'football'
    
    assert activity.__str__() == '14 : [1, 5, 7] : 02.05.2017 : 22:01 : football'
    
    activity1 = Activity(14, None, None, None, None)
    assert activity.__eq__(activity1) == True
    activity1.set_activity_id(5)
    assert activity.__eq__(activity1) == False