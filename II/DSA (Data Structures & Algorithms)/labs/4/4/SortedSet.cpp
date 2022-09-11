#pragma once

#include "SortedSet.h"
#include "SortedSetIterator.h"


//theta(1)
SortedSet::SortedSet(Relation r){
	this->relation = r;
	
	this->capacity = 10;
	this->elements = new TElem[this->capacity];
	this->next = new int[this->capacity];

	for (int index = 0; index < this->capacity - 1; index++) {
		this->next[index] = index + 1;
	}
	this->next[this->capacity - 1] = -1;

	this->head = -1;
	this->firstEmpty = 0;
}


//O(n)
bool SortedSet::add(TComp e) {
	int current = this->head;
	int previous = -1;

	while (current != -1 && this->elements[current] != e && this->relation(this->elements[current], e)) {
		previous = current;
		current = this->next[current];
	}

	if (this->elements[current] == e) {
		return false;
	}

	this->elements[this->firstEmpty] = e;

	int empty = this->firstEmpty;

	if (this->next[firstEmpty] == -1) {
		int * newElements = new TElem[this->capacity * 2];
		int * newNext = new int[this->capacity * 2];

		for (int index = 0; index < this->capacity; index++) {
			newElements[index] = this->elements[index];
			newNext[index] = this->next[index];
		}

		newNext[firstEmpty] = this->capacity;
		firstEmpty = this->capacity;

		delete this->elements;
		this->elements = newElements;

		delete this->next;
		this->next = newNext;

		this->capacity *= 2;
	}
	else {
		this->firstEmpty = this->next[firstEmpty];
	}

	if (previous == -1) {
		this->next[empty] = this->head;
		this->head = empty;
	}
	else {
		this->next[previous] = empty;
		this->next[empty] = current;
	}

	return true;
}


//O(n)
bool SortedSet::remove(TComp e)
{
	int current = this->head;
	int previous = -1;

	while (current != -1 && this->elements[current] != e) {
		previous = current;
		current = this->next[current];
	}

	if (current == -1) {
		return false;
	}

	if (previous == -1) {
		this->head = this->next[current];
	}
	else {
		this->next[previous] = this->next[current];
	}

	this->next[current] = this->firstEmpty;
	this->firstEmpty = current;

	return true;
}


//O(n)
bool SortedSet::search(TElem elem) const
{
	int current = this->head;

	while (current != -1) {
		if (this->elements[current] == elem) {
			return true;
		}
		current = this->next[current];
	}

	return false;
}


//theta(n)
int SortedSet::size() const
{
	int size = 0;
	int current = this->head;

	while (current != -1) {
		size++;
		current = this->next[current];
	}

	return size;
}


//theta(1)
bool SortedSet::isEmpty() const
{
	return (this->head == -1);
}


//theta(1)
SortedSetIterator SortedSet::iterator() const
{
	return SortedSetIterator(*this);
}


//theta(1)
SortedSet::~SortedSet()
{
	delete this->elements;
	delete this->next;
}
