#include "gui.h"

//GUI

void GUI::initGUI() {

	//layout
	layout = new QHBoxLayout;
	auto tabs = new QTabWidget;
	layout->addWidget(tabs);
	this->setLayout(layout);

	//tab1
	auto tab1 = new QWidget;
	tabs->addTab(tab1, "Discipline");
	auto layout1 = new QHBoxLayout(tab1);

	auto left = new QVBoxLayout;
	list = new QListWidget;
	for (auto el : srv.getRepo()) {
		list->addItem(QString::fromStdString(el.getNume()));
	}
	left->addWidget(list);
	sort = new QComboBox;
	sort->addItem("Sort by..");
	sort->addItem("Nume");
	sort->addItem("Ore");
	sort->addItem("Prof & Tip");
	left->addWidget(sort);
	auto filt = new QHBoxLayout;
	filter = new QComboBox;
	filter->addItem("Filter by..");
	filter->addItem("Ore");
	filter->addItem("Prof");
	filt->addWidget(filter);
	criteriu = new QLineEdit;
	criteriu->setPlaceholderText("insert criteriu here..");
	filt->addWidget(criteriu);
	left->addLayout(filt);

	auto right = new QVBoxLayout;
	nume = new QLineEdit;
	nume->setPlaceholderText("insert nume here..");
	right->addWidget(nume);
	auto o = new QHBoxLayout;
	o->addWidget(new QLabel("Nr ore: "));
	ore = new QSpinBox;
	o->addWidget(ore);
	right->addLayout(o);
	tip = new QLineEdit;
	tip->setPlaceholderText("insert tip here..");
	right->addWidget(tip);
	prof = new QLineEdit;
	prof->setPlaceholderText("insert prof here..");
	right->addWidget(prof);
	buttonAdd = new QPushButton{ "Add" };
	right->addWidget(buttonAdd);
	buttonUpd = new QPushButton{ "Edit" };
	right->addWidget(buttonUpd);
	buttonDel = new QPushButton{ "Remove" };
	right->addWidget(buttonDel);
	buttonUndo = new QPushButton{ "Undo" };
	right->addWidget(buttonUndo);

	layout1->addLayout(left, Qt::AlignHCenter);
	layout1->addLayout(right, Qt::AlignHCenter);

	//tab2
	tab2 = new QWidget;
	tabs->addTab(tab2, "Contract");
	auto layout2 = new QVBoxLayout(tab2);

	table = new QTableWidget(list->count() + 5, 4);
	layout2->addWidget(table);

	auto buttons = new QHBoxLayout;
	buttonGenerate = new QPushButton("Generate");
	buttons->addWidget(buttonGenerate);
	buttonAddC = new QPushButton("Add");
	buttons->addWidget(buttonAddC);
	buttonDelC = new QPushButton("Clear");
	buttonDelC->setEnabled(false);
	buttons->addWidget(buttonDelC);
	buttonExport = new QPushButton("Export");
	buttonExport->setEnabled(false);
	buttons->addWidget(buttonExport);
	layout2->addLayout(buttons);

	//butoane
	butt = new QVBoxLayout;
	layout->addLayout(butt);
	for (auto el : srv.getRepo()) {
		auto buton = new QPushButton(QString::fromStdString(el.getNume()));
		QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
			try {
				srv.del(buton->text().QString::toStdString());
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			list->clear();
			for (auto el : srv.getRepo()) {
				list->addItem(QString::fromStdString(el.getNume()));
			}
			buton->hide();
		});
		butt->addWidget(buton);
	}
}

void GUI::initConnect() {
	obs.addObserver(this);
	connectList();
	connectAdd();
	connectUpd();
	connectSort();
	connectFilter();
	connectUndo();
	connectGenerate();
	connectAddC();
	connectDelC();
	connectExport();
	connectWindows();
}

