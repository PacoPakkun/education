USE [LIQUOR STORE];

-- PRODUSE

GO
CREATE PROCEDURE CreateProdus( -- create
@id INT,
@nume VARCHAR(100),
@concentratie FLOAT,
@cantitate FLOAT,
@descriere NVARCHAR(300),
@pret FLOAT,
@id_furnizor INT,
@id_categorie INT)
AS 
BEGIN
	IF dbo.ValidateProdusId(@id) = 1
	BEGIN
		PRINT 'Produs existent';
		RETURN;
	END;
	IF dbo.ValidateFurnizorId(@id_furnizor) = 0
	BEGIN
		PRINT 'Furnizor inexistent';
		RETURN;
	END;
	IF dbo.ValidateCategorieId(@id_categorie) = 0
	BEGIN
		PRINT 'Categorie inexistenta';
		RETURN;
	END;
	INSERT INTO Produse(id, nume, concentratie, cantitate, descriere, pret, id_furnizor, id_categorie)
	VALUES (@id, @nume, @concentratie, @cantitate, @descriere, @pret, @id_furnizor, @id_categorie);
END;
EXEC CreateProdus 2, 'Tatratea', 99, 2, 'puliq', 115, 1, 1;
SELECT * FROM Produse;

GO
CREATE PROCEDURE ReadProduse( -- read
@nume VARCHAR(100))
AS 
BEGIN
	SELECT * FROM Produse
	WHERE nume=@nume;
END;
EXEC ReadProduse 'Tatratea';

GO
CREATE PROCEDURE UpdateProdus( -- update
@id INT,
@concentratie INT,
@descriere VARCHAR(100),
@pret FLOAT)
AS
BEGIN
	IF dbo.ValidateProdusId(@id) = 1
	BEGIN
		UPDATE Produse
		SET concentratie = @concentratie, descriere = @descriere, pret = @pret
		WHERE id = @id;
		RETURN;
	END;
	PRINT 'Produs inexistent';
END;
EXEC UpdateProdus 1, 72, 'frumii', 120;
SELECT * FROM Produse;

GO
CREATE PROCEDURE DeleteProdus( -- delete
@id INT)
AS 
BEGIN
	DELETE FROM Produse
	WHERE id=@id;
END;
EXEC DeleteProdus 1;
SELECT * FROM Produse;

-- MAGAZINE

GO
CREATE PROCEDURE CreateMagazin( -- create
@id INT,
@adresa NVARCHAR(200),
@spatiu BIGINT,
@data_infiintarii DATE,
@oras VARCHAR(200),
@ora_deschiderii INT,
@ora_inchiderii INT)
AS 
BEGIN
	IF dbo.ValidateMagazinId(@id) = 1
	BEGIN
		PRINT 'Magazin existent';
		RETURN;
	END;
	INSERT INTO Magazine(id, adresa, spatiu, data_infiintarii, oras, ora_deschiderii, ora_inchiderii)
	VALUES (@id, @adresa, @spatiu, @data_infiintarii, @oras, @ora_deschiderii, @ora_inchiderii);
END;
EXEC CreateMagazin 2, 'Splaiul Independentei 3', 200, '2000-08-23', 'Cluj-Napoca', 12, 1;
SELECT * FROM Magazine;

GO
CREATE PROCEDURE ReadMagazine( -- read
@oras VARCHAR(200))
AS 
BEGIN
	SELECT * FROM Magazine
	WHERE oras=@oras;
END;
EXEC ReadMagazine 'Cluj-Napoca';

GO
CREATE PROCEDURE UpdateMagazin( -- update
@id INT,
@ora_deschiderii INT,
@ora_inchiderii INT)
AS
BEGIN
	IF dbo.ValidateMagazinId(@id) = 1
	BEGIN
		UPDATE Magazine
		SET ora_deschiderii = @ora_deschiderii, ora_inchiderii = @ora_inchiderii
		WHERE id = @id;
		RETURN;
	END;
	PRINT 'Magazin inexistent';
END;
EXEC UpdateMagazin 1, 14, 11;
SELECT * FROM Magazine;

GO
CREATE PROCEDURE DeleteMagazin( -- delete
@id INT)
AS 
BEGIN
	DELETE FROM Magazine
	WHERE id=@id;
END;
EXEC DeleteMagazin 1;
SELECT * FROM Magazine;

-- PRODUSE-MAGAZINE

GO
CREATE PROCEDURE CreateProdusMagazin( -- create
@id_produs INT,
@id_magazin INT)
AS 
BEGIN
	IF dbo.ValidateProdusMagazinId(@id_produs, @id_magazin) = 0 AND dbo.ValidateProdusId(@id_produs) = 1 AND dbo.ValidateMagazinId(@id_magazin) = 1
	BEGIN
		INSERT INTO ProduseMagazine(id_produs, id_magazin)
		VALUES (@id_produs, @id_magazin);
		RETURN;
	END;
	PRINT ':(';
