#pragma once

#include"Repository.h"
#include"Building.h"
#include"Block.h"
#include"House.h"

class Controller
{
private:
	Repository * repository;

public:
	Controller();
	Controller(Repository * _repository);
	~Controller();

	void addBlock(std::string _address, int _constructionYear, int _totalApartments, int _occupiedApartments);
	void addHouse(std::string _address, int _constructionYear, std::string _type, bool _isHistorical);
	std::vector<Building*> & getAll();
	std::vector<Building*> & mustBeRestored(int year, std::string address);
};

