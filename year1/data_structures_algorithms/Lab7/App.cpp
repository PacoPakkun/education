#include <iostream>
#include <assert.h>
#include "TestExtins.h"
#include "TestScurt.h"
#include "Multime.h"

using namespace std;

void test() {
	Multime m;
	assert(m.diferentaMaxMin() == -1);
	m.adauga(2);
	m.adauga(10);
	m.adauga(5);
	m.adauga(1);
	assert(m.diferentaMaxMin() == 9);
}

int main() {

	test();
	testAll();
	testAllExtins();

	cout<<"End";
}
