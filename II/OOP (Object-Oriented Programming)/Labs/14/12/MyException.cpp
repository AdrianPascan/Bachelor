#pragma once

#include "MyException.h"

MyException::MyException(std::string _message) : message{ _message + "\n" }
{
}

const char * MyException::what() const noexcept
{
	return this->message.c_str();
}
