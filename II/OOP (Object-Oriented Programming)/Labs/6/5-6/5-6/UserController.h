#pragma once

#include "Dog.h"
#include "Repository.h"
#include <stdio.h>

class Repository;

class UserController
{
	friend class Repository;

private:
	Repository * repository;
	int currentDogPosition;
	DynamicVector<Dog> matchingDogs;
	DynamicVector<Dog> savedDogs;

public:
	UserController();
	UserController(Repository * repository);
	~UserController();

	int setMatching(char * breed = NULL, char * vaccinations = NULL);
	const Dog & nextDog();
	int saveDog(char * name);
	const DynamicVector<Dog> & getMatching();
	const DynamicVector<Dog> & getSaved();

private:
	bool validateInteger(char * string, int & number);
	bool validatePositiveInteger(char * string, int & number);
};

