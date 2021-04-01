#include <string.h>
#include <stdlib.h>
#include "domain.h"

MateriePrima createMateriePrima(char nume[], char producator[], int cantitate) {
	//creeaza un struct de tip materie prima alocat dinamic
	//nume: sir de caractere nevid
	//producator: sir de caractere nevid
	//cantitate: int >=0
	//returneaza un struct MateriePrima
	MateriePrima materiePrima;
	size_t nrC = strlen(nume) + 1;
	materiePrima.nume = (char*)malloc(nrC * sizeof(char));
	strcpy(materiePrima.nume,nume);
	nrC = strlen(producator) + 1;
	materiePrima.producator = (char*)malloc(nrC * sizeof(char));
	strcpy(materiePrima.producator,producator);
	materiePrima.cantitate = (int*)malloc(sizeof(int));
	*materiePrima.cantitate = cantitate;
	return materiePrima;
}

void destroyMateriePrima(MateriePrima* materiePrima) {
	//dealoca spatiul ocupat de o materie prima
	//seteaza pointerii la NULL
	free(materiePrima->nume);
	materiePrima->nume = NULL;
	free(materiePrima->producator);
	materiePrima->producator = NULL;
	free(materiePrima->cantitate);
	materiePrima->cantitate = NULL;
}

int equal(MateriePrima materiePrima1, MateriePrima materieprima2) {
	//verifica daca 2 materii prime sunt egale
	//compara fiecare camp
	//returneaza 0 daca sunt egale
	//returneaza 1 altfel
	if (strcmp(materiePrima1.nume, materieprima2.nume) != 0) return 1;
	if (strcmp(materiePrima1.producator, materieprima2.producator) != 0) return 1;
	if (*materiePrima1.cantitate != *materieprima2.cantitate) return 1;
	return 0;
}

MateriePrima copy(MateriePrima materiePrima) {
	MateriePrima copie = createMateriePrima("", "", 0);
	setNume(&copie, getNume(&materiePrima));
	setProducator(&copie, getProducator(&materiePrima));
	setCantitate(&copie, getCantitate(&materiePrima));
	return copie;
}

void setNume(MateriePrima* materiePrima, char nume[]) {
	free(materiePrima->nume);
	materiePrima->nume = NULL;
	materiePrima->nume = (char*)malloc((strlen(nume) + 1) * sizeof(char));
	strcpy((*materiePrima).nume, nume);
}

void setProducator(MateriePrima* materiePrima, char producator[]) {
	free(materiePrima->producator);
	materiePrima->producator = NULL;
	materiePrima->producator = (char*)malloc((strlen(producator) + 1) * sizeof(char));
	strcpy((*materiePrima).producator, producator);
}

void setCantitate(MateriePrima* materiePrima, int cantitate) {
	*(*materiePrima).cantitate = cantitate;
}

char* getNume(MateriePrima* materiePrima) {
	return (*materiePrima).nume;
}

char* getProducator(MateriePrima* materiePrima) {
	return (*materiePrima).producator;
}

int getCantitate(MateriePrima* materiePrima) {
	return *(*materiePrima).cantitate;
}
