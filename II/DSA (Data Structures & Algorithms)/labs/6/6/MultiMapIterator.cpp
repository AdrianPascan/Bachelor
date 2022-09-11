#include "MultiMapIterator.h"


// O(m)
MultiMapIterator::MultiMapIterator(const MultiMap & _mm) : mm{ _mm }
{
	currentElement = 0;

	while (currentElement < mm.m && mm.elements[currentElement].values == NULL) {
		++currentElement;
	}

	currentValue = 0;
}


// O(m)
void MultiMapIterator::first()
{
	currentElement = 0;

	while (currentElement < mm.m && mm.elements[currentElement].values == NULL) {
		++currentElement;
	}

	currentValue = 0;
}


// O(m)
void MultiMapIterator::next()
{
	if (!valid()) {
		throw std::exception{};
	}

	if (currentValue + 1 < mm.elements[currentElement].size) {
		++currentValue;
	}
	else {
		do {
			++currentElement;
		} while (currentElement < mm.m && mm.elements[currentElement].values == NULL);

		currentValue = 0;
	}
}


// theta(1)
bool MultiMapIterator::valid() const
{
	return (currentElement < mm.m && currentValue < mm.elements[currentElement].size);
}


// theta(1)
TElem MultiMapIterator::getCurrent() const
{
	if (!valid()){
		throw std::exception{};
	}

	TElem element;
	element.first = mm.elements[currentElement].key;
	element.second = mm.elements[currentElement].values[currentValue];

	return element;
}
