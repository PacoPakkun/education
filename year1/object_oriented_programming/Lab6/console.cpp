#include <iostream>
#include <string>
#include "console.h"

using std::cout;
using std::cin;

void UI::show(const vector<Disciplina>& repo) const {
	cout << "Discipline:\n";
	for (const auto& d : repo) {
		cout << ' ' << d.getNume() << ' ' << d.getOre() << ' ' << d.getTip() << ' ' << d.getProf() << '\n';
	}
	cout << "Sfarsit lista\n";
}

void UI::addUI() {
	string nume, tip, prof;
	int ore;
	cout << "Dati nume:";
	cin >> nume;
	cout << "Dati nr ore/sapt:";
	cin >> ore;
	cout << "Dati tip:";
	cin >> tip;
	cout << "Dati prof:";
	cin >> prof;
	this->srv.add(nume, ore, tip, prof);
	cout << "Adaugat cu succes\n";
}

void UI::findUI() const {
	cout << "Dati nume:";
	string nume;
	cin >> nume;
	const Disciplina& d = this->srv.find(nume);
	cout << ' ' << d.getNume() << ' ' << d.getOre() << ' ' << d.getTip() << ' ' << d.getProf() << '\n';
}

void UI::delUI() {
	cout << "Dati nume:";
	string nume;
	cin >> nume;
	this->srv.del(nume);
}

void UI::updateUI() {
	string nume, tip, prof;
	int ore;
	cout << "Dati nume:";
	cin >> nume;
	cout << "Dati nr ore/sapt:";
	cin >> ore;
	cout << "Dati tip:";
	cin >> tip;
	cout << "Dati prof:";
	cin >> prof;
	this->srv.update(nume, ore, tip, prof);
}

void UI::sortUI() const {
	cout << "Submeniu\n1 nume\n2 ore\n3 prof + tip\nAlegeti criteriul:";
	int cmd;
	cin >> cmd;
	cout << "\n";
	switch (cmd) {
	case 1:
		show(this->srv.sortByNume());
		break;
	case 2:
		show(this->srv.sortByOre());
		break;
	case 3:
		show(this->srv.sortByProfTip());
		break;
	default:
		cout << "Comanda invalida\n";
	}
}

void UI::filterUI() const {
	cout << "Submeniu:\n1 ore\n2 prof\nAlegeti criteriul:";
	int cmd = 0, ore = 0;
	string prof;
	cin >> cmd;
	switch (cmd) {
	case 1:
		cout << "Dati nr ore:";
		cin >> ore;
		cout << "\n";
		show(this->srv.filterByOre(ore));
		break;
	case 2:
		cout << "Dati prof:";
		cin >> prof;
		cout << "\n";
		show(this->srv.filterByProf(prof));
		break;
	default:
		cout << "Comanda invalida\n";
	}
}

void UI::run() {
	while (true) {
		cout << "Meniu:\n";
		cout << "1 adauga\n2 tipareste\n3 cauta\n4 sterge\n5 modifica\n6 sortare\n7 filtrare\n0 Iesire\nDati comanda:";
		int cmd;
		cin >> cmd;
		cout << "\n";
		try {
			switch (cmd) {
			case 1:
				addUI();
				cout << "\n";
				break;
			case 2:
				show(this->srv.getRepo());
				cout << "\n";
				break;
			case 3:
				findUI();
				cout << "\n";
				break;
			case 4:
				delUI();
				cout << "\n";
				break;
			case 5:
				updateUI();
				cout << "\n";
				break;
			case 6:
				sortUI();
				cout << "\n";
				break;
			case 7:
				filterUI();
				cout << "\n";
				break;
			case 8:
				break;
			case 0:
				return;
			default:
				cout << "Comanda invalida\n";
			}
		}
		catch (const RepoException& ex) {
			cout << ex.getMsg() << "\n";
		}
	}
}