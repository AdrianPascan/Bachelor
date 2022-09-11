from Domain.person import Person
from Domain.activity import Activity
from datetime import datetime, date


def stringPersonList (personList):
    # Converts the list of people into a string
    if len(personList) == 0:
        return 'List of people is void.'
    string = 'Person list:\n'
    for person in personList:
        string += person.__str__() + '\n'
    return string


def stringActivityList (activityList):
    # Converts the list of activities into a string
    if len(activityList) == 0:
        return 'List of activities is void.'
    string = 'Activities list:\n'
    for activity in activityList:
        string += activity.__str__() + '\n'
    return string


def findPersonInList (personList, ID):
    # Finds a person in list by ID and returns its index (-1 if it does not exist)
    for index in range(len(personList)):
        if personList[index].get_id() == ID :
            return index
    return -1


def findActivityInList (activityList, ID):
    # Finds an activity in list by ID and returns its index (-1 if it does not exist)
    for index in range(len(activityList)):
        if activityList[index].get_activity_id() == ID :
            return index
    return -1


def addPersonToList (personList, person, activityList, participatesList = []):
    # Appends a new person to list if it does not exist already
    if findPersonInList(personList, person.get_id()) != -1:
        raise ValueError ('There exists a person with the same ID already!')
    personList.append(person)
    for activity in activityList:
        if activity in participatesList:
            activity.get_person_ids().append(person.get_id())


def validateDateTime (activityList, activity):
    # Checks if an activity overlaps with the already existent ones
    for index in range(len(activityList)):
        if activityList[index].get_date() == activity.get_date() and activityList[index].get_time() == activity.get_time():
            raise ValueError ('Activity overlaps!')
        
def validatePersonIDsList(personList, personIDsList):
    # Checks if the people attending an activity exist
    for index in range(len(personIDsList)):
        if findPersonInList(personList, personIDsList[index]) == -1:
            raise ValueError ('At least one of the people does not exist.')


def addActivityToList (activityList, personList, activity):
    # Adds a new activity to list if it is valid (does not overlap and the people attending exist)
    validateDateTime(activityList, activity)
    validatePersonIDsList(personList, activity.get_person_ids())
    activityList.append(activity)


def removePersonFromList (personList, activityList, ID):
    # Removes a person with a given ID from the list if it exists
    index = findPersonInList(personList, ID)
    if index == -1:
        raise ValueError('Person does not exist.')
    for currentActivity in range(len(activityList)):
        if personList[index].get_id() in activityList[currentActivity].get_person_ids():
            activityList[currentActivity].get_person_ids().pop(activityList[currentActivity].get_person_ids().index(personList[index].get_id()))
    personList.pop(index)


def removeActivityFromList (activityList, ID):
    # Removes an activity with a given ID from the list if it exists
    index = findActivityInList(activityList, ID)
    if index == -1:
        raise ValueError('Activity does not exist.')
    activityList.pop(index)


def updatePersonInList (personList, activityList, oldPersonID, newPerson):
    # Updates a person with a given ID in the list if it exists
    index = findPersonInList(personList, oldPersonID)
    if index == -1:
        raise ValueError('Person does not exist.')
    for currentActivity in range(len(activityList)):
        if personList[index].get_id() in activityList[currentActivity].get_person_ids():
            activityList[currentActivity].get_person_ids()[activityList[currentActivity].get_person_ids().index(personList[index].get_id())] = newPerson.get_id()
    personList[index] = newPerson


def updateActivityInList (activityList, oldActivityID, newActivity):
    # Updates an activity with a given ID if it exists
    index = findActivityInList(activityList, oldActivityID)
    if index == -1:
        raise ValueError('Activity does not exist!')
    activityList[index] = newActivity
    

def searchPersonInList (personList, string):
    # Searches for a person in a the list by name, phone number or a part of these two
    matchingList = []
    for index in range(len(personList)):
        if string.lower() in personList[index].get_name().lower() or string in personList[index].get_phone_number():
            matchingList.append(personList[index])
    return matchingList
            
            
def searchActivityInList (activityList, string):
    # Searches for an activity in the list by date, time, description or a part of these three
    matchingList = []
    for index in range(len(activityList)):
        if string in activityList[index].get_date() or string in activityList[index].get_time() or string in (activityList[index].get_date() + ' ' + activityList[index].get_time()) or string.lower() in activityList[index].get_description().lower():
            matchingList.append(activityList[index])
    return matchingList


def addInListDescendingOrder(_list, element):
    # Returns the index where an element should be added in the list in order to maintain the descending order
    index = 0
    while (index < len(_list) and _list[index] >= element):
        index += 1
    return index


def activitiesForDay (activityList, date):
    # Returns the activities for a given day, in the order of their start time
    matchingList = []
    for activity in activityList:
        if activity.get_date() == date:
            index = 0
            while index < len(matchingList) and datetime.strptime(activity.get_time(), '%H:%M') > datetime.strptime(matchingList[index].get_time(), '%H:%M'):
                index += 1
            matchingList.insert(index, activity)
    return matchingList
        #datetime.strptime(activity.get_date(), '%d.%m.%y')


