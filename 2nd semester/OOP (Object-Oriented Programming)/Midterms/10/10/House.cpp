#include "House.h"



House::House()
{
}

House::House(std::string _address, int _constructionYear, std::string _type, bool _isHistorical)
{
	address.assign(_address); 
	constructionYear = _constructionYear;
	type.assign(_type);
	isHistorical = _isHistorical;
}

House::~House()
{
}

std::string House::toString()
{
	std::string house{ "House: " + address + ", " + std::to_string(constructionYear) + ", " + type + ", "};

	if (!isHistorical) {
		house.append("not ");
	}

	house.append("historical\n");

	return house;
}
