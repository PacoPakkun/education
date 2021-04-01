USE [LIQUOR STORE];

-- test #1

-- view Clienti
GO
CREATE VIEW view_Clienti
AS
SELECT * 
FROM Clienti;

-- inserare Clienti
GO
CREATE PROCEDURE insert_Clienti(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id FROM Clienti WHERE id = @id))
		BEGIN
			INSERT INTO Clienti (id, nume, prenume, nr_telefon) VALUES (@id, '', '', 700000000);
			SET @inserted = @inserted + 1;
		END
	END
END


-- test #2

-- view Angajati x Magazine
GO
CREATE VIEW view_AngajatiMagazine
AS
SELECT A.nume, A.prenume
FROM Magazine M INNER JOIN Angajati A ON M.id = A.id_magazin;

-- inserare Magazine
GO
CREATE PROCEDURE insert_Magazine(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id FROM Magazine WHERE id = @id))
		BEGIN
			INSERT INTO Magazine (id, adresa, spatiu, data_infiintarii, oras, ora_deschiderii, ora_inchiderii) VALUES (@id, '', 0, '2000-11-15', '', 0, 0);
			SET @inserted = @inserted + 1;
		END
	END
END

-- inserare Angajati
GO
CREATE PROCEDURE insert_Angajati(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id FROM Angajati WHERE id = @id))
		BEGIN
			INSERT INTO Angajati (id, nume, prenume, cnp, nr_telefon, email, id_magazin) VALUES (@id, '', '', 2000000000000, 700000000, '', @id);
			SET @inserted = @inserted + 1;
		END
	END
END

-- test #3

-- view Produse x Magazine
GO
CREATE VIEW view_ProduseMagazine
AS
SELECT M.id, COUNT(P.id) AS [nr produse]
FROM Produse P INNER JOIN ProduseMagazine PM ON PM.id_produs=P.id 
               INNER JOIN Magazine M ON PM.id_magazin=M.id
GROUP BY M.id;

-- inserare Magazine
/*
GO
CREATE PROCEDURE insert_Magazine(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id FROM Magazine WHERE id = @id))
		BEGIN
			INSERT INTO Magazine (id, adresa, spatiu, data_infiintarii, oras, ora_deschiderii, ora_inchiderii) VALUES (@id, '', 0, '2000-11-15', '', 0, 0);
			SET @inserted = @inserted + 1;
		END
	END
END
*/

-- inserare Produse
GO
CREATE PROCEDURE insert_Produse(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	IF (not exists(SELECT TOP 1 id FROM Furnizori WHERE id = -1))
	BEGIN
		INSERT INTO Furnizori (id, nume, oras) VALUES (-1, '', '');
	END;
	IF (not exists(SELECT TOP 1 id FROM Categorii WHERE id = -1))
	BEGIN
		INSERT INTO Categorii(id, nume) VALUES (-1, '');
	END

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id FROM Produse WHERE id = @id))
		BEGIN
			INSERT INTO Produse (id, nume, concentratie, cantitate, descriere, pret, id_furnizor, id_categorie) VALUES (@id, '', 0, 0, '', -1, -1, -1);
			SET @inserted = @inserted + 1;
		END
	END
END

-- inserare ProduseMagazine
GO
CREATE PROCEDURE insert_ProduseMagazine(@no_rows INT)
AS
BEGIN
	SET NOCOUNT ON;

	DECLARE @id SMALLINT = 0, @inserted INT = 0;

	WHILE @inserted < @no_rows
	BEGIN
		SET @id = @id + 1;
		IF (not exists(SELECT TOP 1 id_magazin, id_produs FROM ProduseMagazine WHERE id_magazin = @id AND id_produs = @id))
		BEGIN
			INSERT INTO ProduseMagazine(id_produs, id_magazin) VALUES (@id, @id);
			SET @inserted = @inserted + 1;
		END
	END
END

-- ruleaza testele

GO
CREATE PROCEDURE test
AS 
BEGIN
	SET NOCOUNT ON;

	DELETE FROM TestRuns;
	DBCC CHECKIDENT (TestRuns, RESEED, 0);

	DECLARE @id_test INT, @id_view INT, @id_test_run INT, @id_table INT, @no_rows INT, 
		    @test_name VARCHAR(30), @table_name VARCHAR(30), @view_name VARCHAR(30), 
			@SQL VARCHAR(100), @func VARCHAR(30), @desc VARCHAR(100), 
			@starts DATETIME;

	DECLARE cursor_tests CURSOR
		FOR SELECT TestID, Name FROM Tests
		ORDER BY TestID

	OPEN cursor_tests

	FETCH NEXT FROM cursor_tests INTO @id_test, @test_name

	WHILE @@FETCH_STATUS = 0
	BEGIN
		DECLARE cursor_tabele SCROLL CURSOR
			FOR SELECT TT.TableID, Name, NoOfRows
			FROM TestTables TT INNER JOIN Tables T ON TT.TableID = T.TableID
			WHERE TT.TestID = @id_test
			ORDER BY Position

		OPEN cursor_tabele

		PRINT('DELETE');

		SET @desc = 'Test: ' + CAST(@id_test AS VARCHAR(10));
		INSERT INTO TestRuns(Description, StartAt, EndAt) VALUES (@desc, GETDATE(), NULL);
		SET @id_test_run = @@IDENTITY;

		FETCH NEXT FROM cursor_tabele INTO @id_table, @table_name, @no_rows

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; Tabel: ' + @table_name);

			SET @SQL = 'DELETE FROM ' + @table_name;
			EXEC(@SQL);

			FETCH NEXT FROM cursor_tabele INTO @id_table, @table_name, @no_rows
		END

		PRINT('INSERT');

		FETCH PRIOR FROM cursor_tabele INTO @id_table, @table_name, @no_rows

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; Tabel: ' + @table_name);

			SET @starts = GETDATE();

			SET @func = 'insert_' + @table_name;
			EXEC @func @no_rows;

			INSERT INTO TestRunTables(TestRunID, TableID, StartAt, EndAt) VALUES (@id_test_run, @id_table, @starts, GETDATE());

			FETCH PRIOR FROM cursor_tabele INTO @id_table, @table_name, @no_rows
		END

		CLOSE cursor_tabele
		DEALLOCATE cursor_tabele

		DECLARE cursor_views CURSOR
			FOR SELECT TV.ViewID, Name
			FROM TestViews TV INNER JOIN Views V ON TV.ViewID = V.ViewID
			WHERE TV.TestID = @id_test

		OPEN cursor_views

		PRINT('VIEW');

		FETCH NEXT FROM cursor_views INTO @id_view, @view_name

		WHILE @@FETCH_STATUS = 0
		BEGIN
			PRINT('Test: ' + CAST(@id_test AS VARCHAR(10)) + '; View: ' + @view_name);

			SET @starts = GETDATE();

			SET @SQL = 'SELECT * FROM ' + @view_name;
			EXEC(@SQL);

			INSERT INTO TestRunViews(TestRunID, ViewID, StartAt, EndAt) VALUES (@id_test_run, @id_view, @starts, GETDATE());

			FETCH NEXT FROM cursor_views INTO @id_view, @view_name
		END

		CLOSE cursor_views
		DEALLOCATE cursor_views

		UPDATE TestRuns SET EndAt = GETDATE() WHERE TestRunID = @id_test_run;

		FETCH NEXT FROM cursor_tests INTO @id_test, @test_name
	END

	CLOSE cursor_tests
	DEALLOCATE cursor_tests
END

EXEC test;

SELECT *
FROM TestRuns;

SELECT *
FROM TestRunTables;

SELECT *
FROM TestRunViews;
