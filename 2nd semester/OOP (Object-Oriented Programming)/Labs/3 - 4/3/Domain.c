#pragma once

#include "Domain.h"
#include <stdlib.h>
#include <string.h>


Material* createMaterial(int ID, char* supplier, char* name, int quantity)
{
	Material* material = (Material*)malloc(sizeof(Material));

	material->ID = ID;

	material->supplier = (char*)malloc(strlen(supplier) + 1);
	strcpy(material->supplier, supplier);

	material->name = (char*)malloc(strlen(name) + 1);
	strcpy(material->name, name);

	material->quantity = quantity;

	return material;
}


void destroyMaterial(Material* material)
{
	free(material->supplier);
	free(material->name);
	free(material);
	material = NULL;
}


Material* copyMaterial(Material* material)
{
	Material* copy = createMaterial(material->ID, material->supplier, material->name, material->quantity);

	return copy;
}