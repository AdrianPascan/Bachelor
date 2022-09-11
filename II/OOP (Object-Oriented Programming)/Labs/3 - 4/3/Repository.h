#pragma once

#include "Domain.h"

typedef struct
{
	Material **materials;
	int capacity, length;
}Repository;


Repository *createRepository();
int store(Repository*, int, char*, char*, int);
int erase(Repository*, int);
int update(Repository*, int, char*, char*, int);
char* stringRepository(Repository*);
char* stringNameContains(Repository*, char*);
char* stringQuantityLessThan(Repository*, int);
void destroyRepository(Repository*);
Material* getMaterial(Repository*, int);

