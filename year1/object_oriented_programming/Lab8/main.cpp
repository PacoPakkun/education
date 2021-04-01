#include "test.h"
#include "console.h"

int main() {
	Test t;
	t.runAllTests();
	Repo repo;
	Repo contract;
	Service srv{ repo, contract };
	UI ui{ srv };
	ui.run();
	return 0;
}