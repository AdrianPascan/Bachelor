#pragma once

#include "UserController.h"
#include <string.h>
#include <stdlib.h>


UserController::UserController()
{
}

UserController::UserController(Repository * repository)
{
	this->repository = repository;
}


UserController::~UserController()
{
}

int UserController::setMatching(char * breed, char * vaccinations)
{
	this->currentDogPosition = 0;
	this->matchingDogs.clear();
	this->savedDogs.clear();

	if (breed == NULL) {
		this->matchingDogs = this->repository->getAll();
		return 0;
	}

	int vaccinationsNumber = 0;
	if (!(this->validatePositiveInteger(vaccinations, vaccinationsNumber))) {
		return 1;
	}

	for (int index = 0; index < this->repository->getSize(); index++) {
		const Dog & current = this->repository->dogs[index];
		if (strcmp(current.getBreed(), breed) == 0 && current.getVaccinations() < vaccinationsNumber) {
			this->matchingDogs.add(current);
		}
	}

	return 0;
}

const Dog & UserController::nextDog()
{
	if (this->matchingDogs.getSize() == 0) {
		return Dog{};
	}
	if (this->currentDogPosition >= this->matchingDogs.getSize()) {
		this->currentDogPosition = 0;
	}
	return this->matchingDogs[this->currentDogPosition++];
}

int UserController::saveDog(char * name)
{
	Dog * dog = new Dog{ name };
	int position = this->matchingDogs.find(*dog);
	delete dog;

	if (position == -1) {
		return -1;
	}

	this->savedDogs.add(this->matchingDogs[position]);
}

const DynamicVector<Dog>& UserController::getMatching()
{
	return this->matchingDogs;
}

const DynamicVector<Dog>& UserController::getSaved()
{
	return this->savedDogs;
}

bool UserController::validateInteger(char * string, int & number)
{
	number = atoi(string);
	char * newString = new char[strlen(string) + 1];
	_itoa(number, newString, 10);
	bool valid = (strlen(string) == strlen(newString));
	delete newString;

	return valid;
}

bool UserController::validatePositiveInteger(char * string, int & number)
{
	if (this->validateInteger(string, number)) {
		if (number < 0) {
			return false;
		}
	}

	return true;
}
