#include "MD.h"
#include "IteratorMD.h"
#include <exception>
#include <iostream>

using namespace std;


MD::MD() {
	/* Theta(cap) */
	cap = 100;
	list = new TElem[cap];
	prim = -1;
	ultim = -1;
	liber = 0;
	size = 0;
	urm = new int[cap];
	for (int i = 0; i < cap - 1; i++)
		urm[i] = i + 1;
	urm[cap - 1] = -1;
}


void MD::redim() {
	/* Theta(2*cap) */
	TElem* nou = new TElem[2 * cap];
	int* urmnou = new int[2 * cap];
	for (int i = 0; i < cap; i++) {
		nou[i] = list[i];
		urmnou[i] = urm[i];
	}
	for (int i = cap; i < 2 * cap - 1; i++) {
		urmnou[i] = i + 1;
	}
	urmnou[2 * cap - 1] = -1;
	delete[] list;
	delete[] urm;
	list = nou;
	urm = urmnou;
	liber = cap;
	cap = 2 * cap;
}

void MD::adauga(TCheie c, TValoare v) {
	/* Theta(1) */
	if (liber == -1) {
		redim();
	}
	list[liber] = TElem{ c,v };
	if (prim == -1) {
		prim = liber;
	}
	int aux = liber;
	liber = urm[liber];
	urm[aux] = -1;
	if (ultim == -1) {
		ultim = aux;
	}
	else {
		urm[ultim] = aux;
		ultim = aux;
	}
	size++;
}


bool MD::sterge(TCheie c, TValoare v) {
	/* O(n) */
	if (prim == -1) {
		return false;
	}
	int curent = prim;
	if (list[curent].first == c && list[curent].second == v) {
		prim = urm[curent];
		urm[curent] = liber;
		liber = curent;
		size--;
		return true;
	}
	if (urm[curent] == -1) {
		return false;
	}
	while (urm[curent] != -1) {
		if (list[urm[curent]].first == c && list[urm[curent]].second == v) {
			if (urm[curent] == ultim) {
				ultim = curent;
			}
			int aux = urm[curent];
			urm[curent] = urm[urm[curent]];
			urm[aux] = liber;
			liber = aux;
			size--;
			return true;
		}
		curent = urm[curent];
	}
	return false;
}


vector<TValoare> MD::cauta(TCheie c) const {
	/* O(n) */
	vector<TValoare> v;
	if (prim == -1) {
		return v;
	}
	int curent = prim;
	while (curent != -1) {
		if (list[curent].first == c) {
			v.push_back(list[curent].second);
		}
		curent = urm[curent];
	}
	return v;
}


int MD::dim() const {
	/* Theta(1) */
	return size;
}


bool MD::vid() const {
	/* Theta(1) */
	if (size == 0) {
		return true;
	}
	else {
		return false;
	}
}

IteratorMD MD::iterator() const {
	return IteratorMD(*this);
}


MD::~MD() {
	/* Theta(1) */
	delete[] list;
	delete[] urm;
}

vector<TValoare>MD::colectiaValorilor() const {
	/* 
		best case: Theta(1)
		worst case: Theta(n)
		average case: 1/n(1+2+...+n) -> Theta(n)
		overall: O(n)
	*/
	vector<TValoare> v;
	if (prim == -1) {
		return v;
	}
	int curent = prim;
	while (curent != -1) {
		v.push_back(list[curent].second);
		curent = urm[curent];
	}
	return v;
}