#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>

#include <stdio.h>
#include <assert.h>
#include "repo.h"
#include "tests.h"
#include "service.h"
#include "ui.h"

// Problema 5 

int main()
{
	RunAllTests();

	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	UiRun(serv);
	_CrtDumpMemoryLeaks();

	return 0;
}