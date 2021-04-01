#include "test.h"

void Test::testDisciplina() const {
	_CrtDumpMemoryLeaks();
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

	Disciplina d2{ d };

	assert(d2.getNume() == "SO");
	assert(d2.getOre() == 3);
	assert(d2.getTip() == "facultativa");
	assert(d2.getProf() == "Boian");
}

void Test::testRepo() const {
	_CrtDumpMemoryLeaks();
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
	_CrtDumpMemoryLeaks();
	Repo repo;
	Service srv{ repo };

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

	srv.add("Grafuri", 2, "optionala", "Suciu");
	Vector<Disciplina> v = srv.sortByNume();
	assert(v.get(0).getNume() == "Grafuri");
	assert(v.get(1).getNume() == "OOP");
	v = srv.sortByOre();
	assert(v.get(0).getOre() == 2);
	assert(v.get(1).getOre() == 3);
	srv.add("mate", 2, "optionala", "Boian");
	v = srv.sortByProfTip();
	assert(v.get(0).getProf() == "Boian");
	assert(v.get(1).getProf() == "Suciu");
	assert(v.get(2).getProf() == "Suciu");
	assert(v.get(0).getTip() == "optionala");
	assert(v.get(1).getTip() == "facultativa");
	assert(v.get(2).getTip() == "optionala");

	v = srv.filterByOre(3);
	assert(v.size() == 1);
	assert(v.get(0).getOre() == 3);
	v = srv.filterByProf("Suciu");
	assert(v.size() == 2);
	assert(v.get(0).getProf() == "Suciu");
	assert(v.get(1).getProf() == "Suciu");
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

	vector<DisciplinaDTO> dto = srv.mapDTO();
	assert(dto.at(0).getTip() == "obligatorie");
	assert(dto.at(1).getTip() == "optionala");
	assert(dto.at(2).getTip() == "facultativa");
	assert(dto.at(0).getCount() == 0);
	assert(dto.at(1).getCount() == 2);
	assert(dto.at(2).getCount() == 1);

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

void Test::testTemplate() const {
	Vector<Disciplina> t;
	assert(t.size() == 0);

	t.push_back(Disciplina{ "OOP", 2, "obligatorie", "Istvan" });
	assert(t.size() == 1);
	assert(t.get(0).getNume() == "OOP");

	t.push_back(Disciplina{ "Graf", 3, "obligatorie", "Suciu" });
	assert(t.size() == 2);
	assert(t.get(1).getNume() == "Graf");

	t.set(1, Disciplina{ "SO", 3, "obligatorie", "Boian" });
	assert(t.get(1).getNume() == "SO");

	Vector<Disciplina> t2{ t };
	assert(t2.size() == 2);
	assert(t2.get(0).getNume() == "OOP");
	assert(t2.get(1).getNume() == "SO");

	Vector<Disciplina> t3;
	t3 = t;
	assert(t3.size() == 2);
	assert(t3.get(0).getNume() == "OOP");
	assert(t3.get(1).getNume() == "SO");

	t.erase(0);
	assert(t.size() == 1);
	assert(t.get(0).getNume() == "SO");
}

void Test::testDTO() const {
	Repo repo;
	Service srv{ repo };

	srv.add("OOP", 2, "obligatorie", "Istvan");
	srv.add("Graf", 3, "obligatorie", "Suciu");
	DisciplinaDTO dto{ srv.getRepo(), "obligatorie" };
	
	assert(dto.getCount() == 2);
	assert(dto.getTip() == "obligatorie");
	assert(dto.getList().at(0) == "OOP");
	assert(dto.getList().at(1) == "Graf");
}

void Test::runAllTests() const {
	testDisciplina();
	testRepo();
	testService();
	testTemplate();
	testDTO();
}