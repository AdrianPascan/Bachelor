#pragma once

#include "Building.h"

class House :
	public Building
{
private:
	std::string type;
	bool isHistorical;

public:
	House();
	House(std::string _address, int _constructionYear, std::string _type, bool _isHistorical);
	~House();

	bool mustBeRestored() override { return true; };
	bool canBeDemolished() override { return true; };
	std::string toString() override;
};

