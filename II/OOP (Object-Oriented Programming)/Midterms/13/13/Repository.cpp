#include "Repository.h"

using namespace std;

Repository::Repository()
{
	//data.push_back(Weather{ 6,9,10,13,"foggy" });
	//data.push_back(Weather{ 9,12,13,7,"cloudy" });
	//data.push_back(Weather{ 12,14,18,23,"sunny" });
	//data.push_back(Weather{ 14,18,20,52,"sunny" });
	//data.push_back(Weather{ 18,21,20,68,"rainy" });
}


Repository::~Repository()
{
}

std::vector<Weather>& Repository::getAll()
{
	return data;
}

void Repository::readFromFile()
{
	ifstream file{ "D:\\Faculty\\Sem. II\\OOP (Object Oriented Programming)\\Midterms\\13\\data.txt" };

	while (!file.eof()) {
		string input{};
		getline(file, input);

		if (!input.empty()) {
			istringstream stream{ input };

			string token{};
			vector<string> tokens;

			while (getline(stream, token, ';')) {
				tokens.push_back(token);
			}

			if (tokens.size() != 5) {
				throw exception{ "Invalid data read for weather recording." };
			}

			Weather weather{ stoi(tokens[0]), stoi(tokens[1]), stod(tokens[2]), stod(tokens[3]), tokens[4] };
			data.push_back(weather);
		}
	};

	file.close();
}
