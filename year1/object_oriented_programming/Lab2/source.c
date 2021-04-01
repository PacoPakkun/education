#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>
#include "ui.h"
#include "service.h"
#include "repo.h"
#include "test.h"

int main() {
	//punct de intrare in aplicatie
	//apeleaza testele
	//initializeaza service si interfata
	//ruleaza program
	//verifica starea memoriei alocate la finalul executiei
	run_all_tests();
	MateriePrimaRepo materiePrimaRepo = createRepo();
	Service service = createService(materiePrimaRepo);
	UI ui = createUI(service);
	run(ui);
	destroyService(&service);
	_CrtDumpMemoryLeaks();
	return 0;
}