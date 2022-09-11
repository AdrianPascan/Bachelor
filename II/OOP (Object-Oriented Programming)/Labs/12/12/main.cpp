#include "GUI.h"
#include <QtWidgets/QApplication>

#include "FileRepository.h"
#include "AdministratorController.h"
#include "UserController.h"

#include <crtdbg.h>

using namespace std;

int main(int argc, char *argv[])
{
	FileRepository repository{};
	AdministratorController administratorController{ &repository };
	UserController userController{ &repository };

	QApplication application(argc, argv);

	GUI gui{ &administratorController, &userController };
	gui.show();

	return application.exec();
}
