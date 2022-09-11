#pragma once

#include "Repository.h"
#include "Controller.h"
#include "UI_console.h"
#include <crtdbg.h>
#include <string.h>

int testAll()
{
	Repository* repository = createRepository();
	Controller* controller = createController(repository);
	int result = 0;

	// LAB. 3

	result = addMaterial(controller, "100", "metro", "flour", "450");

	if (result == -1)
		return 1;

	if (repository->length != 1 || repository->materials[0]->ID != 100 || strcmp(repository->materials[0]->supplier, "metro") != 0 || \
		strcmp(repository->materials[0]->name, "flour") != 0 || repository->materials[0]->quantity != 450)
		return 1;


	result = addMaterial(controller, "100", "", "", "");

	if (result != -1)
		return 1;


	result = updateMaterial(controller, "100", "kaufland", "sugar", "300");

	if (result == -1)
		return 2;

	if (repository->length != 1 || repository->materials[0]->ID != 100 || strcmp(repository->materials[0]->supplier, "kaufland") != 0 || \
		strcmp(repository->materials[0]->name, "sugar") != 0 || repository->materials[0]->quantity != 300)
		return 2;


	result = updateMaterial(controller, "10", "", "", "");

	if (result != -1)
		return 2;


	result = deleteMaterial(controller, "100");

	if (result == -1 || repository->length != 0)
		return 3;


	result = deleteMaterial(controller, "10");

	if (result != -1)
		return 3;


	// LAB. 4

	emptyStack(controller->undoStack);

	addMaterial(controller, "100", "metro", "flour", "450");
	updateMaterial(controller, "100", "kaufland", "sugar", "300");

	undo(controller);

	if (repository->length != 1 || repository->materials[0]->ID != 100 || strcmp(repository->materials[0]->supplier, "metro") != 0 || \
		strcmp(repository->materials[0]->name, "flour") != 0 || repository->materials[0]->quantity != 450)
		return 4;

	undo(controller);

	if (repository->length != 0)
		return 4;

	result = undo(controller);

	if (result != -1)
		return 42;

	redo(controller);

	if (repository->length != 1 || repository->materials[0]->ID != 100 || strcmp(repository->materials[0]->supplier, "metro") != 0 || \
		strcmp(repository->materials[0]->name, "flour") != 0 || repository->materials[0]->quantity != 450)
		return 5;

	redo(controller);

	if (repository->length != 1 || repository->materials[0]->ID != 100 || strcmp(repository->materials[0]->supplier, "kaufland") != 0 || \
		strcmp(repository->materials[0]->name, "sugar") != 0 || repository->materials[0]->quantity != 300)
		return 5;

	redo(controller);

	if (result != -1)
		return 52;

	deleteMaterial(controller, "100");

	redo(controller);

	if (result != -1)
		return 53;

	destroyRepository(repository);
	destroyController(controller);

	_CrtDumpMemoryLeaks();

	return 0;
}

