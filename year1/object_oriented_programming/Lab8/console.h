#pragma once
#include <iostream>
#include <fstream>
#include <string>
#include "service.h"

using std::ofstream;

class UI {
	/*
		*clasa abstracta, reprezinta interfata cu utilizatorul
	*/

private:
	/*
		*campurile si metodele private ale clasei service
		*srv: entitate de tip service
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	Service& srv;

	/*
		*afiseaza contractul de studii curent
	*/
	void show(const vector<Disciplina>& repo) const;

	/*
		*citeste si adauga o disciplina in contract
		*nume: string nevid
		*ore: numar natural nenul
		*tip: string nevid (obligatorie/optionala/facultativa)
		*prof: string nevid
	*/
	void addUI();

	/*
		*citeste un nume si cauta o disciplina dupa nume
		*nume: string nevid
	*/
	void findUI() const;

	/*
		*citeste un nume si elimina o disciplina dupa nume
		*nume: string nevid
	*/
	void delUI();

	/*
		*citeste si modifica o disciplina
		*nume: string nevid
		*ore: numar natural nenul
		*tip: string nevid (obligatorie/optionala/facultativa)
		*prof: string nevid
	*/
	void updateUI();

	/*
		*citeste criteriul de sortare
		*afiseaza contractul de studii curent, sortat dupa criteriu
	*/
	void sortUI() const;

	/*
		*citeste criteriul de filtrare
		*afiseaza contractul de studii curent, filtrat dupa criteriu
	*/
	void filterUI() const;

	/*
		*citeste un nr
		*genereaza un contract de studiu pe un an cu nr discipline aleatorii
	*/
	void generateContractUI();

	/*
		*citeste un nume
		*adauga o disciplina in contract dupa nume
	*/
	void addContractUI();

	/*
		*citeste un nume
		*elimina o disciplina din contract dupa nume
	*/
	void deleteContractUI();

	/*
		*citeste un nume fisier CSV
		*exporta contractul in fisier
	*/
	void exportCSVUI(const vector<Disciplina>& repo) const;

public:
	/*
		*metodele publice ale clasei service
		*constructori
		*rulare interfata
	*/

	UI(Service& srv) noexcept : srv{ srv } {}

	UI() = default;

	/*
		*ruleaza interfata
	*/
	void run();
};
