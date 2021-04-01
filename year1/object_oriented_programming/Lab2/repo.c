#include <string.h>
#include <stdlib.h>
#include "repo.h"
#include "sortare.h"

MateriePrimaRepo createRepo() {
	//creeaza un struct de tip repozitoriu de materii prime alocat dinamic
	//initializeaza un sir static cu materii prime vide
	//initializeaza lungimea cu 0
	//initializeaza capacitatea cu 2
	//returneaza un struct repo de materii prime
	MateriePrimaRepo materiePrimaRepo;
	materiePrimaRepo.length = (int*)malloc(sizeof(int));
	*materiePrimaRepo.length = 0;
	materiePrimaRepo.capacity = (int*)malloc(sizeof(int));
	*materiePrimaRepo.capacity = 2;
	materiePrimaRepo.repo = (MateriePrima*)malloc(*materiePrimaRepo.capacity * sizeof(MateriePrima));
	return materiePrimaRepo;
}

void destroyRepo(MateriePrimaRepo* materiePrimaRepo) {
	//dealoca spatiul ocupat de repozitoriu
	//seteaza pointerii la NULL
	for (int i = 0; i < *materiePrimaRepo->length; i++) {
		destroyMateriePrima(&(materiePrimaRepo->repo[i]));
	}
	free(materiePrimaRepo->repo);
	materiePrimaRepo->repo = NULL;
	free(materiePrimaRepo->length);
	materiePrimaRepo->length = NULL;
	free(materiePrimaRepo->capacity);
	materiePrimaRepo->capacity = NULL;
}

MateriePrimaRepo copyRepo(MateriePrimaRepo materiePrimaRepo) {
	//construieste si returneaza o copie a repozitoriului
	MateriePrimaRepo copie = createRepo();
	for (int i = 0; i < *materiePrimaRepo.length; i++) {
		MateriePrima c = copy(materiePrimaRepo.repo[i]);
		addMateriePrima(&copie, c);
		destroyMateriePrima(&c);
	}
	return copie;
}

MateriePrima* getRepo(MateriePrimaRepo materiePrimaRepo) {
	return materiePrimaRepo.repo;
}

int getLength(MateriePrimaRepo materiePrimaRepo) {
	return *materiePrimaRepo.length;
}

void str(char* repoString[], MateriePrimaRepo materiePrimaRepo) {
	//returneaza un string care reprezinta continutul repozitoriului
	//stringul are formatul "[nume: @nume;producator: @producator;cantitate: @cantitate][..] .. [..]"
	//returneaza stringul vid daca repo e gol
	repoString[0] = '\0';
	for (int i = 0; i < *materiePrimaRepo.length; i++) {
		strcat(repoString, "[nume: ");
		char nume[20];
		strcpy(nume, getNume(&materiePrimaRepo.repo[i]));
		strcat(repoString, nume);
		strcat(repoString, ";producator: ");
		char producator[20];
		producator[0] = '\0';
		strcpy(producator, getProducator(&materiePrimaRepo.repo[i]));
		strcat(repoString, producator);
		strcat(repoString, ";cantitate: ");
		char convert[10];
		_itoa(getCantitate(&materiePrimaRepo.repo[i]),convert,10);
		strcat(repoString, convert);
		strcat(repoString, "]");
	}
}

void redim(MateriePrimaRepo* materiePrimaRepo) {
	//redimensioneaza un repozitoriu
	//verifica daca repo incape pe capacitatea curenta
	//aloca mai mult spatiu daca e necesar
	if (*materiePrimaRepo->length < *materiePrimaRepo->capacity) {
		return;}
	else {
		int newCapacity = *materiePrimaRepo->capacity + 2;
		MateriePrima* repo = malloc(sizeof(MateriePrima) * newCapacity);
		for (int i = 0; i < *materiePrimaRepo->length; i++) {
			repo[i] = materiePrimaRepo->repo[i];
		}
		free(materiePrimaRepo->repo);
		materiePrimaRepo->repo = repo;
		*materiePrimaRepo->capacity = newCapacity;
	}
}

void addMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima) {
	//adauga o materie prima in repo
	//daca materia exista deja, actualizeaza cantitatea
	//daca e nevoie, dubleaza capacitatea
	//materiePrima: struct materie prima nevid
	redim(materiePrimaRepo);
	int found = 0;
	for (int i = 0; i < *(*materiePrimaRepo).length;i++) {
		char nume1[20]="";
		strcpy(nume1,getNume(&(*materiePrimaRepo).repo[i]));
		char nume2[20]="";
		strcpy(nume2,getNume(&materiePrima));
		if (strcmp(nume1, nume2) == 0) {
			setCantitate(&(*materiePrimaRepo).repo[i], getCantitate(&(*materiePrimaRepo).repo[i]) + getCantitate(&materiePrima));
			found = 1;
		}
	}
	if (found == 0){
		*(*materiePrimaRepo).length += 1;
		(*materiePrimaRepo).repo[*(*materiePrimaRepo).length - 1] = copy(materiePrima);
	}
}

int updateMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima) {
	//modifica o materie prima din repo
	//actualizeaza producatorul, respectiv cantitatea
	//returneaza 1 daca nu exista materia in repo
	//returneaza 0 altfel
	for (int i = 0; i < *(*materiePrimaRepo).length; i++) {
		char nume1[20]="";
		strcpy(nume1, getNume(&(*materiePrimaRepo).repo[i]));
		char nume2[20]="";
		strcpy(nume2, getNume(&materiePrima));
		if (strcmp(nume1, nume2) == 0) {
			char producator[20]="";
			strcpy(producator, getProducator(&materiePrima));
			setProducator(&(*materiePrimaRepo).repo[i], producator);
			setCantitate(&(*materiePrimaRepo).repo[i], getCantitate(&materiePrima));
			return 0;
		}
	}
	return 1;
}

int deleteMateriePrima(MateriePrimaRepo* materiePrimaRepo, MateriePrima materiePrima) {
	//elimina o materie prima din repo
	//returneaza 1 daca nu exista materia in repo
	//returneaza 0 altfel
	for (int i = 0; i < *(*materiePrimaRepo).length; i++) {
		char nume1[20]="";
		strcpy(nume1, getNume(&(*materiePrimaRepo).repo[i]));
		char nume2[20]="";
		strcpy(nume2, getNume(&materiePrima));
		if (strcmp(nume1, nume2) == 0) {
			for (int j = i; j < *(*materiePrimaRepo).length - 1; j++) {
				char nume[20];
				strcpy(nume, getNume(&(*materiePrimaRepo).repo[i + 1]));
				setNume(&(*materiePrimaRepo).repo[i], nume);
				char producator[20];
				strcpy(producator, getProducator(&(*materiePrimaRepo).repo[i + 1]));
				setProducator(&(*materiePrimaRepo).repo[i], producator);
				setCantitate(&(*materiePrimaRepo).repo[i], getCantitate(&(*materiePrimaRepo).repo[i + 1]));
			}
			*(*materiePrimaRepo).length -= 1;
			destroyMateriePrima(&(materiePrimaRepo->repo[*materiePrimaRepo->length]));
			return 0;
		}
	}
	return 1;
}

MateriePrimaRepo filterNume(MateriePrimaRepo materiePrimaRepo, char litera) {
	//returneaza un repozitoriu filtrat dupa nume
	//returneaza doar materiile prime ale caror nume incep cu o litera data
	//litera: char nevid
	MateriePrimaRepo filtru = createRepo();
	for (int i = 0; i < *materiePrimaRepo.length; i++) {
		if (getNume(&materiePrimaRepo.repo[i])[0] == litera) {
			MateriePrima copie = copy(materiePrimaRepo.repo[i]);
			addMateriePrima(&filtru, copie);
			destroyMateriePrima(&copie);
		}
	}
	return filtru;
}

MateriePrimaRepo filterCantitate(MateriePrimaRepo materiePrimaRepo, int cantitate) {
	//returneaza un repozitoriu filtrat dupa cantitate
	//returneaza doar materiile prime cu cantitatea mai mica decat o cantitate data
	//cantitate: int nenul
	MateriePrimaRepo filtru = createRepo();
	for (int i = 0; i < *materiePrimaRepo.length; i++) {
		if (getCantitate(&materiePrimaRepo.repo[i]) < cantitate) {
			MateriePrima copie = copy(materiePrimaRepo.repo[i]);
			addMateriePrima(&filtru, copie);
			destroyMateriePrima(&copie);
		}
	}
	return filtru;
}

