#include "Controller.h"

using namespace std;

Controller::Controller(Repository * repository) : repository{repository}
{
}


Controller::~Controller()
{
}

std::vector<Issue> Controller::getIssues()
{
	return repository->getIssues();
}

void Controller::addIssue(Issue issue)
{
	repository->add(issue);
}

void Controller::removeIssue(int position)
{
	repository->remove(position);
}

void Controller::resolveIssue(int position, std::string solverName)
{
	repository->resolve(position, solverName);
}
