#include "Question.h"



Question::Question()
{
}

Question::Question(int id, std::string text, std::string correctAnswer, int score): id{id}, text{text}, correctAnswer{correctAnswer}, score{score}
{
}


Question::~Question()
{
}

bool Question::operator<(Question & question)
{
	return score > question.getScore();
}
