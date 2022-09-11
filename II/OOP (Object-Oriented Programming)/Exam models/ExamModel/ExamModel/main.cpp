#include "GUI.h"
#include <QtWidgets/QApplication>
#include "Repository.h"
#include "Controller.h"
#include "Tests.h"

int main(int argc, char *argv[])
{
	Tests::testAdd();
	Tests::testResolve();

	QApplication application(argc, argv);
	
	Repository repository{};
	repository.readUsers();
	repository.readIssues();

	Controller controller{ &repository };

	for (User & user : repository.getUsers()) {
		GUI* gui = new GUI{ user, &controller };
		controller.registerObserver(gui);
		gui->show();
	}

	application.exec();

	repository.writeIssues();

	return 0;
}
