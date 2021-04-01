#include "IteratorMD.h"
#include "MD.h"

using namespace std;

IteratorMD::IteratorMD(const MD& _md) : md(_md) {
	/* Theta(1) */
	curent = md.prim;
}

TElem IteratorMD::element() const {
	/* Theta(1) */
	if (valid()) {
		return md.list[curent];
	}
	else {
		throw "";
	}
}

bool IteratorMD::valid() const {
	/* Theta(1) */
	return curent != -1;
}

void IteratorMD::urmator() {
	/* Theta(1) */
	curent = md.urm[curent];
}

void IteratorMD::prim() {
	/* Theta(1) */
	curent = md.prim;
}
