#pragma once

#include "Repository.h"

#include <assert.h>

class Controller
{
private:
	Repository * repository;
public:
	Controller();
	Controller(Repository * repository);
	~Controller();

	std::vector<Weather> & getAll();
	std::vector<Weather> getFiltered(double precipitation);
	int getDescription(std::string description);

	void testDescription();
};

