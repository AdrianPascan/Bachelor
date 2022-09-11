#pragma once

#include "Team.h"
#include <string.h>

Team::Team()
{
	this->name = new char;
	this->name = '\0';

	this->country = new char;
	this->country = '\0';

	this->score = 0;
}

Team::Team( char * name)
{
	this->name = new char[strlen(name) + 1];
	strcpy(this->name, name);

	this->country = new char;
	this->country = '\0';

	this->score = 0;
}


Team::Team( char * name,  char * country, int score)
{
	this->name = new char[strlen(name) + 1];
	strcpy(this->name, name);

	this->country = new char[strlen(country) + 1];
	strcpy(this->country, country);

	this->score = score;
}

Team::Team( Team & team)
{
	this->name = new char[strlen(team.name) + 1];
	strcpy(this->name, team.name);

	this->country = new char[strlen(team.country) + 1];
	strcpy(this->country, team.country);

	this->score = team.score;
}



Team::~Team()
{
	delete this->name;
	delete this->country;
}

bool Team::operator==( Team & team)
{
	return (strcmp(this->name, team.name) == 0);
}

void Team::operator=( Team & team)
{
	this->name = new char[strlen(team.name) + 1];
	strcpy(this->name, team.name);

	this->country = new char[strlen(team.country) + 1];
	strcpy(this->country, team.country);

	this->score = team.score;
}

bool Team::operator>=( Team & team) 
{
	return (this->score >= team.score);
}
