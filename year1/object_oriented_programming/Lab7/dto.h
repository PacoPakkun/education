#pragma once
#include <vector>
#include <string>
#include "template.h"
#include "disciplina.h"

using std::vector;
using std::string;

class DisciplinaDTO {
	/*
		*tip abstract de date, reprezinta o entitate de tip data to object
		*retine numele si numarul disciplinelor de un anumit tip
	*/

private:
	/*
		*campurile private ale clasei dto
		*tip: string nevid
		*listaNume: vector de stringuri
		*count: nr nat
	*/

	string tip;

	vector<string> listaNume;

	int count;

public:
	/*
		*metodele publice ale clasei dto
		*constructori, getteri
	*/

	DisciplinaDTO() = default;
	
	DisciplinaDTO(const Vector<Disciplina>& v, string tip) : tip{ tip }, count{ 0 }{
		for (const Disciplina& d : v) {
			if (d.getTip() == tip) {
				listaNume.push_back(d.getNume());
				count++;
			}
		}
	}

	const vector<string> getList() const {
		return this->listaNume;
	}

	string getTip() const {
		return this->tip;
	}

	int getCount() const{
		return this->count;
	}
};