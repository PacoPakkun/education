USE [LIQUOR STORE];

--modifica tipul unei coloane
CREATE PROCEDURE direct1
AS BEGIN
ALTER TABLE Magazine
ALTER COLUMN spatiu BIGINT;
END;

CREATE PROCEDURE invers1
AS BEGIN
ALTER TABLE Magazine
ALTER COLUMN spatiu INT;
END;

--adauga o constrangere default
CREATE PROCEDURE direct2
AS BEGIN
ALTER TABLE Magazine
ADD CONSTRAINT df DEFAULT 'Cluj-Napoca' FOR oras;
END;

CREATE PROCEDURE invers2
AS BEGIN
ALTER TABLE Magazine
DROP CONSTRAINT df;
END;

--creeaza o tabela noua
CREATE PROCEDURE direct3
AS BEGIN
CREATE TABLE Parteneri (
id INT PRIMARY KEY,
id_magazin INT);
END;

CREATE PROCEDURE invers3
AS BEGIN
DROP TABLE Parteneri;
END;

--adauga un camp nou
CREATE PROCEDURE direct4
AS BEGIN
ALTER TABLE Parteneri
ADD nume NVARCHAR(100);
END;

CREATE PROCEDURE invers4
AS BEGIN
ALTER TABLE Parteneri
DROP COLUMN nume;
END;

--adauga o cheie straina
CREATE PROCEDURE direct5
AS BEGIN
ALTER TABLE Parteneri
ADD CONSTRAINT fk FOREIGN KEY(id_magazin) REFERENCES Magazine(id);
END;

CREATE PROCEDURE invers5
AS BEGIN
ALTER TABLE Parteneri
DROP CONSTRAINT fk;
END;

--tabela de versiuni
CREATE TABLE Versiune(
versiune INT);

INSERT INTO Versiune VALUES (0);

--aduce baza de date la alta versiune
CREATE PROCEDURE main
(@versiune INT)
AS BEGIN
IF @versiune<0
	RAISERROR('Versiune invalida!',11,1);
ELSE BEGIN
IF @versiune>5
	RAISERROR('Versiune invalida!',11,1);
ELSE BEGIN
	DECLARE @curent AS INT;
	SELECT @curent=versiune FROM Versiune;
	DECLARE @name VARCHAR(100);
	DECLARE @nr VARCHAR(100);
	DECLARE @procname VARCHAR(100);
	UPDATE Versiune
	SET versiune=@versiune
	WHERE versiune=@curent;
	PRINT 'versiune curenta: ';
	PRINT @versiune;
	IF @versiune>@curent
	BEGIN
		SET @name='direct';
		WHILE @curent<@versiune
		BEGIN
			SET @curent=@curent+1;
			SET @procname=@name+CAST(@curent AS VARCHAR(100));
			EXEC @procname;
		END;
	END;
	IF @versiune<@curent
	BEGIN
		SET @name='invers';
		WHILE @curent>@versiune
		BEGIN
			SET @procname=@name+CAST(@curent AS VARCHAR(100));
			EXEC @procname;
			SET @curent=@curent-1;
		END;
	END;
END;
END;
END;

EXEC main 0;

SELECT * FROM Versiune;

