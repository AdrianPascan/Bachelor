#include "SortedIndexedListIterator.h"

SortedIndexedListIterator::SortedIndexedListIterator(const SortedIndexedList & _sortedList) : sortedList{ _sortedList }
{
	this->first();
	//stack = std::stack<int>{};

	//current = sortedList.root;
	//while (current != -1) {
	//	stack.push(current);
	//	current = sortedList.nodes[current].leftSon;
	//}

	//if (!stack.empty()) {
	//	current = stack.top();
	//	stack.pop();
	//}
}

void SortedIndexedListIterator::first()
{
	stack = std::stack<int>{};

	int node = sortedList.root;
	while (node != -1) {
		stack.push(node);
		node = sortedList.nodes[node].leftSon;
	}

	if (!stack.empty()) {
		current = stack.top();
	}
	else {
		current = -1;
	}
}

void SortedIndexedListIterator::next()
{
	if (current == -1) {
		throw std::exception{ "Invalid iterator!" };
	}
	
	int node = stack.top();
	stack.pop();

	if (sortedList.nodes[node].rightSon != -1) {
		node = sortedList.nodes[node].rightSon;

		while (node != -1) {
			stack.push(node);
			node = sortedList.nodes[node].leftSon;
		}
	}
	if (!stack.empty()) {
		current = stack.top();
	}
	else {
		current = -1;
	}
}

bool SortedIndexedListIterator::valid() const
{
	return current != -1;
}

TComp SortedIndexedListIterator::getCurrent() const
{
	if (current == -1) {
		throw std::exception{ "Invalid iterator!" };
	}

	return sortedList.nodes[current].info;
}
