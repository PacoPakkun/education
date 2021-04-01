#pragma once
#include <assert.h>
#include "disciplina.h"
#include "repo.h"
#include "service.h"

class Test {
	/*
		*clasa abstracta, reprezinta entitatea de testare
	*/
private:
	/*
		*metodele private ale clasei test
		*teste specifice fiecarei functionalitati
	*/

	/*
		*testeaza clasa disciplina
	*/
	void testDisciplina() const;

	/*
		*testeaza clasa repo
	*/
	void testRepo() const;

	/*
		*testeaza clasa service
	*/
	void testService() const;

public:
	/*
		*metodele publice ale clasei Test
		*constructor
		*ruleaza toate testele
	*/

	Test() = default;

	void runAllTests() const;

};