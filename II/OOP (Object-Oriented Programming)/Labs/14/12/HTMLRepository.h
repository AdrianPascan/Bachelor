#pragma once

#include "Repository.h"

class HTMLRepository: public Repository
{
private:
	std::string filePath{};

public:
	HTMLRepository();
	HTMLRepository(char * filePath);
	~HTMLRepository();

	void add(Dog & dog) override;
	Dog update(Dog & dog) override;
	Dog remove(Dog & dog) override;
	void setFilePath(char * filePath, bool read, bool write) override;
	void openInApp() override;
	void clear(bool write = true) override;

private:
	void writeToFile();
};

