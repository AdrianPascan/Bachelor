#pragma once

#include<vector>
#include<fstream>
#include<sstream>

#include "Weather.h"

class Repository
{
private:
	std::vector<Weather> data;

public:
	Repository();
	~Repository();

	std::vector<Weather> & getAll();

	void readFromFile();
};

