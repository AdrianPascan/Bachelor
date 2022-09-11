#pragma once

#include<string>

class Building
{
protected:
	std::string address;
	int constructionYear;

public:
	Building();
	~Building();

	virtual bool mustBeRestored() = 0;
	virtual bool canBeDemolished() = 0;
	virtual std::string toString() = 0;
	std::string getAddress() { return address; };
	int getConstructionYear() { return constructionYear; };
};

