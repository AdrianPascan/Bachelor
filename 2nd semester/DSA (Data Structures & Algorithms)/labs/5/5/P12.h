#pragma once

#include "ThreeHeap.h"
#include <vector>

using namespace std;

typedef int TElem;
typedef bool(*Relation) (TElem e1, TElem e2);

//returns a vector with the first K elements (considering a relation) of the vector
//if k is less than or equal to 0 an exception will be thrown.
vector<TElem> firstK(vector<TElem> inputVector, Relation r, int k);
