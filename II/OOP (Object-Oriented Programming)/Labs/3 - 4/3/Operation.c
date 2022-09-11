#pragma once

#include "Operation.h"
#include <stdlib.h>
#include <string.h>


Operation* createOperation(Material* material, char* operationType)
{
	Operation* operation = (Operation*)malloc(sizeof(Operation));

	operation->material = material;

	operation->type = (char*)malloc(strlen(operationType) + 1);
	strcpy(operation->type, operationType);

	return operation;
}


void destroyOperation(Operation* operation)
{
	free(operation->type);
	free(operation);
}