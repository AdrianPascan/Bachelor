#pragma once

#include "BaseRepository.h"
#include "Dog.h"


class Repository: public BaseRepository
{
public:
	Repository();
	~Repository();

	void add(Dog & dog) override;
	void update(Dog & dog) override;
	void remove(Dog & dog) override;
	void setFilePath(char * filePath, bool read, bool write) override;
	void openInApp() override;
	void clear(bool write = true) override;
};

