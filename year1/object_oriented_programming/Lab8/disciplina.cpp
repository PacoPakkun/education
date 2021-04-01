#include "disciplina.h"

string Disciplina::getNume() const {
	return this->nume;
}

int Disciplina::getOre() const noexcept{
	return this->ore;
}

string Disciplina::getTip() const {
	return this->tip;
}

string Disciplina::getProf() const {
	return this->prof;
}

void Disciplina::setNume(string n) {
	this->nume = n;
}

void Disciplina::setOre(int o) noexcept {
	this->ore = o;
}

void Disciplina::setTip(string t) {
	this->tip = t;
}

void Disciplina::setProf(string p) {
	this->prof = p;
}