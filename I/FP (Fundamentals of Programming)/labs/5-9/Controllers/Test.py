import unittest
from Repositories.PersonRepository import PersonRepository
from Repositories.ActivityRepository import ActivityRepository
from Repositories.RepositoryDTO import RepositoryDTO
from Controllers.PersonController import PersonController
from Domain.PersonValidator import ValidatePerson
from Domain.ActivityValidator import ValidateActivity
from Controllers.ActivityController import ActivityController
from Exceptions.ValidationException import ValidationError
from Exceptions.RepositoryException import RepositoryError


class Test(unittest.TestCase):


    def setUp(self):
        
        self.__personRepository = PersonRepository()
        self.__activityRepository = ActivityRepository()
        self.__repositoryDTO = RepositoryDTO(self.__personRepository, self.__activityRepository)
        self.__personValidator = ValidatePerson()
        self.__personController = PersonController(self.__personValidator, self.__personRepository, self.__repositoryDTO)
        self.__activityValidator = ValidateActivity()
        self.__activityController = ActivityController(self.__activityValidator, self.__activityRepository, self.__repositoryDTO)
        

    def tearDown(self):
        del self


    def test(self):
        
        #test "Add person"
        
        self.assertRaises(ValidationError, self.__personController.create, "id", "Pascan_Adrian", "0745111444", "Peana 145")
        self.assertRaises(ValidationError, self.__personController.create, "1", "Pascan", "0745111444", "Peana 145")
        self.assertRaises(ValidationError, self.__personController.create, "1", "Pascan_Adrian", "0745111", "Peana 145")
        self.assertRaises(ValidationError, self.__personController.create, "1", "Pascan_Adrian", "0745111444", "")
        
        self.__personController.create("1", "Pascan_Adrian", "0745111444", "Peana 145")
        self.assertEqual(len(self.__personRepository), 1)
        self.assertEqual(self.__personRepository._data[0].ID, 1)
        self.assertRaises(RepositoryError, self.__personController.create, "1", "Pascan_Adrian", "0745111444", "Peana 145")
        
        #test "Remove person" (1)
        
        self.assertRaises(RepositoryError, self.__personController.remove, "2")
        
        self.__personController.create("2", "Pascan_Adrian", "0745111444", "Peana 145")
        self.__personController.remove("2")
        self.assertEqual(len(self.__personRepository), 1)
        
        #test "Update person"
        
        self.assertRaises(RepositoryError, self.__personController.update, "2", "Bulai_Maricica", "0745899699", "Peana 125")
        
        self.__personController.update("1", "Bulai_Maricica", "0745899699", "Peana 125")
        self.assertEqual(len(self.__personRepository), 1)
        self.assertEqual(self.__personRepository._data[0].ID, 1)
        self.assertEqual(self.__personRepository._data[0].name, "Bulai_Maricica")
        
        #test "Add activity"
        
        self.assertRaises(ValidationError, self.__activityController.create, "id", ["1", "2", "3"], "15.05.2019", "14:00", "rugby")
        self.assertRaises(ValidationError, self.__activityController.create, "1", [], "15.05.2019", "14:00", "rugby")
        self.assertRaises(ValidationError, self.__activityController.create, "1", ["1", "2", "2"], "15.05.2019", "14:00", "rugby")
        self.assertRaises(ValidationError, self.__activityController.create, "1", ["1", "2", "3"], "155.2019", "14:00", "rugby")
        self.assertRaises(ValidationError, self.__activityController.create, "1", ["1", "2", "3"], "15.05.2019", "00", "rugby")
        self.assertRaises(ValidationError, self.__activityController.create, "1", ["1", "2", "3"], "15.05.2019", "14:00", "")
        self.assertRaises(RepositoryError, self.__activityController.create, "1", ["1", "4"], "15.05.2019", "14:00", "rugby")
        self.assertRaises(RepositoryError, self.__activityController.create, "1", ["1", "2", "3"], "15.05.2019", "14:00", "rugby")
        
        self.__personController.create("2", "Pascan_Adrian", "0745111444", "Peana 145")
        self.__personController.create("3", "Popescu_Nicolae", "0744022114", "Peana 100")
        self.__activityController.create("1", ["1", "2", "3"], "15.05.2019", "14:00", "rugby")
        self.assertEqual(len(self.__activityRepository), 1)
        self.assertEqual(self.__activityRepository._data[0].ID, 1)
        
        #test "Remove activity"
        
        self.assertRaises(RepositoryError, self.__activityController.remove, "2")
        
        self.__activityController.create("2", ["1", "2", "3"], "15.05.2019", "14:00", "rugby")
        self.__activityController.remove("2")
        self.assertEqual(len(self.__activityRepository), 1)
        
        #test "Update activity"
        
        self.assertRaises(RepositoryError, self.__activityController.update, "2", ["1", "2", "3"], "15.05.2019", "14:00", "rugby")
        
        self.__activityController.update("1", ["1", "2", "3"], "15.05.2019", "16:00", "rugby")
        self.assertEqual(len(self.__activityRepository), 1)
        self.assertEqual(self.__activityRepository._data[0].time, "16:00")
        
        #test "Remove person" (2)
        
        self.__personController.remove("1")
        self.assertEqual(self.__activityRepository._data[0].personIDs, [2, 3])


if __name__ == "__main__":
    unittest.main()