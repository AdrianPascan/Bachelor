#pragma once

#include "Repository.h"
#include "Controller.h"
#include "UI_console.h"
#include "test.h"
#include <crtdbg.h>

int main()
{
	int testResult = testAll();
	if (testResult != 0)
	{
		printf("Test failed! (number %d)\n", testResult);
		system("pause");
		return -1;
	}

	Repository* repository = createRepository();
	Controller* controller = createController(repository);
	Console* console = createConsole(controller);
	
	start(console);
	
	destroyRepository(repository);
	destroyController(controller);
	destroyConsole(console);

	_CrtDumpMemoryLeaks();

	return 0;
}