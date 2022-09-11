#pragma once

#include "UI_Console.h"
#include <stdlib.h>
#include <string>
#include <iostream>

using namespace std;


UI_Console::UI_Console()
{
}

UI_Console::UI_Console(Controller * controller)
{
	this->controller = controller;
}


UI_Console::~UI_Console()
{
}

void UI_Console::run()
{
	string input = "";
	int number = 0;
	char command[100];

	while (true) {
		cout << ">>> ";
		getline(cin, input);
		strcpy(command, input.c_str());

		if (!(this->validatePositiveInteger(command, number)) || number > 3) {
			cout << "Invalid input." << endl;
			continue;
		}

		if (number == 0) {
			cout << "Application closed." << endl;
			return;
		}
		else if (number == 1) {
			string name, country, score;
			char nameString[100], countrryString[100], scoreString[100];

			cout << "Name: " << endl;
			getline(cin, name);

			cout << "Country: " << endl;
			getline(cin, country);

			cout << "Score: " << endl;
			getline(cin, score);
			int number = 0;
			strcpy(nameString, name.c_str());
			strcpy(countrryString, country.c_str());
			strcpy(scoreString, score.c_str());
			if (!(this->validatePositiveInteger(scoreString, number))) {
				cout << "Invalid score." << endl;
				continue;
			}

			if (this->controller->addTeam(nameString, countrryString, number) == -1) {
				cout << name << " exist already." << endl;
			}
			else {
				cout << name << " added." << endl;
			}
		}
		else if (number == 2) {
			DynamicVector<Team> teams = this->controller->getAll();
			
			for (int index = 0; index < teams.getSize(); index++) {
				 Team & team = teams[index];
				cout << team.getName() << ": " << team.getCoutry() << ", score " << team.getScore() << endl;
			}
			
		}

	}
}


bool UI_Console::validatePositiveInteger(char * string, int & number)
{
	if (strlen(string) == 0) {
		return false;
	}
	number = atoi(string);
	if (number < 0) {
		return false;
	}
	char * stringNumber = new char[strlen(string) + 1];
	_itoa(number, stringNumber, 10);
	bool valid = (strlen(string) == strlen(stringNumber));
	delete[] stringNumber;
	return valid;
}

void UI_Console::printMenu()
{
	cout << "MENU:" << endl;
	cout << "1. add team" << endl;
	cout << "2. list teams in descending order by score" << endl;
	cout << "3. play match" << endl;
	cout << "0. exit" << endl;
}
