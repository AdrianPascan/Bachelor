#pragma once

#include "Repository.h"

class Controller
{
private:
	Repository * repository;

public:
	Controller();
	Controller(Repository * repository);
	~Controller();

	int addTeam( char * name,  char * country, int score);
	DynamicVector<Team> & getAll();

};

