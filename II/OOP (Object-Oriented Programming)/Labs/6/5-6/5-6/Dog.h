#pragma once

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
	Dog(char * name, char * breed,  char * birthDate, int vaccinations, char * photograph);
	Dog(const Dog & dog);

	char * getName() const { return this->name; };
	char * getBreed() const { return this->breed; };
	char * getBirthDate() const { return this->birthDate; };
	int getVaccinations() const { return this->vaccinations; };
	char * getPhotograph() const { return this->photograph; };

	bool operator==(const Dog& dog);
	void operator=(const Dog& dog);

	~Dog();
};

