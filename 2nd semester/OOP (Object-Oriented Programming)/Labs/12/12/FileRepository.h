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
	void update(Dog & dog) override;
	void remove(Dog & dog) override;
	void setFilePath(char * filePath, bool read, bool write) override;
	void openInApp() override;
	void clear(bool write = true) override;

protected:
	void readFromFile();
	void writeToFile();
};

