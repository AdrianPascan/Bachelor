#pragma once

#include "Controller.h"
#include "Domain.h"
#include <stdlib.h>
#include <string>
#include <exception>


bool Controller::validatePositiveNumber(std::string & string)
{
	if (string.size() == 0)
		return false;

	for (int index = 0; index < string.size(); index++)
		if (!(string[index] >= '0' && string[index] <= '9'))
			return false;

	if (string.size() > 1 && string[0] == '0')
		return false;

	return true;
}

Controller::Controller(Repository & repository): repository(repository)
{
}


Controller::~Controller()
{
}

int Controller::addDog(std::string & name, std::string & breed, std::string & birthDate, std::string & vaccinationCount, std::string & photograph)
{
	if (this->validatePositiveNumber(vaccinationCount) == false)
		return 1;

	Dog dog{ name, breed, birthDate, vaccinationCount, photograph };
	return this->repository.add(dog);
}

int Controller::updateDog(std::string & name, std::string & newBreed, std::string & newBirthDate, std::string & newVaccinationCount, std::string & newPhotograph)
{
	if (this->validatePositiveNumber(newVaccinationCount) == false)
		return 1;

	Dog dog{ name, newBreed, newBirthDate, newVaccinationCount, newPhotograph };
	return this->repository.update(dog);
}

int Controller::deleteDog(std::string & name)
{
	Dog dog{ name };
	return this->repository.remove(dog);
}

std::string Controller::listDogs()
{
	return this->repository.list();
}

