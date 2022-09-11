#pragma once
class Observer
{
public:
	Observer();
	virtual ~Observer() = 0;
	virtual void update() = 0;
};

