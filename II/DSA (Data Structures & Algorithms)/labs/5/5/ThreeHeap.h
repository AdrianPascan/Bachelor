#pragma once

typedef int TElem;
typedef bool(*Relation) (TElem e1, TElem e2);

class ThreeHeap
{
private:
	int length;
	int capacity;
	TElem * elements;
	Relation relation;

public:
	ThreeHeap();
	ThreeHeap(Relation relation);
	~ThreeHeap();
	void add(TElem element);
	TElem remove();

private:
	void bubbleUp(int position);
	void bubbleDown(int position);
	void resize(int factor = 2);
};