void GUI::update(){
	table->blockSignals(true);
	table->clear();
	auto contract = srv.getContract();
	int i = 0;
	for (auto el : contract) {
		table->setItem(i, 0, new QTableWidgetItem(QString::fromStdString(el.getNume())));
		table->setItem(i, 1, new QTableWidgetItem(QString::number(el.getOre())));
		table->setItem(i, 2, new QTableWidgetItem(QString::fromStdString(el.getTip())));
		table->setItem(i, 3, new QTableWidgetItem(QString::fromStdString(el.getProf())));
		table->item(i, 0)->setData(Qt::UserRole, QString::fromStdString(el.getNume()));
		table->item(i, 1)->setData(Qt::UserRole, QString::number(el.getOre()));
		table->item(i, 2)->setData(Qt::UserRole, QString::fromStdString(el.getTip()));
		table->item(i, 3)->setData(Qt::UserRole, QString::fromStdString(el.getProf()));
		if (el.getTip() == "obligatorie") {
			table->item(i, 0)->setBackgroundColor(QColor("#ffbac1"));
			table->item(i, 1)->setBackgroundColor(QColor("#ffbac1"));
			table->item(i, 2)->setBackgroundColor(QColor("#ffbac1"));
			table->item(i, 3)->setBackgroundColor(QColor("#ffbac1"));
		}
		i++;
	}
	for (int i = 0; i < table->rowCount(); i++)
		for (int j = 0; j < table->columnCount(); j++) {
			QObject::connect(table, &QTableWidget::cellChanged, [&](int i, int j) {
				switch (j) {
				case 0:
					table->item(i, j)->setText(table->item(i, j)->data(Qt::UserRole).toString());
					break;
				default:
					try {
						srv.update(table->item(i, 0)->text().QString::toStdString(), table->item(i, 1)->text().QString::toInt(), table->item(i, 2)->text().QString::toStdString(), table->item(i, 3)->text().QString::toStdString());
						srv.updateContract(table->item(i, 0)->text().QString::toStdString(), table->item(i, 1)->text().QString::toInt(), table->item(i, 2)->text().QString::toStdString(), table->item(i, 3)->text().QString::toStdString());
						table->item(i, j)->setData(Qt::UserRole, table->item(i, j)->text());
					}
					catch (RepoException& ex) {
						QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
					}
					break;
				}
			});
		}
	if (srv.getContract().size() != 0) {
		buttonDelC->setEnabled(true);
		buttonExport->setEnabled(true);
	}
	else{
		buttonDelC->setEnabled(false);
		buttonExport->setEnabled(false);
	}
	table->blockSignals(false);
}

void GUI::connectList() {
	QObject::connect(list, &QListWidget::itemClicked, [&]() {
		auto curent = list->selectedItems().at(0)->text();
		nume->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getNume()));
		ore->setValue(srv.find(curent.QString::toStdString()).getOre());
		tip->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getTip()));
		prof->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getProf()));
	});
}

void GUI::connectAdd() {
	QObject::connect(buttonAdd, &QPushButton::clicked, [&]() {
		try {
			srv.add(nume->text().QString::toStdString(), ore->text().toInt(), tip->text().QString::toStdString(), prof->text().QString::toStdString());
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
		}
		list->clear();
		while (auto buton = butt->takeAt(0)) {
			delete buton->widget();
			delete buton;
		}
		for (auto el : srv.getRepo()) {
			list->addItem(QString::fromStdString(el.getNume()));
			auto buton = new QPushButton(QString::fromStdString(el.getNume()));
			QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
				try {
					srv.del(buton->text().QString::toStdString());
				}
				catch (RepoException& ex) {
					QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
				}
				list->clear();
				for (auto el : srv.getRepo()) {
					list->addItem(QString::fromStdString(el.getNume()));
				}
				buton->hide();
			});
			butt->addWidget(buton);
		}
	});
}

void GUI::connectUpd() {
	QObject::connect(buttonUpd, &QPushButton::clicked, [&]() {
		try {
			srv.update(nume->text().QString::toStdString(), ore->text().toInt(), tip->text().QString::toStdString(), prof->text().QString::toStdString());
			srv.updateContract(nume->text().QString::toStdString(), ore->text().toInt(), tip->text().QString::toStdString(), prof->text().QString::toStdString());
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
		}
		list->clear();
		for (auto el : srv.getRepo()) {
			list->addItem(QString::fromStdString(el.getNume()));
		}
		obs.notify();
	});
}

