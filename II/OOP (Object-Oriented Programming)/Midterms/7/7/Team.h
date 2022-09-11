#pragma once

#include <string.h>

class Team
{
private: 
	char * name;
	char * country;
	int score;

public:
	Team();
	Team( char * name);
	Team( char * name,  char * country, int score = 0);
	Team( Team & team);
	void operator=( Team & team);
	~Team();

	char * getName() { return this->name; } ;
	char * getCoutry() { return this->country; } ;
	int getScore() { return this->score; } ;

	bool operator==( Team & team);
	bool operator>=( Team & team) ;
};
