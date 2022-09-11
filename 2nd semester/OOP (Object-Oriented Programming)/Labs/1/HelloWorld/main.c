#include <stdio.h>

int main()
{
	printf("Hello world!\n\n");

	char string[100];
	printf("Input something: ");
	scanf("%[^'\n']s", string);
	//fgets(string, 100, stdin); 
	
	printf("Your input: %s", string);

	return 0;
}