#include "GUI.h"
#include <QtWidgets/QApplication>
#include"Repository.h"
#include"Controller.h"
#include"Tests.h"

int main(int argc, char *argv[])
{
	Tests::test();

	QApplication application(argc, argv);

	Repository repository{};
	repository.readQuestions();
	repository.readParticipants();
	
	Controller controller{ &repository };

	for (auto & participant : repository.getParticipants()) {
		GUI * participantGUI = new GUI{&controller, participant};
		//controller.registerObserver(participantGUI);
		participantGUI->show();
	}

	GUI presenter{ &controller, Participant{}, true };
	presenter.show();
	controller.registerObserver(&presenter);
	
	application.exec();

	repository.writeQuestions();

	return 0;
}
