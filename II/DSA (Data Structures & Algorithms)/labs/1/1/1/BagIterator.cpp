#pragma once

#include "BagIterator.h"
#include "Bag.h"
#include <exception>

// theta(1)
BagIterator::BagIterator(const Bag& container) : bag(container)
{
	this->positionsIndex = 0;
}

// theta(1)
void BagIterator::first()
{
	this->positionsIndex = 0;
}

// theta(1)
void BagIterator::next()
{
	if (!this->valid())
		throw std::exception();

	this->positionsIndex++;
}

// theta(1)
bool BagIterator::valid() const
{
	if (this->positionsIndex < this->bag.positionsSize)
		return true;

	return false;
}

// theta(1)
TElem BagIterator::getCurrent() const
{
	if (!this->valid())
		throw std::exception();

	return this->bag.elementsList[this->bag.positionsList[this->positionsIndex]];
}
