#pragma once

#include "SMMIterator.h"
#include "SortedMultiMap.h"
#include <exception>

// theta(1)
SMMIterator::SMMIterator(const SortedMultiMap & container): smm(container)
{
	this->currentKey = this->smm.head;

	if (this->smm.head != NULL)
		this->currentValue = this->currentKey->head;
	else
		this->currentValue = NULL;
}

// theta(1)
void SMMIterator::first()
{
	this->currentKey = this->smm.head;

	if (this->smm.head != NULL)
		this->currentValue = this->currentKey->head;
	else
		this->currentValue = NULL;
}

// theta(1)
void SMMIterator::next()
{
	if (!(this->valid()))
		throw std::exception();

	if (this->currentValue->next != NULL)
		this->currentValue = this->currentValue->next;
	else
	{
		this->currentKey = this->currentKey->next;

		if (this->currentKey != NULL)
			this->currentValue = this->currentKey->head;
		else
			this->currentValue = NULL;
	}
}

// theta(1)
bool SMMIterator::valid() const
{
	if (this->currentKey == NULL || this->currentValue == NULL)
		return false;

	return true;
}

// theta(1)
TElem SMMIterator::getCurrent() const
{
	if (!this->valid())
		throw std::exception();

	return TElem(this->currentKey->key, this->currentValue->value);
}
