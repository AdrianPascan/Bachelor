#include "Repository.h"

using namespace std;

Repository::Repository()
{
}


Repository::~Repository()
{
}

void Repository::readQuestions()
{
	questionsPath = "D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Exam\\Exam\\questions.txt";
	vector<string> recordings = readFromFile(questionsPath);

	for (auto & recording : recordings) {
		vector<string> tokens = tokenize(recording);

		if (tokens.size() == 4) {
			Question question{ stoi(tokens[0]), tokens[1], tokens[2], stoi(tokens[3]) };
			questions.push_back(question);
		}
	}

	sort(questions.begin(), questions.end());
}

void Repository::readParticipants()
{
	vector<string> recordings = readFromFile("D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Exam\\Exam\\participants.txt");

	for (auto & recording : recordings) {
		vector<string> tokens = tokenize(recording);

		if (tokens.size() == 2) {
			Participant participant{ tokens[0], stoi(tokens[1]) };
			participants.push_back(participant);
		}
	}

	sort(participants.begin(), participants.end());
}

void Repository::addQuestion(Question question)
{
	for (auto & current : questions) {
		if (current.getId() == question.getId()) {
			throw std::exception("There is a question with the same id already.");
		}
	}

	questions.push_back(question);
}

int Repository::answerQuestion(int row, std::string answer, std::string name)
{
	if (!(questions[row].getCorrectAnswer() == answer)) {
		throw std::exception{ "Incorrect answer." };
	}

	/*for (auto & participant : participants) {
		if (participant.getName() == name) {
			participant.addScore(questions[row].getScore());
		}
	}*/

	for (auto iterator = participants.begin(); iterator != participants.end(); iterator++) {
		if (iterator->getName() == name) {
			iterator->addScore(questions[row].getScore());
		}
	}

	rowsAnswered.push_back(row);

	return questions[row].getScore();
}

std::vector<std::string> Repository::readFromFile(std::string path)
{
	vector<string> recordings;

	ifstream file{ path };

	string input{};

	while (!file.eof()) {
		getline(file, input);
		if (!input.empty()) {
			recordings.push_back(input);
		}
	}

	file.close();

	return recordings;
}

std::vector<std::string> Repository::tokenize(std::string recording)
{
	std::vector<string> tokens{};
	std::string token{};

	istringstream stream{ recording };

	while (getline(stream, token, ',')) {
		tokens.push_back(token);
	}

	return tokens;
}

void Repository::writeQuestions()
{
	ofstream file{ questionsPath };

	for (auto & question : questions) {
		file << question.getId() << "," << question.getText() << "," << question.getCorrectAnswer() << "," << question.getScore() << "\n";
	}

	file.close();
}

void Repository::addParticipant(Participant participant)
{
	participants.push_back(participant);
}
