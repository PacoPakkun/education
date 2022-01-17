USE [LIQUOR STORE]

-- VALIDATOARE
GO
CREATE FUNCTION validateNume (@nume	VARCHAR(20)) RETURNS INT AS
	BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@nume is not null and @nume<>'')
	BEGIN
		SET @return=1
	END
	RETURN @return
END

GO
CREATE FUNCTION validateConcentratie (@concentratie	FLOAT) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@concentratie is not null AND @concentratie>=0 AND @concentratie<100)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateCantitate (@cantitate FLOAT) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@cantitate is not null AND @cantitate>0)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateDescriere (@descriere VARCHAR(200)) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@descriere is not null)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validatePret (@pret FLOAT) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@pret is not null AND @pret>0)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateAdresa (@adresa NVARCHAR(200)) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@adresa is not null AND @adresa<>'')
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateOras (@oras VARCHAR(200)) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@oras is not null AND @oras in ('Cluj-Napoca','Brasov','Ibiza'))
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateSpatiu (@spatiu INT) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@spatiu is not null AND @spatiu>0)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateData (@data DATE) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@data is not null AND year(@data)>2005 AND year(@data)<2022)
		SET @return=1
	RETURN @return
END

GO
CREATE FUNCTION validateOra (@ora INT) RETURNS INT AS
BEGIN
	DECLARE @return INT
	SET @return=0
	IF(@ora is not null AND @ora>0 AND @ora<=24)
		SET @return=1
	RETURN @return
END

-- TRANZACTII

-- procedura cu full rollback
GO
CREATE PROCEDURE AddFullRollback @nume varchar(20), @concentratie float, @cantitate float, @descriere varchar(200), @pret float, @adresa nvarchar(200), @oras varchar(200), @spatiu int, @data date, @ora_open INT, @ora_close INT
AS
BEGIN
	DECLARE @id1 INT, @id2 INT
	BEGIN TRAN
		BEGIN TRY
		IF(dbo.validateNume(@nume)<>1)
		BEGIN
			RAISERROR('Product name must not be null',14,1)
		END
		IF(dbo.validateConcentratie(@concentratie)<>1)
		BEGIN
			RAISERROR('Product concentration must be a value between 0 and 100',14,1)
		END
		IF(dbo.validateCantitate(@cantitate)<>1)
		BEGIN
			RAISERROR('Product quantity must be a positive number',14,1)
		END
		IF(dbo.validateDescriere(@descriere)<>1)
		BEGIN
			RAISERROR('Product description must not be null',14,1)
		END
		IF(dbo.validatePret(@pret)<>1)
		BEGIN
			RAISERROR('Product price must be a positive number',14,1)
		END
		IF(dbo.validateAdresa(@adresa)<>1)
		BEGIN
			RAISERROR('Store address must be a valid location',14,1)
		END
		IF(dbo.validateOras(@oras)<>1)
		BEGIN
			RAISERROR('Store city must be a Cluj-Napoca, Brasov or Ibiza',14,1)
		END
		IF(dbo.validateSpatiu(@spatiu)<>1)
		BEGIN
			RAISERROR('Store space must be a positive number',14,1)
		END
		IF(dbo.validateData(@data)<>1)
		BEGIN
			RAISERROR('Store opening date must be a valid date between 2005 and 2021',14,1)
		END
		IF(dbo.validateOra(@ora_open)<>1)
		BEGIN
			RAISERROR('Store opening hour must be a valid 24 format hour',14,1)
		END
		IF(dbo.validateOra(@ora_close)<>1)
		BEGIN
			RAISERROR('Store closing hour must be a valid 24 format hour',14,1)
		END		SET @id1=(SELECT max(id) from Produse)+1		SET @id2=(SELECT max(id) from Magazine)+1
		INSERT INTO Produse (id, nume, concentratie, cantitate, descriere, pret) VALUES (@id1, @nume, @concentratie, @cantitate, @descriere, @pret)
		INSERT INTO Magazine (id, adresa, oras, spatiu, data_infiintarii, ora_deschiderii, ora_inchiderii) VALUES (@id2, @adresa, @oras, @spatiu, @data, @ora_open, @ora_close)
		INSERT INTO ProduseMagazine (id_magazin, id_produs) VALUES (@id2, @id1)
	COMMIT TRAN
	SELECT 'Transaction committed'
	END TRY

	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH
END

