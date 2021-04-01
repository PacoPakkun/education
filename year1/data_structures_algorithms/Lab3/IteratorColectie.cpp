#include "IteratorColectie.h"
#include "Colectie.h"


IteratorColectie::IteratorColectie(const Colectie& c) : col(c) {
	this->nodCurent = c.cap;
	this->frecventa = 1;
}

TElem IteratorColectie::element() const {
	//Theta(1)
	if (valid()) {
		return nodCurent->val.first;
	}
	else {
		throw "";
	}
}

bool IteratorColectie::valid() const {
	// Theta(1)
	if (nodCurent == nullptr) {
		return false;
	}
	if (frecventa > nodCurent->val.second) {
		return false;
	}
	if (frecventa == nodCurent->val.second && nodCurent->urm==nullptr) {
		return false;
	}
	return true;
}

void IteratorColectie::urmator() {
	//Theta(1)
	if (valid()) {
		if (frecventa == nodCurent->val.second) {
			nodCurent = nodCurent->urm;
		}
		else {
			frecventa++;
		}
	}
	else {
		throw "";
	}
}

void IteratorColectie::prim() {
	nodCurent = this->col.cap;
	frecventa = 1;
}