#pragma once


typedef struct
{
	int quantity, ID;
	char *supplier, *name;
}Material;


Material* createMaterial(int, char*, char*, int);
void destroyMaterial(Material*);
Material* copyMaterial(Material*);