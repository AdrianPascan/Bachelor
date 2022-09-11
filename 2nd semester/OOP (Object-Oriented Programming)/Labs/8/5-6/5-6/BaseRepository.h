#pragma once

#include "Dog.h"
#include <vector>

class BaseRepository
{
protected:
	std::vector<Dog> dogs{};

public:
	BaseRepository();
	~BaseRepository();

	virtual void add(Dog & dog) = 0;
	virtual void update(Dog & dog) = 0;
	virtual void remove(Dog & dog) = 0;
	//int getSize();
	std::vector<Dog> & getAll();
	virtual void setFilePath(char * filePath) = 0;

protected:
	std::vector<Dog>::iterator find(Dog & dog);
};

