#pragma once

#include "Repository.h"
#include "Subject.h"
#include <vector>

class Controller: public Subject
{
private:
	Repository * repository;
	
public:
	Controller(Repository * repository);
	~Controller();
	std::vector<User> getUsers() { return repository->getUsers(); }
	std::vector<Issue> getIssues();
	void addIssue(Issue issue);
	void removeIssue(int position);
	void resolveIssue(int position, std::string solverName);
};

