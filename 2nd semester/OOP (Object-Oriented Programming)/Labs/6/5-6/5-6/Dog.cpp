#pragma once

#include "Dog.h"
#include <string.h>


Dog::Dog()
{
	this->name = new char;
	this->name = '\0';

	this->breed = new char;
	this->breed = '\0';

	this->birthDate = new char;
	this->birthDate = '\0';

	this->vaccinations = -1;

	this->photograph = new char;
	this->photograph = '\0';
}

Dog::Dog(char * name)
{
	this->name = new char[strlen(name) + 1];
	strcpy(this->name, name);

	this->breed = new char;
	this->breed = '\0';

	this->birthDate = new char;
	this->birthDate = '\0';

	this->vaccinations = -1;

	this->photograph = new char;
	this->photograph = '\0';
}

Dog::Dog(char * name, char * breed, char * birthDate, int vaccinations, char * photograph)
{
	this->name = new char[strlen(name) + 1];
	strcpy(this->name, name);

	this->breed = new char[strlen(breed) + 1];
	strcpy(this->breed, breed);

	this->birthDate = new char[strlen(birthDate) + 1];
	strcpy(this->birthDate, birthDate);

	this->vaccinations = vaccinations;

	this->photograph = new char[strlen(photograph) + 1];
	strcpy(this->photograph, photograph);
}

Dog::Dog(const Dog & dog)
{
	this->name = new char[strlen(dog.getName()) + 1];
	strcpy(this->name, dog.getName());

	this->breed = new char[strlen(dog.getBreed()) + 1];
	strcpy(this->breed, dog.getBreed());

	this->birthDate = new char[strlen(dog.getBirthDate()) + 1];
	strcpy(this->birthDate, dog.getBirthDate());

	this->vaccinations = dog.getVaccinations();

	this->photograph = new char[strlen(dog.getPhotograph()) + 1];
	strcpy(this->photograph, dog.getPhotograph());
}


bool Dog::operator==(const Dog & dog)
{
	return (strcmp(this->name, dog.getName()) == 0);
}

void Dog::operator=(const Dog & dog)
{
	this->name = new char[strlen(dog.getName()) + 1];
	strcpy(this->name, dog.getName());

	this->breed = new char[strlen(dog.getBreed()) + 1];
	strcpy(this->breed, dog.getBreed());

	this->birthDate = new char[strlen(dog.getBirthDate()) + 1];
	strcpy(this->birthDate, dog.getBirthDate());

	this->vaccinations = dog.getVaccinations();

	this->photograph = new char[strlen(dog.getPhotograph()) + 1];
	strcpy(this->photograph, dog.getPhotograph());
}

Dog::~Dog()
{
	delete[] this->name;
	delete[] this->breed;
	delete[] this->birthDate;
	delete[] this->photograph;
}
