#include "repo.h"
using std::exception;

const vector<Disciplina>& Repo::getRepo() const noexcept {
	return this->repo;
}

void Repo::addRepo(const Disciplina& d) {
	for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == d.getNume()) {
			throw RepoException("[Disciplina existenta]");
		}
	}
	this->repo.push_back(d);
}

const Disciplina& Repo::findRepo(string nume) const {
	for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			return disc;
		}}
	throw RepoException("[Disciplina inexistenta]");}

void Repo::deleteRepo(string nume) {
	int i = 0;
	for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			this->repo.erase(this->repo.begin()+i);
			return;
		}
		i++;
	}
	throw RepoException("[Disciplina inexistenta]");}

void Repo::updateRepo(const Disciplina& d) {
	for (Disciplina& disc : this->repo) {
		if (disc.getNume() == d.getNume()) {
			disc = d;
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}