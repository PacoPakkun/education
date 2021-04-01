#pragma once
#include <vector>
#include "disciplina.h"

using std::vector;

class Repo {
	/*
		*clasa abstracta, reprezinta un repozitoriu de discipline
	*/

private:
	/*
		*campurile private ale clasei repo
		*repo: vector de discipline
		*len: int
	*/

	vector<Disciplina> repo;

public:
	/*
		*metodele publice ale clasei repo
		*constructor
		*getteri si setteri pt campurile private
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	Repo() = default;

	Repo(const Repo& r) = delete;

	const vector<Disciplina>& getRepo() const noexcept;

	/*
		*adauga o disciplina in repozitoriu
		*d: disciplina
		*arunca exceptia "[Disciplina existenta]" daca disciplina exista deja
	*/
	void addRepo(const Disciplina& d);

	/*
		*cauta o disciplina in repozitoriu
		*nume: string nevid
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
	*/
	const Disciplina& findRepo(string nume) const;

	/*
		*elimina o disciplina din repozitoriu
		*nume: string nevid
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
	*/
	void deleteRepo(string nume);

	/*
		*modifica o disciplina din repozitoriu
		*d: disciplina
		*arunca exceptia "[Disciplina inexistenta]" daca disciplina nu a fost gasita
	*/
	void updateRepo(const Disciplina& d);
};

class RepoException {
	/*
		*clasa abstracta, reprezinta o exceptie
	*/

private:
	/*
		*campurile private ale clasei exception
		*msg: string
	*/

	string msg;

public:
	/*
		*metodele publice ale clasei exception
		*constructor
		*getter
	*/

	RepoException(string msg) :msg{ msg } {}

	string getMsg() const {
		return this->msg;
	}
};
