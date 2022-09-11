#pragma once

#include "ThreeHeap.h"
#include <exception>

using namespace std;

//theta(1)
ThreeHeap::ThreeHeap()
{
}

//theta(1)
ThreeHeap::ThreeHeap(Relation _relation): relation(_relation)
{
	length = 0;
	capacity = 10;
	elements = new TElem[10];
}

//theta(n)
ThreeHeap::~ThreeHeap()
{
	delete[] elements;
}


//O(n) / O(log3(n))
void ThreeHeap::add(TElem element)
{
	if (length == capacity) {
		resize();
	}

	elements[length++] = element;
	bubbleUp(length - 1);
}

//O(log3(n))
TElem ThreeHeap::remove()
{
	if (length == 0) {
		throw exception{ "Empty heap" };
	}

	TElem value = elements[0];

	elements[0] = elements[--length];
	bubbleDown(0);

	return value;
}

//O(log3(n))
void ThreeHeap::bubbleUp(int position)
{
	int child = position;
	TElem value = elements[position];
	int parent = (position - 1) / 3;

	while (child && relation(elements[parent], value)) {
		elements[child] = elements[parent];
		child = parent;
		parent = (parent - 1) / 3;
	}
	elements[child] = value;
}

//O(log3(n))
void ThreeHeap::bubbleDown(int position)
{
	int parent = position;
	TElem value = elements[position];
	
	while (parent < length - 1) {
		int maxChild = -1;
		int firstChild = parent * 3 + 1;

		if (firstChild < length) {
			maxChild = firstChild;
		}
		if (firstChild + 1 < length && relation(elements[maxChild], elements[firstChild + 1])) {
			maxChild = firstChild + 1;
		}
		if (firstChild + 2 < length && relation(elements[maxChild], elements[firstChild + 2])) {
			maxChild = firstChild + 2;
		}
		
		if (maxChild != -1 && relation(value, elements[maxChild])) {
			TElem auxiliary = elements[parent];
			elements[parent] = elements[maxChild];
			elements[maxChild] = auxiliary;
			parent = maxChild;
		}
		else {
			break;
		}
	}
}

//theta(n)
void ThreeHeap::resize(int factor)
{
	TElem * auxiliary = new TElem[capacity*factor];

	for (int index = 0; index < capacity; index++) {
		auxiliary[index] = elements[index];
	}

	delete[] elements;
	elements = auxiliary;

	capacity *= factor;
}
