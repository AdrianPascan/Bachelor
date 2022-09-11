#pragma once

#include "Validator.h"
#include "MyException.h"
#include <stdlib.h>
#include <string.h>


//Validator::Validator()
//{
//}


//Validator::~Validator()
//{
//}


int Validator::validateInteger(char * string)
{
	int number = atoi(string);
	char * newString = new char[strlen(string) + 1];
	_itoa(number, newString, 10);
	bool valid = (strlen(string) == strlen(newString));
	delete[] newString;

	if (!valid) {
		throw MyException{ "Invalid integer." };
	}

	return number;
}


int Validator::validatePositiveInteger(char * string)
{
	int number = validateInteger(string);

	if (number < 0) {
		throw MyException{ "Invalid positive integer." };
	}

	return number;
}
