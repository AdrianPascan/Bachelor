#pragma once

#include "Repository.h"

class FileRepository : public Repository
{
private:
	std::string filePath{};

public:
	FileRepository();
	//FileRepository(char * filePath);
	~FileRepository();
	void add(Dog & dog) override;
	void update(Dog & dog) override;
	void remove(Dog & dog) override;
	void setFilePath(char * filePath) override;

private:
	void readFromFile();
	void writeToFile();
};

