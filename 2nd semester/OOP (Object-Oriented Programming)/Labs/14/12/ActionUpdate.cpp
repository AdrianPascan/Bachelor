#include "ActionUpdate.h"


ActionUpdate::ActionUpdate(BaseRepository * repository, Dog & newDog, Dog & oldDog) : newDog{ newDog }, oldDog{ oldDog }
{
	this->repository = repository;
}

ActionUpdate::~ActionUpdate()
{
}

void ActionUpdate::undo()
{
	repository->update(oldDog);
}

void ActionUpdate::redo()
{
	repository->update(newDog);
}
