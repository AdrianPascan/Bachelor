#pragma once

#include<string>

class Dog
{
private:

	std::string name;
	std::string breed;
	std::string birthDate;
	std::string vaccinationsCount;
	std::string photograph;

public:

	Dog(std::string & name, std::string & breed, std::string & birthDate, std::string & vaccinationsCount, std::string & photograph);
	Dog(std::string & name);
	Dog(const Dog & dog);
	~Dog();
	bool operator==(const Dog & dog);
	std::string toString();
};

