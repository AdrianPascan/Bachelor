#pragma once

#include <exception>
#include <string>

class MyException : public std::exception
{
private:
	std::string message;

public:
	MyException(std::string _message);
	const char* what() const noexcept override;
};

