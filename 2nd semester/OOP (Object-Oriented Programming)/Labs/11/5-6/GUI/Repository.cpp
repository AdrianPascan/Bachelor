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

void Repository::initialize()
{
	char name1[] = "Boo";
	char breed1[] = "yorkshireTerrier";
	char birthDate1[] = "01-13-2017";
	int vaccinations1 = 7;
	char photograph1[] = "booPuppy.jpg";
	Dog dog1{ name1,breed1,birthDate1,vaccinations1,photograph1 };
	this->add(dog1);

	char name2[] = "Tom";
	char breed2[] = "bulldog";
	char birthDate2[] = "05-15-2018";
	int vaccinations2 = 2;
	char photograph2[] = "tom.png";
	Dog dog2{ name2,breed2,birthDate2,vaccinations2,photograph2 };
	this->add(dog2);

	char name3[] = "Ben";
	char breed3[] = "pitbull";
	char birthDate3[] = "02-05-2018";
	int vaccinations3 = 4;
	char photograph3[] = "ben.jpg";
	Dog dog3{ name3,breed3,birthDate3,vaccinations3,photograph3 };
	this->add(dog3);
}

void Repository::add(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator != dogs.end()) {
		throw MyException{ std::string {dog.getName()} + " already exists." };
	}

	dogs.push_back(dog);
}

void Repository::update(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator == dogs.end()) {
		throw MyException{ std::string {dog.getName()} + " doesn't exists." };
	}

	*iterator = dog;
}

void Repository::remove(Dog & dog)
{
	vector<Dog>::iterator iterator = find(dog);

	if (iterator == dogs.end()) {
		throw MyException{ std::string {dog.getName()} +" doesn't exists." };
	}

	dogs.erase(iterator);
}

void Repository::setFilePath(char * filePath, bool read, bool write)
{
	throw MyException{ "Cannot set file path of in-memory repository." };
}

void Repository::openInApp()
{
	throw MyException{ "Cannot open in app in-memory repository." };
}

void Repository::clear()
{
	dogs.clear();
}
