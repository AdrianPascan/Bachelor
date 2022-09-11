#pragma once

#include<string>

class User
{
public: 
	std::string name;
	std::string type;

public:
	User();
	User(std::string name, std::string type);
	~User();
};