void GUI::connectDel() {
	QObject::connect(buttonDel, &QPushButton::clicked, [&]() {
		try {
			srv.del(nume->text().QString::toStdString());
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
		}
		list->clear();
		while (auto buton = butt->takeAt(0)) {
			delete buton->widget();
			delete buton;
		}
		for (auto el : srv.getRepo()) {
			list->addItem(QString::fromStdString(el.getNume()));
			auto buton = new QPushButton(QString::fromStdString(el.getNume()));
			QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
				try {
					srv.del(buton->text().QString::toStdString());
				}
				catch (RepoException& ex) {
					QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
				}
				list->clear();
				for (auto el : srv.getRepo()) {
					list->addItem(QString::fromStdString(el.getNume()));
				}
				buton->hide();
			});
			butt->addWidget(buton);
		}
		obs.notify();
	});
}

void GUI::connectSort() {
	QObject::connect(sort, QOverload<int>::of(&QComboBox::currentIndexChanged), [&](int index) {
		switch (index) {
		case 1:
			try {
				auto sortare = srv.sortByNume();
				list->clear();
				while (auto buton = butt->takeAt(0)) {
					delete buton->widget();
					delete buton;
				}
				for (auto el : sortare) {
					list->addItem(QString::fromStdString(el.getNume()));
					auto buton = new QPushButton(QString::fromStdString(el.getNume()));
					QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
						try {
							srv.del(buton->text().QString::toStdString());
						}
						catch (RepoException& ex) {
							QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
						}
						list->clear();
						for (auto el : srv.getRepo()) {
							list->addItem(QString::fromStdString(el.getNume()));
						}
						buton->hide();
					});
					butt->addWidget(buton);
				}
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			break;
		case 2:
			try {
				auto sortare = srv.sortByOre();
				list->clear();
				while (auto buton = butt->takeAt(0)) {
					delete buton->widget();
					delete buton;
				}
				for (auto el : sortare) {
					list->addItem(QString::fromStdString(el.getNume()));
					auto buton = new QPushButton(QString::fromStdString(el.getNume()));
					QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
						try {
							srv.del(buton->text().QString::toStdString());
						}
						catch (RepoException& ex) {
							QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
						}
						list->clear();
						for (auto el : srv.getRepo()) {
							list->addItem(QString::fromStdString(el.getNume()));
						}
						buton->hide();
					});
					butt->addWidget(buton);
				}
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			break;
		case 3:
			try {
				auto sortare = srv.sortByProfTip();
				list->clear();
				while (auto buton = butt->takeAt(0)) {
					delete buton->widget();
					delete buton;
				}
				for (auto el : sortare) {
					list->addItem(QString::fromStdString(el.getNume()));
					auto buton = new QPushButton(QString::fromStdString(el.getNume()));
					QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
						try {
							srv.del(buton->text().QString::toStdString());
						}
						catch (RepoException& ex) {
							QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
						}
						list->clear();
						for (auto el : srv.getRepo()) {
							list->addItem(QString::fromStdString(el.getNume()));
						}
						buton->hide();
					});
					butt->addWidget(buton);
				}
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			break;
		}
	});
}

