#include "test.h"

void Test::testDisciplina() const {
	Disciplina d{ "OOP", 2, "obligatorie", "Istvan" };

	assert(d.getNume() == "OOP");
	assert(d.getOre() == 2);
	assert(d.getTip() == "obligatorie");
	assert(d.getProf() == "Istvan");

	d.setNume("SO");
	d.setOre(3);
	d.setTip("facultativa");
	d.setProf("Boian");

	assert(d.getNume() == "SO");
	assert(d.getOre() == 3);
	assert(d.getTip() == "facultativa");
	assert(d.getProf() == "Boian");

	//bum
	Disciplina d2{ d };

	assert(d2.getNume() == "SO");
	assert(d2.getOre() == 3);
	assert(d2.getTip() == "facultativa");
	assert(d2.getProf() == "Boian");
}

void Test::testRepo() const {
	Repo r;
	Disciplina d{ "OOP", 2, "obligatorie", "Istvan" };

	assert(r.getRepo().size() == 0);
	try {
		r.findRepo("OOP");
		assert(false);
	}
	catch (const RepoException& ex){
		assert(ex.getMsg()=="[Disciplina inexistenta]");
	}

	//bum
	r.addRepo(Disciplina{ "OOP", 2, "obligatorie", "Istvan" });

	assert(r.getRepo().size() == 1);
	assert(r.findRepo("OOP").getNume() == "OOP");
	assert(r.findRepo("OOP").getOre() == 2);
	assert(r.findRepo("OOP").getTip() == "obligatorie");
	assert(r.findRepo("OOP").getProf() == "Istvan");
	try {
		r.addRepo(Disciplina{ "OOP", 2, "obligatorie", "Istvan" });
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Disciplina existenta]");
	}

	r.updateRepo(Disciplina{ "OOP", 3, "facultativa", "Boian" });

	assert(r.findRepo("OOP").getNume() == "OOP");
	assert(r.findRepo("OOP").getOre() == 3);
	assert(r.findRepo("OOP").getTip() == "facultativa");
	assert(r.findRepo("OOP").getProf() == "Boian");
	try {
		r.updateRepo(Disciplina{ "SO", 3, "facultativa", "Boian" });
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Disciplina inexistenta]");
	}

	r.deleteRepo("OOP");

	assert(r.getRepo().size() == 0);
	try {
		r.findRepo("OOP");
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Disciplina inexistenta]");
	}
	try {
		r.deleteRepo("OOP");
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Disciplina inexistenta]");
	}
}

void Test::testService() const {
	Repo repo;
	Service srv{ repo };

	//bum
	srv.add("OOP", 2, "optionala", "Istvan");
	assert(srv.getRepo().size() == 1);
	try {
		srv.add("", 2, "obligatorie", "Istvan" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nume invalid]");
	}
	try {
		srv.add("OOP", 0, "obligatorie", "Istvan" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nr ore invalid]");
	}
	try {
		srv.add("OOP", 2, "oblig", "Istvan" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Tip invalid]");
	}
	try {
		srv.add("OOP", 2, "obligatorie", "" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Prof invalid]");
	}

	const Disciplina& d = srv.find("OOP");
	assert(d.getNume() == "OOP");
	try {
		srv.find("");
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nume invalid]");
	}

	srv.update("OOP", 3, "facultativa", "Suciu");
	assert(srv.find("OOP").getOre() == 3);
	assert(srv.find("OOP").getTip() == "facultativa");
	assert(srv.find("OOP").getProf() == "Suciu");
	try {
		srv.update("", 3, "facultativa", "Boian" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nume invalid]");
	}
	try {
		srv.update("OOP", -1, "facultativa", "Boian" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nr ore invalid]");
	}
	try {
		srv.update("OOP", 3, "fac", "Boian" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Tip invalid]");
	}
	try {
		srv.update("OOP", 3, "facultativa", "" );
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Prof invalid]");
	}

	//2*bum
	srv.add("Grafuri", 2, "optionala", "Suciu");
	//2*bum
	vector<Disciplina> v = srv.sortByNume();
	assert(v.at(0).getNume() == "Grafuri");
	assert(v.at(1).getNume() == "OOP");
	//2*bum
	v = srv.sortByOre();
	assert(v.at(0).getOre() == 2);
	assert(v.at(1).getOre() == 3);
	//3*bum
	srv.add("mate", 2, "optionala", "Boian");
	//3*bum
	v = srv.sortByProfTip();
	assert(v.at(0).getProf() == "Boian");
	assert(v.at(1).getProf() == "Suciu");
	assert(v.at(2).getProf() == "Suciu");
	assert(v.at(0).getTip() == "optionala");
	assert(v.at(1).getTip() == "facultativa");
	assert(v.at(2).getTip() == "optionala");

	//bum
	v = srv.filterByOre(3);
	assert(v.size() == 1);
	assert(v.at(0).getOre() == 3);
	//2*bum
	v = srv.filterByProf("Suciu");
	assert(v.size() == 2);
	assert(v.at(0).getProf() == "Suciu");
	assert(v.at(1).getProf() == "Suciu");
	try {
		v = srv.filterByOre(0);
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nr ore invalid]");
	}
	try {
		v = srv.filterByProf("");
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Prof invalid]");
	}

	srv.del("Grafuri");
	assert(srv.getRepo().size() == 2);
	srv.del("OOP");
	assert(srv.getRepo().size() == 1);
	try {
		srv.del("");
		assert(false);
	}
	catch (const RepoException & ex) {
		assert(ex.getMsg() == "[Nume invalid]");
	}
}

void Test::runAllTests() const {
	testDisciplina();
	testRepo();
	testService();
}