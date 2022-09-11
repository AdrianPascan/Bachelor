#pragma once

#include "Repository.h"

Repository::Repository()
{
}


Repository::~Repository()
{
}

int Repository::add( Team & team)
{
	return this->teams.add(team);
}

 DynamicVector<Team>& Repository::getAll()
{
	DynamicVector<Team> sorted{this->teams};

	bool changed = true;
	while (changed) {
		changed = false;
		for (int index = 0; index < sorted.getSize() - 1; index++) {
			if (!(sorted[index] >= sorted[index+1])) {
				changed = true;
				Team auxiliary{ sorted[index] };
				sorted[index] = sorted[index + 1];
				sorted[index + 1] = auxiliary;
			}
		}
	}

	return teams;
}
