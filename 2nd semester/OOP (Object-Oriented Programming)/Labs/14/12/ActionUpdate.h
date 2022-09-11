#pragma once
#include "Action.h"
class ActionUpdate :
	public Action
{
private:
	Dog newDog;
	Dog oldDog;

public:
	ActionUpdate(BaseRepository * repository, Dog & newDog, Dog & oldDog);
	~ActionUpdate();

	void undo() override;
	void redo() override;
};

