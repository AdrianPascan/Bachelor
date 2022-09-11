#pragma once

#include "Domain.h"
#include "DynamicVector.h"
#include <string>

class Repository
{
private:

	DynamicVector * dogs;

public:

	Repository();

	~Repository();

	int add(Dog & dog);
	int update(Dog & dog);
	int remove(Dog & dog);
	std::string list();
};

