#include"Repository.h"
#include"Controller.h"
#include"UI_Console.h"
#include<crtdbg.h>

int main() {
	Repository * repository = new Repository{};
	Controller * controller = new Controller{ repository };
	UI_Console * console = new UI_Console{ controller };

	repository->initialise();
	console->run();

	_CrtDumpMemoryLeaks();

	return 0;
}