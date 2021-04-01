#pragma once
#include <fstream>
#include <random>
#include <QtGui>
#include <QtWidgets>
#include <qobject.h>
#include "service.h"
#include "observer.h"

class TableModel :public QAbstractTableModel {
public:

	Service& srv;
	Observable& obs;
	bool mutex;

	TableModel(Service& srv, Observable& obs) :srv{ srv }, obs{ obs }, mutex{ false } {}
	TableModel(Service& srv, Observable& obs, bool mutex) :srv{ srv }, obs{ obs }, mutex{ mutex } {}

	void setMutex(bool mtx) {
		mutex = mtx;
	}

	int rowCount(const QModelIndex& parent=QModelIndex()) const override {
		return srv.getContract().size();
	}

	int columnCount(const QModelIndex& parent = QModelIndex()) const override {
		return 4;
	}

	QVariant headerData(int section, Qt::Orientation orientation, int role) const {
		if (role == Qt::DisplayRole) {
			if (orientation == Qt::Horizontal) {
				return QString("%1").arg(section);
			}
			else {
				return QString("%1").arg(section);
			}
		}
		return QVariant();
	}

	QVariant data(const QModelIndex& index, int role = Qt::DisplayRole) const override {
		if (role == Qt::DisplayRole) {
			if (index.column() == 0) {
				return QString::fromStdString(srv.getContract()[index.row()].getNume());
			}
			if (index.column() == 1) {
				return QString::number(srv.getContract()[index.row()].getOre());
			}
			if (index.column() == 2) {
				return QString::fromStdString(srv.getContract()[index.row()].getTip());
			}
			if (index.column() == 3) {
				return QString::fromStdString(srv.getContract()[index.row()].getProf());
			}
		}
		if (role == Qt::BackgroundRole) {
			if (srv.getContract()[index.row()].getTip() == "obligatorie") {
				return QBrush{ Qt::red };
			}
		}
		return QVariant{};
	}

	Qt::ItemFlags flags(const QModelIndex& index) const {
		return Qt::ItemIsSelectable | Qt::ItemIsEditable | Qt::ItemIsEnabled;
	}

	bool setData(const QModelIndex& index, const QVariant& value, int role) {
		if (mutex == false) {
			if (role == Qt::EditRole) {
				if (index.column() == 0) {
					return true;
				}
				QString tip, prof, name = this->itemData(this->index(index.row(), 0)).value(0).toString();
				int ore;
				if (index.column() == 1) {
					ore = value.toInt();
					tip = this->itemData(this->index(index.row(), 2)).value(0).toString();
					prof = this->itemData(this->index(index.row(), 3)).value(0).toString();
				}
				if (index.column() == 2) {
					ore = this->itemData(this->index(index.row(), 1)).value(0).toInt();
					tip = value.toString();
					prof = this->itemData(this->index(index.row(), 3)).value(0).toString();
				}
				if (index.column() == 3) {
					ore = this->itemData(this->index(index.row(), 1)).value(0).toInt();
					tip = this->itemData(this->index(index.row(), 2)).value(0).toString();
					prof = value.toString();
				}
				srv.update(name.QString::toStdString(), ore, tip.QString::toStdString(), prof.QString::toStdString());
				srv.updateContract(name.QString::toStdString(), ore, tip.QString::toStdString(), prof.QString::toStdString());
				obs.notify();
			}
		}
		return true;
	}
};

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
	TableModel* model;
	QTableView* table;
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