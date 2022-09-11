import datetime
from Domain.person import Person
from Domain.activity import Activity
from Operations.functions import addPersonToList, addActivityToList,\
    removePersonFromList, removeActivityFromList, updatePersonInList,\
    updateActivityInList, stringPersonList, stringActivityList, findPersonInList,\
    searchPersonInList, searchActivityInList, personParticipatesToUpcoming,\
    activitiesForDay, activitiesForWeek, peopleInDescendingOrderByActivities,\
    busiestDays, undo, redo, findActivityInList, personParticipatesTo
from Settings.properties import readPersonsFromTextFile, populatePersonList,\
    populateActivityList, readActivitiesFromTextFile, readPersonsFromBinaryFile,\
    readActivitiesFromBinaryFile, writePersonsToTextFile,\
    writeActivitiesToBinaryFile, writePersonsToBinaryFile,\
    writeActivitiesToTextFile

def getMenu ():
    menu = 'Available commands:\n'
    menu += '\t1. add person\n'
    menu += '\t2. add activity\n'
    menu += '\t3. remove person\n'
    menu += '\t4. remove activity\n'
    menu += '\t5. update person\n'
    menu += '\t6. update activity\n'
    menu += '\t7. list persons\n'
    menu += '\t8. list activities\n'
    menu += '\t9. search for person by name / phone number (or a part of these 2)\n'
    menu += '\t10. search for activity by date / time (date time) or description (or a part of these 3)\n'
    menu += '\t11. activities for a day\n'
    menu += '\t12. activities for a week\n'
    menu += '\t13. busiest upcoming days\n'
    menu += '\t14. upcoming activities to which a person will participate\n'
    menu += '\t15. persons in descending order of the number of upcoming activities to which they will participate\n'
    menu += '\t16. undo\n'
    menu += '\t17. redo\n'
    menu += '\t0. exit\n'
    return menu

    
def runMenu ():
    methodCode = readStorageMethod()
    storage = initialiseListsByStorageMethod(methodCode)
    personList = storage[0][:]
    activityList = storage[1][:]
    undoList = []
    redoList = []
    print(getMenu())
    stop = False
    while stop == False:
        command = input('Input command:\n\t > ') 
        if command == '0':
            stop = True
            print ('Application has been closed.')
            if methodCode == 2:
                writePersonsToTextFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/persons.txt", personList)
                writeActivitiesToTextFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/activities.txt", activityList)
            elif methodCode == 3:
                writePersonsToBinaryFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/persons.pickle", personList)
                writeActivitiesToBinaryFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/activities.pickle", activityList)
        else:
            try:
                if command == '1':
                    person = readPerson('Person')
                    addPersonToList(personList, person, activityList)
                    IdUndo = person.get_id()
                    undoList.append([removePersonFromList, personList, activityList, IdUndo])
                    redoList = []
                elif command == '2':
                    activity = readActivity(personList, 'Activity')
                    addActivityToList(activityList, personList, activity)
                    IdUndo = activity.get_activity_id()
                    undoList.append([removeActivityFromList, activityList, IdUndo])
                    redoList = []
                elif command == '3':
                    ID = readID('Person ')
                    personUndo = personList[findPersonInList(personList, ID)]
                    undoList.append([addPersonToList, personList, personUndo, activityList, personParticipatesTo(activityList, personList, ID)])
                    removePersonFromList(personList, activityList, ID)
                    redoList = []
                elif command == '4':
                    ID = readID('Activity ')
                    activityUndo = activityList[findActivityInList(activityList, ID)]
                    undoList.append([addActivityToList, activityList, personList, activityUndo])
                    removeActivityFromList(activityList, ID)
                    redoList = []
                elif command == '5':
                    oldPersonID = readID('Old person ')
                    newPerson = readPerson('New person')
                    oldPersonIdUndo = newPerson.get_id()
                    newPersonUndo = personList[findPersonInList(personList, oldPersonID)]
                    undoList.append([updatePersonInList, personList, activityList, oldPersonIdUndo, newPersonUndo])
                    updatePersonInList(personList, activityList, oldPersonID, newPerson)
                    redoList = []
                elif command == '6':
                    oldActivityID = readID('Old activity ')
                    newActivity = readActivity(personList, 'New activity')
                    oldActivityIdUndo = newActivity.get_activity_id()
                    newActivityUndo = activityList[findActivityInList(activityList, oldActivityID)]
                    undoList.append([updateActivityInList, activityList, oldActivityIdUndo, newActivityUndo])
                    updateActivityInList(activityList, oldActivityID, newActivity)
                    redoList = []
                elif command == '7':
                    print(stringPersonList(personList))
                elif command == '8':
                    print(stringActivityList(activityList))
                elif command == '9':
                    string = input('Name, phone number or a part of these two: ')
                    print(stringPersonList(searchPersonInList(personList, string)))
                elif command == '10':
                    string = input('Date/time, description or a part of these three: ')
                    print(stringActivityList(searchActivityInList(activityList, string)))
                elif command == '11':
                    date = readDate()
                    print(stringActivityList(activitiesForDay(activityList, date)))
                elif command == '12':
                    date = readDate()
                    print(stringActivityList(activitiesForWeek(activityList, date)))
                elif command == '13':
                    if len(activityList) == 0:
                        raise ValueError('List of activities is void.')
                    days = busiestDays(activityList)
                    print('Busiest days:')
                    for day in days:
                        print(' ' + day)
                elif command == '14':
                    personID = readID('Person ')
                    print(stringActivityList(personParticipatesToUpcoming(activityList, personList, personID)))
                elif command == '15':
                    print(stringPersonList(peopleInDescendingOrderByActivities(activityList, personList)))
                elif command == '16':
                    undo(undoList, redoList, personList, activityList)
                elif command == '17':
                    redo(undoList, redoList, personList, activityList)
                else:
                    print ('Invalid command.')
            except ValueError as error:
                print(error)


