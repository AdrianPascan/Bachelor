#pragma once

#include "Tests.h"
#include <string>

using namespace std;

Tests::Tests()
{
	this->repository = new FileRepository{};
	this->administrator = new AdministratorController{ repository };
	this->user = new UserController{ repository };
}


Tests::~Tests()
{
	delete this->user;
	delete this->administrator;
	delete this->repository;
}

void Tests::testAll()
{
	// Administator
	test_setFilePath();
	test_addDog();
	test_updateDog();
	test_deleteDog();
	test_getAllDogs();

	// User
	test_setMatching();
	test_saveDog();
	test_next();
	test_getMatching();
	test_getSaved();
}

void Tests::test_setFilePath()
{
	char filePath[] = "D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Laboratories\\8\\data.txt";
	administrator->setFilePath(filePath);

	cout << "REPO:" << endl;
	for (Dog & dog : administrator->getAllDogs()) {
		cout << dog;
	}
}

void Tests::test_addDog()
{
	char name1[] = "Tom";
	char breed[] = "chiuaua";
	char birthDate[] = "15-08-2018";
	char vaccinations[] = "2";
	char photograph[] = "oscar.jpg";

	char invalidVaccinations1[] = "-a";
	char invalidVaccinations2[] = "-1";
	try {
		administrator->addDog(name1, breed, birthDate, invalidVaccinations1, photograph);
	}
	catch (exception &) {
	}
	try {
		administrator->addDog(name1, breed, birthDate, invalidVaccinations2, photograph);
	}
	catch (exception &) {
	}

	try {
		administrator->addDog(name1, breed, birthDate, vaccinations, photograph);
	}
	catch (exception & exception) {
		exception.what();
	}

	char name2[] = "Oscar";
	administrator->addDog(name2, breed, birthDate, vaccinations, photograph);
}

void Tests::test_updateDog()
{
	char name1[] = "Max";
	char breed[] = "chiuaua";
	char birthDate[] = "15-08-2018";
	char vaccinations[] = "2";
	char photograph[] = "oscar.jpg";

	try {
		administrator->updateDog(name1, breed, birthDate, vaccinations, photograph);
	}
	catch (exception &) {
	}

	char name2[] = "Oscar";
	administrator->updateDog(name2, breed, birthDate, vaccinations, photograph);
}

void Tests::test_deleteDog()
{
	char name1[] = "Max";

	try {
		administrator->deleteDog(name1);
	}
	catch (exception &) {
	}

	char name2[] = "Oscar";
	administrator->deleteDog(name2);
}

void Tests::test_getAllDogs()
{
	administrator->getAllDogs();
}

void Tests::test_setMatching()
{
	char breed[] = "chiuaua";
	char vaccinations[] = "5";
	user->setMatching(breed, vaccinations);

	user->setMatching();
}

void Tests::test_saveDog()
{	
	char name1[] = "Max";

	try {
		user->saveDog(name1);
	}
	catch (exception &) {
	}

	char name2[] = "Boo";
	user->saveDog(name2);

	try {
		user->saveDog(name2);
	}
	catch (exception &) {
	}
}

void Tests::test_next()
{
	for (int index = 1; index <= 5; index++) {
		user->nextDog();
	}

	char breed[] = "akita";
	char vaccinations[] = "10";
	user->setMatching(breed, vaccinations);

	try {
		user->nextDog();
	}
	catch (exception &) {
	}
}

void Tests::test_getMatching()
{
	user->getMatching();
}

void Tests::test_getSaved()
{
	user->getSaved();
}
