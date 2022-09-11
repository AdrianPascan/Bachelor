#pragma once

#include "UI_console.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>


Console* createConsole(Controller* controller)
{
	Console* console = (Console*)malloc(sizeof(Console));

	console->controller = controller;

	return console;
}


void start(Console* console)
{
	char command[1000] = "", parameters[20][20], delimiter[] = " ", *list;
	int result = 0, length = 0, doNotStop = 1;

	while (doNotStop == 1)
	{
		printf(">>> ");
		gets(command);

		if (strcmp(command, "exit") == 0)
			doNotStop = 0;
		else
		{
			result = 3;

			char* word;
			length = 0;

			word = strtok(command, delimiter);
			while (word != NULL)
			{
				if (strcmp(word, delimiter) != 0)
				{
					length++;
					strcpy(parameters[length - 1], word);
				}
				word = strtok(NULL, " ");
			}

			if (length == 5 && strcmp(parameters[0], "add") == 0)
			{
				result = addMaterial(console->controller, parameters[1], parameters[2], parameters[3], parameters[4]);
				if (result == -1)
					printf("Material with same ID exists already.\n");
			}

			else if (length == 2 && strcmp(parameters[0], "delete") == 0)
			{
				result = deleteMaterial(console->controller, parameters[1]);
				if (result == -1)
					printf("Material with given ID doesn't exist.\n");
			}

			else if (length == 5 && strcmp(parameters[0], "update") == 0)
			{
				result = updateMaterial(console->controller, parameters[1], parameters[2], parameters[3], parameters[4]);
				if (result == -1)
					printf("Material with given ID doesn't exist.\n");
			}

			else if (length <= 2 && strcmp(parameters[0], "list") == 0)
			{
				if (length == 1)
					list = listMaterials(console->controller);
				else
					list = listMaterialsFiltered(console->controller, parameters[1]);

				printf("%s", list);
				//free(list);
			}

			else if (length == 1 && strcmp(parameters[0], "undo") == 0)
			{
				result = undo(console->controller);

				if (result == -1)
					printf("No more undos available.\n");
				else if (result == 1)
					printf("Number of available redos is 100. Operation cannot be performed.\n");
			}

			else if (length == 1 && strcmp(parameters[0], "redo") == 0)
			{
				result = redo(console->controller);

				if (result == -1)
					printf("No more redos available.\n");
				else if (result == 1)
					printf("Number of available undos is 100. Operation cannot be performed.\n");
			}

			else if (result == 3)
				printf("Invalid command!\n");
		}
	}
}


void destroyConsole(Console* console)
{
	free(console);
}