#pragma once
#include <vector>

using std::vector;

class Observer {
	/*
		clasa virtuala de tip observer
		contine metoda update
	*/
public: 
	/*
		metoda virtuala care reincarca un observer
	*/
	virtual void update() = 0;
};

class Observable {
	/*
		clasa abstracta de tip observable
		v: vector de observeri pe care ii gestioneaza
		contine metode de adaugare, stergere, respectiv notify
	*/
private:
	vector<Observer*> v;

public:
	/*
		atentioneaza toti observerii gestionati sa isi reincarce datele
	*/
	void notify() {
		for (auto el : v) {
			el->update();
		}
	}

	void addObserver(Observer* o) {
		v.push_back(o);
	}

	void delObserver(Observer* o) {
		v.erase(std::remove(v.begin(), v.end(), o));
	}
};