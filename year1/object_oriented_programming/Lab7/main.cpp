#define _CRTDBG_MAP_ALLOC
#include <crtdbg.h>
#include "test.h"
#include "console.h"

void run() {
	Test t;
	t.runAllTests();
	Repo repo;
	Service srv{ repo };
	UI ui{ srv };
	ui.run();
}

int main() {
	run();
	_CrtDumpMemoryLeaks();
	return 0;
}