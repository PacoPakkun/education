#pragma once

typedef int TElem;

typedef bool(*Relatie)(TElem, TElem);

struct Nod {
	TElem elem;
	Nod* st;
	Nod* dr;
	Nod(TElem elem, Nod* st, Nod* dr) :elem{ elem }, st{ st }, dr{ dr }{}
};

//in implementarea operatiilor se va folosi functia (relatia) rel (de ex, pentru <=)
// va fi declarata in .h si implementata in .cpp ca functie externa colectiei
bool rel(TElem, TElem);

class IteratorMultime;

class Multime {

	friend class IteratorMultime;

private:
	Nod* root;
	int size;

	TElem min(Nod* curent) const;
	TElem max(Nod* curent) const;
	bool adaugaRec(Nod* curent, Nod* anterior, TElem elem);
	bool stergeRec(Nod* curent, Nod* anterior, TElem elem);
	bool cautaRec(Nod* curent, TElem elem) const;
	void dealocaRec(Nod* curent);

public:
		//constructorul implicit
		Multime();

		//adauga un element in multime
		//returneaza adevarat daca elementul a fost adaugat (nu exista deja in multime)
		bool adauga(TElem e);

		//sterge un element din multime
		//returneaza adevarat daca elementul a existat si a fost sters
		bool sterge(TElem e);

		//verifica daca un element se afla in multime
		bool cauta(TElem elem) const;


		//intoarce numarul de elemente din multime;
		int dim() const;

		//verifica daca multimea e vida;
		bool vida() const;

		int diferentaMaxMin() const;

		//returneaza un iterator pe multime
		IteratorMultime iterator() const;

		// destructorul multimii
		~Multime();

};

