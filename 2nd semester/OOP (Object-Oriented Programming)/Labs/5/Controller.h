#pragma once

#include "Repository.h"
#include <string>


class Controller
{
private:

	Repository & repository;

	bool validatePositiveNumber(std::string & string);

public:
	Controller(Repository &repository);
	~Controller();

	int addDog(std::string & name, std::string & breed, std::string & birthDate, std::string & vaccinationCount, std::string & photograph);
	int updateDog(std::string & name, std::string & newBreed, std::string & newBirthDate, std::string & newVaccinationCount, std::string & newPhotograph);
	int deleteDog(std::string& name);
	std::string listDogs();
};

