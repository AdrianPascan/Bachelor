#pragma once

#include "SortedSetIterator.h"
#include "SortedSet.h"
#include <exception>


//theta(1)
SortedSetIterator::SortedSetIterator(const SortedSet& sortedSet) : ss( sortedSet ) {
	this->current = sortedSet.head;
}


//theta(1)
void SortedSetIterator::first()
{
	this->current = this->ss.head;
}


//theta(1)
void SortedSetIterator::next()
{
	if (!(this->valid()))
		throw std::exception();

	this->current = this->ss.next[current];
}


//theta(1)
bool SortedSetIterator::valid() const
{
	return (this->current != -1);
}


//theta(1)
TElem SortedSetIterator::getCurrent() const
{
	if (!(this->valid()))
		throw std::exception();

	return this->ss.elements[this->current];
}


//O(k)
void SortedSetIterator::jumpForward(int k)
{
	if (k <= 0) {
		throw std::exception();
	}

	int steps = 0;

	while (this->current != -1 && steps < k) {
		steps++;
		this->current = this->ss.next[current];
	}

	if (steps != k) {
		throw std::exception();
	}
}
