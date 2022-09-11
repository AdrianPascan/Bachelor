#include "Weather.h"

using namespace std;

Weather::Weather()
{
}

Weather::Weather(int start, int end, double temperature, double precipitation, std::string description): start{start}, end{end}, temperature{temperature}, precipitation{precipitation}, description{description}
{
}


Weather::~Weather()
{
}

std::string Weather::toString()
{
	return std::string{ "Start: " + to_string(start) + ", end: " + to_string(end) + ", temp.: " + to_string(temperature) + ", precip. : " + to_string(precipitation) + ", description: " + description };
}
