#pragma once

#include "DynamicVector.h"
#include "Dog.h"

class UserController;

class Repository
{
	friend class UserController;

private:
	DynamicVector<Dog> dogs{};

public:
	Repository();
	~Repository();

	int add(const Dog & dog);
	int update(const Dog & dog);
	int remove(const Dog & dog);
	int getSize();
	const Dog & operator[](int position);
	const DynamicVector<Dog>& getAll();
};

