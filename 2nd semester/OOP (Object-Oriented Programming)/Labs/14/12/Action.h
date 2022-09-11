#pragma once

#include "Repository.h"

class Action
{
protected:
	BaseRepository * repository;

public:
	Action();
	~Action();

	virtual void undo() = 0;
	virtual void redo() = 0;
};

