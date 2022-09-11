#include "Subject.h"



Subject::Subject()
{
}


Subject::~Subject()
{
}

void Subject::registerObserver(Observer * observer)
{
	observers.push_back(observer);
}

void Subject::unregisterObserver(Observer * observer)
{
	observers.erase(std::remove(observers.begin(), observers.end(), observer));
}

void Subject::notify()
{
	for (Observer* observer : observers) {
		observer->update();
	}
}
