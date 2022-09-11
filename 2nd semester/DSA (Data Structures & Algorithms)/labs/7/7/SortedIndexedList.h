#pragma once

#include "SortedIndexedListIterator.h"

#include <stack>
#include <exception>

#define NullTComp -2147483647

typedef int TComp;
typedef bool(*Relation)(TComp, TComp);

class SortedIndexedListIterator;

class BSTNode {

public:

	TComp info;
	int leftSon;
	int rightSon;
	int position;

	BSTNode() { info = NullTComp; leftSon = -1; rightSon = -1; position = 0; };
	BSTNode(TComp information, int leftSon, int rightSon, int position) : info{ info }, leftSon{ leftSon }, rightSon{ rightSon }, position{ position } {};
};

class SortedIndexedList {

	friend class SortedIndexedListIterator;

private:
	//representation of SortedIndexedList

	Relation relation;
	BSTNode* nodes;
	int root;
	int elementsNumber;
	int capacity;
	int firstFree;

public:

	// constructor

	SortedIndexedList(Relation r);

	// returns the size of the list

	int size() const;

	//checks if the list is empty

	bool isEmpty() const;

	// returns an element from a position
	//throws exception if the position is not valid

	TComp getElement(int pos) const;

	// adds an element

	void add(TComp e);

	// removes an element from a given position
	//returns the removed element
	//throws an exception if the position is not valid

	TComp remove(int pos);

	// searches for an element and returns the first position where the element appears or -1 if the element is not in the list

	int search(TComp e) const;

	// returns an iterator set to the first element of the list or invalid if the list is empty

	SortedIndexedListIterator iterator();

	//destructor

	~SortedIndexedList();
};