END;
EXEC CreateProdusMagazin 1, 1;
SELECT * FROM ProduseMagazine;

GO
CREATE PROCEDURE ReadProduseMagazine( -- read
@id_magazin INT)
AS 
BEGIN
	SELECT * FROM ProduseMagazine
	WHERE id_magazin=@id_magazin;
END;
EXEC ReadProduseMagazine 1;

GO
ALTER PROCEDURE UpdateProdusMagazin( -- update
@id_produs INT,
@id_magazin INT,
@id_produs2 INT,
@id_magazin2 INT)
AS
BEGIN
	IF dbo.ValidateProdusMagazinId(@id_produs, @id_magazin) = 1 AND dbo.ValidateProdusId(@id_produs) = 1 AND dbo.ValidateMagazinId(@id_magazin) = 1
	BEGIN
		UPDATE ProduseMagazine
		SET id_produs = @id_produs2, id_magazin = @id_magazin2
		WHERE id_produs = @id_produs AND id_magazin = @id_magazin;
		RETURN;
	END;
	PRINT ':(';
END;
EXEC UpdateProdusMagazin 1, 1, 2, 2;
SELECT * FROM ProduseMagazine;

GO
CREATE PROCEDURE DeleteProdusMagazin( -- delete
@id_produs INT,
@id_magazin INT)
AS 
BEGIN
	DELETE FROM ProduseMagazine
	WHERE id_produs = @id_produs AND id_magazin = @id_magazin;
END;
EXEC DeleteProdusMagazin 2, 2;
SELECT * FROM ProduseMagazine;

-- VALIDATOARE

GO
CREATE FUNCTION ValidateProdusId(
@id INT)
RETURNS INT AS
BEGIN
	DECLARE @count INT;
	SET @count = 0;
	SELECT @count = COUNT(*) FROM Produse
	GROUP BY id
	HAVING id = @id;
	RETURN @count;
END;

GO
CREATE FUNCTION ValidateFurnizorId(
@id INT)
RETURNS INT AS
BEGIN
	DECLARE @count INT;
	SET @count = 0;
	SELECT @count = COUNT(*) FROM Furnizori
	GROUP BY id
	HAVING id = @id;
	RETURN @count;
END;

GO
CREATE FUNCTION ValidateCategorieId(
@id INT)
RETURNS INT AS
BEGIN
	DECLARE @count INT;
	SET @count = 0;
	SELECT @count = COUNT(*) FROM Categorii
	GROUP BY id
	HAVING id = @id;
	RETURN @count;
END;

GO
CREATE FUNCTION ValidateMagazinId(
@id INT)
RETURNS INT AS
BEGIN
	DECLARE @count INT;
	SET @count = 0;
	SELECT @count = COUNT(*) FROM Magazine
	GROUP BY id
	HAVING id = @id;
	RETURN @count;
END;

GO
CREATE FUNCTION ValidateProdusMagazinId(
@id_produs INT,
@id_magazin INT)
RETURNS INT AS
BEGIN
	DECLARE @count INT;
	SET @count = 0;
	SELECT @count = COUNT(*) FROM ProduseMagazine
	GROUP BY id_produs, id_magazin
	HAVING id_produs = @id_produs AND id_magazin = @id_magazin;
	RETURN @count;
END;

-- VIEWURI

CREATE NONCLUSTERED INDEX IX_ProduseMagazineIDP ON
ProduseMagazine (id_magazin ASC);
--DROP INDEX ProduseMagazine.IX_ProduseMagazineIDP
CREATE NONCLUSTERED INDEX IX_Magazine ON
Magazine (oras ASC);
--DROP INDEX Magazine.IX_Magazine

-- produsele vandute in Cj
GO
CREATE VIEW ViewProduseMagazineCj AS
	SELECT P.nume
	FROM Produse P
	INNER JOIN ProduseMagazine PM ON PM.id_produs = P.id
	INNER JOIN Magazine M ON M.id = PM.id_magazin
	WHERE M.oras = 'Cluj-Napoca';
GO
SELECT * FROM ViewProduseMagazineCj;

CREATE NONCLUSTERED INDEX IX_ProduseMagazineIDM ON
ProduseMagazine (id_produs ASC);
--DROP INDEX ProduseMagazine.IX_ProduseMagazineIDM
CREATE NONCLUSTERED INDEX IX_Produse ON
Produse (id ASC);
--DROP INDEX Magazine.IX_Magazine

-- magazinele care vand Tatra
GO
CREATE VIEW ViewProduseMagazineTatra AS
	SELECT M.id
	FROM Magazine M
	INNER JOIN ProduseMagazine PM ON PM.id_magazin = M.id
	INNER JOIN Produse P ON P.id = PM.id_produs
	WHERE P.id = 379;
GO
SELECT * FROM ViewProduseMagazineTatra;