#include "Multime.h"
#include "IteratorMultime.h"
#include <iostream>

using namespace std;

//o posibila relatie
bool rel(TElem e1, TElem e2) {
	if (e1 <= e2) {
		return true;
	}
	else {
		return false;
	}
}

Multime::Multime() {
	root = nullptr;
	size = 0;
}

bool Multime::adaugaRec(Nod* curent, Nod* anterior, TElem elem) {
	if (curent == nullptr){
		if(rel(elem,anterior->elem))
			anterior->st = new Nod{ elem,nullptr,nullptr };
		else
			anterior->dr = new Nod{ elem,nullptr,nullptr };
		return true;
	}
	if (curent->elem == elem)
		return false;
	else if (rel(elem, curent->elem))
		return adaugaRec(curent->st, curent,elem);
	else
		return adaugaRec(curent->dr, curent,elem);
}

bool Multime::adauga(TElem elem) {
	//O(log2n)
	if (root == nullptr) {
		root = new Nod{ elem,nullptr,nullptr };
		size++;
		return true;
	}
	bool ok = adaugaRec(root, nullptr,elem);
	if (ok)
		size++;
	return ok;
}

TElem Multime::min(Nod* curent) const {
	if (curent->st != nullptr)
		return min(curent->st);
	else
		return curent->elem;
}

TElem Multime::max(Nod* curent) const {
	if (curent->dr != nullptr)
		return max(curent->dr);
	else
		return curent->elem;
}

int Multime::diferentaMaxMin() const {
	if (root == nullptr)
		return -1;
	else
		return max(root) - min(root);
}


bool Multime::stergeRec(Nod* curent, Nod* anterior, TElem elem) {
	if (curent == nullptr)
		return false;
	if (curent->elem == elem) {
		if (curent->st == nullptr && curent->dr == nullptr) {
			if (curent == root) {
				delete root;
				root = nullptr;
			}
			else {
				if (anterior->st == curent)
					anterior->st = nullptr;
				else
					anterior->dr = nullptr;
				delete curent;
			}
			return true;
		}
		else if (curent->st == nullptr || curent->dr == nullptr) {
			if (curent->st == nullptr) {
				if (anterior == nullptr)
					root = curent->dr;
				else if (anterior->st == curent)
					anterior->st = curent->dr;
				else
					anterior->dr = curent->dr;
			}
			else {
				if (anterior == nullptr)
					root = curent->st;
				else if (anterior->st == curent)
					anterior->st = curent->st;
				else
					anterior->dr = curent->st;
			}
			delete curent;
			return true;
		}
		else {
			auto minim = min(curent->dr);
			curent->elem = minim;
			return stergeRec(curent->dr, curent, minim);
		}
	}
	else if (rel(elem, curent->elem))
		return stergeRec(curent->st, curent, elem);
	else
		return stergeRec(curent->dr, curent, elem);
}

bool Multime::sterge(TElem elem) {
	//O(log2n)
	if (root == nullptr)
		return false;
	bool ok = stergeRec(root, nullptr, elem);
	if (ok)
		size--;
	return ok;
}

bool Multime::cautaRec(Nod* curent, TElem elem) const {
	if (curent == nullptr) {
		return false;
	}
	if (curent->elem == elem) {
		return true;;
	}
	else if (rel(elem, curent->elem))
		cautaRec(curent->st, elem);
	else
		cautaRec(curent->dr, elem);
}

bool Multime::cauta(TElem elem) const {
	//O(log2n)
	if (root == nullptr)
		return false;
	return cautaRec(root, elem);
}


int Multime::dim() const {
	//Theta(1)
	return size;
}



bool Multime::vida() const {
	//Theta(1)
	return size == 0;
}

IteratorMultime Multime::iterator() const {
	return IteratorMultime(*this);
}

void Multime::dealocaRec(Nod* curent) {
	if (curent->st == nullptr && curent->dr == nullptr) {
		delete curent;
	}
	else {
		if (curent->st != nullptr)
			dealocaRec(curent->st);
		if (curent->dr != nullptr)
			dealocaRec(curent->dr);
	}
}

Multime::~Multime() {
	//Theta(n)
	if (root != nullptr)
		dealocaRec(root);
}






