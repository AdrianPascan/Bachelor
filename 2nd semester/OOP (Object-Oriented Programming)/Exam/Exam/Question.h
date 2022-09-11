#pragma once

#include<string>

class Question
{
	int id;
	std::string text;
	std::string correctAnswer;
	int score;

public:
	Question();
	Question(int id, std::string text, std::string correctAnswer, int score);
	~Question();
	int getId() { return id; }
	std::string getText() { return text; }
	std::string getCorrectAnswer() { return correctAnswer; }
	int getScore() { return score; }
	bool operator<(Question& question);
};

