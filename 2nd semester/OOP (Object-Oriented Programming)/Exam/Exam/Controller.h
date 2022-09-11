#pragma once

#include"Repository.h"
#include"Subject.h"

class Controller: public Subject
{
	Repository * repository;
public:
	Controller();
	Controller(Repository * repository);
	~Controller();
	std::vector<Question> getQuestions() { return repository->getQuestions(); };
	std::vector<Participant> getParticipants() { return repository->getParticipants(); };
	std::vector<int> getRowsAnswered() { return repository->getRowsAnswered(); }
	void addQuestion(Question question);
	int answerQuestion(int row, std::string answer, std::string name);
};

