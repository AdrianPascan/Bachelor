#include "Issue.h"



Issue::Issue()
{
}

Issue::Issue(std::string description, std::string status, std::string reporter, std::string solver): description{description}, status{status}, reporter{reporter}, solver{solver}
{
}


Issue::~Issue()
{
}

std::string Issue::toString()
{
	return description + ": " + status + ", reporter " + reporter + ", solver " + solver;
}

bool Issue::operator<(const Issue & issue)
{
	if (status.compare(issue.status) == 0) {
		return description.compare(issue.description) < 0;
	}
	return status.compare("open") == 0;
}
