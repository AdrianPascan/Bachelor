from Domain.person import Person
from Domain.activity import Activity
from Operations.functions import stringPersonList, stringActivityList
import pickle
from Domain import person
import random


def populatePersonList ():
    # Creates a random list of 100 persons
    personList = []
    lastNameList = ['Popescu', 'Aelenei', 'Iuga', 'Pascan', 'Bulai', 'Candrea', 'Doroftiese', 'Morosanu', 'Danea', 'Florea']
    firstNameList = ['Adrian', 'Diana', 'Ruxandra', 'Olga', 'Maricica', 'Mircea', 'Luca', 'Ciprian', 'Bogdan', 'Iulian'] 
    numbersList = ['48', '75', '14', '10', '99', '66', '70', '39', '00', '55']
    addressList = [['Trandafirilor', 'Peana', 'Kogalniceanu', 'Primaverii', 'Petreni', 'Transilvaniei', 'Chilia', 'Oltului', 'Rucar', 'Islazului'],\
                   ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '']]
    for ID in range(1,101):
        name = random.choice(lastNameList) + '_' + random.choice(firstNameList)
        phoneNumber = '07' + random.choice(numbersList) + random.choice(numbersList) + random.choice(numbersList) + random.choice(numbersList)
        address = random.choice(addressList[0]) + ' ' + random.choice(addressList[1][1:10]) + random.choice(addressList[1])
        personList.append(Person(ID, name, phoneNumber, address))
    return personList
    

def populateActivityList ():
    # Creates a random list of 100 activities
    activityList = []
    numbersList = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']
    descriptionList = ['rugby', 'volleyball', 'basketball', 'football', 'cricket', 'hokey', 'baseball', 'tennis', 'bowling', 'golf']
    for ID in range(1, 101):
        personIDs = [0, 0, 0]
        while personIDs[0] == personIDs[1] or personIDs[0] == personIDs[2] or personIDs[1] == personIDs[2]:
            personIDs = [random.randrange(1, 100), random.randrange(1, 100), random.randrange(1, 100)]
        date = random.choice(numbersList[0:3]) + random.choice(numbersList[1:9]) + '.0' + random.choice(numbersList[1:10]) + '.20' + random.choice(numbersList[1:3]) + random.choice(numbersList)
        time = random.choice(numbersList[:2]) + random.choice(numbersList) + ':' + random.choice(numbersList[0:6]) + random.choice(numbersList)
        description = random.choice(descriptionList)
        activityList.append(Activity(ID, personIDs, date, time, description))
    return activityList


def readPersonsFromTextFile(fileName):
    personList = []
    try:
        file0 = open(fileName, "r")
        line = file0.readline().strip().split(":")
        while not(len(line) == 1 and line[0] == ''):  
            try:
                person = Person(None, None, None, None)
                person.set_id(int(line[0].strip()))
                person.set_name(line[1].strip())
                person.set_phone_number(line[2].strip())
                person.set_address(line[3].strip())
                personList.append(person)
            except ValueError as error:
                print("Could not validate person with id ", line[0].strip(), ": ", error)
            line = file0.readline().strip().split(":")
        file0.close()
    except IOError as error:
        print("Input error: ", error)
    return personList


def readActivitiesFromTextFile(fileName):
    activityList = []
    try:
        file0 = open(fileName, "r")
        line = file0.readline().strip().split(":")
        while not(len(line) == 1 and line[0] == ''):  
            try:
                activity = Activity(None, None, None, None, None)
                activity.set_activity_id(int(line[0].strip()))
                line[1] = line[1].strip()
                line[1] = line[1][1:(len(line[1])-1)].split(",")
                personsIDs = []
                for ID in line[1]:
                    personsIDs.append(int(ID.strip()))
                activity.set_person_ids(personsIDs)
                activity.set_date(line[2].strip())
                activity.set_time(line[3].strip() + ":" + line[4].strip())
                activity.set_description(line[5].strip())
                activityList.append(activity)
            except ValueError as error:
                print("Could not validate person with id ", line[0].strip(), ": ", error)
            line = file0.readline().strip().split(":")
        file0.close()
    except IOError as error:
        print("Input error: ", error)
    return activityList


def writePersonsToTextFile(filename, personList):
    try:
        file0 = open(filename, "w")
        file0.write(stringPersonList(personList))
        file0.close()
    except Exception as error:
        print("Output error: " + error)


def writeActivitiesToTextFile(filename, activityList):
    try:
        file0 = open(filename, "w")
        file0.write(stringActivityList(activityList))
        file0.close()
    except Exception as error:
        print("Output error: " + error)


def readPersonsFromBinaryFile (fileName):
    try:
        file0 = open(fileName, "rb") 
        personList = pickle.load(file0)
        file0.close()
        return personList
    except EOFError:
        return personList
    except IOError as error:
        print("Input error: ", error)


def readActivitiesFromBinaryFile (fileName):
    try:
        file0 = open(fileName, "rb")
        activityList = pickle.load(file0)
        file0.close()
        return activityList
    except EOFError:
        return activityList
    except IOError as error:
        print("Input error: ", error)
        
        
def writePersonsToBinaryFile (fileName, personList):
    file0 =open(fileName, "wb")
    pickle.dump(personList, file0)
    file0.close()


def writeActivitiesToBinaryFile (fileName, activityList):
    file0 =open(fileName, "wb")
    pickle.dump(activityList, file0)
    file0.close()
