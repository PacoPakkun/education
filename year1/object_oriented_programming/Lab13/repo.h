#pragma once
#include <vector>
#include <map>
#include <algorithm>
#include <numeric>
#include <fstream>
#include "disciplina.h"

using std::vector;
using std::map;
using std::pair;

class Repo {
public:

	virtual vector<Disciplina> getRepo() = 0;

	virtual int getSize() = 0;

	virtual void addRepo(const Disciplina & d) = 0;

	virtual const Disciplina& findRepo(string nume) const = 0;

	virtual void deleteRepo(string nume) = 0;

	virtual void updateRepo(const Disciplina & d) = 0;
};

class RepoStandard:public Repo {
	/*
		*clasa abstracta, reprezinta un repozitoriu de discipline
	*/

private:
	/*
		*campurile private ale clasei repo
		*repo: vector de discipline
		*len: int
	*/

	map<int, Disciplina> repo;

public:
	/*
		*metodele publice ale clasei repo
		*constructor
		*getteri si setteri pt campurile private
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	RepoStandard() = default;

	RepoStandard(const RepoStandard& r) = delete;

	vector<Disciplina> getRepo();

	int getSize();

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

class RepoFile :public Repo {
	/*
		*clasa abstracta, reprezinta un repozitoriu de discipline
	*/

private:
	/*
		*campurile private ale clasei repo
		*repo: vector de discipline
		*len: int
	*/

	map<int, Disciplina> repo;
	std::string fName;
	void loadFromFile();
	void writeToFile();

public:
	/*
		*metodele publice ale clasei repo
		*constructor
		*getteri si setteri pt campurile private
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	RepoFile() = default;

	RepoFile(const RepoFile& r) = delete;

	RepoFile(string fileName) : fName{ fileName } {
		loadFromFile();
	}

	vector<Disciplina> getRepo();

	int getSize();

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

class RepoMonkey :public Repo {
	/*
		*clasa abstracta, reprezinta un repozitoriu de discipline
	*/

private:
	/*
		*campurile private ale clasei repo
		*repo: vector de discipline
		*len: int
	*/

	map<int, Disciplina> repo;
	float rnd;

public:
	/*
		*metodele publice ale clasei repo
		*constructor
		*getteri si setteri pt campurile private
		*adaugare, stergere, modificare, cautare, filtrare, sortare
	*/

	RepoMonkey() = default;

	RepoMonkey(float rnd) :rnd{ rnd } {}

	RepoMonkey(const RepoMonkey& r) = delete;

	vector<Disciplina> getRepo();

	int getSize();

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
