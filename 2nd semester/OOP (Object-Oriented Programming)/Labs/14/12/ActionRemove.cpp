#include "ActionRemove.h"

ActionRemove::ActionRemove(BaseRepository * repository, Dog & dog) : dog{ dog }
{
	this->repository = repository;
}

ActionRemove::~ActionRemove()
{
}

void ActionRemove::undo()
{
	repository->add(dog);
}

void ActionRemove::redo()
{
	repository->remove(dog);
}