void GUI::connectFilter() {
	QObject::connect(filter, QOverload<int>::of(&QComboBox::currentIndexChanged), [&](int index) {
		switch (index) {
		case 2:
			try {
				auto filtrare = srv.filterByProf(criteriu->text().QString::toStdString());
				list->clear();
				while (auto buton = butt->takeAt(0)) {
					delete buton->widget();
					delete buton;
				}
				for (auto el : filtrare) {
					list->addItem(QString::fromStdString(el.getNume()));
					auto buton = new QPushButton(QString::fromStdString(el.getNume()));
					QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
						try {
							srv.del(buton->text().QString::toStdString());
						}
						catch (RepoException& ex) {
							QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
						}
						list->clear();
						for (auto el : srv.getRepo()) {
							list->addItem(QString::fromStdString(el.getNume()));
						}
						buton->hide();
					});
					butt->addWidget(buton);
				}
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			break;
		case 1:
			try {
				auto filtrare = srv.filterByOre(criteriu->text().QString::toInt());
				list->clear();
				while (auto buton = butt->takeAt(0)) {
					delete buton->widget();
					delete buton;
				}
				for (auto el : filtrare) {
					list->addItem(QString::fromStdString(el.getNume()));
					auto buton = new QPushButton(QString::fromStdString(el.getNume()));
					QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
						try {
							srv.del(buton->text().QString::toStdString());
						}
						catch (RepoException& ex) {
							QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
						}
						list->clear();
						for (auto el : srv.getRepo()) {
							list->addItem(QString::fromStdString(el.getNume()));
						}
						buton->hide();
					});
					butt->addWidget(buton);
				}
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
			break;
		}
	});
}

void GUI::connectUndo() {
	QObject::connect(buttonUndo, &QPushButton::clicked, [&]() {
		try {
			srv.undo();
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
		}
		list->clear();
		while (auto buton = butt->takeAt(0)) {
			delete buton->widget();
			delete buton;
		}
		for (auto el : srv.getRepo()) {
			list->addItem(QString::fromStdString(el.getNume()));
			auto buton = new QPushButton(QString::fromStdString(el.getNume()));
			QObject::connect(buton, &QPushButton::clicked, [&, buton]() {
				try {
					srv.del(buton->text().QString::toStdString());
				}
				catch (RepoException& ex) {
					QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
				}
				list->clear();
				for (auto el : srv.getRepo()) {
					list->addItem(QString::fromStdString(el.getNume()));
				}
				buton->hide();
			});
			butt->addWidget(buton);
		}
		obs.notify();
	});
}

void GUI::connectGenerate() {
	QObject::connect(buttonGenerate, &QPushButton::clicked, [&]() {
		auto window = new QDialog;
		auto layout = new QVBoxLayout;
		auto text = new QLabel("Nr discipline:\n");
		text->setAlignment(Qt::AlignHCenter);
		layout->addWidget(text);
		auto dial = new QDial();
		dial->setMinimum(1);
		dial->setMaximum(list->count());
		layout->addWidget(dial);
		auto label = new QLineEdit("1");
		label->setAlignment(Qt::AlignHCenter);
		label->setEnabled(false);
		layout->addWidget(label);
		auto button = new QPushButton("Generate\nContract");
		layout->addWidget(button);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Generate Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(dial, &QDial::valueChanged, [&, label, dial]() {
			label->setText(QString::number(dial->value()));
		});
		QObject::connect(button, &QPushButton::clicked, [&, window, dial]() {
			try {
				int nr = dial->value();
				srv.generateContract(nr);
				obs.notify();
				window->close();
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});
		window->show();
	});
}

void GUI::connectAddC() {
	QObject::connect(buttonAddC, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
		auto layout = new QVBoxLayout;
		auto text = new QLineEdit;
		text->setPlaceholderText("Nume");
		layout->addWidget(text);
		auto button = new QPushButton("Add");
		layout->addWidget(button);
		auto button2 = new QPushButton("Close");
		layout->addWidget(button2);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Add to Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(button, &QPushButton::clicked, [&, text]() {
			try {
				srv.addContract(text->text().QString::toStdString());
				obs.notify();
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});
		QObject::connect(button2, &QPushButton::clicked, [&, window]() {
			window->close();
		});
		window->show();
	});
}

void GUI::connectDelC() {
	QObject::connect(buttonDelC, &QPushButton::clicked, [&]() {
		srv.deleteContract();
		obs.notify();
	});
}

void GUI::connectExport() {
	QObject::connect(buttonExport, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
		auto layout = new QVBoxLayout;
		auto text = new QLineEdit;
		text->setPlaceholderText("insert file name here..");
		layout->addWidget(text);
		auto button = new QPushButton("Export");
		layout->addWidget(button);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Export Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(button, &QPushButton::clicked, [&, window, text]() {
			string numefisier = text->text().QString::toStdString();
			std::ofstream csv;
			csv.open(numefisier);
			auto repo = srv.getContract();
			for (const auto& d : repo) {
				csv << d.getNume() << ',' << d.getOre() << ',' << d.getTip() << ',' << d.getProf() << '\n';
			}
			csv.close();
			window->close();
		});
		window->show();
	});
}

