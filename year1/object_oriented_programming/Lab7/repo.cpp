#include "repo.h"
using std::exception;

const Vector<Disciplina>& Repo::getRepo() const noexcept {
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
		}
	}
	throw RepoException("[Disciplina inexistenta]");}

void Repo::deleteRepo(string nume) {
	int i = 0;
	for (const Disciplina& disc : this->repo) {
		if (disc.getNume() == nume) {
			this->repo.erase(i);
			return;
		}
		i++;
	}
	throw RepoException("[Disciplina inexistenta]");}

void Repo::updateRepo(const Disciplina& d) {
	for (int i = 0; i < this->repo.size(); i++) {
		if (this->repo.get(i).getNume() == d.getNume()) {
			this->repo.set(i, d);
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}