#pragma once

#include"Participant.h"
#include"Question.h"
#include<vector>
#include<fstream>
#include<sstream>
#include<algorithm>

class Repository
{
	std::vector<Question> questions;
	std::vector<Participant> participants;
	std::vector<int> rowsAnswered;
	std::string questionsPath;

public:
	Repository();
	~Repository();
	void readQuestions();
	void readParticipants();
	void writeQuestions();

	std::vector<Question> getQuestions() { return questions; }
	std::vector<Participant> getParticipants() { return participants; }
	std::vector<int> getRowsAnswered() { return rowsAnswered; }

	void addParticipant(Participant participant);

	/*
	Adds a question to the repository.
	Input: question - Question object
	Output: -
	Throws: std::exception, if there is a question with the same id
	*/
	void addQuestion(Question question);

	/*
	Checks if a question was answered correctly; if so, the it increases the score of the corresponding participant.
	Input: row - int, index in questions
			answer - string
			name - string, the name of the participant who anwered the queston
	*/
	int answerQuestion(int row, std::string answer, std::string name);
	
private:
	std::vector<std::string> readFromFile(std::string path);
	std::vector<std::string> tokenize(std::string recording);
};

