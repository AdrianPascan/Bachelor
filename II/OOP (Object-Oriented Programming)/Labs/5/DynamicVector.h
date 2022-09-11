#pragma once

#include "Domain.h"
#include <string>

class Dog;
typedef Dog TypeElement;


class DynamicVector
{
private:

	TypeElement** elements;
	int capacity;
	int size;

public:

	DynamicVector(int capacity = 10);

	~DynamicVector();

	int add(TypeElement & element);

	int remove(TypeElement & element);

	int length();

	int update(TypeElement & element);

	std::string list();

private:

	void resize();
	int find(TypeElement & element);
};

