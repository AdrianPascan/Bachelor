#pragma once
#include "Action.h"
class ActionAdd :
	public Action
{
private:
	Dog dog;

public:
	ActionAdd(BaseRepository * repository, Dog & dog);
	~ActionAdd();

	void undo() override;
	void redo() override;
};