void GUI::connectWindows() {
	tab2->setContextMenuPolicy(Qt::CustomContextMenu);
	connect(tab2, &QWidget::customContextMenuRequested, [&](const QPoint& pos) {
		QPoint globalPos = tab2->mapToGlobal(pos);
		auto menu = new QMenu;
		auto submenu = new QMenu("Open in new window");
		menu->addMenu(submenu);
		submenu->addAction("CRUD");
		submenu->addAction("ReadOnly");
		auto x = menu->exec(globalPos);
		if (x != nullptr && x->text() == "CRUD") {
			auto window = new WindowCRUD{ srv,obs };
			window->show();
		}
		else if (x != nullptr && x->text() == "ReadOnly") {
			auto window = new WindowReadOnly{ srv,obs };
			window->show();
		}
	});
}

//WindowCrud

void WindowCRUD::update() {
	tabel->blockSignals(true);
	tabel->clear();
	auto contract = srv.getContract();
	int i = 0;
	for (auto el : contract) {
		tabel->setItem(i, 0, new QTableWidgetItem(QString::fromStdString(el.getNume())));
		tabel->setItem(i, 1, new QTableWidgetItem(QString::number(el.getOre())));
		tabel->setItem(i, 2, new QTableWidgetItem(QString::fromStdString(el.getTip())));
		tabel->setItem(i, 3, new QTableWidgetItem(QString::fromStdString(el.getProf())));
		tabel->item(i, 0)->setData(Qt::UserRole, QString::fromStdString(el.getNume()));
		tabel->item(i, 1)->setData(Qt::UserRole, QString::number(el.getOre()));
		tabel->item(i, 2)->setData(Qt::UserRole, QString::fromStdString(el.getTip()));
		tabel->item(i, 3)->setData(Qt::UserRole, QString::fromStdString(el.getProf()));
		if (el.getTip() == "obligatorie") {
			tabel->item(i, 0)->setBackgroundColor(QColor("#ffbac1"));
			tabel->item(i, 1)->setBackgroundColor(QColor("#ffbac1"));
			tabel->item(i, 2)->setBackgroundColor(QColor("#ffbac1"));
			tabel->item(i, 3)->setBackgroundColor(QColor("#ffbac1"));
		}
		i++;
	}
	for (int i = 0; i < tabel->rowCount(); i++)
		for (int j = 0; j < tabel->columnCount(); j++) {
			QObject::connect(tabel, &QTableWidget::cellChanged, [&](int i, int j) {
				switch (j) {
				case 0:
					tabel->item(i, j)->setText(tabel->item(i, j)->data(Qt::UserRole).toString());
					break;
				default:
					try {
						srv.update(tabel->item(i, 0)->text().QString::toStdString(), tabel->item(i, 1)->text().QString::toInt(), tabel->item(i, 2)->text().QString::toStdString(), tabel->item(i, 3)->text().QString::toStdString());
						srv.updateContract(tabel->item(i, 0)->text().QString::toStdString(), tabel->item(i, 1)->text().QString::toInt(), tabel->item(i, 2)->text().QString::toStdString(), tabel->item(i, 3)->text().QString::toStdString());
						obs.notify();

					}
					catch (RepoException& ex) {
						QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
					}
				}
			});
		}
	if (srv.getContract().size() != 0) {
		buttDelC->setEnabled(true);
		buttExport->setEnabled(true);
	}
	else {
		buttDelC->setEnabled(false);
		buttExport->setEnabled(false);
	}
	tabel->blockSignals(false);
}

