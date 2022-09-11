#pragma once

#include "UserController.h"
#include "BaseRepository.h"
#include "Validator.h"
#include "MyException.h"
#include "FileRepository.h"
#include "CSVRepository.h"
#include "HTMLRepository.h"
#include <string.h>
#include <stdlib.h>
#include <algorithm>
#include <string>
#include <fstream>
#include <stdlib.h>

using namespace std;

UserController::UserController()
{
}

UserController::UserController(BaseRepository * repository)
{
	this->repository = repository;
	this->matchingRepository = new Repository{};
	this->savedRepository = new Repository{};
	this->currentDog = this->savedRepository->getAll().begin();
}


UserController::~UserController()
{
}

void UserController::setMylistPath(char * path)
{
	string mylistPath{path};
	if (mylistPath.substr(mylistPath.length() - 4) != ".csv" &&
		mylistPath.substr(mylistPath.length() - 4) != ".txt" &&
		mylistPath.substr(mylistPath.length() - 5) != ".html") {
		throw MyException{ "Invalid mylist path." };
	}
	changeSavedRepository(mylistPath);

	savedRepository->setFilePath(path, false, true);
}

void UserController::setMatching(char * breed, char * vaccinations)
{
	vector<Dog> & dogs = this->repository->getAll();

	savedRepository->clear();
	matchingRepository->clear();

	if (breed == NULL) {
		for (Dog & dog : repository->getAll()) {
			matchingRepository->add(dog);
		}
	}
	else {
		int vaccinationsNumber = Validator::validatePositiveInteger(vaccinations);

		for (Dog & dog : repository->getAll()) {
			if (strcmp(dog.getBreed(), breed) == 0 && dog.getVaccinations() < vaccinationsNumber){
				matchingRepository->add(dog);
			}
		}
	}
	currentDog = matchingRepository->getAll().begin();
}

Dog & UserController::nextDog()
{
	if (matchingRepository->isEmpty()) {
		throw MyException{ "None of the dogs match the criteria." };
	}
	if (currentDog == matchingRepository->getAll().end()) {
		currentDog = matchingRepository->getAll().begin();
	}
	return *(currentDog++);
}

void UserController::saveDog(char * name)
{
	Dog dog{ name };
	vector<Dog>::iterator iterator = matchingRepository->find(dog);

	if (iterator == matchingRepository->getAll().end()) {
		throw MyException{ string{name} +" doesn't match the criteria / doesn't exist." };
	}

	savedRepository->add(*iterator);
}

std::vector<Dog> & UserController::getMatching()
{
	return matchingRepository->getAll();
}

std::vector<Dog> & UserController::getSaved()
{
	return savedRepository->getAll();
}

void UserController::openSavedFile()
{
	savedRepository->openInApp();
}

void UserController::resetSavedRepository()
{
	changeSavedRepository(string{});
}

void UserController::changeSavedRepository(std::string mylistPath)
{
	BaseRepository * newRepository;

	if (mylistPath.empty()) {
		newRepository = new Repository{};
	}
	else if (mylistPath.substr(mylistPath.length() - 4) == ".txt") {
		newRepository = new FileRepository{};
	}
	else if (mylistPath.substr(mylistPath.length() - 4) == ".csv") {
		newRepository = new CSVRepository{};
	}
	else {
		newRepository = new HTMLRepository{};
	}

	for (Dog & dog : savedRepository->getAll()) {
		newRepository->add(dog);
	}
	delete savedRepository;
	savedRepository = newRepository;
}
