#pragma once

#include<iostream>
#include<fstream>
#include<vector>

class Dog
{
private:
	char * name;
	char * breed;
	char * birthDate;
	int vaccinations;
	char * photograph;

public:
	Dog();
	Dog(char * name);
	Dog(char * name, char * breed, char * birthDate, int vaccinations, char * photograph);
	Dog(const Dog & dog);

	char * getName() const { return this->name; };
	char * getBreed() const { return this->breed; };
	char * getBirthDate() const { return this->birthDate; };
	int getVaccinations() const { return this->vaccinations; };
	char * getPhotograph() const { return this->photograph; };

	bool operator==(const Dog& dog);
	void operator=(const Dog& dog);
	friend void operator>>(std::ifstream & stream, Dog & dog);
	friend void operator<<(std::ofstream & stream, Dog & dog);
	friend void operator<<(std::ostream & stream, Dog & dog);

	~Dog();

private:
	friend std::vector<char*> tokenize(std::string & line);
	friend void destroyTokens(std::vector<char*> & tokens);
};

