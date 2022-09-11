#pragma once

#include "Repository.h"


Repository::Repository()
{
//	this->dogs = new DynamicVector<Dog>;
}


Repository::~Repository()
{
//	delete this->dogs;
}

int Repository::add(const Dog & dog)
{
	return this->dogs.add(dog);
}

int Repository::update(const Dog & dog)
{
	return this->dogs.update(dog);
}

int Repository::remove(const Dog & dog)
{
	return this->dogs.remove(dog);
}

int Repository::getSize()
{
	return this->dogs.getSize();
}

const Dog & Repository::operator[](int position)
{
	return this->dogs[position];
}

const DynamicVector<Dog>& Repository::getAll() 
{
	return this->dogs;
}
