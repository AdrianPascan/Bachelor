#pragma once

#include<string>

class Weather
{
public:
	int start;
	int end;
	double temperature;
	double precipitation;
	std::string description;

public:
	Weather();
	Weather(int start, int end, double temperature, double precipitation, std::string description);
	~Weather();

	std::string toString();
};

