#pragma once

#include "Repository.h"
#include "FileRepository.h"
#include "Validator.h"
#include "AdministratorController.h"
#include "UserController.h"
#include "UI_Console.h"
#include "Test.h"
#include <crtdbg.h>

int main() {
	test();

	FileRepository * repository = new FileRepository{};
	AdministratorController * administratorController = new AdministratorController{ repository };
	UserController * userController = new UserController{ repository };
	UI_Console * console = new UI_Console{administratorController, userController};

	console->run();

	delete repository;

	_CrtDumpMemoryLeaks();

	return 0;
}