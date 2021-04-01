#include "Colectie.h"
#include "IteratorColectie.h"
#include <iostream>

using namespace std;

bool rel(TElem e1, TElem e2) {
	return e1 <= e2;
}

Colectie::Colectie() : cap{ nullptr }, n{ 0 } {
}

void Colectie::adauga(TElem e) {
	//O(nr elem distincte)
	auto nod = cap;
	if (nod == nullptr) {
		cap = new Node{ Pereche{ e, 1 }, nullptr };
		n++;
		return;
	}
	if (nod->val.first == e) {
		nod->val.second++;
		n++;
		return;
	}
	if (rel(e, nod->val.first)) {
		cap = new Node{ Pereche{ e, 1 }, nod };
		n++;
		return;
	}
	while (nod != nullptr && nod->urm != nullptr) {
		if (nod->urm->val.first == e) {
			nod->urm->val.second++;
			n++;
			return;
		}
		if (rel(e, nod->urm->val.first)) {
			auto aux = nod->urm;
			nod->urm = new Node{ Pereche{ e, 1 }, aux };
			n++;
			return;
		}
		nod = nod->urm;
	}
	nod->urm = new Node{ Pereche{ e, 1 }, nullptr };
	n++;
}


bool Colectie::sterge(TElem e) {
	//O(nr elem distincte)
	auto nod = cap;
	if (nod == nullptr) {
		return false;
	}
	if (nod->val.first == e) {
		if (nod->val.second > 1) {
			nod->val.second--;
		}
		else {
			auto aux = nod->urm;
			delete nod;
			cap = aux;
		}
		n--;
		return true;
	}
	while (nod != nullptr && nod->urm != nullptr) {
		if (nod->urm->val.first == e) {
			if (nod->urm->val.second > 1) {
				nod->urm->val.second--;
			}
			else {
				auto aux = nod->urm;
				nod->urm = nod->urm->urm;
				delete aux;

			}
			n--;
			return true;
		}
		nod = nod->urm;
	}
	return false;
}


bool Colectie::cauta(TElem elem) const {
	//O(nr elem distincte)
	auto nod = cap;
	while (nod != nullptr) {
		if (nod->val.first == elem) {
			return true;
		}
		nod = nod->urm;
	}
	return false;
}


int Colectie::nrAparitii(TElem elem) const {
	//O(nr elem distincte)
	auto nod = cap;
	while (nod != nullptr) {
		if (nod->val.first == elem) {
			return nod->val.second;
		}
		nod = nod->urm;
	}
	return 0;
}



int Colectie::dim() const {
	//O(1)
	return n;
	/*auto nod = cap;
	int i = 0;
	while (nod != nullptr) {
		i += nod->val.second;
		nod = nod->urm;
	}
	return i;*/
}


bool Colectie::vida() const {
	//O(1)
	return cap == nullptr;
}


IteratorColectie Colectie::iterator() const {
	return  IteratorColectie(*this);
}


Colectie::~Colectie() {
	Node* nod = cap;
	while (nod != nullptr) {
		Node* aux = nod->urm;
		delete nod;
		nod = aux;
	}
	cap = nullptr;
}

void Colectie::adaugaAparitiiMultiple(int nr, TElem elem) {
	/*
		best case: Theta(1)
		worst case: Theta(nr elem distincte)
		average case: Theta(n+1/2)=Theta(n)
		overall: O(nr elem distincte)
	*/
	if (nr < 0) {
		throw "";
	}
	auto nod = cap;
	if (nod == nullptr) {
		cap = new Node{ Pereche{ elem, nr }, nullptr };
		n+=nr;
		return;
	}
	if (nod->val.first == elem) {
		nod->val.second+=nr;
		n+=nr;
		return;
	}
	if (rel(elem, nod->val.first)) {
		cap = new Node{ Pereche{ elem, nr }, nod };
		n+=nr;
		return;
	}
	while (nod != nullptr && nod->urm != nullptr) {
		if (nod->urm->val.first == elem) {
			nod->urm->val.second+=nr;
			n+=nr;
			return;
		}
		if (rel(elem, nod->urm->val.first)) {
			auto aux = nod->urm;
			nod->urm = new Node{ Pereche{ elem, nr }, aux };
			n+=nr;
			return;
		}
		nod = nod->urm;
	}
	nod->urm = new Node{ Pereche{ elem, nr }, nullptr };
	n+=nr;
}