#pragma once

#include "Repository.h"
#include "Controller.h"
#include "UI_Console.h"

int main() {
	Repository * repository = new Repository{};
	Controller * controller = new Controller{ repository };
	UI_Console * console = new UI_Console{ controller };

	console->run();

	return 0;
}