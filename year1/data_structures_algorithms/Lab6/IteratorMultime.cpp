#include "IteratorMultime.h"
#include "Multime.h"


void IteratorMultime::deplasare() {
	while ((curent < multime.m) && multime.e[curent] == MAX )
		curent++;
}

IteratorMultime::IteratorMultime(const Multime& m) : multime(m){
	curent = 0;
	deplasare();
}


void IteratorMultime::prim() {
	curent = 0;
	deplasare();
}

void IteratorMultime::urmator() {
	curent++;
	deplasare();
}

void IteratorMultime::anterior() {
	curent--;
	while ((curent >= 0) && multime.e[curent] == MAX)
		curent--;
}

TElem IteratorMultime::element() const {
	return multime.e[curent];
}

bool IteratorMultime::valid() const {
	return (curent < multime.m && curent >= 0);
}
