#pragma once

#include <string>;

class Participant
{
	std::string name;
	int score;
public:
	Participant();
	Participant(std::string name, int score = 0);
	~Participant();
	std::string getName() { return this->name; }
	int getScore() { return this->score; }
	void addScore(int score) { this->score = this->score + score; }
	void setScore(int score) { this->score = score; }
	bool operator<(Participant& participant);
};

