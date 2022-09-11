#pragma once

#include "Domain.h"

typedef struct
{
	Material* material;
	char* type;
}Operation;


Operation* createOperation(Material*, char*);
void destroyOperation(Operation*);