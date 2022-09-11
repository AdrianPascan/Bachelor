#include "CSVRepository.h"



CSVRepository::CSVRepository() : FileRepository{}
{
}

CSVRepository::CSVRepository(char * filePath): FileRepository{filePath}
{
}


CSVRepository::~CSVRepository()
{
}
