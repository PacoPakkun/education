#include "Dictionar.h"
#include <iostream>
#include "IteratorDictionar.h"

Dictionar::Dictionar() {
	/* de adaugat */
	this->max_len = 200;
	this->dict = new TElem[this->max_len];
	this->len = 0;
}

Dictionar::~Dictionar() {
	/* de adaugat */
	delete[] this->dict;
}

void Dictionar::redim() {
	/* Theta(n) */
	int new_max_len = 2 * this->max_len;
	TElem* new_dict = new TElem[new_max_len];
	for (int i = 0; i < this->max_len; i++) {
		new_dict[i] = this->dict[i];
	}
	delete[] this->dict;
	this->dict = new_dict;
}

TValoare Dictionar::adauga(TCheie c, TValoare v) {
	/* de adaugat */
	/*T(n) -> O(n)*/
	for (int i = 0; i < this->len; i++) {
		if ((this->dict[i]).first == c) {
			TValoare t = (this->dict[i]).second;
			(this->dict[i]).second = v;
			return t;
		}
	}
	if (this->len == this->max_len) {
		redim();
		this->max_len = this->max_len * 2;
	}
	this->dict[len] = TElem(c, v);
	this->len ++;
	return NULL_TVALOARE;
}



//cauta o cheie si returneaza valoarea asociata (daca dictionarul contine cheia) sau null
TValoare Dictionar::cauta(TCheie c) const {
	/* de adaugat */
	/* O(n) */
	for (int i = 0; i < this->len; i++) {
		if ((this->dict[i]).first == c) {
			return (this->dict[i]).second;
		}
	}
	return NULL_TVALOARE;
}


TValoare Dictionar::sterge(TCheie c) {
	/* de adaugat */
	/* Theta(n) */
	for (int i = 0; i < this->len; i++) {
		if ((this->dict[i]).first == c) {
			TValoare t = (this->dict[i]).second;
			for (int j = i + 1; j < this->len; j++) {
				this->dict[j-1] = this->dict[j];
			}
			this->len --;
			return t;
		}
	}
	return NULL_TVALOARE;
}


int Dictionar::dim() const {
	/* de adaugat */
	/* Theta(1) */
	return this->len;
}

bool Dictionar::vid() const {
	/* de adaugat */
	return this->len==0;
}


IteratorDictionar Dictionar::iterator() const {
	return  IteratorDictionar(*this);
}

