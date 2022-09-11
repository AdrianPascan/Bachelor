#pragma once

#include "UI_Console.h"
#include <stdio.h>
#include <string.h>
#include <iostream>
#include <string>
#include <vector>
#include <sstream>

using namespace std;


UI_Console::UI_Console(Controller & controller) : controller(controller)
{
	this->mode = '\0';
}


void UI_Console::start()
{
	while (true) 
	{
		if (this->mode == '\0')
			cout << "Warning: mode is not set.\n";
		

		string command{};
		string token{};
		vector<string> parameters{};

		cout << ">>> ";
		getline(cin, command);

		stringstream check{ command };
		bool invalidCommand = false;

		while (getline(check, token, ',') && !invalidCommand)
		{
			int frontSpacesNumber = 0;

			while (token[frontSpacesNumber] == ' ')
				frontSpacesNumber++;

			token.erase(0, frontSpacesNumber);

			while (token.back() == ' ')
				token.pop_back();

			if (token.size() == 0)
				invalidCommand = true;
			else
			{
				if (parameters.size() == 0)
				{
					stringstream toCheck{ token };

					string currentToken{};

					while (getline(toCheck, currentToken, ' '))
					{
						if (currentToken.size() != 0)
						{
							if (parameters.size() == 2)
								parameters[1].append(" " + currentToken);
							else
								parameters.push_back(currentToken);
						}
					}

					if (parameters.size() == 0 || parameters.size() > 2)
						invalidCommand = true;
				}
				else
					parameters.push_back(token);
			}
		}

		if (!invalidCommand)
		{
			int length = parameters.size();

			if (length == 1 && parameters[0] == "exit")
				return;

			else if (length == 2 && parameters[0] == "mode")
			{
				if (parameters[1].size() == 1)
				{
					if (parameters[1] == "A")
					{
						this->mode = 'A';
						cout << "Administator mode set.\n";
					}
					else if (parameters[1] == "B")
					{
						this->mode = 'B';
						cout << "User mode set.\n";
					}
					else
						cout << "Invalid 'mode' command!\n";
				}
				else
					cout << "Invalid 'mode' command!\n";
			}

			else if (this->mode == 'A')
				this->administratorCommand(parameters);

			else
				this->userCommand(parameters);
		}
		else
			cout << "Invalid command!\n";
	}
}


void UI_Console::administratorCommand(std::vector<std::string> & parameters)
{
	int length = parameters.size();
	int result = 0;

	if (length == 6 && parameters[0] == "add")
	{
		
		result = this->controller.addDog(parameters[1], parameters[2], parameters[3], parameters[4], parameters[5]);
		if (result == 0)
			cout << parameters[1] << " added.\n";
		else if (result == -1)
			cout << parameters[1] << " exists already.\n";
		else if (result == 1)
			cout << "Invalid data.\n";
	}

	else if (length == 6 && parameters[0] == "update")
	{
		result = this->controller.updateDog(parameters[1], parameters[2], parameters[3], parameters[4], parameters[5]);
		if (result == 0)
			cout << parameters[1] << " updated.\n";
		else if (result == -1)
			cout << parameters[1] << " doesn't exist.\n";
		else
			cout << "Invalid data.\n";
	}

	else if (length == 2 && parameters[0] == "delete")
	{
		if (this->controller.deleteDog(parameters[1]) == 0)
			cout << parameters[1] << " deleted.\n";
		else
			cout << parameters[1] << " doesn't exist.\n";
	}

	else if (length == 1 && parameters[0] == "list")
	{
		string list = this->controller.listDogs();
		cout << list;
	}

	else
		cout << "Invalid administrator command!\n";
}

void UI_Console::userCommand(std::vector<std::string> & parameters)
{
	int length = parameters.size();
}


UI_Console::~UI_Console()
{
}