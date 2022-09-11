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

Dog FileRepository::update(Dog & dog)
{
	Dog oldDog{ Repository::update(dog) };

	writeToFile();

	return oldDog;
}

Dog FileRepository::remove(Dog & dog)
{
	Dog oldDog{ Repository::remove(dog) };

	writeToFile();

	return oldDog;
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

void FileRepository::clear(bool write)
{
	dogs.clear();
	if (write) {
		writeToFile();
	}
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

