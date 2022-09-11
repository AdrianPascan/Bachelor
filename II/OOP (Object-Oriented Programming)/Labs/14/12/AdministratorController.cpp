#pragma once

#include "AdministratorController.h"
#include "Validator.h"
#include <string.h>
#include <stdlib.h>


//AdministratorController::AdministratorController()
//{
//}

AdministratorController::AdministratorController(BaseRepository * repository)
{
	this->repository = repository;
	indexAction = 0;
}


AdministratorController::~AdministratorController()
{
}

void AdministratorController::addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph)
{
	int vaccinationsNumber = Validator::validatePositiveInteger(vaccinations);
	Dog newDog{ name, breed, birthDate,vaccinationsNumber,photograph };
	this->repository->add(newDog);

	actions.erase(actions.begin() + indexAction, actions.end());
	indexAction++;

	actions.push_back(std::make_unique <ActionAdd>(repository, newDog));
}

void AdministratorController::updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph)
{
	int newVaccinationsNumber = Validator::validatePositiveInteger(newVaccinations);
	Dog updatedDog{ name,newBreed, newBirthDate, newVaccinationsNumber, newPhotograph };
	Dog oldDog{ this->repository->update(updatedDog) };

	actions.erase(actions.begin() + indexAction, actions.end());
	indexAction++;

	actions.push_back(std::make_unique <ActionUpdate>(repository, updatedDog, oldDog));
}

void AdministratorController::deleteDog(char * name)
{
	Dog exitingDog{ name };
	Dog oldDog{ this->repository->remove(exitingDog) };

	actions.erase(actions.begin() + indexAction, actions.end());
	indexAction++;

	actions.push_back(std::make_unique <ActionRemove>(repository, oldDog));
}

std::vector<Dog> & AdministratorController::getAllDogs()
{
	return this->repository->getAll();
}

void AdministratorController::setFilePath(char * filePath)
{
	this->repository->clear(false);
	this->repository->setFilePath(filePath, true, false);
}

void AdministratorController::undo()
{
	if (indexAction == 0) {
		throw MyException{ "There are no actions left to be undone." };
	}

	actions[--indexAction].get()->undo();
}

void AdministratorController::redo()
{
	if (indexAction == actions.size()) {
		throw MyException{ "There are no actions left to be redone." };
	}
	
	actions[indexAction++].get()->redo();
}

void AdministratorController::clearActions()
{
	actions.clear();
	indexAction = 0;
}
