#include "service.h"

vector<Disciplina> Service::getRepo() const{
	return this->repo.getRepo();
}

vector<Disciplina> Service::getContract() const {
	return this->contract.getRepo();
}

int Service::getSize() const {
	return this->repo.getSize();
}

void Service::add(string nume, int ore, string tip, string prof) {
	if (nume == "")
		throw RepoException("Nume invalid!");
	if (ore <= 0)
		throw RepoException("Nr ore invalid!");
	if (tip != "obligatorie" && tip != "optionala" && tip != "facultativa")
		throw RepoException("Tip invalid!");
	if (prof == "")
		throw RepoException("Prof invalid!");
	Disciplina d{ nume, ore, tip, prof };
	this->undoList.push_back(make_unique<AddUndo>(repo, d));
	this->repo.addRepo(d);
}

const Disciplina& Service::find(string nume) const {
	if (nume == "")
		throw RepoException("Nume invalid!");
	return this->repo.findRepo(nume);
}

void Service::del(string nume) {
	if (nume == "")
		throw RepoException("Nume invalid!");
	this->undoList.push_back(make_unique<DelUndo>(repo, repo.findRepo(nume)));
	this->repo.deleteRepo(nume);
}

void Service::update(string nume, int ore, string tip, string prof) {
	if (nume == "")
		throw RepoException("Nume invalid!");
	if (ore <= 0)
		throw RepoException("Nr ore invalid!");
	if (tip != "obligatorie" && tip != "optionala" && tip != "facultativa")
		throw RepoException("Tip invalid!");
	if (prof == "")
		throw RepoException("Prof invalid!");
	Disciplina d{ nume, ore, tip, prof };
	this->undoList.push_back(make_unique<UpdateUndo>(repo, repo.findRepo(nume)));
	this->repo.updateRepo(d);
}

/*vector<Disciplina> Service::generalSort (bool(*maiMicF)(const Disciplina&, const Disciplina&)) const {
	vector<Disciplina> v{ this->repo.getRepo() };
	for (size_t i = 0; i < v.size(); i++) {
		for (size_t j = i + 1; j < v.size(); j++) {
			if (!maiMicF(v.at(i), v.at(j))) {
				Disciplina aux;
				aux = v.at(i);
				v.at(i) = v.at(j);
				v.at(j) = aux;
			}
		}
	}
	return v;
}*/

vector<Disciplina> Service::sortByNume() const {
	/*return generalSort(!(const Disciplina& d1, const Disciplina& d2) {
		return d1.getNume() < d2.getNume(); 
	});*/
	vector<Disciplina> v{ this->repo.getRepo() };
	std::sort(v.begin(), v.end(), [](const Disciplina& d1, const Disciplina& d2) {
		return d1.getNume() < d2.getNume();
	});
	return v;
}

vector<Disciplina> Service::sortByOre() const {
	/*return generalSort(!(const Disciplina& d1, const Disciplina& d2) noexcept {
		return d1.getOre() < d2.getOre();
	});*/
	vector<Disciplina> v{ this->repo.getRepo() };
	std::sort(v.begin(), v.end(), [](const Disciplina& d1, const Disciplina& d2) noexcept {
		return d1.getOre() < d2.getOre();
	});
	return v;
}

vector<Disciplina> Service::sortByProfTip() const {
	
	/*return generalSort(!(const Disciplina& d1, const Disciplina& d2) {
		if (d1.getProf() == d2.getProf()) {
			return d1.getTip() < d2.getTip();
		}
		return d1.getProf() < d2.getProf();
	});*/
	vector<Disciplina> v{ this->repo.getRepo() };
	std::sort(v.begin(), v.end(), [](const Disciplina& d1, const Disciplina& d2) {
		if (d1.getProf() == d2.getProf()) {
			return d1.getTip() < d2.getTip();
		}
		return d1.getProf() < d2.getProf();
	});
	return v;
}

/*vector<Disciplina> Service::filter(function <bool(const Disciplina&)> fct) const {
	vector<Disciplina> f;
	for (const auto& d : this->repo.getRepo()) {
		if (fct(d)) {
			f.push_back(d);
		}
	}
	return f;
}*/

vector<Disciplina> Service::filterByOre(int ore) const {
	/*if (ore <= 0)
		throw RepoException("Nr ore invalid!");
	return filter(ore!(const Disciplina& d) noexcept {
		return d.getOre() >= ore;
	});*/
	if (ore <= 0)
		throw RepoException("Nr ore invalid!");
	vector<Disciplina> v = this->repo.getRepo();
	vector<Disciplina> f{};
	std::copy_if(v.begin(), v.end(), std::back_inserter(f), [ore](const Disciplina& d) noexcept {
		return d.getOre() >= ore;
	});
	return f;
}

vector<Disciplina> Service::filterByProf(string prof) const {
	/*if (prof == "")
		throw RepoException("Prof invalid!");
	return filter(prof!(const Disciplina& d) {
		return d.getProf() == prof;
	});*/
	if (prof == "")
		throw RepoException("Prof invalid!");
	vector<Disciplina> v = this->repo.getRepo();
	vector<Disciplina> f;
	std::copy_if(v.begin(), v.end(), std::back_inserter(f), [prof](const Disciplina& d) {
		return d.getProf() == prof;
	});
	return f;
}

void Service::generateContract(size_t nr) {
	if (nr <= 0 || this->contract.getRepo().size()+nr > this->repo.getRepo().size()) {
		throw RepoException("Nr discipline invalid!");
	}
	while (nr > 0) {
		std::mt19937 mt{ std::random_device{}() };
		const std::uniform_int_distribution<> dist(0, this->repo.getRepo().size() - 1);
		const int rndNr = dist(mt);
		try { 
			this->contract.addRepo(this->repo.getRepo().at(rndNr)); 
			nr--;
		}
		catch (const RepoException&) {

		}
	}
}

void Service::addContract(string nume) {
	if (nume == "") {
		throw RepoException("Nume invalid!");
	}
	try {
		auto found = this->contract.findRepo(nume);
		throw RepoException("Disciplina existenta!");
	}
	catch (const RepoException&) {
		auto disc = this->repo.findRepo(nume);
		this->undoList.push_back(make_unique<AddUndo>(contract, disc));
		this->contract.addRepo(disc);
	}
}

void Service::deleteContract() {
	for (auto el : contract.getRepo()) {
		contract.deleteRepo(el.getNume());
	}
}

void Service::undo() {
	if (undoList.empty()) {
		throw RepoException("Nu exista operatii anterioare!");
	}
	else {
		undoList.back()->doUndo();
		undoList.pop_back();
	}
}