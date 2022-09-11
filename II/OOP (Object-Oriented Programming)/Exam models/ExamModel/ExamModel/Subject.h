#pragma once

#include<vector>
#include<algorithm>
#include"Observer.h"

class Subject
{
protected:
	std::vector<Observer*> observers;
	
public:
	Subject();
	~Subject();
	void registerObserver(Observer* observer);
	void unregisterObserver(Observer* observer);
	void notify();
};