-- procedura cu partial rollback
GO
CREATE PROCEDURE AddPartialRollback @nume varchar(20), @concentratie float, @cantitate float, @descriere varchar(200), @pret float, @adresa nvarchar(200), @oras varchar(200), @spatiu int, @data date, @ora_open INT, @ora_close INT
AS
BEGIN
	DECLARE @id1 INT, @id2 INT
	BEGIN TRAN
	BEGIN TRY
		IF(dbo.validateNume(@nume)<>1)
		BEGIN
			RAISERROR('Product name must not be null',14,1)
		END
		IF(dbo.validateConcentratie(@concentratie)<>1)
		BEGIN
			RAISERROR('Product concentration must be a value between 0 and 100',14,1)
		END
		IF(dbo.validateCantitate(@cantitate)<>1)
		BEGIN
			RAISERROR('Product quantity must be a positive number',14,1)
		END
		IF(dbo.validateDescriere(@descriere)<>1)
		BEGIN
			RAISERROR('Product description must not be null',14,1)
		END
		IF(dbo.validatePret(@pret)<>1)
		BEGIN
			RAISERROR('Product price must be a positive number',14,1)
		END		SET @id1=(SELECT max(id) from Produse)+1
		INSERT INTO Produse (id, nume, concentratie, cantitate, descriere, pret) VALUES (@id1, @nume, @concentratie, @cantitate, @descriere, @pret)
	COMMIT TRAN
	SELECT 'First transaction committed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'First transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH

	BEGIN TRAN
	BEGIN TRY
		IF(dbo.validateAdresa(@adresa)<>1)
		BEGIN
			RAISERROR('Store address must be a valid location',14,1)
		END
		IF(dbo.validateOras(@oras)<>1)
		BEGIN
			RAISERROR('Store city must be a Cluj-Napoca, Brasov or Ibiza',14,1)
		END
		IF(dbo.validateSpatiu(@spatiu)<>1)
		BEGIN
			RAISERROR('Store space must be a positive number',14,1)
		END
		IF(dbo.validateData(@data)<>1)
		BEGIN
			RAISERROR('Store opening date must be a valid date between 2005 and 2021',14,1)
		END
		IF(dbo.validateOra(@ora_open)<>1)
		BEGIN
			RAISERROR('Store opening hour must be a valid 24 format hour',14,1)
		END
		IF(dbo.validateOra(@ora_close)<>1)
		BEGIN
			RAISERROR('Store closing hour must be a valid 24 format hour',14,1)
		END		SET @id2=(SELECT max(id) from Magazine)+1
		INSERT INTO Magazine (id, adresa, oras, spatiu, data_infiintarii, ora_deschiderii, ora_inchiderii) VALUES (@id2, @adresa, @oras, @spatiu, @data, @ora_open, @ora_close)
	COMMIT TRAN
	SELECT 'Second transaction committed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Second transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH

	BEGIN TRAN
	BEGIN TRY
		INSERT INTO ProduseMagazine (id_magazin, id_produs) VALUES (@id2, @id1)
	COMMIT TRAN
	SELECT 'Last transaction committed'
	END TRY
	BEGIN CATCH
		ROLLBACK TRAN
		SELECT 'Last transaction rollbacked', ERROR_MESSAGE() as Error
	END CATCH

END

-- TESTE

SELECT * from Produse
SELECT * from Magazine
SELECT * from ProduseMagazine
DELETE FROM Produse WHERE id=398 or id=399
DELETE FROM Magazine WHERE id=2 or id=3

EXEC AddFullRollback 'Ursus', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddFullRollback 'Ursus', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Oradea', 350, '2020-11-15', 14, 2
EXEC AddFullRollback '', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddFullRollback 'Ursus', 4.5, 0, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddFullRollback 'Ursus', 4.5, 0.2, 'cu visine', 8.69, null, 'Ibiza', 350, '2020-11-15', 14, 2

EXEC AddPartialRollback 'Ursus', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddPartialRollback 'Ursus', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Oradea', 350, '2020-11-15', 14, 2
EXEC AddPartialRollback '', 4.5, 0.5, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddPartialRollback 'Ursus', 4.5, 0, 'cu visine', 8.69, 'Albac 16', 'Ibiza', 350, '2020-11-15', 14, 2
EXEC AddPartialRollback 'Ursus', 4.5, 0.2, 'cu visine', 8.69, null, 'Ibiza', 350, '2020-11-15', 14, 2
