#include "IteratorMultime.h"
#include "Multime.h"

void IteratorMultime::init(Nod* curent) {
	if (curent->st == nullptr && curent->dr == nullptr) {
		s.push(curent->elem);
	}
	else {
		if (curent->dr != nullptr)
			init(curent->dr);
		s.push(curent->elem);
		if (curent->st != nullptr)
			init(curent->st);
	}
}

IteratorMultime::IteratorMultime(const Multime& m): mult(m) {
	//Theta(n)
	if (mult.root != nullptr)
		init(mult.root);
}

TElem IteratorMultime::element() const {
	//Theta(1)
	auto el = s.top();
	return el;
}

bool IteratorMultime::valid() const {
	//Theta(1)
	return !s.empty();
}

void IteratorMultime::urmator() {
	//Theta(1)
	s.pop();
}

void IteratorMultime::prim() {
	//Theta(n)
	while (!s.empty())
		s.pop();
	if (mult.root != nullptr)
		init(mult.root);
}