def activitiesForWeek (activityList, dateString):
    # Returns the activities for a given week, in the order of their date/time
    matchingList = []
    week = date(int(dateString.split('.')[2]), int(dateString.split('.')[1]), int(dateString.split('.')[0])).isocalendar()[1]
    year = int(dateString.split('.')[2])
    for activity in activityList:
        if date(int(activity.get_date().split('.')[2]), int(activity.get_date().split('.')[1]), int(activity.get_date().split('.')[0])).isocalendar()[1] == week and int(activity.get_date().split('.')[2]) == year:
            index = 0
            while index < len(matchingList) and datetime.strptime(activity.get_date(), '%d.%m.%Y') >= datetime.strptime(matchingList[index].get_date(), '%d.%m.%Y') and datetime.strptime(activity.get_time(), '%H:%M') > datetime.strptime(matchingList[index].get_time(), '%H:%M'):
                index += 1
            matchingList.insert(index, activity)
    return matchingList


def upcoming (dateString):
    # Checks whether a date is an upcoming one
    today = datetime.now()
    splitDate = dateString.split('.')
    year = int(splitDate[2])
    month = int(splitDate[1])
    day = int(splitDate[0])    
    if year > today.year or (year == today.year and month > today.month) or (year == today.year and month == today.month and day >= today.day):
        return True
    return False


def busiestDays(activityList):
    # Returns the days with activities in descending order of the number of activities in each day         
    daysList = []
    countActivitiesList = []
    for activity in activityList:
        if activity.get_date() in daysList:
            countActivitiesList[daysList.index(activity.get_date())] += 1
        elif upcoming(activity.get_date()) == True:
            daysList.append(activity.get_date())
            countActivitiesList.append(1)
    busiestDaysList = []
    orderedCountActivities = []
    for current in range(len(countActivitiesList)):
        index = addInListDescendingOrder(orderedCountActivities, countActivitiesList[current])
        orderedCountActivities.insert(index, countActivitiesList[current])
        busiestDaysList.insert(index, daysList[current])
    return busiestDaysList


def personParticipatesTo (activityList, personList, personID):
    # Returns the list of activities to which a given person participates
    if findPersonInList(personList, personID) == -1:
        raise ValueError('Person does not exist.')
    participatesList = []
    for activity in activityList:
        if personID in activity.get_person_ids():
            participatesList.append(activity)
    return participatesList


def personParticipatesToUpcoming (activityList, personList, personID):
    # Returns the upcoming activities to which a given person participates
    participatesList = personParticipatesTo(activityList, personList, personID)
    upcomingList = []
    for activity in participatesList:
        if upcoming(activity.get_date()):
            upcomingList.append(activity)
    return upcomingList


def peopleInDescendingOrderByActivities (activityList, personList):
    # Returns  all persons in the address book, sorted in descending order of the number of upcoming activities to which they will participate
    newList = []
    countActivitiesList = []
    for person in personList:
        countActivities = 0
        for activity in activityList:
            if upcoming(activity.get_date()) == True and person.get_id() in activity.get_person_ids():
                countActivities += 1
        index = addInListDescendingOrder(countActivitiesList, countActivities)
        countActivitiesList.insert(index, countActivities)
        newList.insert(index, person)
    return newList

def undo (undoList, redoList, personList, activityList):
    if len(undoList) == 0:
        raise ValueError('No more undos left.')
    command = undoList.pop()
    if command[0] == addPersonToList:
        ID = command[2].get_id()
        redoList.append([removePersonFromList, personList, activityList, ID])
    elif command[0] == addActivityToList:
        ID = command[3].get_activity_id()
        redoList.append([removeActivityFromList, activityList, ID])
    elif command[0] == removePersonFromList:
        person = personList[findPersonInList(personList, command[3])]
        participatesList = personParticipatesTo(activityList, personList, command[3])
        redoList.append([addPersonToList, personList, person, activityList, participatesList])
    elif command[0] == removeActivityFromList:
        activity = activityList[findActivityInList(activityList, command[2])]
        redoList.append([addActivityToList, activityList, personList, activity])
    elif command[0] == updatePersonInList:
        oldPersonID = command[4].get_id()
        newPerson = personList[findPersonInList(personList, command[3])]
        redoList.append([updatePersonInList, personList, activityList, oldPersonID, newPerson])
    elif command[0] == updateActivityInList:
        oldActivityID = command[3].get_activity_id()
        newActivity = activityList[findActivityInList(activityList, command[2])]
        redoList.append([updateActivityInList, activityList, oldActivityID, newActivity])
    command[0](*command[1:])
    

def redo (undoList, redoList, personList, activityList):
    if len(redoList) == 0:
        raise ValueError('No more redos left.')
    command = redoList.pop()
    if command[0] == addPersonToList:
        ID = command[2].get_id()
        undoList.append([removePersonFromList, personList, activityList, ID])
    elif command[0] == addActivityToList:
        ID = command[3].get_activity_id()
        undoList.append([removeActivityFromList, activityList, ID])
    elif command[0] == removePersonFromList:
        person = personList[findPersonInList(personList, command[3])]
        participatesList = personParticipatesTo(activityList, personList, command[3])
        undoList.append([addPersonToList, personList, person, activityList, participatesList])
    elif command[0] == removeActivityFromList:
        activity = activityList[findActivityInList(activityList, command[2])]
        undoList.append([addActivityToList, activityList, personList, activity])
    elif command[0] == updatePersonInList:
        oldPersonID = command[4].get_id()
        newPerson = personList[findPersonInList(personList, command[3])]
        undoList.append([updatePersonInList, personList, activityList, oldPersonID, newPerson])
    elif command[0] == updateActivityInList:
        oldActivityID = command[3].get_activity_id()
        newActivity = activityList[findActivityInList(activityList, command[2])]
        undoList.append([updateActivityInList, activityList, oldActivityID, newActivity])
    command[0](*command[1:])
    
