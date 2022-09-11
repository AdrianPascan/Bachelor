#pragma once

#include "Repository.h"
#include "FileRepository.h"
#include "Validator.h"
#include "AdministratorController.h"
#include "UserController.h"
#include "UI_Console.h"
#include "Tests.h"
#include <crtdbg.h>

int main() {
	Tests tests;
	tests.testAll();

	/*FileRepository * repository = new FileRepository{};
	Validator * validator = new Validator{};
	AdministratorController * administratorController = new AdministratorController{ repository };
	UserController * userController = new UserController{ repository };
	UI_Console * console = new UI_Console{administratorController, userController};

	console->run();

	delete repository;*/

	_CrtDumpMemoryLeaks();

	return 0;
}