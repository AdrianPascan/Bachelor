#pragma once

#include<vector>
#include<utility>
#include"MultiMapIterator.h"

using namespace std;

typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
typedef bool(*Condition)(TValue);

typedef struct _Element{
	TKey key;
	TValue * values;
	int capacity;
	int size;
}Element;

class MultiMapIterator;

class MultiMap
{
	friend class MultiMapIterator;

private:

	/* representation of the MultiMap */
	int m;
	Element* elements;
	int pairsNumber;


public:

	//constructor
	MultiMap();


	//adds a key value pair to the multimap
	void add(TKey c, TValue v);


	//removes a key value pair from the multimap
	//returns true if the pair was removed (if it was in the multimap) and false otherwise
	bool remove(TKey c, TValue v);


	//returns the vector of values associated to a key. If the key is not in the MultiMap, the vector is empty
	vector<TValue> search(TKey c) const;


	//returns the number of pairs from the multimap
	int size() const;


	//checks whether the multimap is empty
	bool isEmpty() const;


	//returns an iterator for the multimap
	MultiMapIterator iterator() const;


	//descturctor
	~MultiMap();


	//extra
	void filter(Condition cond);


private:

	//other functions
	int hash(TKey k, int i) const;
	TKey hashCode(TKey k) const;
	bool isPrime(int number);

};


