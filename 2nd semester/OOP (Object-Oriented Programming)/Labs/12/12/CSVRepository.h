#pragma once

#include "FileRepository.h"

class CSVRepository : public FileRepository
{
public:
	CSVRepository();
	CSVRepository(char *filePath);
	~CSVRepository();
};

