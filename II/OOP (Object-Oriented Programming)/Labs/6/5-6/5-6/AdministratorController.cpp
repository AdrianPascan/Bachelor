#pragma once

#include "AdministratorController.h"
#include <string.h>
#include <stdlib.h>


AdministratorController::AdministratorController()
{
}

AdministratorController::AdministratorController(Repository * repository)
{
	this->repository = repository;
}


AdministratorController::~AdministratorController()
{
}

int AdministratorController::addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph)
{
	int vaccinationsNumber = 0;
	if (!this->validatePositiveInteger(vaccinations, vaccinationsNumber)) {
		return 1;
	}
	Dog * newDog = new Dog{ name, breed, birthDate,vaccinationsNumber,photograph };
	int result = this->repository->add(*newDog);
	delete newDog;
	return result;
}

int AdministratorController::updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph)
{
	int newVaccinationsNumber = 0;
	if (!this->validatePositiveInteger(newVaccinations, newVaccinationsNumber)) {
		return 1;
	}
	Dog * updatedDog = new Dog{ name,newBreed, newBirthDate, newVaccinationsNumber, newPhotograph };
	int result = this->repository->update(*updatedDog);
	delete updatedDog;
	return result;
}

int AdministratorController::deleteDog(char * name)
{
	Dog * exitingDog = new Dog{ name };
	int result = this->repository->remove(*exitingDog);
	delete exitingDog;
	return result;
}

const DynamicVector<Dog>& AdministratorController::getAllDogs()
{
	return this->repository->getAll();
}

bool AdministratorController::validateInteger(char * string, int & number)
{
	number = atoi(string);
	char * newString = new char[strlen(string) + 1];
	_itoa(number, newString, 10);
	bool valid = (strlen(string) == strlen(newString));
	delete newString;

	return valid;
}

bool AdministratorController::validatePositiveInteger(char * string, int & number)
{
	if (this->validateInteger(string, number)) {
		if (number < 0) {
			return false;
		}
	}

	return true;
}
