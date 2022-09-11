#pragma once

#include "Repository.h"
#include "MyException.h"
#include <string>

using namespace std;

Repository::Repository(): BaseRepository{}
{
}


Repository::~Repository()
{
}

void Repository::add(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator != dogs.end()) {
		throw MyException{ std::string {dog.getName()} + " already exists." };
	}

	dogs.push_back(dog);
}

Dog Repository::update(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator == dogs.end()) {
		throw MyException{ std::string {dog.getName()} + " doesn't exists." };
	}

	Dog oldDog{ *iterator };

	*iterator = dog;

	return oldDog;
}

Dog Repository::remove(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator == dogs.end()) {
		throw MyException{ std::string {dog.getName()} +" doesn't exists." };
	}

	Dog oldDog{ *iterator };

	dogs.erase(iterator);

	return oldDog;
}

void Repository::setFilePath(char * filePath, bool read, bool write)
{
	throw MyException{ "Cannot set file path of in-memory repository." };
}

void Repository::openInApp()
{
	throw MyException{ "Cannot open in app in-memory repository." };
}

void Repository::clear(bool write)
{
	dogs.clear();
}
