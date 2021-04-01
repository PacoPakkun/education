#pragma once
#include <stack>
#include "Multime.h"

typedef int TElem;
struct Nod;

using std::stack;

class Multime;

class IteratorMultime
{
	friend class Multime;

private:

	//constructorul primeste o referinta catre Container
	//iteratorul va referi primul element din container
	IteratorMultime(const Multime& m);

	//contine o referinta catre containerul pe care il itereaza
	const Multime& mult;
	stack<TElem> s;

	void init(Nod* curent);

	/* aici e reprezentarea  specifica a iteratorului */

public:

		//reseteaza pozitia iteratorului la inceputul containerului
		void prim();

		//muta iteratorul in container
		// arunca exceptie daca iteratorul nu e valid
		void urmator();

		//verifica daca iteratorul e valid (indica un element al containerului)
		bool valid() const;

		//returneaza valoarea elementului din container referit de iterator
		//arunca exceptie daca iteratorul nu e valid
		TElem element() const;
};

