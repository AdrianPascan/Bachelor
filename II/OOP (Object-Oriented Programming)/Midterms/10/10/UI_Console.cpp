#pragma once

#include "UI_Console.h"
#include <iostream>

using namespace std;

UI_Console::UI_Console()
{
}

UI_Console::UI_Console(Controller * _controller)
{
	controller = _controller;
}


UI_Console::~UI_Console()
{
}

void UI_Console::run()
{
	std::string command;

	while (true) {
		cout << ">>> ";
		getline(cin, command);

		if (command == "exit") {
			return;
		}
		else if (command == "add house") {
			addHouse();
		}
		else if (command == "add block") {
			addBlock();
		}
		else if (command == "list") {
			for (Building * building : controller->getAll()) {
				cout << building->toString();
			}
		}
		else if (command == "restored") {
			mustBeRestored();
		}
		else {
			cout << "Invalid command!" << endl;
		}
	}
}

void UI_Console::addHouse()
{
	std::string address;
	cout << "Address: ";
	getline(cin, address);

	std::string constructionYear;
	cout << "Construction year: ";
	getline(cin, constructionYear);
	int constructionYearNumber = stoi(constructionYear);

	std::string type;
	cout << "Type: ";
	getline(cin, type);

	std::string historical;
	cout << "Is historical: ";
	getline(cin, historical);
	bool isHistorical = (historical == "true");

	controller->addHouse(address, constructionYearNumber, type, isHistorical);
}

void UI_Console::addBlock()
{
	std::string address;
	cout << "Address: ";
	getline(cin, address);

	std::string constructionYear;
	cout << "Construction year: ";
	getline(cin, constructionYear);
	int constructionYearNumber = stoi(constructionYear);

	std::string totalApartments;
	cout << "Total apartments: ";
	getline(cin, totalApartments);
	int totalApartmentNumber = stoi(totalApartments);

	std::string occupiedApartments;
	cout << "Occupied apartments: ";
	getline(cin, occupiedApartments);
	int occupiedApartmentsNumber = stoi(occupiedApartments);

	controller->addBlock(address, constructionYearNumber, totalApartmentNumber, occupiedApartmentsNumber);
}

void UI_Console::mustBeRestored()
{
	std::string constructionYear;
	cout << "Construction year: ";
	getline(cin, constructionYear);
	int constructionYearNumber = stoi(constructionYear);

	std::string address;
	cout << "Address: ";
	getline(cin, address);

	for (Building * building : controller->mustBeRestored(constructionYearNumber, address)) {
		cout << building->toString();
	}
}
