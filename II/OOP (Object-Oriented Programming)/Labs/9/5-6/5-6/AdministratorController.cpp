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
}


AdministratorController::~AdministratorController()
{
}

void AdministratorController::addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph)
{
	int vaccinationsNumber = Validator::validatePositiveInteger(vaccinations);
	Dog newDog{ name, breed, birthDate,vaccinationsNumber,photograph };
	this->repository->add(newDog);
}

void AdministratorController::updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph)
{
	int newVaccinationsNumber = Validator::validatePositiveInteger(newVaccinations);
	Dog updatedDog{ name,newBreed, newBirthDate, newVaccinationsNumber, newPhotograph };
	this->repository->update(updatedDog);
}

void AdministratorController::deleteDog(char * name)
{
	Dog exitingDog{ name };
	this->repository->remove(exitingDog);
}

std::vector<Dog> & AdministratorController::getAllDogs()
{
	return this->repository->getAll();
}

void AdministratorController::setFilePath(char * filePath)
{
	this->repository->setFilePath(filePath, true, false);
}
