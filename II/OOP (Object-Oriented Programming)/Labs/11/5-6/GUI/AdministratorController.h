#pragma once

#include "BaseRepository.h"
#include "Validator.h"
#include "Dog.h"

class AdministratorController
{
private:
	BaseRepository * repository;

public:
	//AdministratorController();
	AdministratorController(BaseRepository * repository);
	~AdministratorController();

	void addDog(char * name, char * breed, char * birthDate, char * vaccinations, char * photograph);
	void updateDog(char * name, char * newBreed, char * newBirthDate, char * newVaccinations, char * newPhotograph);
	void deleteDog(char * name);
	std::vector<Dog> & getAllDogs();
	void setFilePath(char * filePath);
};

