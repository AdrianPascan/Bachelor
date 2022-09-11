#pragma once

#include "Tests.h"
#include <assert.h>
#include <string.h>

void testAll()
{
	Repository * repository = new Repository;
	AdministratorController * administratorController = new AdministratorController{ repository };
	UserController * userController = new UserController{ repository };

	char name[] = "Ben";
	char breed[] = "bulldog";
	char birthDate[] = "04-02-2017";
	char vaccinations[] = "15";
	char photograph[] = "ben.jpg";

	assert(0 == administratorController->addDog(name, breed, birthDate, vaccinations, photograph));

	strcpy(vaccinations, "1a");
	assert (1 == administratorController->addDog(name, breed, birthDate, vaccinations, photograph));
	
	strcpy(birthDate, "04-02-2016");
	strcpy(vaccinations, "15");
	assert (0 == administratorController->updateDog(name, breed, birthDate, vaccinations, photograph));

	strcpy(name, "Tom");
	assert(0 == administratorController->updateDog(name, breed, birthDate, vaccinations, photograph));

	strcpy(name, "Ben");
	assert (0 == administratorController->deleteDog(name));
	assert (-1 == administratorController->deleteDog(name));

	delete repository;
}
