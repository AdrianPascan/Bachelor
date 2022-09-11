#pragma once

#include "Bag.h"
#include "BagIterator.h"

// theta(1)
Bag::Bag()
{
	this->elementsSize = 0;
	this->elementsCapacity = 10;
	this->elementsList = new TElem[this->elementsCapacity];

	this->positionsSize = 0;
	this->positionsCapacity = 10;
	this->positionsList = new int[this->positionsCapacity];
}

// O(positionsSize)
void Bag::add(TElem e)
{
	if (this->positionsSize == this->positionsCapacity) {

		this->positionsCapacity *= 2;

		int *auxiliary = new int[this->positionsCapacity];

		for (int index = 0; index < this->positionsSize; index++)
			auxiliary[index] = this->positionsList[index];

		delete[] this->positionsList;

		this->positionsList = auxiliary;

	}

	this->positionsSize++;

	for (int elementsIndex = 0; elementsIndex < this->elementsSize; elementsIndex++) {

		if (this->elementsList[elementsIndex] == e) {
			
			this->positionsList[this->positionsSize - 1] = elementsIndex;
			return;

		}

	}

	if (this->elementsSize == this->elementsCapacity) {

		this->elementsCapacity *= 2;

		TElem *auxiliary = new TElem[this->elementsCapacity];

		for (int index = 0; index < this->elementsSize; index++)
			auxiliary[index] = this->elementsList[index];

		delete[] this->elementsList;

		this->elementsList = auxiliary;

	}

	this->elementsSize++;

	this->elementsList[this->elementsSize - 1] = e;

	this->positionsList[this->positionsSize-1] = this->elementsSize - 1;

}

// O(positionsSize)
bool Bag::remove(TElem e)
{
	int position = -1;

	for (int elementsIndex = 0; elementsIndex < this->elementsSize; elementsIndex++) {

		if (this->elementsList[elementsIndex] == e) {

			position = elementsIndex;
			break;

		}

	}

	if (position == -1) {

		return false;

	}

	for (int positionsIndex = this->positionsSize - 1; positionsIndex >= 0; positionsIndex--) {

		if (this->positionsList[positionsIndex] == position) {

			if (positionsIndex != this->positionsSize - 1)
				this->positionsList[positionsIndex] = this->positionsList[this->positionsSize - 1];

			this->positionsSize--;

			for (int index = 0; index < positionsIndex; index++)
				if (this->positionsList[index] == position)
					return true;

			this->elementsList[position] = this->elementsList[this->elementsSize - 1];

			this->elementsSize--;

			for (int index = 0; index < this->positionsSize; index++)
				if (this->positionsList[index] == this->elementsSize)
					this->positionsList[index] = position;

			return true; 

		}

	}

}

// O(elementsSize)
bool Bag::search(TElem e) const
{
	for (int index = 0; index < this->elementsSize; index++)
		if (this->elementsList[index] == e)
			return true;

	return false;
}

// O(positionsSize)
int Bag::nrOccurrences(TElem e) const
{
	int position = -1;

	for (int elementsIndex = 0; elementsIndex < this->elementsSize; elementsIndex++) {
		if (this->elementsList[elementsIndex] == e) {
			position = elementsIndex;
			break;
		}
	}

	if (position == -1)
		return 0;

	int occurencesNumber = 0;

	for (int positionsIndex = 0; positionsIndex < this->positionsSize; positionsIndex++)
		if (this->positionsList[positionsIndex] == position)
			occurencesNumber++;

	return occurencesNumber;
}


// O(positionsSize)
TElem Bag::leastFrequent() const
{
	if (this->elementsSize == 0)
		return NULL_TELEM;

	int *frequency = new int[this->elementsSize];
	int minimumFrequency = 2147483647;
	TElem minimumFrequencyElement = this->elementsList[0];

	for (int elementsIndex = 0; elementsIndex < this->elementsSize; elementsIndex++)
		frequency[elementsIndex] = 0;

	for (int positionsIndex = 0; positionsIndex < this->positionsSize; positionsIndex++)
		frequency[this->positionsList[positionsIndex]]++;

	for (int elementsIndex = 0; elementsIndex < this->elementsSize; elementsIndex++)
		if (frequency[elementsIndex] < minimumFrequency)
		{
			minimumFrequency = frequency[elementsIndex];
			minimumFrequencyElement = this->elementsList[elementsIndex];
		}

	delete[] frequency;

	return minimumFrequencyElement;
}

// theta(1)
int Bag::size() const
{
	return this->positionsSize;
}

// theta(1)
BagIterator Bag::iterator() const{
	return BagIterator(*this);
}

// theta(1)
bool Bag::isEmpty() const
{
	return this->elementsSize == 0;
}

// theta(1)
Bag::~Bag()
{
	delete[] this->elementsList;
	delete[] this->positionsList;
}


