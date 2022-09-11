#pragma once

#include "Dog.h"
#include "BaseRepository.h"
#include <stdio.h>

class BaseRepository;

class UserController
{
	friend class BaseRepository;

private:
	BaseRepository * repository;
	std::vector<Dog>::iterator currentDogPosition;
	std::vector<Dog> matchingDogs{};
	std::vector<Dog> savedDogs{};

public:
	//UserController();
	UserController(BaseRepository * repository);
	~UserController();

	void setMatching(char * breed = NULL, char * vaccinations = NULL);
	Dog & nextDog();
	void saveDog(char * name);
	std::vector<Dog> & getMatching();
	std::vector<Dog> & getSaved();
};

