#pragma once

#include<vector>
#include"Building.h"
#include"House.h"
#include"Block.h"

class Repository
{
private:
	std::vector<Building*> buildings;

public:
	Repository();
	~Repository();

	void add(Building* building);
	std::vector<Building*> & getAll();
	void initialise();
};

