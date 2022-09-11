#pragma once

#include "BaseRepository.h"
#include <algorithm>

using namespace std;


BaseRepository::BaseRepository()
{
}


BaseRepository::~BaseRepository()
{
}


/*int BaseRepository::getSize()
{
	return dogs.size();
}*/

std::vector<Dog>& BaseRepository::getAll()
{
	return dogs;
}

std::vector<Dog>::iterator BaseRepository::find(Dog & dog)
{
	return find_if(dogs.begin(), dogs.end(), [&dog](Dog & current) {return current == dog; });
}