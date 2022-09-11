#pragma once

#include "SortedIndexedList.h"

#include <stack>

typedef int TComp;
typedef bool(*Relation)(TComp, TComp);

class SortedIndexedList;

//unidirectional iterator for a container

class SortedIndexedListIterator {

	friend class SortedIndexedList;

private:

	//Constructor receives a reference of the container.
	//after creation the iterator will refer to the first element of the container, or it will be invalid if the container is empty

	SortedIndexedListIterator(const SortedIndexedList & _sortedList);

	//contains a reference of the container it iterates over

	const SortedIndexedList & sortedList;

	/* representation specific for the iterator*/

	int current;
	std::stack<int> stack;

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
	TComp getCurrent() const;
};


