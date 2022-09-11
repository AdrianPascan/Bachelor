#pragma once

#include "DynamicVector.h"
#include <string>


DynamicVector::DynamicVector(int capacity)
{
	this->size = 0;
	this->capacity = capacity;
	this->elements = new TypeElement*[this->capacity];
}


DynamicVector::~DynamicVector()
{
	for (int index = 0; index < this->size; index++)
		delete this->elements[index];
	delete[] this->elements;
}

int DynamicVector::add(TypeElement & element)
{
	if (this->find(element) != -1)
		return -1;

	if (this->size == this->capacity)
		this->resize();

	this->size++;

	this->elements[this->size - 1] = new TypeElement{ element };

	return 0;
}

int DynamicVector::remove(TypeElement & element)
{
	int position = this->find(element);

	if (position == -1)
		return -1;

	delete this->elements[position];

	for (int index = position + 1; index < this->size; index++)
		this->elements[index - 1] = this->elements[index];

	this->size--;
			
	return 0;
}

int DynamicVector::length()
{
	return this->size;
}

int DynamicVector::update(TypeElement & element)
{
	int position = this->find(element);

	if (position == -1)
		return -1;
	
	delete this->elements[position];
	
	TypeElement * updatedElement = new TypeElement{ element };
	this->elements[position] = updatedElement;

	return 0;
}

std::string DynamicVector::list()
{
	std::string string{};

	for (int index = 0; index < this->size; index++)
	{
		std::string elementString = this->elements[index]->toString();
		string.append(elementString);
	}
	
	//if (string == "")
		//string = "No data to show.\n";

	return string;
}

void DynamicVector::resize()
{
	this->capacity *= 2;

	TypeElement** auxiliary = new TypeElement*[this->capacity];

	for (int index = 0; index < this->size; index++)
		auxiliary[index] = this->elements[index];

	delete[] this->elements;

	this->elements = auxiliary;
}

int DynamicVector::find(TypeElement & element)
{
	for (int position = 0; position < this->size; position++)
		if (*(this->elements[position]) == element)
			return position;

	return -1;
}



