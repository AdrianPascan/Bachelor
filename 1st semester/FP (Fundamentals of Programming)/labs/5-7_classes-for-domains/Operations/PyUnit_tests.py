import unittest
from Operations.functions import searchPersonInList, searchActivityInList,\
    personParticipatesToUpcoming, peopleInDescendingOrderByActivities, busiestDays,\
    activitiesForDay, activitiesForWeek, stringActivityList, stringPersonList
from Domain.activity import Activity
from Domain.person import Person


class SearchTest (unittest.TestCase):
    

    def setUp(self):
        unittest.TestCase.setUp(self)
        
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    
    def test_searchPersonInList(self):
        personList = []
        personList.append(Person(1, 'Pop_Adrian', '0745739772', 'aleea_Peana_13'))
        personList.append(Person(2, 'Luca_Iulian', '0743877389', 'aleea_Rucar_2'))
        personList.append(Person(3, 'Pindelea_Ionela', '0748966321', 'strada_Primaverii_14'))
        matchingList = searchPersonInList(personList, 'an')
        assert len(matchingList) == 2 and matchingList[0].get_id() == 1
        matchingList = searchPersonInList(personList, '66321')
        assert len(matchingList) == 1 and matchingList[0].get_id() == 3 
        
    
    def test_searchActivityInList(self):
        activityList = []
        activityList.append(Activity(4, [4,5], '12.04.2019', '00:10', 'rugby'))
        activityList.append(Activity(10, [1,2,3], '12.04.2019', '10:00', 'golf'))
        matchingList = searchActivityInList(activityList, 'g')
        assert len(matchingList) == 2 and matchingList[0].get_activity_id() == 4
        matchingList = searchActivityInList(activityList, '04.2019')
        assert len(matchingList) == 2 and matchingList[0].get_activity_id() == 4
        matchingList = searchActivityInList(activityList, '10:')
        assert len(matchingList) == 1 and matchingList[0].get_activity_id() == 10
        matchingList = searchActivityInList(activityList, '2019 00:')
        assert len(matchingList) == 1 and matchingList[0].get_activity_id() == 4
           

class PersonParticipatesToUpcomingTest (unittest.TestCase):
    

    def setUp(self):
        unittest.TestCase.setUp(self)
        
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    
    def test(self):
        personList = []
        personList.append(Person(1, None, None, None))
        activityList = []
        activityList.append(Activity(4, [4,5], '12.04.2025', '00:10', 'rugby'))
        activityList.append(Activity(10, [1,2,3], '12.04.2025', '10:00', 'golf'))
        participatesList = personParticipatesToUpcoming(activityList, personList, 1)
        assert len(participatesList) == 1 and participatesList[0].get_activity_id() == 10
        

class PeopleByActivitiesInDescendingOrderTest (unittest.TestCase):
    

    def setUp(self):
        unittest.TestCase.setUp(self)
        
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    
    def test(self):
        personList = []
        personList.append(Person(1, 'Pop_Adrian', '0745739772', 'aleea_Peana_13'))
        personList.append(Person(2, 'Luca_Iulian', '0743877389', 'aleea_Rucar_2'))
        personList.append(Person(3, 'Pindelea_Ionela', '0748966321', 'strada_Primaverii_14'))
        activityList = []
        activityList.append(Activity(4, [1,3], '12.04.2025', '00:10', 'rugby'))
        activityList.append(Activity(10, [1,2,3], '12.04.2025', '10:00', 'golf'))
        newList = peopleInDescendingOrderByActivities(activityList, personList)
        assert len(newList) == 3 and newList[0].get_id() == 1 and newList[1].get_id() == 3 and newList[2].get_id() == 2
        

class BusiestDaysTest (unittest.TestCase):
    

    def setUp(self):
        unittest.TestCase.setUp(self)
        
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    
    def test(self):
        activityList = []
        activityList.append(Activity(1, [1,2], '11.11.2025', '16:00', 'rugby'))
        activityList.append(Activity(2, [2,8], '10.12.2025', '08:00', 'tennis'))
        activityList.append(Activity(3, [1,8,9,10], '10.12.2025', '12:30', 'basketball'))
        activityList.append(Activity(4, [4,5,10], '11.11.2025', '14:45', 'football'))
        activityList.append(Activity(5, [3,6,7], '10.01.2026', '16:15', 'swimming'))
        activityList.append(Activity(6, [2,3,4], '20.02.2026', '18:55', 'baseball'))
        activityList.append(Activity(7, [1,2,3,4,5,6,7,8,9], '20.02.2026', '20:30', 'bowling'))
        activityList.append(Activity(8, [5,6,7,8], '11.11.2025', '22:00', 'football'))
        activityList.append(Activity(9, [1,8,9,10], '10.12.2025', '23:10', 'tennis'))
        activityList.append(Activity(10, [4,5,8,9], '11.11.2025', '17:00', 'golf'))
        busiestDaysList = busiestDays(activityList)
        assert len(busiestDaysList) == 4 and busiestDaysList[0] == '11.11.2025' and busiestDaysList[1] == '10.12.2025' and busiestDaysList[2] == '20.02.2026' and busiestDaysList[3] == '10.01.2026'
        

class ActivitiesForDayWeekTest(unittest.TestCase):
    

    def setUp(self):
        unittest.TestCase.setUp(self)
        
    
    def tearDown(self):
        unittest.TestCase.tearDown(self)
        
    
    def test_activitiesForDay(self):
        activityList = []
        activityList.append(Activity(1, [1,2], '11.11.2018', '16:00', 'rugby'))
        activityList.append(Activity(2, [2,8], '10.12.2018', '08:00', 'tennis'))
        activityList.append(Activity(3, [1,8,9,10], '10.12.2018', '12:30', 'basketball'))
        activityList.append(Activity(4, [4,5,10], '11.11.2018', '14:45', 'football'))
        activityList.append(Activity(5, [3,6,7], '10.12.2018', '09:15', 'swimming'))
        matchingList = activitiesForDay(activityList, '10.12.2018')
        assert len(matchingList) == 3 and matchingList[0].get_activity_id() == 2 and matchingList[1].get_activity_id() == 5 and matchingList[2].get_activity_id() == 3
        
        
    def test_activitiesForWeek(self):
        activityList = []
        activityList.append(Activity(1, [1,2], '11.11.2018', '16:00', 'rugby'))
        activityList.append(Activity(2, [2,8], '10.11.2018', '15:00', 'tennis'))
        activityList.append(Activity(3, [1,8,9,10], '05.12.2018', '12:30', 'basketball'))
        activityList.append(Activity(4, [4,5,10], '10.11.2018', '14:45', 'football'))
        activityList.append(Activity(5, [3,6,7], '05.01.2019', '09:15', 'swimming'))
        matchingList = activitiesForWeek(activityList, '08.11.2018')
        assert len(matchingList) == 3 and matchingList[0].get_activity_id() == 4 and matchingList[1].get_activity_id() == 2 and matchingList[2].get_activity_id() == 1
