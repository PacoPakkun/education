#pragma once

typedef struct {
	char* nume;
	char* producator;
	int* cantitate;
} MateriePrima;

MateriePrima createMateriePrima(char nume[], char producator[], int cantitate);

void destroyMateriePrima(MateriePrima* materiePrima);

int equal(MateriePrima materiePrima1, MateriePrima materieprima2);

MateriePrima copy(MateriePrima materiePrima);

void setNume(MateriePrima* materiePrima, char nume[]);

void setProducator(MateriePrima* materiePrima, char producator[]);

void setCantitate(MateriePrima* materiePrima, int cantitate);

char* getNume(MateriePrima* materiePrima);

char* getProducator(MateriePrima* materiePrima);

int getCantitate(MateriePrima* materiePrima);