void WindowCRUD::initLayout() {
	obs.addObserver(this);
	auto layout = new QVBoxLayout;
	this->setLayout(layout);
	f = new QFont("Arial", 10);
	this->setFont(*f);
	this->setWindowTitle("CRUD");
	this->setWindowIcon(QIcon("icon.ico"));
	this->setMinimumHeight(420);
	this->setMinimumWidth(600);
	tabel = new QTableWidget(srv.getSize() + 5, 4);
	layout->addWidget(tabel);
	auto buttons = new QHBoxLayout;
	buttGenerate = new QPushButton("Generate");
	buttons->addWidget(buttGenerate);
	buttAddC = new QPushButton("Add");
	buttons->addWidget(buttAddC);
	buttDelC = new QPushButton("Clear");
	buttDelC->setEnabled(false);
	buttons->addWidget(buttDelC);
	buttExport = new QPushButton("Export");
	buttExport->setEnabled(false);
	buttons->addWidget(buttExport);
	layout->addLayout(buttons);
	obs.notify();
}

void WindowCRUD::initConnect() {
	QObject::connect(buttGenerate, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
		auto layout = new QVBoxLayout;
		auto text = new QLabel("Nr discipline:\n");
		text->setAlignment(Qt::AlignHCenter);
		layout->addWidget(text);
		auto dial = new QDial();
		dial->setMinimum(1);
		dial->setMaximum(srv.getSize());
		layout->addWidget(dial);
		auto label = new QLineEdit("1");
		label->setAlignment(Qt::AlignHCenter);
		label->setEnabled(false);
		layout->addWidget(label);
		auto button = new QPushButton("Generate\nContract");
		layout->addWidget(button);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Generate Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(dial, &QDial::valueChanged, [&, label, dial]() {
			label->setText(QString::number(dial->value()));
		});
		QObject::connect(button, &QPushButton::clicked, [&, window, dial]() {
			try {
				int nr = dial->value();
				srv.generateContract(nr);
				obs.notify();
				window->close();
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});

		window->show();
	});

	QObject::connect(buttDelC, &QPushButton::clicked, [&]() {
		srv.deleteContract();
		obs.notify();
	});

	QObject::connect(buttAddC, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
		auto layout = new QVBoxLayout;
		auto text = new QLineEdit;
		text->setPlaceholderText("Nume");
		layout->addWidget(text);
		auto button = new QPushButton("Add");
		layout->addWidget(button);
		auto button2 = new QPushButton("Close");
		layout->addWidget(button2);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Add to Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(button, &QPushButton::clicked, [&, text]() {
			try {
				srv.addContract(text->text().QString::toStdString());
				obs.notify();
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});
		QObject::connect(button2, &QPushButton::clicked, [&, window]() {
			window->close();
		});
		window->show();
	});

	QObject::connect(buttExport, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
		auto layout = new QVBoxLayout;
		auto text = new QLineEdit;
		text->setPlaceholderText("insert file name here..");
		layout->addWidget(text);
		auto button = new QPushButton("Export");
		layout->addWidget(button);
		window->setLayout(layout);
		window->setFont(*f);
		window->setWindowTitle("Export Contract");
		window->setWindowIcon(QIcon("icon.ico"));
		QObject::connect(button, &QPushButton::clicked, [&, window, text]() {
			string numefisier = text->text().QString::toStdString();
			std::ofstream csv;
			csv.open(numefisier);
			auto repo = srv.getContract();
			for (const auto& d : repo) {
				csv << d.getNume() << ',' << d.getOre() << ',' << d.getTip() << ',' << d.getProf() << '\n';
			}
			csv.close();
			this->close();
		});
		this->show();
	});
}

//WindowReadOnly

void WindowReadOnly::update() {
	repaint();
}

void WindowReadOnly::paintEvent(QPaintEvent* ev) {
	QPainter p{ this };
	for (auto el : srv.getContract()) {
		int x1 = rand() % (width() - 100);
		int y1 = rand() % (height() - 100);
		p.drawImage(x1, y1, QImage("familly.jpg"));
	}
}