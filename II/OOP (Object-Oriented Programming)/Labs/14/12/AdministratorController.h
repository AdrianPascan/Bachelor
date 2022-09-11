#pragma once

#include "BaseRepository.h"
#include "Validator.h"
#include "Dog.h"
#include "Action.h"
#include "ActionAdd.h"
#include "ActionRemove.h"
#include "ActionUpdate.h"
#include "MyException.h"
#include<vector>
#include <memory>

class AdministratorController
{
private:
	BaseRepository * repository;
	std::vector<std::unique_ptr<Action>> actions;
	int indexAction;

public:
	//AdministratorController();
	AdministratorController(BaseRepository * repository);
	~AdministratorController();

	void addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph);
	void updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph);
	void deleteDog(char * name);
	std::vector<Dog> & getAllDogs();
	void setFilePath(char * filePath);

	void undo();
	void redo();
	void clearActions();
};

