#include <iostream>
#include <assert.h>
#include "TestExtins.h"
#include "TestScurt.h"
#include "MD.h"

using namespace std;

void testNou() {
	MD m;
	m.adauga(1, 100);
	m.adauga(2, 200);
	m.adauga(3, 300);
	m.adauga(1, 500);
	m.adauga(2, 600);
	m.adauga(4, 800);

	vector<TValoare> v = m.colectiaValorilor();
	assert(v.size() == 6);
	assert(v[0] == 100);
	assert(v[1] == 200);
	assert(v[2] == 300);
	assert(v[3] == 500);
	assert(v[4] == 600);
	assert(v[5] == 800);
}

int main() {

	testNou();
	testAll();
	testAllExtins();

	cout << "End";

}