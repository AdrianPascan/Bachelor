#pragma once

#include "Repository.h"
#include "AdministratorController.h"
#include "UserController.h"
#include "UI_Console.h"
#include <crtdbg.h>

int main() {
	Repository * repository = new Repository;
	AdministratorController * administratorController = new AdministratorController{ repository };
	UserController * userController = new UserController{ repository };
	UI_Console * console = new UI_Console{administratorController, userController};

	console->run();

	delete repository;

	_CrtDumpMemoryLeaks();

	return 0;
}