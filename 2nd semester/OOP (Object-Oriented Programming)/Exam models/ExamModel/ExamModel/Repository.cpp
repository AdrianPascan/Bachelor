#include "Repository.h"

using namespace std;

Repository::Repository()
{
}


Repository::~Repository()
{
}

void Repository::readUsers()
{
	vector<string> recordings = readFromFile("D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Exam\\Exam model\\My solutions\\ExamModel\\users.txt");

	if (!recordings[0].empty()) {
		for (string & recording : recordings) {
			vector<string> tokens = tokenize(recording);

			if (tokens.size() != 2) {
				throw exception{ "Invalid data read for user." };
			}

			User user{ tokens[0], tokens[1] };
			users.push_back(user);
		}
	}
}

void Repository::readIssues()
{
	issuesPath = "D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Exam\\Exam model\\My solutions\\ExamModel\\issues.txt";
	vector<string> recordings = readFromFile(issuesPath);

	issues.clear();

	if (!recordings[0].empty()){
		for (string & recording : recordings) {
			vector<string> tokens = tokenize(recording);

			if (tokens.size() != 4 && tokens.size() != 0) {
				throw exception{ "Invalid data read for issue." };
			}

			if (tokens.size() != 0) {
				Issue issue{ tokens[0], tokens[1], tokens[2], tokens[3] };
				issues.push_back(issue);
			}
		}
	}

	sort(issues.begin(), issues.end());
}

std::vector<Issue> Repository::getIssues()
{
	return issues;
}

void Repository::add(Issue issue)
{
	for (Issue & current : issues) {
		if (current.description == issue.description) {
			throw exception{ (issue.description + " already exists.").c_str() };
		}
	}

	issues.push_back(issue);
	sort(issues.begin(), issues.end());
}

void Repository::remove(int position)
{
	if (issues[position].status != "closed") {
		throw exception{ (issues[position].description + " is not closed.").c_str() };
	}

	issues.erase(issues.begin() + position);
}

void Repository::resolve(int position, std::string solverName)
{
	issues[position].status = "closed";
	issues[position].solver = solverName;

	sort(issues.begin(), issues.end());
}

std::vector<std::string> Repository::readFromFile(std::string path)
{
	vector<string> recordings{};

	ifstream file(path);

	while (!file.eof()) {
		string input{};
		getline(file, input);
		recordings.push_back(input);
	}

	file.close();

	return recordings;
}

std::vector<std::string> Repository::tokenize(std::string input)
{
	vector<string> tokens{};
	string token{};
	
	istringstream stream{ input };

	while (getline(stream, token, ',')) {
		tokens.push_back(token);
	}

	return tokens;
}

void Repository::writeIssues()
{
	ofstream file{ issuesPath };

	for (auto & issue : issues) {
		file << issue.description << "," << issue.status << "," << issue.reporter << "," << issue.solver << endl;
	}

	file.close();
}
