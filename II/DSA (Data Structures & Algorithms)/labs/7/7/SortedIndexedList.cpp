#include "SortedIndexedList.h"

using namespace std;

SortedIndexedList::SortedIndexedList(Relation r): relation{r}
{
	nodes = new BSTNode[10];
	root = -1;
	elementsNumber = 0;
	capacity = 10;
	firstFree = 0;
}

int SortedIndexedList::size() const
{
	return elementsNumber;
}

bool SortedIndexedList::isEmpty() const
{
	return elementsNumber == 0;
}

TComp SortedIndexedList::getElement(int pos) const
{
	if (pos < 0) {
		throw exception{ "Invalid position!" };
	}

	int current = root;
	int position = pos;

	while (current != -1 && position >= 0) {
		if (nodes[current].position == position) {
			return nodes[current].info;
		}
		else if (nodes[current].position > position) {
			current = nodes[current].leftSon;
		}
		else {
			position = position - (nodes[current].position + 1);
			current = nodes[current].rightSon;
		}
	}

	throw exception{"Invalid position!"};
}

void SortedIndexedList::add(TComp e)
{
	if (firstFree == capacity) { // resize
		BSTNode* newNodes = new BSTNode[capacity * 2];

		for (int index = 0; index < capacity; index++) {
			newNodes[index].info = nodes[index].info;
			newNodes[index].leftSon = nodes[index].leftSon;
			newNodes[index].rightSon = nodes[index].rightSon;
			newNodes[index].position = nodes[index].position;
		}

		delete[] nodes;
		nodes = newNodes;

		capacity *= 2;
	}

	nodes[firstFree].info = e;
	nodes[firstFree].leftSon = -1;
	nodes[firstFree].rightSon = -1;
	nodes[firstFree].position = 0;

	if (root == -1) {
		root = firstFree;
	}
	else {
		int current = root;
		int parent = -1;

		while (current != -1) {
			parent = current;

			if (relation(e, nodes[current].info)) {
				current = nodes[current].leftSon;
				nodes[parent].position++;
			}
			else {
				current = nodes[current].rightSon;
			}
		}

		if (relation(e, nodes[parent].info)) {
			nodes[parent].leftSon = firstFree;
		}
		else {
			nodes[parent].rightSon = firstFree;
		}
	}

	do {
		firstFree++;
	} while (firstFree != capacity && nodes[firstFree].info != NullTComp);

	elementsNumber++;
}

TComp SortedIndexedList::remove(int pos)
{
	if (pos < 0) {
		throw exception{ "Invalid position!" };
	}

	int current = root;
	int parent = -1;
	int position = pos;

	while (current != -1 && position >= 0) {
		if (nodes[current].position == position) {
			break;
		}
		
		parent = current;

		if (nodes[current].position > position) {
			current = nodes[current].leftSon;
		}
		else {
			position = position - (nodes[current].position + 1);
			current = nodes[current].rightSon;
		}
	}

	if (current == -1) {
		throw exception{ "Invalid position!" };
	}

	int element = nodes[current].info;
	
	if (nodes[current].leftSon == -1) {
		if (current == root) {
			root = nodes[current].rightSon;
		}
		else {
			if (nodes[parent].leftSon == current) {
				nodes[parent].leftSon = nodes[current].rightSon;
			}
			else {
				nodes[parent].rightSon = nodes[current].rightSon;
			}
		}

		nodes[current].info = NullTComp;
		nodes[current].leftSon = -1;
		nodes[current].rightSon = -1;
		nodes[current].position = 0;
	}
	else if (nodes[current].rightSon == -1) {
		if (current == root) {
			root = nodes[current].rightSon;
		}
		else {
			if (nodes[parent].leftSon == current) {
				nodes[parent].leftSon = nodes[current].leftSon;
			}
			else {
				nodes[parent].rightSon = nodes[current].leftSon;
			}
		}

		nodes[current].info = NullTComp;
		nodes[current].leftSon = -1;
		nodes[current].rightSon = -1;
		nodes[current].position = 0;
	}
	else {
		int minimum = nodes[current].rightSon;

		if (nodes[minimum].leftSon == -1) {
			nodes[current].rightSon = nodes[minimum].rightSon;
		}
		else {
			int currentParent = current;

			while (nodes[minimum].leftSon != -1) {
				currentParent = minimum;
				minimum = nodes[minimum].leftSon;
			}

			/// !!!
			if (nodes[minimum].rightSon != -1) {
				nodes[currentParent].leftSon = nodes[minimum].rightSon;
			}
		}
		nodes[current].info = nodes[minimum].info;

		nodes[minimum].info = NullTComp;
		nodes[minimum].leftSon = -1;
		nodes[minimum].rightSon = -1;
		nodes[minimum].position = 0;
	}

	elementsNumber--;

	return element;
}

int SortedIndexedList::search(TComp e) const
{
	int current = root;
	int position = 0;

	while (current != -1) {
		if (nodes[current].info == e) {
			position += nodes[current].position;
			return position;
		}
		else if (relation(e, nodes[current].info)) {
			current = nodes[current].leftSon;
		}
		else {
			position += nodes[current].position + 1;
			current = nodes[current].rightSon;
		}
	}

	return -1;
}

SortedIndexedListIterator SortedIndexedList::iterator()
{
	return SortedIndexedListIterator(*this);
}

SortedIndexedList::~SortedIndexedList()
{
	delete[] nodes;
}
