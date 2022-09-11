#include "Controller.h"

using namespace std;

Controller::Controller()
{
}

Controller::Controller(Repository * _repository)
{
	repository = _repository;
}


Controller::~Controller()
{
}

void Controller::addBlock(std::string _address, int _constructionYear, int _totalApartments, int _occupiedApartments)
{
	Block* block = new Block{ _address, _constructionYear, _totalApartments, _occupiedApartments };
	repository->add(block);
}

void Controller::addHouse(std::string _address, int _constructionYear, std::string _type, bool _isHistorical)
{
	House* house = new House{ _address,_constructionYear, _type, _isHistorical };
	repository->add(house);
}

std::vector<Building*>& Controller::getAll()
{
	return repository->getAll();
}

std::vector<Building*> & Controller::mustBeRestored(int year, std::string address)
{
	vector<Building*> matching;

	for (Building * building : repository->getAll()) {
		if (building->getConstructionYear() < year && building->getAddress().find(address) != string::npos) {
			matching.push_back(building);
		}
	}

	return matching;
}
