#pragma once

#include "Repository.h"

class FileRepository : public Repository
{
protected:
	std::string filePath{};

public:
	FileRepository();
	FileRepository(char * filePath);
	~FileRepository();
	void add(Dog & dog) override;
	Dog update(Dog & dog) override;
	Dog remove(Dog & dog) override;
	void setFilePath(char * filePath, bool read, bool write) override;
	void openInApp() override;
	void clear(bool write = true) override;

protected:
	void readFromFile();
	void writeToFile();
};

