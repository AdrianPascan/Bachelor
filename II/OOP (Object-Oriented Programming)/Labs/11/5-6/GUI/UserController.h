#pragma once

#include "Dog.h"
#include "BaseRepository.h"
#include <stdio.h>
#include <fstream>

class UserController
{
private:
	BaseRepository * repository;
	BaseRepository * matchingRepository;
	BaseRepository * savedRepository;
	std::vector<Dog>::iterator currentDog;

public:
	UserController();
	UserController(BaseRepository * repository);
	~UserController();

	void setMylistPath(char * path);
	void setMatching(char * breed = NULL, char * vaccinations = NULL);
	Dog & nextDog();
	void saveDog(char * name);
	std::vector<Dog> & getMatching();
	void getSaved();
	
private:
	void changeSavedRepository(std::string mylistPath);
};

