CREATE DATABASE [LIQUOR STORE];
USE [LIQUOR STORE];

CREATE TABLE Magazine(
	id INT NOT NULL PRIMARY KEY,
	adresa NVARCHAR(200),
	oras VARCHAR(200),
	spatiu INT, 
	data_infiintarii DATE,
	ora_deschiderii INT DEFAULT '10',
	ora_inchiderii INT DEFAULT '24',
	CONSTRAINT uniq_magazin UNIQUE (id, adresa)
);

CREATE TABLE Angajati(
	id INT NOT NULL PRIMARY KEY,
	nume VARCHAR(100),
	prenume VARCHAR(200),
	cnp BIGINT CHECK(cnp>1000000000000),
	nr_telefon BIGINT CHECK(nr_telefon>100000000),
	email NVARCHAR(200),
	CONSTRAINT uniq_nume_angajat UNIQUE (id, nume, prenume),
	CONSTRAINT uniq_cnp_angajat UNIQUE (id, cnp),
	CONSTRAINT uniq_tel_angajat UNIQUE (id, nr_telefon),
	CONSTRAINT uniq_email_angajat UNIQUE (id, email),
	id_magazin INT FOREIGN KEY REFERENCES Magazine(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Furnizori(
	id INT NOT NULL PRIMARY KEY,
	nume VARCHAR(100),
	oras VARCHAR(100),
	CONSTRAINT uniq_furnizor UNIQUE (id, nume)
);

CREATE TABLE Categorii(
	id INT NOT NULL PRIMARY KEY,
	nume VARCHAR(100),
	CONSTRAINT uniq_categorie UNIQUE (id, nume)
);

CREATE TABLE Produse(
	 id INT NOT NULL PRIMARY KEY,
	 nume VARCHAR(100),
	 concentratie FLOAT CHECK(concentratie>=0 AND concentratie<100),
	 cantitate FLOAT,
	 descriere NVARCHAR(300),
	 pret FLOAT NOT NULL,
	 CONSTRAINT uniq_prod UNIQUE (id, nume),
	 id_furnizor INT FOREIGN KEY REFERENCES Furnizori(id) ON DELETE CASCADE ON UPDATE CASCADE,
	 id_categorie INT FOREIGN KEY REFERENCES Categorii(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE Reviews(
	id INT NOT NULL PRIMARY KEY,
	review NVARCHAR(300),
	stele INT CHECK(stele>0 AND stele<=5),
	nume VARCHAR(100),
	id_produs INT FOREIGN KEY REFERENCES Produse(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE ProduseMagazine(
	id_produs INT FOREIGN KEY REFERENCES Produse(id) ON DELETE CASCADE ON UPDATE CASCADE,
	id_magazin INT FOREIGN KEY REFERENCES Magazine(id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT pk_prodmag PRIMARY KEY (id_produs, id_magazin)
);

CREATE TABLE Clienti(
	id INT NOT NULL PRIMARY KEY,
	nume VARCHAR(100),
	prenume VARCHAR(100),
	nr_telefon BIGINT CHECK(nr_telefon>100000000),
	CONSTRAINT uniq_nume_client UNIQUE (id, nume, prenume),
	CONSTRAINT uniq_tel_client UNIQUE (id, nr_telefon)
);

CREATE TABLE Comenzi(
	id INT NOT NULL PRIMARY KEY,
	id_client INT FOREIGN KEY REFERENCES Clienti(id) ON DELETE CASCADE ON UPDATE CASCADE,
	platforma VARCHAR(100) DEFAULT 'Glovo',
	ramburs BIT DEFAULT 0
);

CREATE TABLE ProduseComenzi(
	id_produs INT FOREIGN KEY REFERENCES Produse(id) ON DELETE CASCADE ON UPDATE CASCADE,
	id_comenzi INT FOREIGN KEY REFERENCES Comenzi(id) ON DELETE CASCADE ON UPDATE CASCADE,
	cantitate INT NOT NULL DEFAULT 1,
	CONSTRAINT pk_prodcom PRIMARY KEY (id_produs, id_comenzi)
);

