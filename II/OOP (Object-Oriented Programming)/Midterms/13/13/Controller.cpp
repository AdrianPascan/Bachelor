#include "Controller.h"

using namespace std;

Controller::Controller()
{
}

Controller::Controller(Repository * repository) :repository{ repository }
{
}


Controller::~Controller()
{
}

std::vector<Weather>& Controller::getAll()
{
	return repository->getAll();
}

std::vector<Weather> Controller::getFiltered(double precipitation)
{
	vector<Weather> matching{};

	for (Weather & weather : repository->getAll()) {
		if (weather.precipitation < precipitation) {
			matching.push_back(weather);
		}
	}
	return matching;
}


/*
Computes the total number of hours of the current day which have a given description.
Input: description - std::string
Returns: number - int
*/
int Controller::getDescription(std::string description)
{
	int number = 0;

	for (Weather & weather : repository->getAll()) {
		if (weather.description == description) {
			number += weather.end - weather.start;
		}
	}

	return number;
}

void Controller::testDescription()
{
	assert(getDescription("sunny") == 6);
}
