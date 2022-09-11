#pragma once

#include "Operation.h"


typedef struct
{
	Operation** operations;
	int length;
}OperationStack;


OperationStack* createOperationStack();
void destroyOperationStack(OperationStack*);
int pushOperation(OperationStack*, Operation*);
Operation* popOperation(OperationStack*);
void destroyCopiesStack(OperationStack*);
void emptyStack(OperationStack*);
int isAlmostFull(OperationStack*);