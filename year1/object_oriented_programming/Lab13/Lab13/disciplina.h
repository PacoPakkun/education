#pragma once

#include <string>
#include <iostream>

using std::string;
using std::cout;

class Disciplina {
	/*
		*clasa abstracta, reprezinta o entitate de tip disciplina
	*/

private:
	/*
		*campurile private ale clasei disciplina
		*nume: string nenul
		*ore: numar natural nenul
		*tip: string nenul (obligatorie/optionala/facultativa)
		*prof: string nenul
	*/

	string nume;
	int ore;
	string tip;
	string prof;

public:
	/*
		*metodele publice ale clasei disciplina
		*constructori
		*getteri si setteri pt campurile private
	*/

	Disciplina() : nume{ "" }, ore{ 0 }, tip{ "" }, prof{ "" } {}

	Disciplina(string nume, int ore, string tip, string prof) : nume{ nume }, ore{ ore }, tip{ tip }, prof{ prof } {}

	Disciplina(const Disciplina& d) : nume{ d.nume }, ore{ d.ore }, tip{ d.tip }, prof{ d.prof } {
	}

	void operator=(const Disciplina& d) {
		this->nume = d.nume;
		this->ore = d.ore;
		this->tip = d.tip;
		this->prof = d.prof;
	}

	string getNume() const;

	int getOre() const noexcept;

	string getTip() const;

	string getProf() const;

	void setNume(string n);

	void setOre(int o) noexcept;

	void setTip(string t);

	void setProf(string p);
};