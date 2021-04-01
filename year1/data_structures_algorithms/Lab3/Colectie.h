#pragma once
#include <utility>
#include "IteratorColectie.h"

typedef int TElem;
typedef int frecventa;
typedef std::pair<TElem, frecventa> Pereche;

typedef bool(*Relatie)(TElem, TElem);

struct Node {
	Pereche val;
	Node* urm;
	Node(Pereche v) :val{ v }, urm{ nullptr } {};
	Node(Pereche v, Node* urm) :val{ v }, urm{ urm } {};
};

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorColectie;

class Colectie {

	friend class IteratorColectie;

private:
	/* aici e reprezentarea */
	Node* cap;
	int n;

public:
	//constructorul implicit
	Colectie();

	//adauga un element in colectie
	void adauga(TElem e);

	void adaugaAparitiiMultiple(int nr, TElem elem);

	//sterge o aparitie a unui element din colectie
	//returneaza adevarat daca s-a putut sterge
	bool sterge(TElem e);

	//verifica daca un element se afla in colectie
	bool cauta(TElem elem) const;

	//returneaza numar de aparitii ale unui element in colectie
	int nrAparitii(TElem elem) const;


	//intoarce numarul de elemente din colectie;
	int dim() const;

	//verifica daca colectia e vida;
	bool vida() const;

	//returneaza un iterator pe colectie
	IteratorColectie iterator() const;

	// destructorul colectiei
	~Colectie();


};
