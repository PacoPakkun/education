#include "service.h"

const Vector<Disciplina>& Service::getRepo() const noexcept{
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

Vector<Disciplina> Service::generalSort (bool(*maiMicF)(const Disciplina&, const Disciplina&)) const {
	Vector<Disciplina> v{ this->repo.getRepo() };
	for (int i = 0; i < v.size(); i++) {
		for (int j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v.get(i), v.get(j))) {
				Disciplina aux;
				aux = v.get(i);
				v.set(i, v.get(j));
				v.set(j, aux);
			}
		}
	}
	return v;
}

Vector<Disciplina> Service::sortByNume() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) {
		return d1.getNume() < d2.getNume(); 
	});
}

Vector<Disciplina> Service::sortByOre() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) noexcept {
		return d1.getOre() < d2.getOre();
	});
}

Vector<Disciplina>  Service::sortByProfTip() const {
	return generalSort([](const Disciplina& d1, const Disciplina& d2) {
		if (d1.getProf() == d2.getProf()) {
			return d1.getTip() < d2.getTip();
		}
		return d1.getProf() < d2.getProf();
	});
}

Vector<Disciplina> Service::filter(function <bool(const Disciplina&)> fct) const {
	Vector<Disciplina> f;
	for (const auto& d : this->repo.getRepo()) {
		if (fct(d)) {
			f.push_back(d);
		}
	}
	return f;
}

Vector<Disciplina> Service::filterByOre(int ore) const {
	if (ore <= 0)
		throw RepoException("[Nr ore invalid]");
	return filter([ore](const Disciplina& d) noexcept {
		return d.getOre() >= ore;
	});
}

Vector<Disciplina> Service::filterByProf(string prof) const {
	if (prof == "")
		throw RepoException("[Prof invalid]");
	return filter([prof](const Disciplina& d) {
		return d.getProf() == prof;
	});
}

vector<DisciplinaDTO> Service::mapDTO() {
	map<string, DisciplinaDTO> m;
	m.insert(pair<string, DisciplinaDTO>("obligatorie", DisciplinaDTO{ getRepo(),"obligatorie" }));
	m.insert(pair<string, DisciplinaDTO>("optionala", DisciplinaDTO{ getRepo(),"optionala" }));
	m.insert(pair<string, DisciplinaDTO>("facultativa", DisciplinaDTO{ getRepo(),"facultativa" }));
	vector<DisciplinaDTO> v;
	v.push_back(m.find("obligatorie")->second);
	v.push_back(m.find("optionala")->second);
	v.push_back(m.find("facultativa")->second);
	return v;
}