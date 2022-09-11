#pragma once

#include "Repository.h"
#include <string>

using namespace std;


Repository::Repository()
{
	this->dogs = new DynamicVector;
}


Repository::~Repository()
{
	delete this->dogs;
}

int Repository::add(Dog & dog)
{
	return this->dogs->add(dog);
}

int Repository::update(Dog & dog)
{
	return this->dogs->update(dog);
}

int Repository::remove(Dog & dog)
{
	return this->dogs->remove(dog);
}

std::string Repository::list()
{
	return this->dogs->list();
}