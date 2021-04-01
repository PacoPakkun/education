
#include "TestExtins.h"
#include "TestScurt.h"
#include "Multime.h"
#include "IteratorMultime.h"
#include <assert.h>
#include <iostream>
using namespace std;

void testNou() {
	Multime m;
	assert(m.adauga(5) == true);
	assert(m.adauga(1) == true);
	assert(m.adauga(10) == true);
	assert(m.adauga(7) == true);
	assert(m.adauga(-3) == true);

	IteratorMultime im = m.iterator();
	im.prim();
	while (im.valid()) {
		im.urmator();
	}
	im.anterior();
	int s = 0;
	while (im.valid()) {
		auto e = im.element();
		s += e;
		im.anterior();
	}
	assert(s == 20);
}

int main() {

	testAll();
	testAllExtins();
	testNou();

	cout << "That's all!" << endl;

}
