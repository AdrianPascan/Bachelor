from Domain.PersonValidator import ValidatePerson
from Domain.ActivityValidator import ValidateActivity
from Repositories.PersonRepository import PersonRepository
from Repositories.ActivityRepository import ActivityRepository
from Controllers.PersonController import PersonController
from Controllers.ActivityController import ActivityController
from UI import ConsoleUI
from Repositories.RepositoryDTO import RepositoryDTO

personValidator = ValidatePerson()
activityValidator = ValidateActivity()

personRepository = PersonRepository()
activityRepository = ActivityRepository()
repositoryDTO = RepositoryDTO(personRepository, activityRepository)

personController = PersonController(personValidator, personRepository, repositoryDTO)
activityController = ActivityController(activityValidator, activityRepository, repositoryDTO)

console = ConsoleUI(personController, activityController)

if __name__ == "__main__":
    console.start()

