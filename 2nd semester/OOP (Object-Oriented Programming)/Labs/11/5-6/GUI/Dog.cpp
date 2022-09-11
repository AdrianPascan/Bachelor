#pragma once

#include "Dog.h"
#include "MyException.h"
#include <string.h>
#include <string>
#include <vector>
#include <stdlib.h>

using namespace std;


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

std::vector<char*> tokenize(std::string & line)
{
	vector<char*> tokens;

	char * input = new char[line.size() + 1];
	strcpy(input, line.c_str());

	char * token = strtok(input, ",");

	while (token != NULL) {
		char * copy = new char[strlen(token) + 1];
		strcpy(copy, token);
		tokens.push_back(copy);
		token = strtok(NULL, ",");
	}

	delete[] input;

	return tokens;
}

void destroyTokens(std::vector<char*>& tokens)
{
	for (char * current : tokens) {
		delete[] current;
	}
}

void operator>>(std::ifstream & stream, Dog & dog)
{
	string line{};
	getline(stream, line);

	vector<char*> tokens = tokenize(line);

	if (tokens.size() != 5) {
		throw MyException{ "Couldn't read dog." };
	}

	dog = Dog{ tokens[0], tokens[1], tokens[2], atoi(tokens[3]), tokens[4] };

	destroyTokens(tokens);
}

void operator<<(std::ofstream & stream, Dog & dog)
{
	stream << dog.getName() << ',' << dog.getBreed() << ',' << dog.getBirthDate() << ',' << dog.getVaccinations() << ',' << dog.getPhotograph() << endl;
}

void operator<<(std::ostream & stream, Dog & dog)
{
	stream << dog.getName() << ": " << dog.getBreed() << ", " << dog.getBirthDate() << ", " << dog.getVaccinations() << " vaccinations, photo '" << dog.getPhotograph() << "'" << endl;
}

std::string Dog::toString()
{
	std::string string{};
	string.append(this->getName());
	string.append(": ");
	string.append(this->getBreed());
	string.append(", ");
	string.append(this->getBirthDate());
	string.append(", ");
	string.append(to_string(this->getVaccinations()));
	string.append(" vaccinations, photo '");
	string.append(this->getPhotograph());
	string.append("'");
	return string;
}

Dog::~Dog()
{
	delete[] this->name;
	delete[] this->breed;
	delete[] this->birthDate;
	delete[] this->photograph;
}
