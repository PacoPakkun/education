#include "repo.h"
using std::exception;

vector<Disciplina> Repo::getRepo() {
	//vector<Disciplina> v = mapToVector(this->repo);
	//return v;
	vector<Disciplina> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) {return p.second; });
	return v;
}

int Repo::getSize() {
	vector<int> v;
	std::transform(repo.begin(), repo.end(), std::back_inserter(v), [](pair<int, Disciplina> p) {return 1; });
	int sum = std::accumulate(v.begin(), v.end(), 0);
	return sum;
}

void Repo::addRepo(const Disciplina& d) {
	for (auto el : this->repo) {
		if (el.second.getNume() == d.getNume()) {
			throw RepoException("[Disciplina existenta]");
		}
	}
	this->repo.insert(pair<int, Disciplina>(this->repo.size(), d));
}

const Disciplina& Repo::findRepo(string nume) const {
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

void Repo::deleteRepo(string nume) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == nume) {
			this->repo.erase(disc.first);
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");}

void Repo::updateRepo(const Disciplina& d) {
	for (pair<int, Disciplina> disc : this->repo) {
		if (disc.second.getNume() == d.getNume()) {
			this->repo.at(disc.first) = d;
			return;
		}
	}
	throw RepoException("[Disciplina inexistenta]");
}