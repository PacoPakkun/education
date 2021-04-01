#pragma once
#include <fstream>
#include <random>
#include <QtGui>
#include <QtWidgets>
#include <qobject.h>
#include "service.h"
#include "observer.h"

/*
	*clasa abstracta, reprezinta interfata cu utilizatorul
	*interfata de tip formular, mostenita din biblioteca Qt
*/
class GUI : public QWidget, public Observer {
private:
	/*
		*campurile si metodele private ale clasei GUI
		*srv: entitate de tip service
		*obs: observabil care gestioneaza sincronizarea cu celelalte ferestre
		*label/list/button/..: elemente variabile in cadrul interfetei
	*/

	Service& srv;

	Observable& obs;

	/*
		*metoda privata care initializeaza interfata grafica
	*/
	void initGUI();

	/*
		*metoda privata care initializeaza interactiunile posibile
	*/
	void initConnect();

	/*
		reincarca elementele necesare atunci cand este apelata sincronizarea observerelor	
	*/
	void update() override;

	/*
		*metode private corespunzatoare diferitelor conexiuni
	*/
	void connectList();
	void connectAdd();
	void connectUpd();
	void connectDel();
	void connectSort();
	void connectFilter();
	void connectUndo();
	void connectGenerate();
	void connectAddC();
	void connectDelC();
	void connectExport();
	void connectWindows();

	QHBoxLayout* layout;
	QFont* f;
	QWidget* tab2;
	QListWidget* list;
	QPushButton* buttonAdd;
	QPushButton* buttonUpd;
	QPushButton* buttonDel;
	QPushButton* buttonUndo;
	QComboBox* sort;
	QComboBox* filter;
	QLineEdit* criteriu;
	QLineEdit* nume;
	QSpinBox* ore;
	QLineEdit* tip;
	QLineEdit* prof;
	QVBoxLayout* butt;
	QTableWidget* table;
	QPushButton* buttonGenerate;
	QPushButton* buttonAddC;
	QPushButton* buttonDelC;
	QPushButton* buttonExport;

public:
	/*
		*metodele publice ale clasei GUI
		*constructor & destructor
	*/

	GUI(Service& srv, Observable& obs) :srv{ srv }, obs{ obs } {
		setMinimumHeight(420);
		setMinimumWidth(720);
		setWindowTitle("Manager Contract de Studii");
		setWindowIcon(QIcon("icon.ico"));

		QPalette pal = palette();
		pal.setColor(QPalette::Background, QColor("#edece6"));
		this->setAutoFillBackground(true);
		this->setPalette(pal);

		f = new QFont("Arial", 10);
		this->setFont(*f);

		initGUI();
		initConnect();
	}

	~GUI() {
		obs.delObserver(this);
	}
};

/*
	*clasa de tip fereastra, reprezinta interfata CRUD a cosului
*/
class WindowCRUD :public QWidget, public Observer {
private:
	/*
		*campurile si metodele private ale ferestrei
		*srv: entitate de tip service
		*obs: observabil care gestioneaza sincronizarea cu celelalte ferestre
		*tabel/button/..: elemente variabile in cadrul interfetei
	*/
	Service& srv;
	Observable& obs;
	QFont* f;
	QTableWidget* tabel;
	QPushButton* buttGenerate;
	QPushButton* buttAddC;
	QPushButton* buttDelC;
	QPushButton* buttExport;

	/*
		*metoda privata care initializeaza interfata grafica
	*/
	void initLayout();

	/*
		*metoda privata care initializeaza interactiunile posibile
	*/
	void initConnect();

	/*
		*reincarca elementele necesare atunci cand este apelata sincronizarea observerelor
	*/
	void update() override;

	public:
		/*
			*constructor & destructor
		*/
		WindowCRUD(Service& srv, Observable& obs) :srv{ srv }, obs{ obs } {
			initLayout();
			initConnect();
		}

		~WindowCRUD() {
			obs.delObserver(this);
		}
};

/*
	*clasa de tip fereastra, reprezinta interfata grafica ReadOnly a cosului
*/
class WindowReadOnly :public QWidget, public Observer {
private:
	Service& srv;
	Observable& obs;

	/*
		*reincarca elementele necesare atunci cand este apelata sincronizarea observerelor
	*/
	void update() override;

	void paintEvent(QPaintEvent* ev) override;
	//void mouseMoveEvent(QMouseEvent* ev) override;

public:
	/*
		*constructor si destructor
	*/
	WindowReadOnly(Service& srv, Observable& obs) :srv{ srv }, obs{ obs } {
		obs.addObserver(this);
		setMouseTracking(true);
	}

	~WindowReadOnly() {
		obs.delObserver(this);
	}
};