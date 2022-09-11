#pragma once

#include "P12.h"

//O(n*log3(k))
vector<TElem> firstK(vector<TElem> inputVector, Relation r, int k)
{
	if (k <= 0) {
		throw exception{ "k must be greater than 0" };
	}

	if (k > inputVector.size()) {
		return vector<TElem>{inputVector};
	}

	ThreeHeap heap{ r };

	for (int index = 0; index < k;index++) {
		heap.add(inputVector[index]);
	}
	for (int index = k; index < inputVector.size(); index++) {
		heap.add(inputVector[index]);
		heap.remove();
	}

	vector<TElem> result{};
	for (int index = 0; index < k; index++) {
		result.push_back(heap.remove());
	}
	return result;
}
