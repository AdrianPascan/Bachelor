#pragma once

#include "Repository.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>


Repository* createRepository()
{
	Repository* repository = (Repository*)malloc(sizeof(Repository));
	repository->capacity = 100;
	repository->length = 0;
	repository->materials = (Material**)malloc(sizeof(Material*) * 100);

	return repository;
}

int find(Repository* repository, int ID)
{
	for (int index = 0; index < repository->length; index++)
		if (repository->materials[index]->ID == ID)
			return index;

	return -1;
}


Material* getMaterial(Repository* repository, int ID)
{
	int position = find(repository, ID);

	if (position == -1)
		return NULL;

	return copyMaterial(repository->materials[position]);
}


void resizeRepository(Repository* repository)
{
	repository->capacity = repository->capacity*2;

	Material** auxiliary = (Material**)malloc(sizeof(Material*) * repository->capacity);
	
	for (int index = 0; index < repository->length; index++)
		auxiliary[index] = repository->materials[index];

	free(repository->materials);

	repository->materials = auxiliary;
}


int store(Repository* repository, int ID, char *supplier, char *name, int quantity)
{
	if (repository->length != 0 && find(repository, ID) != -1)
		return -1;

	Material* material = createMaterial(ID, supplier, name, quantity);
	
	if (repository->length == repository->capacity)
		resizeRepository(repository);

	repository->length++;
	repository->materials[repository->length - 1] = material;

	return 0;
}


int erase(Repository* repository, int ID)
{
	int position = find(repository, ID);
	
	if (position == -1)
		return -1;

	destroyMaterial(repository->materials[position]);

	repository->materials[position] = repository->materials[repository->length - 1];

	repository->length--;

	return 0;
}


int update(Repository* repository, int ID, char* newSupplier, char* newName, int newQuantity)
{
	int result = erase(repository, ID);

	if (result == -1)
		return -1;

	store(repository, ID, newSupplier, newName, newQuantity);

	return 0;
}


void resizeString(char* string, int* capacity)
{
	*capacity *= 2;

	char *newString = (char*)malloc(*capacity);

	strcpy(newString, string);

	free(string);

	string = newString;
}


char* stringMaterials(Material** materials, int length)
{
	char *string = (char*)malloc(100);
	*string = '\0';
	int stringLength = 1, capacity = 100;

	char *IDString = (char*)malloc(11);
	char *quantityString = (char*)malloc(11);
	int currentLength = 0;
	Material *current;

	for (int index = 0; index < length; index++)
	{
		current = materials[index];
		currentLength = 0;

		_itoa(current->ID, IDString, 10);
		currentLength += strlen(IDString);

		currentLength += strlen(current->supplier);
		currentLength += strlen(current->name);

		_itoa(current->quantity, quantityString, 10);
		currentLength += strlen(quantityString);

		currentLength += 9;

		if (stringLength + currentLength > capacity)
			resizeString(string, &capacity);

		strcat(string, IDString);
		strcat(string, ": ");
		strcat(string, current->supplier);
		strcat(string, " - ");
		strcat(string, current->name);
		strcat(string, " - ");
		strcat(string, quantityString);
		strcat(string, "\n");
	}

	free(IDString);
	free(quantityString);

	return string;
}


char* stringRepository(Repository* repository)
{
	return stringMaterials(repository->materials, repository->length);
}


char* stringNameContains(Repository* repository, char* contains)
{
	Material** matching = (Material**)malloc(sizeof(Material*) * repository->length);
	int matchingLength = 0;
	Material* current;

	for (int index = 0; index < repository->length; index++)
	{
		current = repository->materials[index];
		if (strstr(current->name, contains) != NULL)
		{
			matching[matchingLength] = current;
			matchingLength++;
		}
	}

	char *string = stringMaterials(matching, matchingLength);

	free(matching);

	return string;
}


char* stringQuantityLessThan(Repository* repository, int maximumQuantity)
{
	Material** matching = (Material**)malloc(sizeof(Material*) * repository->length);
	int matchingLength = 0;
	Material* current;

	for (int index = 0; index < repository->length; index++)
	{
		current = repository->materials[index];
		if (current->quantity < maximumQuantity)
		{
			if (matchingLength == 0 || (strcmp(current->supplier, matching[matchingLength-1]->supplier) > 0))
				matching[matchingLength++] = current;
			else
			{
				int position = matchingLength - 1;

				while (position > 0 && strcmp(current->supplier, matching[position - 1]->supplier) < 0)
					position--;

				for (int matchingIndex = matchingLength - 1; matchingIndex >= position; matchingIndex--)
					matching[matchingIndex + 1] = matching[matchingIndex];

				matching[position] = current;

				matchingLength++;
			}
		}
	}

	char *string = stringMaterials(matching, matchingLength);

	free(matching);

	return string;
}


void destroyRepository(Repository* repository)
{
	for (int index = 0; index < repository->length; index++)
		destroyMaterial(repository->materials[index]);

	free(repository->materials);
	free(repository);
}