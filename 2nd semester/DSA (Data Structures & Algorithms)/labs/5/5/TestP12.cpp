#pragma once

#include "P12.h"
#include "TestP12.h"
#include <algorithm>
#include <assert.h>
#include <iostream>

using namespace std;

bool r1(TElem e1, TElem e2) {
	return e1 <= e2;
}

bool r2(TElem e1, TElem e2) {
	return e1 >= e2;
}


vector<TElem> createRandom(int from, int to, int nr) {
	int size = to - from + 1;
	bool* elems = new bool[size];
	for (int i = 0; i < size; i++) {
		elems[i] = true;
	}
	int count = 0;
	while (count < nr) {
		int pos = rand() % size;
		if (elems[pos] == true) {
			elems[pos] = false;
			count++;
		}
	}
	vector<TElem> result;
	for (int i = 0; i < size; i++) {
		if (!elems[i]) {
			result.push_back(from + i);
		}
	}
	random_shuffle(result.begin(), result.end());
	return result;
}

void testVect(vector<TElem> init, vector<TElem> res, Relation r) {
	sort(init.begin(), init.end(), r);
	sort(res.begin(), res.end(), r);
	for (int i = 0; i < res.size(); i++) {
		assert(init[i] == res[i]);
	}
}

void testRel(Relation r) {
	for (int i = -10; i < 10; i = i + 2) {
		for (int j = 100; j < 1500; j = j + 200) {
			for (int k = 1; k < 30; k = k + 4) {				
				int nr = 2 * (j - i) / 3;
				vector<TElem> random = createRandom(i, j, nr);
				vector<TElem> res = firstK(random, r, k);
				testVect(random, res, r);
			}
		}
	}
}

void testP12() {
	cout << "Test r1" << endl;
	testRel(r1);
	cout << "Test r2" << endl;
	testRel(r2);
}