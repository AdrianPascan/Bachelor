#include "Participant.h"



Participant::Participant()
{
}

Participant::Participant(std::string name, int score): name{name}, score{score}
{
}


Participant::~Participant()
{
}

bool Participant::operator<(Participant & participant)
{
	return score >= participant.getScore();
}
