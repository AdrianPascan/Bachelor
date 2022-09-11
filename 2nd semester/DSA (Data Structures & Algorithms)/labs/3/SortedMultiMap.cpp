#pragma once

#include "SortedMultiMap.h"
#include "SMMIterator.h"

// theta(1)
SortedMultiMap::SortedMultiMap(Relation r)
{
	this->relation = r;
	this->length = 0;
	this->head = NULL;
	this->tail = NULL;
}


// O(nr. of keys)
void SortedMultiMap::add(TKey c, TValue v)
{
	KeyNode *currentKey = this->head;
	ValueNode *newValueNode = new ValueNode{ v };

	while (currentKey != NULL && currentKey->key != c)
	{
		currentKey = currentKey->next;
	}

	if (currentKey == NULL)
	{
		KeyNode * newKeyNode = new KeyNode{ c };
		newKeyNode->head = newKeyNode->tail = newValueNode;

		if (this->length == 0)
		{
			this->head = this->tail = newKeyNode;
		}
		else
		{
			currentKey = this->head;

			while (currentKey != NULL && !(this->relation(c, currentKey->key)))
				currentKey = currentKey->next;

			if (currentKey == NULL)
			{
				this->tail->next = newKeyNode;
				newKeyNode->previous = this->tail;
				this->tail = newKeyNode;
			}
			else if (currentKey == this->head)
			{
				this->head->previous = newKeyNode;
				newKeyNode->next = this->head;
				this->head = newKeyNode;
			}
			else
			{
				newKeyNode->next = currentKey;
				newKeyNode->previous = currentKey->previous;
				currentKey->previous->next = newKeyNode;
				currentKey->previous = newKeyNode;
			}
		}
	}
	else
	{
		currentKey->tail->next = newValueNode;
		newValueNode->previous = currentKey->tail;
		currentKey->tail = newValueNode;
	}

	this->length++;
}


// O(size)
vector<TValue> SortedMultiMap::search(TKey c) const
{
	std::vector<TValue> values;

	KeyNode * currentKey = this->head;

	while (currentKey != NULL && currentKey->key != c)
		currentKey = currentKey->next;

	if (currentKey != NULL)
	{
		ValueNode * currentValue = currentKey->head;

		while (currentValue != NULL)
		{
			values.push_back(currentValue->value);
			currentValue = currentValue->next;
		}
	}

	return values;
}

// O(size)
bool SortedMultiMap::remove(TKey c, TValue v)
{
	if (this->length == 0)
		return false;

	KeyNode *currentKey = this->head;
	while (currentKey != NULL && currentKey->key != c)
		currentKey = currentKey->next;

	if (currentKey == NULL)
		return false;

	ValueNode *currentValue = currentKey->head;
	while (currentValue != NULL && currentValue->value != v)
		currentValue = currentValue->next;

	if (currentValue == NULL)
		return false;

	if (currentKey->head == currentKey->tail)
	{
		if (currentKey == this->head)
		{
			this->head = this->head->next;
			if (this->head != NULL)
				this->head->previous = NULL;
		}
		else if (currentKey == this->tail)
		{
			this->tail = this->tail->previous;
			if (this->tail != NULL)
				this->tail->next = NULL;
		}
		else
		{
			currentKey->previous->next = currentKey->next;
			currentKey->next->previous = currentKey->previous;
		}
		delete currentKey;
	}
	else
	{
		if (currentValue == currentKey->head)
		{
			currentKey->head = currentKey->head->next;
			if (currentKey->head != NULL)
				currentKey->head->previous = NULL;
		}
		else if (currentValue == currentKey->tail)
		{
			currentKey->tail = currentKey->tail->previous;
			if (currentKey->tail != NULL)
				currentKey->tail->next = NULL;
		}
		else
		{
			currentValue->previous->next = currentValue->next;
			currentValue->next->previous = currentValue->previous;
		}
	}

	delete currentValue;

	this->length--;

	return true;
}


// theta(1)
int SortedMultiMap::size() const
{
	return this->length;
}


// theta(1)
bool SortedMultiMap::isEmpty() const
{
	if (this->length == 0)
		return true;

	return false;
}


// theta(1)
SMMIterator SortedMultiMap::iterator() const
{
	return SMMIterator(*this);
}


// theta(size)
SortedMultiMap::~SortedMultiMap()
{
	if (this->length != 0)
	{
		KeyNode * currentKey = this->head;

		while (currentKey != NULL)
		{
			if (currentKey->head == currentKey->tail)
				delete currentKey->head;
			else
			{
				ValueNode *currentValue = currentKey->head->next;

				while (currentValue != NULL)
				{
					delete currentValue->previous;
					currentValue = currentValue->next;
				}
				delete currentKey->tail;
			}

			currentKey = currentKey->next;
		}

		if (this->head == this->tail)
			delete this->head;
		else
		{
			currentKey = this->head->next;

			while (currentKey != NULL)
			{
				delete currentKey->previous;
				currentKey = currentKey->next;
			}
			delete this->tail;
		}
	}
}


//theta(size)
int SortedMultiMap::getValueRange() const
{
	if (this->head == NULL) {
		return -1;
	}

	KeyNode * currentKey = this->head;
	int maxValue = currentKey->head->value;
	int minValue = currentKey->head->value;

	while (currentKey != NULL) {
		ValueNode * currentValue = currentKey->head;
		while (currentValue != NULL) {
			if (currentValue->value > maxValue) {
				maxValue = currentValue->value;
			}
			if (currentValue->value < minValue) {
				minValue = currentValue->value;
			}
			currentValue = currentValue->next;
		}
		currentKey = currentKey->next;
	}
	return maxValue - minValue;
}