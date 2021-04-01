#include <stdio.h>

void modul();

char prop[100];

int main() 
{
	printf("Introduceti o propozitie:\n");
	// fgets(prop, 100, stdin);
	scanf("%s", prop);
	modul();
	return 0;
}