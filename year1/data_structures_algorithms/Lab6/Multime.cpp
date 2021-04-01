#include "Multime.h"
#include "IteratorMultime.h"

#include <iostream>

int Multime::d(TElem e) const {
	return abs(e) % m;
}

Multime::Multime() {
	m = 100;
	e = new TElem[m];
	urm = new int[m];
	for (int i = 0; i < m; i++) {
		e[i] = MAX;
		urm[i] = -1;
	}
	primLiber = 0;
	nr = 0;
}

void Multime::redim() {
	//O(m)
	TElem* old = new TElem[nr];
	for (int i = 0; i < nr; ++i) {
		old[i] = MAX;
	}
	int curent = 0;
	for (int i = 0; i < m; ++i) {
		if (e[i] != MAX) {
			old[curent++] = e[i];
		}
	}

	m *= 2;
	TElem* nou = new TElem[m];
	int* urmnou = new int[m];
	for (int i = 0; i < m; ++i) {
		nou[i] = MAX;
		urmnou[i] = -1;
	}
	delete[] e;
	delete[] urm;
	e = nou;
	urm = urmnou;

	primLiber = 0;
	int oldnr = nr;
	nr = 0;
	for (int i = 0; i < oldnr; ++i) {
		adauga(old[i]);
	}
	delete[] old;
}

bool Multime::adauga(TElem elem) {
	//O(m/n)
	if ((nr / m) * 100 > 70)
		redim();
	int i = d(elem);
	if (e[i] == MAX)
	{
		e[i] = elem;
		if (i == primLiber) {
			primLiber++;
			while ((primLiber < m) && e[primLiber] != MAX)
				primLiber++;
		}
		nr++;
		return true;
	}

	int j = -1;
	while (i != -1)
	{
		if (e[i] == elem)
			return false;
		j = i;
		i = urm[i];
	}


	e[primLiber] = elem;
	urm[j] = primLiber;
	primLiber++;
	while ((primLiber < m) && e[primLiber] != MAX)
		primLiber++;
	nr++;
	return true;


}


bool Multime::sterge(TElem elem) {
	//Theta(m/n)
	int i = d(elem);
	int j = -1;
	int index = 0;

	while (i != -1 && e[i] != elem) {
		j = i;
		i = urm[i];
	}

	if (i == -1) {
		return false;
	}
	else {
		bool ok = false;
		do {
			int curent = urm[i];
			int pred = i;

			while (curent != -1 && d(e[curent]) != i) {
				pred = curent;
				curent = urm[curent];
			}

			if (curent == -1) {
				ok = true;
			}
			else {
				e[i] = e[curent];
				i = curent;
				j = pred;
			}
		} while (ok == false);

		if (j != -1) {
			urm[j] = urm[i];
		}
		e[i] = MAX;
		urm[i] = -1;
		if (i < primLiber) {
			primLiber = i;
		}
	}
	--nr;
	return true;
}


bool Multime::cauta(TElem elem) const {
	//O(m/n)
	int i = d(elem);
	if (e[i] == elem)
	{
		return true;
	}

	int j = -1;
	while (i != -1)
	{
		if (e[i] == elem)
			return true;
		j = i;
		i = urm[i];
	}

	return false;
}


int Multime::dim() const {
	//Theta(1)
	return nr;
}

bool Multime::vida() const {
	//Theta(1)
	return nr == 0;
}


Multime::~Multime() {
	delete[] e;
	delete[] urm;
}



IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}

