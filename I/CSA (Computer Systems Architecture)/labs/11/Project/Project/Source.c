#include <stdio.h>

void printBinaryValue(int);

int main()
{
	int string[100] = { 0 }, number = 0, index = 0;

	printf("How many numbers do you want to input?: ");
	scanf_s("%d", &number);

	printf("Please input the numbers one by one:\n");
	for (index = 0; index < number; index++)
		scanf_s("%d", &string[index]);

	printf("Corresponding binary and hexadecimal values of the numbers:\n");
	for (index = 0; index < number; index++)
	{
		printf("%d. %d : %08xh - ", index + 1, string[index], string[index]);
		printBinaryValue(string[index]);
		printf("\n");
	}

	return 0;

}