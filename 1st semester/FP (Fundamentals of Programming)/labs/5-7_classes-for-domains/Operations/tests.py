from Operations.functions import findPersonInList, findActivityInList,\
    addPersonToList, addActivityToList, removePersonFromList,\
    removeActivityFromList, updatePersonInList, updateActivityInList,\
    stringPersonList, stringActivityList, upcoming, undo, redo,\
    personParticipatesTo
from Domain.person import Person
from Domain.activity import Activity


def test_stringPersonList ():
    personList = []
    assert stringPersonList(personList) == 'List of people is void.'
    personList.append(Person(1, 'Pascan_Adrian', '0745711455', 'Aleea_Trandafirilor_1'))
    assert stringPersonList(personList) == 'Person list:\n1 : Pascan_Adrian : 0745711455 : Aleea_Trandafirilor_1\n'


def test_stringActivityList ():
    activityList = []
    assert stringActivityList(activityList) == 'List of activities is void.'
    activityList.append(Activity(4, [4,5], '12.04.2019', '00:10', 'rugby'))
    assert stringActivityList(activityList) == 'Activities list:\n4 : [4, 5] : 12.04.2019 : 00:10 : rugby\n'


def test_findPersonInList ():
    personList = []
    personList.append(Person(1, 'Pascan_Adrian', '0745711455', 'Aleea_Trandafirilor_1'))
    assert findPersonInList(personList, 1) == 0
    assert findPersonInList(personList, 9) == -1


def test_findActivityInList ():
    activityList = []
    activityList.append(Activity(4, [4,5], '12.04.2019', '00:10', 'rugby'))
    assert findActivityInList(activityList, 4) == 0
    assert findActivityInList(activityList, 1) == -1
    

def test_addPersonInList ():
    personList = []
    addPersonToList(personList, Person(11, 'Pop_Magdalena', '0745555100', 'Strada_Camplului_5'), [])
    assert len(personList) == 1 and personList[0].get_id() == 11
    
    
def test_addActivityToList ():
    personList = []
    personList.append(Person(1, 'Pop_Adrian', '0745739772', 'aleea_Peana_13'))
    personList.append(Person(2, 'Luca_Iulian', '0743877389', 'aleea_Rucar_2'))
    personList.append(Person(3, 'Pindelea_Ionela', '0748966321', 'strada_Primaverii_14'))
    activityList = []
    addActivityToList(activityList, personList, Activity(10, [1,2,3], '12.04.2019', '10:00', 'golf'))
    assert len(activityList) == 1 and activityList[0].get_activity_id() == 10


def test_removePersonFromList ():
    personList = []
    personList.append(Person(1, 'Pascan_Adrian', '0745711455', 'Aleea_Trandafirilor_1'))
    removePersonFromList(personList, [], 1)
    assert len(personList) == 0
    
    
def test_removeActivityFromList ():
    activityList = []
    activityList.append(Activity(4, [4,5], '12.04.2019', '00:10', 'rugby'))
    removeActivityFromList(activityList, 4)
    assert len(activityList) == 0
    
    
def test_updatePersonInList ():
    personList = []
    personList.append(Person(1, 'Pascan_Adrian', '0745711455', 'Aleea_Trandafirilor_1'))
    updatePersonInList(personList, [], 1, Person(3, 'Pindelea_Ionela', '0748966321', 'strada_Primaverii_14'))
    assert len(personList) == 1 and personList[0].get_id() == 3
    

def test_updateActivityInList ():
    activityList = []
    activityList.append(Activity(4, [4,5], '12.04.2019', '00:10', 'rugby'))
    updateActivityInList(activityList, 4, Activity(10, [1,2,4], '12.04.2019', '10:00', 'golf'))
    assert len(activityList) == 1 and activityList[0].get_activity_id() == 10
    

def test_upcoming ():
    assert upcoming('25.10.2025') == True
    assert upcoming('20.11.2018') == False


def test_undoRedo():
    personList = []
    activityList = []
    undoList = []
    redoList = []
    addPersonToList(personList, Person(15, None, None, None), activityList)
    undoList.append([removePersonFromList, personList, activityList, 15])
    assert len(personList) == 1
    undo(undoList, redoList, personList, activityList)
    assert len(undoList) == 0 and len(redoList) == 1 and len(personList) == 0 
    redo(undoList, redoList, personList, activityList)
    assert len(undoList) == 1 and len(redoList) == 0 and len(personList) == 1 and personList[0].get_id() == 15
    addActivityToList(activityList, personList, Activity(10, [15], None, None, None))
    undoList.append([removeActivityFromList, activityList, 10])
    assert len(activityList) == 1
    undo(undoList, redoList, personList, activityList)
    assert len(undoList) == 1 and len(redoList) == 1 and len(activityList) == 0
    redo(undoList, redoList, personList, activityList)
    assert len(undoList) == 2 and len(redoList) == 0 and len(activityList) == 1 and activityList[0].get_activity_id() == 10
    undoList.append([addPersonToList, personList, Person(15, None, None, None), activityList, personParticipatesTo(activityList, personList, 15)])
    removePersonFromList(personList, activityList, 15)
    assert len(personList) == 0
    undo(undoList, redoList, personList, activityList)
    assert len(undoList) == 2 and len(redoList) == 1 and len(personList) == 1 and personList[0].get_id() == 15
    redo(undoList, redoList, personList, activityList)
    assert len(undoList) == 3 and len(redoList) == 0 and len(personList) == 0
    undo(undoList, redoList, personList, activityList)
    redoList = []
    removeActivityFromList(activityList, 10)
    undoList.append([addActivityToList, activityList, personList, Activity(10, [15], None, None, None)])
    assert len(activityList) == 0
    undo(undoList, redoList, personList, activityList)
    assert len(undoList) == 2 and len(redoList) == 1 and len(activityList) == 1 and activityList[0].get_activity_id() == 10
    redo(undoList, redoList, personList, activityList)
    assert len(undoList) == 3 and len(redoList) == 0 and len(activityList) == 0
    undo(undoList, redoList, personList, activityList)
    redoList = []
    

def runTests ():
    test_stringPersonList()
    test_stringActivityList()
    test_findPersonInList()
    test_findActivityInList()
    test_addPersonInList()
    test_addActivityToList()
    test_removePersonFromList()
    test_removeActivityFromList()
    test_updatePersonInList()
    test_updateActivityInList()
    test_upcoming()
    test_undoRedo()

