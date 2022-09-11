#include "QtGuiApplication.h"
#include <QtWidgets/QApplication>

int main(int argc, char *argv[])
{
	QApplication application(argc, argv);

	Repository repository{};
	repository.readFromFile();

	Controller controller{ &repository };

	controller.testDescription();

	QtGuiApplication window{ &controller };
	window.show();

	return application.exec();
}
