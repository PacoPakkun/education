#pragma once

template <typename Element>
class Iterator;

template <typename Element>
class Vector {
	/*
		*clasa abstracta, reprezinta o lista de obiecte
		*implementata prin vector dinamic
	*/
	friend class Iterator<Element>;

private:
	/*
		*campurile si metodele private ale clasei template
		*list: lista alocata dinamic
	*/
	
	Element* list;
	int len;
	int cap;

public:
	/*
		*metodele publice ale clasei template
		*constructori, destructori, operatori
	*/

	Vector() :list{ new Element[1] }, len{ 0 }, cap{ 1 }{}

	Vector(const Vector& t) : list{ new Element[t.cap] }, len{ t.len }, cap{ t.cap }{
		for (int i = 0; i < this->len; i++) {
			this->list[i] = t.list[i];
		}
	}

	void operator=(const Vector& t) {
		delete[] this->list;
		this->list = new Element[t.cap];
		this->len = t.len;
		this->cap = t.cap;
		for (int i = 0; i < this->len; i++) {
			this->list[i] = t.list[i];
		}
	}

	~Vector() {
		delete[] list;
	}

	/*
		*returneaza nr obiectelor din lista
	*/
	int size() const noexcept {
		return this->len;
	}

	/*
		*adauga un obiect in lista
		*redimensioneaza daca e nevoie
	*/
	void push_back(const Element& d) {
		if (this->len == this->cap) {
			Element* nou = new Element[this->cap * 2];
			this->cap *= 2;
			for (int i = 0; i < this->len; i++) {
				nou[i] = this->list[i];
			}
			delete[] list;
			list = nou;
		}
		this->list[this->len++] = d;
	}

	/*
		*elimina un obiect din lista
		*i: nr nat < len
	*/
	void erase(int i) {
		for (int j = i + 1; j < this->len; j++) {
			this->list[j - 1] = this->list[j];
		}
		this->len--;
	}

	/*
		*returneaza obiectul de la pozitia data
		*i: nr nat < len
	*/
	const Element& get(int i) const noexcept {
		return this->list[i];
	}

	/*
		*modifica obiectul de la pozitia data
		*i: nr nat < len
	*/
	void set(int i, const Element& d) {
		this->list[i] = d;
	}

	Iterator<Element> begin() const noexcept {
		return Iterator<Element>(*this, 0);
	}

	Iterator<Element> end() const noexcept {
		return Iterator<Element>(*this, this->len);
	}
};

template <typename Element>
class Iterator {
	friend class Vector<Element>;
private:

	const Vector<Element>& v;

	int index;

public:

	Iterator(const Vector<Element>& v) :v{ v }, index{ 0 }{}

	Iterator(const Vector<Element>& v, int index) noexcept : v{ v }, index{ index } {}

	void prim() {
		this->index = 0;
	}

	void operator++() noexcept {
		this->index++;
	}

	bool operator!=(const Iterator& ot) noexcept {
		return this->index != ot.index;
	}

	/*bool valid() const {
		return this->index + 1 < this->v.len;
	}*/

	const Element& operator*() noexcept {
		return this->v.list[this->index];
	}
};