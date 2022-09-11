#pragma once

#include "Controller.h"
#include <stdlib.h>
#include <string.h>


Controller* createController(Repository* repository)
{
	Controller* controller = (Controller*)malloc(sizeof(Controller));

	controller->repository = repository;
	controller->undoStack = createOperationStack();
	controller->redoStack = createOperationStack();

	return controller;
}


int addMaterial(Controller* controller, char* ID, char* supplier, char* name, char* quantity)
{
	int numberID = atoi(ID);
	int numberQuantity = atoi(quantity);

	Material* copy = createMaterial(numberID, supplier, name, numberQuantity);
	Operation *undo = createOperation(copy, "delete");

	int result = pushOperation(controller->undoStack, undo);
	if (result == -1)
		return 1;

	emptyStack(controller->redoStack);

	return store(controller->repository, numberID, supplier, name, numberQuantity);
}


int deleteMaterial(Controller* controller, char* ID)
{
	int numberID = atoi(ID);

	Material* copy = getMaterial(controller->repository, numberID);
	
	if (copy == NULL)
		return -1;
	
	Operation *undo = createOperation(copy, "add");

	int result = pushOperation(controller->undoStack, undo);
	if (result == -1)
		return 1;

	emptyStack(controller->redoStack);

	return erase(controller->repository, numberID);
}


int updateMaterial(Controller* controller, char* ID, char* newSupplier, char* newName, char* newQuantity)
{
	int numberID = atoi(ID);
	int numberNewQuantity = atoi(newQuantity);

	Material* copy = getMaterial(controller->repository, numberID);

	if (copy == NULL)
		return -1;

	Operation *undo = createOperation(copy, "update");

	int result = pushOperation(controller->undoStack, undo);
	if (result == -1)
		return 1;

	emptyStack(controller->redoStack);

	return update(controller->repository, numberID, newSupplier, newName, numberNewQuantity);
}


char *listMaterials(Controller* controller)
{
	return stringRepository(controller->repository);
}


char *listMaterialsFiltered(Controller* controller, char* criterion)
{
	int number = atoi(criterion);
	char *stringNumber = (char*)malloc(11);
	_itoa(number, stringNumber, 10);
	int isNumber = (strlen(stringNumber) == strlen(criterion));
	free(stringNumber);

	if (isNumber)
		return stringQuantityLessThan(controller->repository, number);

	return stringNameContains(controller->repository, criterion);
}


int undo(Controller* controller)
{
	Operation* undo = popOperation(controller->undoStack);

	if (undo == NULL)
		return -1;

	if (isAlmostFull(controller->redoStack))
		return 1;

	Operation* redo;
	Material* material = undo->material;

	if (strcmp(undo->type, "add") == 0)
	{
		store(controller->repository, material->ID, material->supplier, material->name, material->quantity);

		redo = createOperation(material, "delete");
	}
	else if (strcmp(undo->type, "delete") == 0)
	{
		erase(controller->repository, material->ID);

		redo = createOperation(material, "add");
	}
	else
	{
		Material* copy = getMaterial(controller->repository, material->ID);

		update(controller->repository, material->ID, material->supplier, material->name, material->quantity);
		free(material);

		redo = createOperation(copy, "update");
	}

	pushOperation(controller->redoStack, redo);
	destroyOperation(undo);

	return 0;
}


int redo(Controller* controller)
{
	Operation* redo = popOperation(controller->redoStack);

	if (redo == NULL)
		return -1;

	if (isAlmostFull(controller->undoStack))
		return 1;

	Operation* undo;
	Material* material = redo->material;

	if (strcmp(redo->type, "add") == 0)
	{
		store(controller->repository, material->ID, material->supplier, material->name, material->quantity);

		undo = createOperation(material, "delete");
	}
	else if (strcmp(redo->type, "delete") == 0)
	{
		erase(controller->repository, material->ID);

		undo = createOperation(material, "add");
	}
	else
	{
		Material* copy = getMaterial(controller->repository, material->ID);

		update(controller->repository, material->ID, material->supplier, material->name, material->quantity);
		free(material);

		undo = createOperation(copy, "update");
	}

	pushOperation(controller->undoStack, undo);
	destroyOperation(redo);

	return 0;
}

void destroyController(Controller* controller)
{
	emptyStack(controller->undoStack);
	destroyOperationStack(controller->undoStack);

	emptyStack(controller->undoStack);
	destroyOperationStack(controller->redoStack);

	free(controller);
}