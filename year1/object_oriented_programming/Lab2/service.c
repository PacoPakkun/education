#include "service.h"

Service createService(MateriePrimaRepo materiePrimaRepo) {
	//creeaza un struct de tip service
	//primeste ca parametru un repozitoriu pe care il poate gestiona
	Service service;
	service.repo = materiePrimaRepo;
	return service;
}

void destroyService(Service* service) {
	//distruge repozitoriul asociat
	destroyRepo(&service->repo);
}

int srv_add(Service* service, char nume[], char producator[], int cantitate) {
	//construieste o materie prima
	//o adauga in repo
	//nume: string nevid
	//producator: string nevid
	//cantitate: int nenul
	//returneaza 0 daca adaugarea a fost facuta cu succes
	MateriePrima materiePrima = createMateriePrima(nume, producator, cantitate);
	addMateriePrima(&(*service).repo, materiePrima);
	destroyMateriePrima(&materiePrima);
	return 0;
}

int srv_update(Service* service, char nume[], char producator[], int cantitate) {
	//construieste o materie prima
	//actualizeaza materia prima cu numele dat
	//modifica producatorul, respectiv cantitatea in parametrii dati
	//nume: string nevid
	//producator: string nevid
	//cantitate: int nenul
	//returneaza 0 daca modificarea a fost facuta cu succes
	//returneaza 1 daca materia prima data nu exista
	MateriePrima materiePrima = createMateriePrima(nume, producator, cantitate);
	int err = updateMateriePrima(&(*service).repo, materiePrima);
	destroyMateriePrima(&materiePrima);
	return err;
}

int srv_delete(Service* service, char nume[]) {
	//constr o materie prima cu numele dat
	//o elimina din repo
	//nume: string nevid
	//returneaza 0 daca modificarea a fost facuta cu succes
	//returneaza 1 daca materia prima data nu exista
	MateriePrima materiePrima = createMateriePrima(nume, "", 0);
	int err = deleteMateriePrima(&(*service).repo, materiePrima);
	destroyMateriePrima(&materiePrima);
	return err;
}

int srv_filterNume(Service* service, MateriePrimaRepo* filtru, char litera) {
	//filtreaza un repozitoriu dupa nume
	//obtine doar materiile prime ale caror nume incep cu o litera data
	//litera: char nevid
	//returneaza 0 daca filtrarea a avut loc cu succes
	*filtru = filterNume((*service).repo, litera);
	return 0;
}

int srv_filterCantitate(Service* service, MateriePrimaRepo* filtru, int cantitate) {
	//filtreaza un repozitoriu dupa caqntitate
	//obtine doar materiile prime ale caror cantitate e mai mica decat cea data
	//cantitate: int nenul
	//returneaza 0 daca filtrarea a avut loc cu succes
	*filtru = filterCantitate((*service).repo, cantitate);
	return 0;
}

int srv_sortNume(Service* service, MateriePrimaRepo* sortare, int ordine) {
	//sorteaza un repozitoriu dupa nume crescator/descrescator
	//litera: int +-1 (1: crescator; -1: descrescator)
	//returneaza 0 daca sortarea a avut loc cu succes
	*sortare = sortNume((*service).repo, ordine);
	return 0;
}

int srv_sortCantitate(Service* service, MateriePrimaRepo* sortare, int ordine) {
	//sorteaza un repozitoriu dupa cantitate crescator/descrescator
	//litera: int +-1 (1: crescator; -1: descrescator)
	//returneaza 0 daca sortarea a avut loc cu succes
	*sortare = sortCantitate((*service).repo, ordine);
	return 0;
}