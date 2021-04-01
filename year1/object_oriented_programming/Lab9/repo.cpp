#include "repo.h"
using std::exception;

vector<Disciplina> RepoStandard::getRepo() {
	//vector<Disciplina> v = mapToVector(this->repo);
	//return v;
	vector<Disciplina> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) {return p.second; });
	return v;
}

int RepoStandard::getSize() {
	vector<int> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) noexcept {return 1; });
	const int sum = std::accumulate(v.begin(), v.end(), 0);
	return sum;
}

void RepoStandard::addRepo(const Disciplina& d) {
	for (auto el : this->repo) {
		if (el.second.getNume() == d.getNume()) {
			throw RepoException("[Disciplina existenta]");
		}
	}
	this->repo.insert(pair<int, Disciplina>(this->repo.size(), d));
}

const Disciplina& RepoStandard::findRepo(string nume) const {
	/*for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			return disc;
		}}
	throw RepoException("[Disciplina inexistenta]");*/
	auto disc = std::find_if(this->repo.begin(), this->repo.end(), [&](pair<int, Disciplina> disc) {return disc.second.getNume() == nume; });
	if (disc != this->repo.end()) {
		return (*disc).second;
	}
	else {
		throw RepoException("[Disciplina inexistenta]");
	}
}

void RepoStandard::deleteRepo(string nume) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == nume) {
			this->repo.erase(disc.first);
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");}

void RepoStandard::updateRepo(const Disciplina& d) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == d.getNume()) {
			this->repo.at(disc.first) = d;
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}

//

void RepoFile::loadFromFile() {
	std::ifstream in(fName);
	if (!in.is_open()) { //verify if the stream is opened		
		throw RepoException("Unable to open file:" + fName);
	}
	while (!in.eof()) {
		std::string nume;
		in >> nume;
		int ore;
		in >> ore;
		std::string tip;
		in >> tip;
		std::string prof;
		in >> prof;
		if (in.eof()) {	//nu am reusit sa citesc numarul
			break;
		}
		Disciplina d{ nume,ore,tip,prof };
		this->repo.insert(pair<int, Disciplina>(this->repo.size(), d));
	}
	in.close();
}

void RepoFile::writeToFile() {
	std::ofstream out(fName);
	if (!out.is_open()) { //verify if the stream is opened
		throw RepoException("Unable to open file:");
	}
	for (auto& p : getRepo()) {
		out << p.getNume();
		out << std::endl;
		out << p.getOre();
		out << std::endl;
		out << p.getTip();
		out << std::endl;
		out << p.getProf();
		out << std::endl;
	}
	out.close();
}

vector<Disciplina> RepoFile::getRepo() {
	//vector<Disciplina> v = mapToVector(this->repo);
	//return v;
	vector<Disciplina> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) {return p.second; });
	return v;
}

int RepoFile::getSize() {
	vector<int> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) noexcept {return 1; });
	const int sum = std::accumulate(v.begin(), v.end(), 0);
	return sum;
}

void RepoFile::addRepo(const Disciplina& d) {
	for (auto el : this->repo) {
		if (el.second.getNume() == d.getNume()) {
			throw RepoException("[Disciplina existenta]");
		}
	}
	this->repo.insert(pair<int, Disciplina>(this->repo.size(), d));
	writeToFile();
}

const Disciplina& RepoFile::findRepo(string nume) const {
	/*for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			return disc;
		}}
	throw RepoException("[Disciplina inexistenta]");*/
	auto disc = std::find_if(this->repo.begin(), this->repo.end(), [&](pair<int, Disciplina> disc) {return disc.second.getNume() == nume; });
	if (disc != this->repo.end()) {
		return (*disc).second;
	}
	else {
		throw RepoException("[Disciplina inexistenta]");
	}
}

void RepoFile::deleteRepo(string nume) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == nume) {
			this->repo.erase(disc.first);
			writeToFile();
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}

void RepoFile::updateRepo(const Disciplina& d) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == d.getNume()) {
			this->repo.at(disc.first) = d;
			writeToFile();
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}

//

vector<Disciplina> RepoMonkey::getRepo() {
	//vector<Disciplina> v = mapToVector(this->repo);
	//return v;
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	vector<Disciplina> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) {return p.second; });
	return v;
}

int RepoMonkey::getSize() {
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	vector<int> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) noexcept {return 1; });
	const int sum = std::accumulate(v.begin(), v.end(), 0);
	return sum;
}

void RepoMonkey::addRepo(const Disciplina& d) {
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	for (auto el : this->repo) {
		if (el.second.getNume() == d.getNume()) {
			throw RepoException("[Disciplina existenta]");
		}
	}
	this->repo.insert(pair<int, Disciplina>(this->repo.size(), d));
}

const Disciplina& RepoMonkey::findRepo(string nume) const {
	/*for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			return disc;
		}}
	throw RepoException("[Disciplina inexistenta]");*/
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	auto disc = std::find_if(this->repo.begin(), this->repo.end(), [&](pair<int, Disciplina> disc) {return disc.second.getNume() == nume; });
	if (disc != this->repo.end()) {
		return (*disc).second;
	}
	else {
		throw RepoException("[Disciplina inexistenta]");
	}
}

void RepoMonkey::deleteRepo(string nume) {
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == nume) {
			this->repo.erase(disc.first);
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}

void RepoMonkey::updateRepo(const Disciplina& d) {
	bool monkey = (rand() % 100) < rnd * 100;
	if (monkey == true) {
		throw RepoException{ "\nMONKY\n" };
	}
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == d.getNume()) {
			this->repo.at(disc.first) = d;
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}