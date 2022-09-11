#pragma once

#include "OperationStack.h"
#include <stdlib.h>


OperationStack* createOperationStack()
{
	OperationStack* operationStack = (OperationStack*)malloc(sizeof(OperationStack));

	operationStack->operations = (Operation**)malloc(sizeof(Operation*) * 100);

	operationStack->length = 0;
}


void destroyOperationStack(OperationStack* operationStack) 
{
	free(operationStack->operations);
	free(operationStack);
}


int isEmpty(OperationStack* operationStack)
{
	return (operationStack->length == 0);
}


int isFull(OperationStack* operationStack)
{
	return (operationStack->length == 100);
}


int isAlmostFull(OperationStack* operationStack)
{
	return (operationStack->length == 99);
}


int pushOperation(OperationStack* operationStack, Operation* operation)
{
	if (isFull(operationStack))
		return -1;
	
	operationStack->length++;

	operationStack->operations[operationStack->length - 1] = operation;

	return 0;
}


Operation* popOperation(OperationStack* operationStack)
{
	if (isEmpty(operationStack))
		return NULL;

	Operation* operation;

	operation = operationStack->operations[operationStack->length - 1];
	operationStack->length--;

	return operation;
}


void emptyStack(OperationStack* operationStack)
{
	if (!isEmpty(operationStack))
	{
		for (int index = 0; index < operationStack->length; index++)
		{
			free(operationStack->operations[index]->material);
			destroyOperation(operationStack->operations[index]);
		}

		operationStack->length = 0;
	}
}


