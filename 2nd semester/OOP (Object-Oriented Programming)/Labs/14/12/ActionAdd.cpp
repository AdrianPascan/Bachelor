#include "ActionAdd.h"


ActionAdd::ActionAdd(BaseRepository * repository, Dog & dog) : dog{ dog }
{
	this->repository = repository;
}

ActionAdd::~ActionAdd()
{
}

void ActionAdd::undo()
{
	repository->remove(dog);
}

void ActionAdd::redo()
{
	repository->add(dog);
}
