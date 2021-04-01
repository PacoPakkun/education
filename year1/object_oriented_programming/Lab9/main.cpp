#include "test.h"
#include "console.h"
#include <crtdbg.h>
#include <iostream>
#define _CRTDBG_MAP_ALLOC

using std::cout;
using std::cin;

void run() {
	Test t;
	t.runAllTests();
	cout << "Alegeti tip repo:\n";
	int tip;
	cin >> tip;
	if (tip == 1) {
		RepoStandard repo;
		RepoStandard contract;
		Service srv{ repo, contract };
		UI ui{ srv };
		ui.run();
	}
	if (tip == 2) {
		RepoFile repo{ "file.txt" };
		RepoStandard contract;
		Service srv{ repo, contract };
		UI ui{ srv };
		ui.run();
	}
	if(tip==3){
		cout << "Introduceti probabilitate: ";
		float rnd;
		cin >> rnd;
		RepoMonkey repo{ rnd };
		RepoMonkey contract{ rnd };
		Service srv{ repo, contract };
		UI ui{ srv };
		ui.run();
	}
}

int main() {
	run();
	_CrtDumpMemoryLeaks();
	return 0;
}