#pragma once
#include "domain.h"

typedef struct {
	MateriePrima* repo;
	int* length;
	int* capacity;
} MateriePrimaRepo;

MateriePrimaRepo createRepo();

void destroyRepo(MateriePrimaRepo* materiePrimaRepo);

MateriePrimaRepo copyRepo(MateriePrimaRepo materiePrimaRepo);

MateriePrima* getRepo(MateriePrimaRepo materiePrimaRepo);

int getLength(MateriePrimaRepo materiePrimaRepo);

void str(char* repoString[], MateriePrimaRepo materiePrimaRepo);

void addMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima);

int updateMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima);

int deleteMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima);

MateriePrimaRepo filterNume(MateriePrimaRepo materiePrimaRepo, char litera);

MateriePrimaRepo filterCantitate(MateriePrimaRepo materiePrimaRepo, int cantitate);

MateriePrimaRepo sortNume(MateriePrimaRepo materiePrimaRepo, int ordine);

MateriePrimaRepo sortCantitate(MateriePrimaRepo materiePrimaRepo, int ordine);