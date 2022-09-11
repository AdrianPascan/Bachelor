#pragma once

#include "DynamicVector.h"
#include "Team.h"

class Repository
{
private:
	DynamicVector<Team> teams{};

public:
	Repository();
	~Repository();

	/*
	Adds a new team.
	Input: team - Team instance to be added
	Returns: 0, if it was added successfully
			 -1, if it exists already
	*/
	int add( Team & team);

	DynamicVector<Team> & getAll();
};

