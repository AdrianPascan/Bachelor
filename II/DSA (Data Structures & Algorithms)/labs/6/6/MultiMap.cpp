#include "MultiMap.h"


// theta(m)
MultiMap::MultiMap()
{
	m = 11;

	elements = new Element[m];
	for (int index = 0; index < m; ++index) {
		elements[index].capacity = elements[index].size = 0;
		elements[index].values = NULL;
	}

	pairsNumber = 0;
}


// O(oldM * newM)
void MultiMap::add(TKey c, TValue v)
{
	int factor = 0, position = 0, firstEmpty = -1;
	do {
		position = hash(c, factor);

		if (elements[position].values == NULL && firstEmpty == -1) {
			firstEmpty = position;
		}

		++factor;
	} while (factor < m && elements[position].key != c);

	if (factor == m && elements[position].key != c) {
		if (firstEmpty == -1) { // resize + rehash
			int oldM = m;

			m = m * 2 + 1;
			while (!isPrime(m)) {
				m += 2;
			}

			Element* newElements = new Element[m];
			for (int index = 0; index < m; ++index) {
				newElements[index].capacity = newElements[index].size = 0;
				newElements[index].values = NULL;
			}

			for (int index = 0; index < oldM; ++index) {
				if (elements[index].values != NULL) {
					int currentFactor = 0, currentPosition = 0;
					do {
						currentPosition = hash(elements[index].key, currentFactor);
						++currentFactor;
					} while (currentFactor < m && newElements[currentPosition].values != NULL);

					newElements[currentPosition].key = elements[index].key;
					newElements[currentPosition].capacity = elements[index].capacity;
					newElements[currentPosition].size = elements[index].size;

					// should work: 
					// newElements[currentPosition].values = elements[index].values;

					// COPY 'VALUES' ARRAY
					newElements[currentPosition].values = new TValue[elements[index].capacity];
					for (int currentIndex = 0; currentIndex < elements[index].size; ++currentIndex) {
						newElements[currentPosition].values[currentIndex] = elements[index].values[currentIndex];
					}
				}
			}

			delete[] elements;
			elements = newElements;

			int currentFactor = 0, currentPosition = 0;
			do {
				currentPosition = hash(c, currentFactor);
				++currentFactor;
			} while (currentFactor < m && elements[currentPosition].values != NULL);

			elements[currentPosition].key = c;
			
			elements[currentPosition].capacity = 10;
			elements[currentPosition].size = 1;

			elements[currentPosition].values = new TValue[10];
			elements[currentPosition].values[0] = v;
		}
		else {
			elements[firstEmpty].key = c;

			elements[firstEmpty].capacity = 10;
			elements[firstEmpty].size = 1;

			elements[firstEmpty].values = new TValue[10];
			elements[firstEmpty].values[0] = v;
		}
	}
	else {
		if (elements[position].size == elements[position].capacity) { // resize elements[position].values
			if (elements[position].capacity == 0) {
				elements[position].capacity = 10;
			}
			else {
				elements[position].capacity *= 2;
			}

			TValue * newValues = new TValue[elements[position].capacity];

			for (int index = 0; index < elements[position].size; ++index) {
				newValues[index] = elements[position].values[index];
			}

			delete[] elements[position].values;
			elements[position].values = newValues;
		}

		elements[position].values[(elements[position].size)++] = v;
	}

	++pairsNumber;
}


// O(m + elements[position].capacity)
bool MultiMap::remove(TKey c, TValue v)
{
	int factor = 0, position = 0;
	do {
		position = hash(c, factor);
		++factor;
	} while (factor < m && elements[position].key != c);

	if (factor == m && elements[position].key != c) {
		return false;
	}

	int index = 0;
	while (index < elements[position].size && elements[position].values[index] != v) {
		++index;
	}

	if (index == elements[position].size) {
		return false;
	}

	if (elements[position].size == 1) {
		elements[position].capacity = elements[position].size = 0;
		delete[] elements[position].values;
		elements[position].values = NULL;
	}
	else {
		elements[position].values[index] = elements[position].values[--(elements[position].size)];
	}

	--pairsNumber;
	return true;
}


// O(m + elements[position].size)
vector<TValue> MultiMap::search(TKey c) const
{
	vector<TValue> values{};

	int factor = 0, position = 0;
	do {
		position = hash(c, factor);
		++factor;
	} while (factor < m && elements[position].key != c);

	if (elements[position].key == c) {
		for (int index = 0; index < elements[position].size; ++index) {
			values.push_back(elements[position].values[index]);
		}
	}

	return values;
}


// theta(1)
int MultiMap::size() const
{
	return pairsNumber;
}


// theta(1)
bool MultiMap::isEmpty() const
{
	return (pairsNumber == 0);
}


// theta(1)
MultiMapIterator MultiMap::iterator() const
{
	return MultiMapIterator{ *this };
}


// theta(m + nr. of pairs)
MultiMap::~MultiMap()
{
	for (int index = 0; index < m; ++index) {
		if (elements[index].values != NULL) {
			delete[] elements[index].values;
		}
	}
	delete[] elements;
}

void MultiMap::filter(Condition cond)
{
	for (int index = 0; index < m; ++index) {
		if (elements[index].values != NULL) {
			for (int current = 0; current < elements[index].size; ++current) {
				if (!cond(elements[index].values[current])) {
					elements[index].values[current] = elements[index].values[--(elements[index].size)];
					--pairsNumber;
				}
			}
			if (elements[index].size == 0) {
				elements[index].capacity = 0;
				elements[index].values = NULL;
			}
		}
	}
}


// theta(1)
int MultiMap::hash(TKey key, int factor) const
{
	//TKey code = hashCode(key);
	long long code = hashCode(key);
	long long a = (1 + (code % (m - 1)));
	long long b = code % m;
	long long c = (factor * a) % m;
	long long d = (b + c) % m;
	return d;
	//(code % m + factor * (1 + (code % (m - 1)))) % m
}


// theta(1)
TKey MultiMap::hashCode(TKey k) const
{
	if (k < 0) {
		k *= -1;
	}
	return k;
}


// O(squareRoot(number))
bool MultiMap::isPrime(int number)
{
	if (number <= 1 || (number % 2 == 0 && number != 2)) {
		return false;
	}

	for (int divisor = 3; divisor*divisor <= number; ++divisor) {
		if (number % divisor == 0) {
			return false;
		}
	}

	return true;
}
