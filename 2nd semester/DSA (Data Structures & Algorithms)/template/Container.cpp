#include "Container.h"
#include "IteratorContainer.h"
#include <iostream>

//implementation of the operations from Container.h


Container::Container() {
	// TBA 
}

IteratorContainer Container::iterator() const {
	return IteratorContainer(*this);
}


Container::~Container() {
	// TBA
}


//the rest of the operations