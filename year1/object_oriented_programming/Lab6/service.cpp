#include "service.h"

const vector<Disciplina>& Service::getRepo() const noexcept{
	return this->repo.getRepo();
}

void Service::add(string nume, int ore, string tip, string prof) {
	if (nume == "")
		throw RepoException("[Nume invalid]");
	if (ore <= 0)
		throw RepoException("[Nr ore invalid]");
	if (tip != "obligatorie" && tip != "optionala" && tip != "facultativa")
		throw RepoException("[Tip invalid]");
	if (prof == "")
		throw RepoException("[Prof invalid]");
	Disciplina d{ nume, ore, tip, prof };
	this->repo.addRepo(d);
}

const Disciplina& Service::find(string nume) const {
	if (nume == "")
		throw RepoException("[Nume invalid]");
	return this->repo.findRepo(nume);
}

void Service::del(string nume) {
	if (nume == "")
		throw RepoException("[Nume invalid]");
	this->repo.deleteRepo(nume);
}

void Service::update(string nume, int ore, string tip, string prof) {
	if (nume == "")
		throw RepoException("[Nume invalid]");
	if (ore <= 0)
		throw RepoException("[Nr ore invalid]");
	if (tip != "obligatorie" && tip != "optionala" && tip != "facultativa")
		throw RepoException("[Tip invalid]");
	if (prof == "")
		throw RepoException("[Prof invalid]");
	Disciplina d{ nume, ore, tip, prof };
	this->repo.updateRepo(d);
}

vector<Disciplina> Service::generalSort (bool(*maiMicF)(const Disciplina&, const Disciplina&)) const {
	vector<Disciplina> v{ this->repo.getRepo() };//fac o copie	
	for (size_t i = 0; i < v.size(); i++) {
		for (size_t j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v.at(i), v.at(j))) {
				//interschimbam
				Disciplina aux;
				aux = v.at(i);
				v.at(i) = v.at(j);
				v.at(j) = aux;
			}
		}
	}
	return v;
}

vector<Disciplina> Service::sortByNume() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) {
		return d1.getNume() < d2.getNume(); 
	});
}

vector<Disciplina> Service::sortByOre() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) noexcept {
		return d1.getOre() < d2.getOre();
	});
}

vector<Disciplina> Service::sortByProfTip() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) {
		if (d1.getProf() == d2.getProf()) {
			return d1.getTip() < d2.getTip();
		}
		return d1.getProf() < d2.getProf();
	});
}

vector<Disciplina> Service::filter(function <bool(const Disciplina&)> fct) const {
	vector<Disciplina> f;
	for (const auto& d : this->repo.getRepo()) {
		if (fct(d)) {
			f.push_back(d);
		}
	}
	return f;
}

vector<Disciplina> Service::filterByOre(int ore) const {
	if (ore <= 0)
		throw RepoException("[Nr ore invalid]");
	return filter([ore](const Disciplina& d) noexcept {
		return d.getOre() >= ore;
	});
}

vector<Disciplina> Service::filterByProf(string prof) const {
	if (prof == "")
		throw RepoException("[Prof invalid]");
	return filter([prof](const Disciplina& d) {
		return d.getProf() == prof;
	});
}