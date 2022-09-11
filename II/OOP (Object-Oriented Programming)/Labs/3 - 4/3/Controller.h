#pragma once

#include "Repository.h"
#include "OperationStack.h"

typedef struct
{
	Repository* repository;
	OperationStack *undoStack, *redoStack;
}Controller;


Controller* createController(Repository*);
int addMaterial(Controller*, char*, char*, char*, char*);
int deleteMaterial(Controller*, char*);
int updateMaterial(Controller*, char*, char*, char*, char*);
char *listMaterials(Controller*);
char *listMaterialsFiltered(Controller*, char*);
void destroyController(Controller*);
int undo(Controller*);
int redo(Controller*);
