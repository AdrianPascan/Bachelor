#pragma once

#include "UserController.h"
#include "BaseRepository.h"
#include <string.h>
#include <stdlib.h>
#include <algorithm>
#include <string>
#include "Validator.h"
#include "MyException.h"

using namespace std;

//UserController::UserController()
//{
//}

UserController::UserController(BaseRepository * repository)
{
	this->repository = repository;
}


UserController::~UserController()
{
}

void UserController::setMatching(char * breed, char * vaccinations)
{
	vector<Dog> & dogs = this->repository->getAll();

	this->savedDogs.clear();

	if (breed == NULL) {
		this->matchingDogs = dogs;
	}
	else {
		int vaccinationsNumber = Validator::validatePositiveInteger(vaccinations);

		this->matchingDogs.resize(dogs.size());
		vector<Dog>::iterator last = copy_if(dogs.begin(), dogs.end(), this->matchingDogs.begin(),
			[breed, vaccinationsNumber](Dog & dog) {return (strcmp(dog.getBreed(), breed) == 0 && dog.getVaccinations() < vaccinationsNumber); });
		this->matchingDogs.resize(last - matchingDogs.begin());
	}
	this->currentDogPosition = this->matchingDogs.begin();
}

Dog & UserController::nextDog()
{
	if (this->matchingDogs.empty()) {
		throw MyException{ "None of the dogs match the criteria." };
	}
	if (this->currentDogPosition == this->matchingDogs.end()) {
		this->currentDogPosition = this->matchingDogs.begin();
	}
	return *(this->currentDogPosition++);
}

void UserController::saveDog(char * name)
{
	Dog dog{ name };

	vector<Dog>::iterator position = find_if(this->matchingDogs.begin(), this->matchingDogs.end(), [&dog](Dog & current) {return current == dog; });
	if (position == this->matchingDogs.end()) {
		throw MyException{ string{name} +" doesn't exist." };
	}

	vector<Dog>::iterator existing = find_if(this->savedDogs.begin(), this->savedDogs.end(), [&dog](Dog & current) {return current == dog; });
	if (existing != this->savedDogs.end()) {
		throw MyException{ string{name} +" already saved." };
	}

	this->savedDogs.push_back(*position);
}

std::vector<Dog> & UserController::getMatching()
{
	return this->matchingDogs;
}

std::vector<Dog> & UserController::getSaved()
{
	return this->savedDogs;
}