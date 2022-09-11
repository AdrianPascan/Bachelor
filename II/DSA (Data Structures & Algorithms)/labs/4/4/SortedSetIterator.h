#pragma once

#include "SortedSet.h"

typedef int TElem;

class SortedSet;

using namespace std;


//unidirectional iterator for a container

class SortedSetIterator {

	friend class SortedSet;

private:

	//Constructor receives a reference of the container.

	//after creation the iterator will refer to the first element of the container, or it will be invalid if the container is empty

	SortedSetIterator(const SortedSet& sortedSet);


	//contains a reference of the container it iterates over

	const SortedSet& ss;


	/* representation specific for the iterator*/

	int current;


public:

	//sets the iterator to the first element of the container

	void first();


	//moves the iterator to the next element

	//throws exception if the iterator is not valid

	void next();


	//checks if the iterator is valid

	bool valid() const;


	//returns the value of the current element from the iterator

	// throws exception if the iterator is not valid

	TElem getCurrent() const;


	//extra

	void jumpForward(int k);

};


