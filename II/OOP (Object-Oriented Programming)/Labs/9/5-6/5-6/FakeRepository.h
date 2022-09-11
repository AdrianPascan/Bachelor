#pragma once

#include "Repository.h"

class FakeRepository: public Repository
{
public:
	FakeRepository();
	void setFilePath(char * filePath, bool read, bool write) override {};
	void openInApp() override {};
	void clear() override {};
	~FakeRepository();
};

