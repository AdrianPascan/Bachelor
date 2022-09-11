#include "Repository.h"



Repository::Repository()
{
}


Repository::~Repository()
{
	for (Building* building : buildings) {
		delete building;
	}
}

void Repository::add(Building* building)
{
	buildings.push_back(building);
}

std::vector<Building*>& Repository::getAll()
{
	return buildings;
}

void Repository::initialise()
{
	House * house = new House{ "Peana", 2000, "duplex", false };
	buildings.push_back(house);

	Block * block = new Block{ "Diecilor", 1985, 200, 150 };
	buildings.push_back(block);
}
