#pragma once

#include "Building.h"

class Block :
	public Building
{
private:
	int totalApartments;
	int occupiedApartments;

public:
	Block();
	Block(std::string _address, int _constructionYear, int _totalApartments, int _occupiedApartments);
	~Block();

	bool mustBeRestored() override { return true; };
	bool canBeDemolished() override { return true; };
	std::string toString() override;
};

