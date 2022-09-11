#pragma once

#include <vector>
#include "SMMIterator.h"
#include <utility>

using namespace std;

typedef int TKey;
typedef int TValue;
typedef std::pair<TKey, TValue> TElem;
typedef bool(*Relation)(TKey, TKey);


class SMMIterator;


class ValueNode {

public:
	TValue value;
	ValueNode * next, *previous;

	ValueNode(TValue value, ValueNode *next = NULL, ValueNode *previous = NULL) {
		this->value = value;
		this->next = next;
		this->previous = previous;
	}

};


class KeyNode {

public:
	TKey key;
	KeyNode * next, *previous;
	ValueNode * head, * tail;

	KeyNode(TKey key, KeyNode *next = NULL, KeyNode *previous = NULL, ValueNode *head = NULL, ValueNode * tail = NULL) {
		this->key = key;
		this->next = next;
		this->previous = previous;
		this->head = head;
		this->tail = tail;
	}

};


class SortedMultiMap {

	friend class SMMIterator;

private:

	/* representation of the SortedMultiMap */

	KeyNode* head, *tail;
	int length;
	Relation relation;

public:



	// constructor

	SortedMultiMap(Relation r);



	//adds a new key value pair to the sorted multi map

	void add(TKey c, TValue v);



	//returns the values belonging to a given key

	vector<TValue> search(TKey c) const;



	//removes a key value pair from the sorted multimap

	//returns true if the pair was removed (it was part of the multimap), false if nothing is removed

	bool remove(TKey c, TValue v);



	//returns the number of key-value pairs from the sorted multimap

	int size() const;



	//verifies if the sorted multi map is empty

	bool isEmpty() const;



	// returns an iterator for the sorted multimap. The iterator will returns the pairs as required by the relation (given to the constructor)	

	SMMIterator iterator() const;



	// destructor

	~SortedMultiMap();

	//extra
	int getValueRange() const;

};
