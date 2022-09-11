#pragma once

#include "Repository.h"
#include "Controller.h"
#include "UI_Console.h"
#include <crtdbg.h>
#include <string>
#include <vector>

int main()
{
	Repository repository{};
	Controller controller{ repository };
	UI_Console console = { controller };

	console.start();

	_CrtDumpMemoryLeaks();
	
	return 0;
}