#pragma once
#include <functional>
#include "repo.h"

using std::function;

class Service {
	/*
		*clasa abstracta, reprezinta entitatea de service
	*/
	
private:
	/*
		*campurile si metodele private ale clasei service
		*repo: entitate de tip repo
	*/

	Repo& repo;

	/*
		*functie generala de sortare
		*primeste o functie de comparare
		*returneaza o copie sortata a repozitoriului
	*/
	vector<Disciplina> generalSort(bool(*maiMicF)(const Disciplina&, const Disciplina&)) const ;

	/*
		*functie generala de filtrare
		*primeste o functie de filtrare
		*returneaza o copie filtrata a repozitoriului
	*/
	vector<Disciplina> filter(function <bool(const Disciplina&)> fct) const ;

public:
	/*
		*metodele publice ale clasei service
		*constructori
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	Service (Repo& repo) noexcept : repo { repo }{}

	Service() = default;

	Service(Service& srv) = delete;

	const vector<Disciplina>& getRepo() const noexcept;

	/*
		*valideaza datele
		*creeaza o disciplina
		*o adauga in contract
		*nume: string nevid
		*ore: nr nat nenul
		*tip: string nevid (obligatorie/facultativa/optionala)
		*prof: string nevid
		*arunca exceptia "[Disciplina existenta]" daca disciplina exista deja
		*arunca exceptia "[Nume invalid]" daca numele nu e un string nevid
		*arunca exceptia "[Nr ore invalid]" daca ore nu e un nr nat nenul
		*arunca exceptia "[Tip invalid]" daca tipul nu e obligatorie/facultativa/optionala
		*arunca exceptia "[Prof invalid]" daca prof nu e un string nevid
	*/
	void add(string nume, int ore, string tip, string prof);


	/*
		*cauta o disciplina in contract dupa nume
		*nume: string nevid
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
		*arunca exceptia "[Nume invalid]" daca numele nu e un string nevid
	*/
	const Disciplina& find(string nume) const;

	/*
		*elimina o disciplina din contract
		*nume: string nevid
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
		*arunca exceptia "[Nume invalid]" daca numele nu e un string nevid
	*/
	void del(string nume);

	/*
		*modifica o disciplina
		*nume: string nevid
		*ore: nr nat nenul
		*tip: string nevid (obilgatorie/facultativa/optionala)
		*prof: string nevid
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
		*arunca exceptia "[Nume invalid]" daca numele nu e un string nevid
		*arunca exceptia "[Nr ore invalid]" daca ore nu e un nr nat nenul
		*arunca exceptia "[Tip invalid]" daca tipul nu e obligatorie/facultativa/optionala
		*arunca exceptia "[Prof invalid]" daca prof nu e un string nevid
	*/
	void update(string nume, int ore, string tip, string prof);

	/*
		*returneaza o copie sortata dupa nume a repozitoriului
	*/
	vector<Disciplina> sortByNume() const ;

	/*
		*returneaza o copie sortata dupa ore a repozitoriului
	*/
	vector<Disciplina> sortByOre() const ;

	/*
		*returneaza o copie sortata dupa prof si tip a repozitoriului
	*/
	vector<Disciplina> sortByProfTip() const ;

	/*
		*returneaza o copie filtrata dupa nr de ore a repozitoriului
		*ore: nr nenul
		*arunca exceptia "[Nr ore invalid]" daca ore nu e un nr nat nenul
	*/
	vector<Disciplina> filterByOre(int ore) const ;

	/*
		*returneaza o copie filtrata dupa prof a repozitoriului
		*prof: string nevid
		*arunca exceptia "[Prof invalid]" daca prof nu e un string nevid
	*/
	vector<Disciplina> filterByProf(string prof) const ;
};