int compareNume(MateriePrima* materieprima1, MateriePrima* materieprima2) {
	//compara numele a 2 materii prime
	//returneaza 0 daca sunt egale
	//1 daca e mai mare
	//-1 daca e mai mic
	return strcmp(materieprima1->nume, materieprima2->nume);
}

int compareCantitate(MateriePrima* materieprima1, MateriePrima* materieprima2) {
	//compara cantitatea a 2 materii prime
	//returneaza 0 daca sunt egale
	//1 daca e mai mare
	//-1 daca e mai mic
	if (*materieprima1->cantitate == *materieprima2->cantitate) return 0;
	else if (*materieprima1->cantitate > *materieprima2->cantitate) return 1;
	else return -1;
}

MateriePrimaRepo sortNume(MateriePrimaRepo materiePrimaRepo, int ordine) {
	//returneaza un repozitoriu sortat dupa nume
	//sortarea se face crescator sau descrescator in fct de parametrul dat
	//ordine: int +-1 (1: crescator; -1: descrescator)
	MateriePrimaRepo sortare = copyRepo(materiePrimaRepo);
	sort(&sortare, compareNume, ordine);
	/*if (ordine == 1) {
		for (int i = 0; i < *sortare.length - 1; i++) {
			for (int j = i + 1; j < *sortare.length; j++) {
				char nume1[20];
				nume1[0] = '\0';
				strcpy(nume1, getNume(&sortare.repo[i]));
				char nume2[20];
				nume2[0] = '\0';
				strcpy(nume2, getNume(&sortare.repo[j]));
				int x = strcmp(nume1, nume2);
				if (strcmp(nume1, nume2) > 0) {
					MateriePrima aux = sortare.repo[i];
					sortare.repo[i] = sortare.repo[j];
					sortare.repo[j] = aux;
				}
			}
		}
	}
	else if (ordine == -1) {
		for (int i = 0; i < *sortare.length - 1; i++) {
			for (int j = i + 1; j < *sortare.length; j++) {
				char nume1[20];
				nume1[0] = '\0';
				strcpy(nume1, getNume(&sortare.repo[i]));
				char nume2[20];
				nume2[0] = '\0';
				strcpy(nume2, getNume(&sortare.repo[j]));
				int x = strcmp(nume1, nume2);
				if (strcmp(nume1, nume2) < 0) {
					MateriePrima aux = sortare.repo[i];
					sortare.repo[i] = sortare.repo[j];
					sortare.repo[j] = aux;
				}
			}
		}
	}*/
	return sortare;
}

MateriePrimaRepo sortCantitate(MateriePrimaRepo materiePrimaRepo, int ordine) {
	//returneaza un repozitoriu sortat dupa cantitate
	//sortarea se face crescator sau descrescator in fct de parametrul dat
	//ordine: int +-1 (1: crescator; -1: descrescator)
	MateriePrimaRepo sortare = copyRepo(materiePrimaRepo);
	sort(&sortare, compareCantitate, ordine);
	/*if (ordine == 1) {
		for (int i = 0; i < *sortare.length - 1; i++) {
			for (int j = i + 1; j < *sortare.length; j++) {
				if (getCantitate(&sortare.repo[i]) > getCantitate(&sortare.repo[j])) {
					MateriePrima aux = sortare.repo[i];
					sortare.repo[i] = sortare.repo[j];
					sortare.repo[j] = aux;
				}
			}
		}
	}
	else if (ordine == -1) {
		for (int i = 0; i < *sortare.length - 1; i++) {
			for (int j = i + 1; j < *sortare.length; j++) {
				if (getCantitate(&sortare.repo[i]) < getCantitate(&sortare.repo[j])) {
					MateriePrima aux = sortare.repo[i];
					sortare.repo[i] = sortare.repo[j];
					sortare.repo[j] = aux;
				}
			}
		}
	}*/
	return sortare;
}