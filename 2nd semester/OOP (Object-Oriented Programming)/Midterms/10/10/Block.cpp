#include "Block.h"



Block::Block()
{
}

Block::Block(std::string _address, int _constructionYear, int _totalApartments, int _occupiedApartments)
{
	address.assign(_address);
	constructionYear = _constructionYear;
	totalApartments = _totalApartments;
	occupiedApartments = _occupiedApartments;
}


Block::~Block()
{
}

std::string Block::toString()
{
	return std::string{ "Block: " + address + ", " + std::to_string(constructionYear) + ", " + std::to_string(totalApartments) + ", " + std::to_string(occupiedApartments) + "\n" };
}
