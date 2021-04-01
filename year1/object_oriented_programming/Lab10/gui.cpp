#include "gui.h"

void GUI::initGUI() {

	//layout
	auto tabs = new QTabWidget;
	layout = new QHBoxLayout;
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
	auto tab2 = new QWidget;
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
		QObject::connect(buton, &QPushButton::clicked, [&,buton]() {
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

	QObject::connect(list, &QListWidget::itemClicked, [&]() {
		auto curent = list->selectedItems().at(0)->text();
		nume->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getNume()));
		ore->setValue(srv.find(curent.QString::toStdString()).getOre());
		tip->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getTip()));
		prof->setText(QString::fromStdString(srv.find(curent.QString::toStdString()).getProf()));
	});

	QObject::connect(buttonAdd, &QPushButton::clicked, [&]() {
		try {
			srv.add(nume->text().QString::toStdString(), ore->text().toInt(), tip->text().QString::toStdString(), prof->text().QString::toStdString());
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr,"Error",QString::fromStdString(ex.getMsg()));
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

	QObject::connect(buttonUpd, &QPushButton::clicked, [&]() {
		try {
			srv.update(nume->text().QString::toStdString(), ore->text().toInt(), tip->text().QString::toStdString(), prof->text().QString::toStdString());
		}
		catch (RepoException& ex) {
			QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
		}
		list->clear();
		for (auto el : srv.getRepo()) {
			list->addItem(QString::fromStdString(el.getNume()));
		}
	});

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
	});

	QObject::connect(sort, QOverload<int>::of (&QComboBox::currentIndexChanged) , [&](int index) {
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
	});

	QObject::connect(buttonGenerate, &QPushButton::clicked, [&]() {
		auto window = new QWidget;
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
				auto contract = srv.getContract();
				int i = 0;
				for (auto el : contract) {
					table->setCellWidget(i, 0, new QLabel(QString::fromStdString(el.getNume())));
					table->setCellWidget(i, 1, new QLabel(QString::number(el.getOre())));
					table->setCellWidget(i, 2, new QLabel(QString::fromStdString(el.getTip())));
					table->setCellWidget(i, 3, new QLabel(QString::fromStdString(el.getProf())));
					i++;
				}
				buttonDelC->setEnabled(true);
				buttonExport->setEnabled(true);
				window->close();
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});

		window->show();
	});

	QObject::connect(buttonDelC, &QPushButton::clicked, [&]() {
		srv.deleteContract();
		table->clear();
		buttonDelC->setEnabled(false);
		buttonExport->setEnabled(false);
	});

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
		QObject::connect(button, &QPushButton::clicked, [&,text]() {
			try {
				srv.addContract(text->text().QString::toStdString());
				auto contract = srv.getContract();
				int i = 0;
				for (auto el : contract) {
					table->setCellWidget(i, 0, new QLabel(QString::fromStdString(el.getNume())));
					table->setCellWidget(i, 1, new QLabel(QString::number(el.getOre())));
					table->setCellWidget(i, 2, new QLabel(QString::fromStdString(el.getTip())));
					table->setCellWidget(i, 3, new QLabel(QString::fromStdString(el.getProf())));
					i++;
				}
				buttonDelC->setEnabled(true);
				buttonExport->setEnabled(true);
			}
			catch (RepoException& ex) {
				QMessageBox::information(nullptr, "Error", QString::fromStdString(ex.getMsg()));
			}
		});
		QObject::connect(button2, &QPushButton::clicked, [&,window]() {
			window->close();
		});
		window->show();
	});

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
		QObject::connect(button, &QPushButton::clicked, [&, window,text]() {
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