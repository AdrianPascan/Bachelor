#pragma once

#include "Domain.h"
#include <string>


Dog::Dog(std::string & name, std::string & breed, std::string & birthDate, std::string & vaccinationsCount, std::string & photograph)
{
	this->name = name;
	this->breed = breed;
	this->birthDate = birthDate;
	this->vaccinationsCount = vaccinationsCount;
	this->photograph = photograph;
}

Dog::Dog(std::string & name)
{
	this->name = name;
	this->breed = "";
	this->birthDate = "";
	this->vaccinationsCount = "";
	this->photograph = "";
}

Dog::Dog(const Dog & dog)
{
	this->name = dog.name;
	this->breed = dog.breed;
	this->birthDate = dog.birthDate;
	this->vaccinationsCount = dog.vaccinationsCount;
	this->photograph = dog.photograph;
}

Dog::~Dog()
{
}

bool Dog::operator==(const Dog & dog)
{
	return this->name == dog.name;
}

std::string Dog::toString()
{
	std::string string{};
	string.append( this->name +": " + this->breed+ ", " + this->birthDate + ", " + this->vaccinationsCount + ", " + this->photograph + "\n" );
	return string;
}

