#include "IteratorDictionar.h"
#include "Dictionar.h"

using namespace std;

IteratorDictionar::IteratorDictionar(const Dictionar& d) : dict(d) {
	/* de adaugat */
	this->index = 0;
}


void IteratorDictionar::prim() {
	/* de adaugat */
	this->index = 0;
}


void IteratorDictionar::urmator() {
	/* de adaugat */
	if (valid()) {
		this->index++;
	}
	else {
		throw;
	}
}


TElem IteratorDictionar::element() const {
	/* de adaugat */
	if (valid()) {
		return (this->dict).dict[this->index];
	}
	else { 
		return pair <TCheie, TValoare>(-1, -1); 
	}
}


bool IteratorDictionar::valid() const {
	/* de adaugat */
	return (this->index)<((this->dict).len);
}
