#include <stdio.h>
#include <stdlib.h>
#include <string.h>


void append(char * string, char character)
{
	int length = strlen(string);
	string[length] = character;
	string[length + 1] = '\0';
}


void readArray(int *length, int *array)
{
	/*
	Reads the length and the elements of an array of integers from the keyboard.
	In: length - int
		array - vector of integers
	Out: length, array
	*/

	char data[1000], current[100];
	int arrayLength = 0;

	scanf("%[^'\n']s", data);
	append(data, '\n');

	int index = 0, dataLength = strlen(data);

	strcpy(current, "");

	while (index < dataLength)
	{
		if (!(data[index] == ' ') && !(data[index] == '\n'))
		{
			append(current, data[index]);
		}
		else
		{
			arrayLength++;
			int number = atoi(current);
			array[arrayLength - 1] = number;
			strcpy(current, "");
		}
		index++;
	}

	*length = arrayLength;

}


int isPrime(int number)
{
	/*
	Checks if number is prime.
	In: number - int
	Out: 1, if it's prime
		 0, otherwise
	*/

	if (number < 2 || (number != 2 && number % 2 == 0))
		return 0;

	for (int divisor = 3; divisor * divisor <= number; divisor += 2)
		if (number % divisor == 0)
			return 0;

	return 1;
}


void longestSequence(int length, int *array, int *start, int *sequenceLength)
{
	/*
	Determines the start and stop positions of the longest sequence in which the difference of two consecutive elements is a prime number.
	In: length - int
		array - vector of int
	Out: start, stop - integers
	*/

	*sequenceLength = 0;
	*start = 0;

	int currentLength = 1, currentStart = 0;

	for (int index = 1; index < length; index++)
	{
		if (isPrime(abs(array[index - 1] - array[index])) == 1)
			currentLength++;
		else
		{
			currentLength = 1;
			currentStart = index;
		}

		if (currentLength > *sequenceLength && currentLength > 1)
		{
			*sequenceLength = currentLength;
			*start = currentStart;
		}

	}
}


void printSequence(int length, int *array, int start, int sequenceLength)
{
	/*
	Prints the sequence of the elemets between start and stop position.
	In: length, start, stop - int
		array - vector of int
	Out: -
	*/

	for (int index = start; index < start + sequenceLength; index++)
		printf("%d ", array[index]);
}


int main()
{
	int start = 0, sequenceLength = 0, length = 0;
	int array[100] = { 0 };

	readArray(&length, array);

	longestSequence(length, array, &start, &sequenceLength);
	printSequence(length, array, start, sequenceLength);

	return 0;
}