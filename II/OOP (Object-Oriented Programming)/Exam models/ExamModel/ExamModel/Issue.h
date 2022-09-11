#pragma once

#include<string>

class Issue
{
public:
	std::string description;
	std::string status;
	std::string reporter;
	std::string solver;

public:
	Issue();
	Issue(std::string description, std::string status, std::string reporter, std::string solver);
	~Issue();
	std::string toString();
	bool operator<(const Issue& issue);
};

