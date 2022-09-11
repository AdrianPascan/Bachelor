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
	virtual Dog update(Dog & dog) = 0;
	virtual Dog remove(Dog & dog) = 0;
	std::vector<Dog> & getAll();
	virtual void setFilePath(char * filePath, bool read = false, bool write = false) = 0;
	virtual void clear(bool write = true) = 0;
	bool isEmpty();
	virtual void openInApp() = 0;
	std::vector<Dog>::iterator find(Dog & dog);
};

