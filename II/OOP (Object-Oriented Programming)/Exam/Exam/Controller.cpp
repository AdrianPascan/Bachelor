#include "Controller.h"



Controller::Controller()
{
}

Controller::Controller(Repository * repository): repository{repository}
{
}


Controller::~Controller()
{
}

void Controller::addQuestion(Question question)
{
	repository->addQuestion(question);
}

int Controller::answerQuestion(int row, std::string answer, std::string name)
{
	return repository->answerQuestion(row, answer, name);
}
