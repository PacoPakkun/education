#pragma once
#include "repo.h"

typedef struct {
	MateriePrimaRepo repo;
} Service;

Service createService(MateriePrimaRepo materiePrimaRepo);

void destroyService(Service* service);

int srv_add(Service* service, char nume[], char producator[], int cantitate);

int srv_update(Service* service, char nume[], char producator[], int cantitate);

int srv_delete(Service* service, char nume[]);

int srv_filterNume(Service* service, MateriePrimaRepo* filtru, char litera);

int srv_filterCantitate(Service* service, MateriePrimaRepo* filtru, int cantitate);

int srv_sortNume(Service* service, MateriePrimaRepo* sortare, int ordine);

int srv_sortCantitate(Service* service, MateriePrimaRepo* sortare, int ordine);