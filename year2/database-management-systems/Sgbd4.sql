USE [LIQUOR STORE]

SELECT * FROM Clienti

-- dirty reads
BEGIN TRANSACTION
UPDATE Clienti SET
nr_telefon=777000000 WHERE id = 257
WAITFOR DELAY '00:00:10'
ROLLBACK TRANSACTION

-- nonrepeatable reads

BEGIN TRAN
UPDATE Clienti SET
nr_telefon=777777777 WHERE id = 257
COMMIT TRAN

-- phantom reads

BEGIN TRAN
INSERT INTO Clienti(id, nume, prenume, nr_telefon) VALUES (265,'Gicu','Nicu', 999999999)
COMMIT TRAN

-- deadlock

begin tran
update Clienti set prenume='Paul' where id=264
waitfor delay '00:00:10'
update Furnizori set nume='Glovo' where id=4
commit tran

select * from Clienti
select * from Furnizori