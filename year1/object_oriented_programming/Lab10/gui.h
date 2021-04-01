#pragma once
#include <fstream>
#include <QtWidgets>
#include <qobject.h>
#include "service.h"

class GUI: public QWidget {
	/*
		*clasa abstracta, reprezinta interfata cu utilizatorul
		*interfata de tip formular, mostenita din biblioteca Qt
	*/
private:
	/*
		*campurile si metodele private ale clasei GUI
		*srv: entitate de tip service
		*label/list/button: elemente variabile in cadrul interfetei
	*/

	Service& srv;

	/*
		metoda privata care initializeaza interfata grafica
	*/
	void initGUI();

	/*
		metoda privata care initializeaza interactiunile posibile
	*/
	void initConnect();

	QHBoxLayout* layout;
	QFont* f;
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
		*constructor
	*/

	GUI(Service& srv) :srv{ srv } {
		setMinimumHeight(420);
		setMinimumWidth(720);
		setWindowTitle("Manager Contract de Studii");
		setWindowIcon(QIcon("icon.ico"));
		//setStyleSheet("background-color: pink;");
		f = new QFont("Arial", 10);
		this->setFont(*f);
		initGUI();
		initConnect();
	}
};