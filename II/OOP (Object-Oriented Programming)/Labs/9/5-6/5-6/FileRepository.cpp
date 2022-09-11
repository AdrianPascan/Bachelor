#pragma once

#include "FileRepository.h"
#include "MyException.h"
#include <string.h>
#include <iostream>
#include <fstream>

using namespace std;

FileRepository::FileRepository() : Repository{}
{
}

FileRepository::FileRepository(char * filePath)
{
	this->filePath.assign(filePath);
}

//FileRepository::FileRepository(char * filePath) : Repository{}
//{
//	this->filePath.assign(filePath);
//	readFromFile();
//}

void FileRepository::add(Dog & dog)
{
	Repository::add(dog);
	writeToFile();
}

void FileRepository::update(Dog & dog)
{
	Repository::update(dog);
	writeToFile();
}

void FileRepository::remove(Dog & dog)
{
	Repository::remove(dog);
	writeToFile();
}

void FileRepository::setFilePath(char * filePath, bool read, bool write)
{
	this->filePath.assign(filePath);
	if (read) {
		readFromFile();
	}
	if (write) {
		writeToFile();
	}
}

void FileRepository::openInApp()
{
	system((string{ "cmd /C \"" } +filePath + "\"").c_str());
}

void FileRepository::clear()
{
	dogs.clear();
	writeToFile();
}

void FileRepository::readFromFile()
{
	ifstream descriptor(this->filePath);
	Dog dog{};

	try {
		while (!descriptor.eof()) {
			descriptor >> dog;
			this->dogs.push_back(dog);
		}
	}
	catch (exception &exception) {
	}

	descriptor.close();
}

void FileRepository::writeToFile()
{
	ofstream descriptor(this->filePath);

	for (Dog & dog : dogs) {
		descriptor << dog;
	}

	descriptor.close();
}


FileRepository::~FileRepository()
{
}

