#pragma once

#include<vector>
#include<string>
#include<fstream>
#include<sstream>
#include<algorithm>
#include"User.h"
#include"Issue.h"

class Repository
{
private:
	std::vector<User> users;
	std::vector<Issue> issues;
	std::string issuesPath;

public:
	Repository();
	~Repository();
	void readUsers();
	void readIssues();
	std::vector<User> getUsers() { return users; }
	std::vector<Issue> getIssues();

	/*
	Adds an issue to the repository.
	Input: issue - Issue object
	Output: - 
	Exception: std::exception, when an issue with the same description exists already
	*/
	void add(Issue issue);

	void remove(int position);

	/*
	Updates an issue from repository.
	Input: position - integer, the position of the issue in the vector of issues
		   solver name - string, the name of the programmer who solved it
	Output: -
	*/
	void resolve(int position, std::string solverName);

	void writeIssues();

private:
	std::vector<std::string> readFromFile(std::string path);
	std::vector<std::string> tokenize(std::string input);
};

