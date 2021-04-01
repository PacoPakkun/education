#pragma once

#include "disciplina.h"
#include "repo.h"

class Undo {
	/*
		*clasa virtuala, reprezinta functionalitatea de undo
		*include 3 subclase corespunzatoare tipurilor de undo
	*/
public:
	virtual void doUndo(int& x) = 0;
	
	virtual ~Undo() = default;
};

class AddUndo :public Undo {
	/*
		*subclasa undo
		*reface ultima operatie de adaugare
	*/
private:
	Repo& repo;
	Disciplina d;
public:
	AddUndo(Repo& r, Disciplina d) :repo{ r }, d{ d }{}

	void doUndo(int& x) override {
		repo.deleteRepo(d.getNume());
	}
};

class DelUndo :public Undo {
	/*
		*subclasa undo
		*reface ultima operatie de stergere
	*/
private:
	Repo& repo;
	Disciplina d;
public:
	DelUndo(Repo& r, Disciplina d) :repo{ r }, d{ d }{}

	void doUndo(int& x) override {
		repo.addRepo(d);
	}
};

class UpdateUndo :public Undo {
	/*
		*subclasa undo
		*reface ultima operatie de modificare
	*/
private:
	Repo& repo;
	Disciplina d;
public:
	UpdateUndo(Repo& r, Disciplina d) :repo{ r }, d{ d }{}

	void doUndo(int& x) override {
		repo.updateRepo(d);
	}
};