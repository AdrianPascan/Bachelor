#pragma once

#include "HTMLRepository.h"
#include "MyException.h"
#include <fstream>

using namespace std;


HTMLRepository::HTMLRepository(): Repository{}
{
}

HTMLRepository::HTMLRepository(char * filePath)
{
	this->filePath.assign(filePath);
}


HTMLRepository::~HTMLRepository()
{
}

void HTMLRepository::add(Dog & dog)
{
	Repository::add(dog);
	writeToFile();
}

void HTMLRepository::update(Dog & dog)
{
	Repository::update(dog);
	writeToFile();
}

void HTMLRepository::remove(Dog & dog)
{
	Repository::remove(dog);
	writeToFile();
}

void HTMLRepository::setFilePath(char * filePath, bool read, bool write)
{
	this->filePath.assign(filePath);
	if (write) {
		writeToFile();
	}
	if (read) {
		throw MyException{"Cannot read from HTML file."};
	}
}

void HTMLRepository::openInApp()
{
	system((string{ "cmd /C \"" } + filePath + "\"").c_str());
}

void HTMLRepository::clear(bool write)
{
	dogs.clear();
	if (write) {
		writeToFile();
	}
}


void HTMLRepository::writeToFile()
{
	ofstream stream(filePath);

	//HEADER
	stream << "<html>" << endl;
	stream << "<html>" << endl;
	stream << "<head>" << endl;
	stream << "<title>Dogs</title>" << endl;
	stream << "</head>" << endl;
	stream << "<body>" << endl;
	stream << "<table border=\"1\">" << endl;
	stream << "<tr>" << endl;
	stream << "<td>Name</td>" << endl;
	stream << "<td>Breed</td>" << endl;
	stream << "<td>Birth date</td>" << endl;
	stream << "<td>Number of vaccinations</td>" << endl;
	stream << "<td>Photohraph</td>" << endl;
	stream << "</tr>" << endl;

	//DOGS
	for (Dog & dog : dogs) {
		stream << "<tr>" << endl;
		stream << "<td>" << dog.getName() << "</td>" << endl;
		stream << "<td>" << dog.getBreed() << "</td>" << endl;
		stream << "<td>" << dog.getBirthDate() << "</td>" << endl;
		stream << "<td>" << dog.getVaccinations() << "</td>" << endl;
		stream << "<td><a href=" << dog.getPhotograph() << ">" << "Link" << "</a></td>" << endl;
		stream << "</tr>" << endl;
	}

	//FOOTER
	stream << "</table>" << endl;
	stream << "</body>" << endl;
	stream << "</html>" << endl;

	stream.close();
}
