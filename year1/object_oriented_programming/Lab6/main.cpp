#include "test.h"
#include "console.h"

int main() {
	Test t;
	t.runAllTests();
	Repo repo;
	Service srv{ repo };
	UI ui{ srv };
	ui.run();
	return 0;
}