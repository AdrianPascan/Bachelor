#pragma once

#include "Bag.h"
#include "BagIterator.h"

Bag::Bag()
{
	this->elementsSize = 0;
	this->elementsCapacity = 10;
	this->elementsList = new TElem[this->elementsCapacity];

	this->positionsSize = 0;
	this->positionsCapacity = 10;
	this->positionsList = new int[this->positionsCapacity];
}

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

			for (int index = positionsIndex + 1; index < this->positionsSize; index++) {

				this->positionsList[index - 1] = this->positionsList[index];

			}

			this->positionsSize--;

			for (int index = 0; index < positionsIndex; index++)
				if (this->positionsList[index] == position)
					return true;

			for (int elementsIndex = position + 1; elementsIndex < this->elementsSize; elementsIndex++)
				this->elementsList[elementsIndex - 1] = this->elementsList[elementsIndex];

			this->elementsSize--;

			for (int index = position; index < this->positionsSize; index++)
				if (this->positionsList[index] >= position)
					this->positionsList[index]--;

			return true;

		}

	}

}

bool Bag::search(TElem e) const
{
	for (int index = 0; index < this->elementsSize; index++)
		if (this->elementsList[index] == e)
			return true;

	return false;
}

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

int Bag::size() const
{
	return this->positionsSize;
}

BagIterator Bag::iterator() const{
	return BagIterator(*this);
}

bool Bag::isEmpty() const
{
	return this->elementsSize == 0;
}

Bag::~Bag()
{
	delete[] this->elementsList;
	delete[] this->positionsList;
}
