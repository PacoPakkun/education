USE [LIQUOR STORE];

--#1 cate tipuri de lichior vinde magazinul 2
SELECT M.id AS id_magazin, P.id_categorie, C.nume, COUNT(P.id) AS [nr produse]
FROM Produse P INNER JOIN ProduseMagazine PM ON PM.id_produs=P.id INNER JOIN Magazine M ON PM.id_magazin=M.id, Categorii C
WHERE M.id=2 AND C.id=P.id_categorie
GROUP BY P.id_categorie, M.id, C.nume
HAVING id_categorie=5;

--#2 produsele de la selgros cu rating > 3
SELECT F.nume AS [furnizor], R.id_produs, P.nume, AVG(R.stele) AS [rating]
FROM Reviews R INNER JOIN Produse P ON R.id_produs=P.id INNER JOIN Furnizori F ON F.id=P.id_furnizor
WHERE F.id=2
GROUP BY F.nume, R.id_produs, P.nume
HAVING AVG(R.stele)>3;

--#3 nr produselor din fiecare comanda
SELECT C.id AS [id_comanda], CL.nume, CL.prenume, SUM(PC.cantitate) AS [nr_produse] 
FROM Comenzi C INNER JOIN ProduseComenzi PC ON PC.id_comenzi=C.id INNER JOIN Produse P ON P.id=PC.id_produs, Clienti CL
WHERE CL.id=C.id_client
GROUP BY C.id, CL.nume, CL.prenume;

--#4 categoriile distincte ale produselor vandute de furnizori
SELECT DISTINCT F.nume AS furnizor, C.nume AS categorie
FROM Furnizori F, Produse P, Categorii C
WHERE F.id=P.id_furnizor AND C.id=P.id_categorie;

--#5 furnizorii si magazinele din acelasi oras
SELECT DISTINCT F.nume AS furnizor, M.id AS id_magazin, M.oras
FROM Produse P INNER JOIN ProduseMagazine PM ON PM.id_produs=P.id INNER JOIN Magazine M ON PM.id_magazin=M.id, Furnizori F
WHERE P.id_furnizor=F.id AND F.oras LIKE M.oras;

--#6 prezentarea produselor din store, cu categoriile si ratingurile lor
SELECT P.id, P.nume, P.concentratie, P.cantitate, P.descriere, P.pret, C.nume AS categorie, AVG(R.stele) AS rating
FROM Categorii C INNER JOIN Produse P ON C.id=P.id_categorie LEFT JOIN Reviews R ON R.id_produs=P.id
GROUP BY P.id, P.nume, P.concentratie, P.cantitate, P.descriere, P.pret, C.nume;

--#7 comenzile reginei Elizabeta
SELECT C.nume, C.prenume, CO.id AS id_comanda, CO.platforma, P.nume, PC.cantitate, P.pret AS [pret/buc]
FROM Comenzi CO INNER JOIN ProduseComenzi PC ON PC.id_comenzi=CO.id INNER JOIN Produse P ON P.id=PC.id_produs, Clienti C
WHERE C.id=CO.id_client AND C.nume='Regina';

--#8 angajatii care au gmail
SELECT id, nume, prenume, email
FROM Angajati 
WHERE email LIKE '%gmail%';

--#9 magazinele deschise dupa ora 12
SELECT id AS id_magazin, adresa, ora_deschiderii, ora_inchiderii
FROM Magazine
WHERE ora_inchiderii>0 AND ora_inchiderii<5;

--#10 comenzile cu plata ramburs
SELECT CO.id, CO.platforma, C.nume, C.prenume, C.nr_telefon
FROM Comenzi CO, Clienti C
WHERE CO.id_client=C.id AND CO.ramburs=1;