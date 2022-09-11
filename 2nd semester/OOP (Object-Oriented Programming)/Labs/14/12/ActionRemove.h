#pragma once

#include "Action.h"

class ActionRemove :
	public Action
{
private:
	Dog dog;

public:
	ActionRemove(BaseRepository * repository, Dog & dog);
	~ActionRemove();

	void undo() override;
	void redo() override;
};

