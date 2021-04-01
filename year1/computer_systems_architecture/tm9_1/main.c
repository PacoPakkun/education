#include <stdio.h>

void concat(char sir1[],char sir2[],char sir_rez1[],char sir_rez2[]);
//void concat();


int main()
{
	char sir1[10] = "";
	char sir2[10] = "";
	char sir_rez1[20] = "";
	char sir_rez2[20] = "";
	printf("Introduceti primul sir de caractere:\n");
	scanf("%s", sir1);
	printf("Introduceti al doilea sir de caractere:\n");
	scanf("%s", sir2);
	concat(sir1, sir2, sir_rez1, sir_rez2);
	//concat();
	printf("Cifrele concatenate direct sunt: %s\n", sir_rez1);
	printf("Cifrele concatenate invers sunt: %s\n", sir_rez2);
	return 0;
}