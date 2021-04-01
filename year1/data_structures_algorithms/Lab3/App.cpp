#include <iostream>
#include <assert.h>
#include "Colectie.h"
#include "TestExtins.h"
#include "TestScurt.h"

using namespace std;

void testNou() {
	Colectie c;
	c.adauga(5);
	c.adauga(6);
	c.adauga(0);
	c.adauga(5);
	c.adauga(10);
	c.adauga(8);
	c.adaugaAparitiiMultiple(20, 6);
	assert(c.dim() == 26);
	assert(c.nrAparitii(6) == 21);
}

int main() {
	testNou();
	testAllExtins();
	testAll();
}