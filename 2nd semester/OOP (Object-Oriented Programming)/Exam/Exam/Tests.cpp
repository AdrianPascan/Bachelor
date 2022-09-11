#include "Tests.h"



Tests::Tests()
{
}


Tests::~Tests()
{
}

void Tests::test()
{
	Repository repository{};

	Question question{ 1,"Capital of Russia?", "Moscow", 4 };

	repository.addQuestion(question);

	try {
		repository.addQuestion(question);
		assert(false);
	}
	catch (std::exception &) {}

	Participant participant{ "Dragos" };
	repository.addParticipant(participant);

	repository.answerQuestion(0, "Moscow", "Dragos");

	assert(repository.getParticipants()[0].getScore() == 4);
}
