#pragma once

#include "Repository.h"
#include "Dog.h"

class AdministratorController
{
private:
	Repository * repository;

public:
	AdministratorController();
	AdministratorController(Repository * repository);
	~AdministratorController();

	int addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph);
	int updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph);
	int deleteDog(char * name);
	const DynamicVector<Dog>& getAllDogs();

private:
	bool validateInteger(char * string, int & number);
	bool validatePositiveInteger(char * string, int & number);
};

