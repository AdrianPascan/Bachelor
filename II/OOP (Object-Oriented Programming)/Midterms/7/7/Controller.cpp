#pragma once

#include "Controller.h"
#include <stdlib.h>


Controller::Controller()
{
}

Controller::Controller(Repository * repository)
{
	this->repository = repository;
}


Controller::~Controller()
{
}

int Controller::addTeam( char * name,  char * country, int score)
{
	Team team{name,country,score};
	return this->repository->add(team);
}

 DynamicVector<Team>& Controller::getAll()
{
	return this->repository->getAll();
}
