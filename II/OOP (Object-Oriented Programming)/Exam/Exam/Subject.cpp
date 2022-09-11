#include "Subject.h"



Subject::Subject()
{
}


Subject::~Subject()
{
}

void Subject::notify()
{
	for (auto observer : observers) {
		observer->update();
	}
}

void Subject::registerObserver(Observer * observer)
{
	observers.push_back(observer);
}