def readStorageMethod ():
    print("Which storage method do you want to use?\n 1. in-memory\n 2. text files\n 3. binary files")
    while True:
        choice = input("\t > ")
        if choice in ["1", "2", "3"]:
            return int(choice)
        print("Invalid command.")
        

def initialiseListsByStorageMethod(methodCode):
    if methodCode == 1:
        personList = populatePersonList()
        activityList = populateActivityList()
    elif methodCode == 2:
        personList = readPersonsFromTextFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/persons.txt")
        activityList = readActivitiesFromTextFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/activities.txt")
    else:
        personList = readPersonsFromBinaryFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/persons.pickle")
        activityList = readActivitiesFromBinaryFile("C:/Users/Adrian/eclipse-workspace/assignment_5-7(classes_for_domains)/activities.pickle")
    return [personList, activityList]


def readDate ():
    while True:
        try:
            date = input('Date: ')
            datetime.datetime.strptime(date, '%d.%m.%Y')
            return date
        except ValueError:
            print('Date must be in this form: day.month.year .')


def readStrictlyPositiveInteger (message):
    number = None
    while True:
        try:
            number = int(input(message + ': '))
            if number <= 0: 
                raise ValueError
            return number
        except ValueError:
            print(message + ' must be a strictly positive integer.')


def readID (message):
    return readStrictlyPositiveInteger(message + 'ID')

            
def readPerson(message):
    person = Person(None, None, None, None)
    while True:
        try:
            print(message)
            person.set_id(readID(''))
            person.set_name(input('Name (LastName_FirstName1-FirstName2-...): '))
            person.set_phone_number(input('Phone number (0xxxxxxxxx): '))
            person.set_address(input('Address: '))
            return person
        except ValueError as error:
            print(error)


def readNumberOfIDs (personList):
    number = None
    while True:
        number = readStrictlyPositiveInteger('Number of IDs')
        if number > len(personList):
            print('There are not as many people in list as you wish to input.')
        else:
            return number


def readPersonIDs (personList):
    personIDs = []
    numberOfIDs = readNumberOfIDs(personList)
    for currentPerson in range(1, numberOfIDs+1):
        while len(personIDs) != currentPerson:
            currentID = readID('')
            if findPersonInList(personList, currentID) == -1:
                print('There is no person with such an ID.')
            else:
                personIDs.append(currentID)
    return personIDs


def readActivity(personList, message):
    activity = Activity(None, None, None, None, None)
    while True:
        try:
            print(message)
            activity.set_activity_id(readID(''))
            activity.set_person_ids(readPersonIDs(personList))
            activity.set_date(input('Date (day.month.year): '))
            activity.set_time(input('Time (hour:minute): '))
            activity.set_description(input('Description: '))
            return activity
        except ValueError as error:
            print(error)


def createPersonList ():
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


def createActivityList ():
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
