#pragma once

#include "UI_Console.h"
#include "MyException.h"
#include <string.h>
#include <string>
#include <iostream>

using namespace std;


UI_Console::UI_Console()
{
}

UI_Console::UI_Console(AdministratorController * administratorController, UserController * userController)
{
	this->mode = '\0';
	this->administratorController = administratorController;
	this->userController = userController;
}


UI_Console::~UI_Console()
{
}

void UI_Console::run()
{
	char * command = NULL;
	char ** parameters = NULL;
	int parametersCount = 0;

	while (true) {
		if (this->mode == 0) {
			cout << "Warning: mode not set." << endl;
		}

		command = this->getCommand();
		parameters = this->getParameters(command, parametersCount);

		if (parameters == NULL) {
			cout << "Invalid command." << endl;
			continue;
		}

		if (parametersCount == 0) {
			cout << "Invalid command." << endl;
		}
		else if (strcmp(parameters[0], "exit") == 0) {
			this->destroyParameters(parameters, parametersCount);
			cout << "Application closed." << endl;
			break;
		}
		else {
			try {
				if (strcmp(parameters[0], "mode") == 0) {
					this->setMode(parameters, parametersCount);
				}
				else if (strcmp(parameters[0], "fileLocation") == 0) {
					this->setFilePath(parameters, parametersCount);
					cout << "File path set." << endl;
				}
				else if (strcmp(parameters[0], "mylistLocation") == 0) {
					this->setMylistPath(parameters, parametersCount);
					cout << "Mylist path set." << endl;
				}
				else if (this->mode == 'A') {
					this->administratorCommand(parameters, parametersCount);
				}
				else if (this->mode == 'B') {
					this->userCommand(parameters, parametersCount);
				}
				else {
					cout << "Invalid command." << endl;
				}
			}
			catch (exception &exception) {
				cout << exception.what();
			}
		}

		this->destroyParameters(parameters, parametersCount);
	}
}

char * UI_Console::getCommand()
{
	cout << ">>> ";
	string input;
	getline(cin, input);
	char *command = new char[input.size() + 1];
	strcpy(command, input.c_str());
	return command;
}

char ** UI_Console::getParameters(char * command, int & parametersCount)
{
	parametersCount = 0;
	char ** parameters = new char*[6];

	char * current = strtok(command, " ");
	while (current != NULL) {
		parametersCount++;

		if (parametersCount > 6) {
			this->destroyParameters(parameters, parametersCount);
			return NULL;
		}

		int last = strlen(current);
		while (current[last - 1] == ' ') {
			last--;
		}
		current[last] = '\0';

		while (current[0] == ' ') {
			current++;
		}

		if (strlen(current) != 0) {
			parameters[parametersCount - 1] = new char[strlen(current) + 1];
			strcpy(parameters[parametersCount - 1], current);
		}	

		current = strtok(NULL, ",");
	}
	return parameters;
}

void UI_Console::destroyParameters(char ** parameters, int parametersCount)
{
	for (int index = 0; index < parametersCount; index++) {
		delete[] parameters[index];
	}
	delete[] parameters;
}

void UI_Console::setMode(char ** parameters, int parametersCount)
{
	if (parametersCount != 2 && strlen(parameters[1]) != 1 && (toupper(parameters[1][0]) != 'A' && toupper(parameters[1][0]) != 'B')) {
		cout << "Invalid 'mode' command." << endl;
		return;
	}

	this->mode = toupper(parameters[1][0]);
	if (this->mode == 'A') {
		cout << "Administrator mode set." << endl;
	}
	else {
		this->userController->setMatching();
		cout << "User mode set." << endl;
	}
}

void UI_Console::setFilePath(char ** parameters, int parametersCount)
{
	if (parametersCount != 2) {
		throw MyException{ "Invalid 'fileLocation' command." };
	}
	this->administratorController->setFilePath(parameters[1]);
}

void UI_Console::setMylistPath(char ** parameters, int parametersCount)
{
	if (parametersCount != 2) {
		throw MyException{ "Invalid 'mylistLocation' command." };
	}
	this->userController->setMylistPath(parameters[1]);
}

void UI_Console::administratorCommand(char ** parameters, int parametersCount)
{
	if (strcmp(parameters[0], "add") == 0 && parametersCount == 6) {
		this->administratorController->addDog(parameters[1], parameters[2], parameters[3], parameters[4], parameters[5]);
		cout << parameters[1] << " added." << endl;
	}
	else if (strcmp(parameters[0], "update") == 0 && parametersCount == 6) {
		this->administratorController->updateDog(parameters[1], parameters[2], parameters[3], parameters[4], parameters[5]);
		cout << parameters[1] << " updated." << endl;
	}
	else if (strcmp(parameters[0], "delete") == 0 && parametersCount == 2) {
		this->administratorController->deleteDog(parameters[1]);
		cout << parameters[1] << " deleted." << endl;
	}
	else if (strcmp(parameters[0], "list") == 0 && parametersCount == 1) {
		vector<Dog> dogs = this->administratorController->getAllDogs();
		this->printDogList(dogs);
	}
	else {
		cout << "Invalid administrator command." << endl;
	}
}

void UI_Console::userCommand(char ** parameters, int parametersCount)
{
	if (strcmp(parameters[0], "next") == 0 && parametersCount == 1) {
		Dog & dog = this->userController->nextDog();
		this->printDog(dog);
	}
	else if (strcmp(parameters[0], "save") == 0 && parametersCount == 2) {
		this->userController->saveDog(parameters[1]);
		cout << parameters[1] << " saved." << endl;
	}
	else if (strcmp(parameters[0], "list") == 0 && parametersCount <= 3) {
		if (parametersCount == 3) {
			this->userController->setMatching(parameters[1], parameters[2]);
		}
		else {
			this->userController->setMatching();
		}
		this->printDogList(this->userController->getMatching());
	}
	else if (strcmp(parameters[0], "mylist") == 0 && parametersCount == 1) {
		this->userController->getSaved();
	}
	else {
		cout << "Invalid user command." << endl;
	}
}

void UI_Console::printDog(Dog & dog)
{
	cout << dog;
}

void UI_Console::printDogList(std::vector<Dog>& dogs)
{
	if (dogs.empty()) {
		throw MyException{ "No dogs to show." };
	}

	for (Dog & dog : dogs) {
		this->printDog(dog);
	}
}
