#include <assert.h>
#include <string.h>
#include <stdlib.h>
#include "tests.h"
#include "transaction.h"
#include "repo.h"
#include "service.h"


void TestTransaction()
{
	transaction* trans = CreateTransaction(1, 2, 300, 1, "steamgames");

	assert(trans->id == 1);
	assert(trans->day == 2);
	assert(trans->sum == 300);
	assert(trans->type == 1);
	assert(strcmp(trans->description, "steamgames") == 0);

	DestroyTransaction(trans);
}

void TestGetters()
{
	transaction* trans = CreateTransaction(1, 2, 300, 1, "Test");
	assert(GetId(trans) == 1);
	assert(GetDay(trans) == 2);
	assert(GetSum(trans) == 300);
	assert(GetType(trans) == 1);
	assert(strcmp(GetDesc(trans), "Test") == 0);

	DestroyTransaction(trans);
}

void TestCompare()
{
	transaction* trans1 = CreateTransaction(1, 2, 300, 1, "Test");
	transaction* trans2 = CreateTransaction(1, 3, 400, 1, "Test");

	assert(CompareDay(trans2, trans1) > 0);
	assert(CompareDay(trans2, trans2) == 0);
	assert(CompareDay(trans1, trans2) < 0);

	assert(CompareSum(trans2, trans1) > 0);
	assert(CompareSum(trans2, trans2) == 0);
	assert(CompareSum(trans1, trans2) < 0);

	DestroyTransaction(trans1);
	DestroyTransaction(trans2);
}

void TestCopy() {
	transaction* trans = CreateTransaction(1, 2, 300, 1, "Test");
	transaction* copie = CopyTransaction(trans);
	DestroyTransaction(trans);
	assert(GetId(copie) == 1);
	assert(GetDay(copie) == 2);
	assert(GetSum(copie) == 300);
	assert(GetType(copie) == 1);
	assert(strcmp(GetDesc(copie), "Test") == 0);

	DestroyTransaction(copie);
}

void TestRepo()
{
	transactionRepo* repo = CreateRepo();
	assert(sizeof(*repo) == sizeof(transactionRepo));
	assert(repo->arraySize == 1);
	assert(repo->usedSize == 0);

	DestroyRepo(repo, DestroyTransaction);
}

void TestExpandRepo()
{
	transactionRepo* repo = CreateRepo();
	int ASize, USize;
	ASize = repo->arraySize;
	USize = repo->usedSize;
	repo = ExpandSize(repo);
	assert(ASize != repo->arraySize);
	assert(USize == repo->usedSize);

	DestroyRepo(repo, DestroyTransaction);
}

void TestAddInRepo()
{
	int exitCode;
	transactionRepo* repo = CreateRepo();
	transaction* t = CreateTransaction(1, 30, 300, 1, "Test");
	exitCode = AddTransaction(t, repo);
	assert(exitCode == 0);

	assert(repo->usedSize == 1);
	assert(repo->arraySize <= repo->usedSize);

	exitCode = AddTransaction(t, repo);
	assert(exitCode == 1);

	ExpandSize(repo);

	AddTransaction(t, repo);
	exitCode = AddTransaction(t, repo);
	assert(exitCode == 2);

	DestroyTransaction(t);
	DestroyRepo(repo, DestroyTransaction);
}

void TestDeleteInRepo()
{
	int exitCode;
	transactionRepo* repo = CreateRepo();
	ExpandSize(repo);
	ExpandSize(repo);
	transaction* t = CreateTransaction(1, 30, 300, 1, "Test");
	AddTransaction(t, repo);
	DestroyTransaction(t);

	t = CreateTransaction(2, 30, 300, 1, "Test");
	AddTransaction(t, repo);
	DestroyTransaction(t);

	t = CreateTransaction(3, 30, 300, 1, "Test");
	AddTransaction(t, repo);
	DestroyTransaction(t);

	exitCode = DeleteTransaction(4, repo);
	assert(RepoGetSize(repo) == 3);
	assert(exitCode == 1);

	exitCode = DeleteTransaction(2, repo);
	assert(RepoGetSize(repo) == 2);
	assert(exitCode == 0);

	DestroyRepo(repo, DestroyTransaction);
}

void TestUpdateInRepo()
{
	int exitCode;
	transactionRepo* repo = CreateRepo();
	transaction* t = CreateTransaction(1, 30, 300, 1, "Test");
	exitCode = UpdateTransaction(t, repo);
	assert(exitCode == 1);

	AddTransaction(t, repo);

	exitCode = UpdateTransaction(t, repo);
	assert(exitCode == 0);

	DestroyTransaction(t);
	DestroyRepo(repo, DestroyTransaction);
}

void TestRepoGetTransactions()
{
	transactionRepo* repo = CreateRepo();
	transaction** trans;
	trans = RepoGetTransactions(repo);
	assert(trans == NULL);
	free(trans);

	transaction* t = CreateTransaction(1, 30, 300, 1, "Test");
	AddTransaction(t, repo);
	DestroyTransaction(t);

	trans = RepoGetTransactions(repo);
	assert(trans != NULL);

	int size = RepoGetSize(repo);
	for (int i = 0; i < size; i++)
	{
		free(trans[i]);
	}
	free(trans);
	DestroyRepo(repo, DestroyTransaction);
}

void TestRepoGetSize()
{
	transactionRepo* repo = CreateRepo();
	int size;

	size = RepoGetSize(repo);
	assert(size == 0);

	transaction* t = CreateTransaction(1, 30, 300, 1, "Test");
	AddTransaction(t, repo);

	size = RepoGetSize(repo);
	assert(size == 1);

	DestroyTransaction(t);
	DestroyRepo(repo, DestroyTransaction);
}

void TestService()
{
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	assert(serv->repo == repo);

	DestroyService(serv);
}

void TestAddInService()
{
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	int exitCode;

	exitCode = ServiceAdd(serv, 1, 1, 200, 1, "Test");
	assert(exitCode == 0);

	ServiceAdd(serv, 2, 23, 300, 1, "Test Extindere marime");

	exitCode = ServiceAdd(serv, 1, 20, 250, 0, "Test ID exista");
	assert(exitCode == 1);

	DestroyService(serv);
}

void TestDeleteInService()
{
	int exitCode;
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	ServiceAdd(serv, 1, 1, 200, 1, "Test");

	exitCode = ServiceDelete(serv, 2);
	assert(exitCode == 1);
	
	exitCode = ServiceDelete(serv, 1);
	assert(exitCode == 0);

	DestroyService(serv);
}

void TestUpdateInService()
{
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	int exitCode;

	exitCode = ServiceUpdate(serv, 1, 1, 200, 1, "Test");
	assert(exitCode == 1);

	ServiceAdd(serv, 1, 23, 300, 1, "Test Extindere marime");

	exitCode = ServiceUpdate(serv, 1, 1, 200, 1, "Test");
	assert(exitCode == 0);

	DestroyService(serv);
}

void TestServiceGetTransactionsNumber()
{
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);

	int size;
	size = ServiceGetTransactionNumber(serv);
	assert(size == 0);

	ServiceAdd(serv, 2, 23, 300, 1, "Test Extindere marime");
	size = ServiceGetTransactionNumber(serv);
	assert(size == 1);

	DestroyService(serv);
}

void TestServiceGetTransactions()
{
	int (*cmp)(transaction*, transaction*);
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	pair trans;

	trans = ServiceGetTransactions(serv, "nnnnnnn", NULL);
	assert(trans.number == 0);
	free(trans.filteredTransactions);

	ServiceAdd(serv, 2, 23, 300, 1, "Test Extindere marime");
	ServiceAdd(serv, 1, 4, 400, 0, "test");
	ServiceAdd(serv, 3, 5, 500, 1, "Test Extindere marime");
	ServiceAdd(serv, 4, 30, 100, 0, "test");

	trans = ServiceGetTransactions(serv, "nnnnnnn", NULL);
	assert(trans.number!= 0);
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);
	cmp = CompareSum;
	trans = ServiceGetTransactions(serv, "os0nnnn", cmp);
	for (int i = 1; i < trans.number; i++)
	{
		assert(GetSum(trans.filteredTransactions[i - 1]) >= GetSum(trans.filteredTransactions[i]));
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);
	cmp = CompareSum;
	trans = ServiceGetTransactions(serv, "os1nnnn", cmp);
	for (int i = 1; i < trans.number; i++)
	{
		assert(GetSum(trans.filteredTransactions[i - 1]) <= GetSum(trans.filteredTransactions[i]));
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);
	cmp = CompareDay;
	trans = ServiceGetTransactions(serv, "oz0nnnn", cmp);
	for (int i = 1; i < trans.number; i++)
	{
		assert(GetDay(trans.filteredTransactions[i - 1]) >= GetDay(trans.filteredTransactions[i]));
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);
	cmp = CompareDay;
	trans = ServiceGetTransactions(serv, "oz1nnnn", cmp);
	for (int i = 1; i < trans.number; i++)
	{
		assert(GetDay(trans.filteredTransactions[i - 1]) <= GetDay(trans.filteredTransactions[i]));
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	trans = ServiceGetTransactions(serv, "nnnt1nn", NULL);
	for (int i = 0; i < trans.number; i++)
	{
		assert(GetType(trans.filteredTransactions[i]) == 1);
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	trans = ServiceGetTransactions(serv, "nnnt0nn", NULL);
	for (int i = 0; i < trans.number; i++)
	{
		assert(GetType(trans.filteredTransactions[i]) == 0);
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	trans = ServiceGetTransactions(serv, "nnnnnnnna350", NULL);
	for (int i = 0; i < trans.number; i++)
	{
		assert(GetSum(trans.filteredTransactions[i]) >= 350);
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	trans = ServiceGetTransactions(serv, "nnnnnnnnb350", NULL);
	for (int i = 0; i < trans.number; i++)
	{
		assert(GetSum(trans.filteredTransactions[i]) <= 350);
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	trans = ServiceGetTransactions(serv, "nnnnnz23n", NULL);
	for (int i = 0; i < trans.number; i++)
	{
		assert(GetDay(trans.filteredTransactions[i]) == 23);
	}
	for (int i = 0; i < trans.number; i++)
	{
		DestroyTransaction(trans.filteredTransactions[i]);
	}
	free(trans.filteredTransactions);

	DestroyService(serv);
	// "0:o/n 1:s/z/n 2:1/0/n 3:t/n 4:1/0/n 6:a/b/n 7:n/________"
}

void TestServiceUndo() {
	transactionRepo* repo = CreateRepo();
	service* serv = CreateService(repo);
	ServiceAdd(serv, 1, 1, 200, 1, "Test");
	assert(RepoGetSize(serv->repo) == 1);
	assert(RepoGetSize(serv->undoList) == 1);

	int exitcode = Undo(serv);
	assert(exitcode == 0);
	assert(RepoGetSize(serv->repo) == 0);
	assert(RepoGetSize(serv->undoList) == 0);

	exitcode = Undo(serv);
	assert(exitcode == 1);

	ServiceAdd(serv, 1, 1, 200, 1, "Test");
	ServiceDelete(serv, 1);
	assert(RepoGetSize(serv->repo) == 0);
	assert(RepoGetSize(serv->undoList) == 2);

	exitcode = Undo(serv);
	assert(exitcode == 0);
	assert(RepoGetSize(serv->repo) == 1);
	assert(RepoGetSize(serv->undoList) == 1);

	ServiceUpdate(serv, 1, 2, 300, 0, "test");
	assert(RepoGetSize(serv->repo) == 1);
	assert(RepoGetSize(serv->undoList) == 2);

	exitcode = Undo(serv);
	assert(exitcode == 0);
	assert(RepoGetSize(serv->repo) == 1);
	assert(RepoGetSize(serv->undoList) == 1);

	ServiceAdd(serv, 2, 2, 200, 1, "Test");
	assert(RepoGetSize(serv->repo) == 2);
	assert(RepoGetSize(serv->undoList) == 2);

	ServiceAdd(serv, 3, 3, 200, 1, "Test");
	assert(RepoGetSize(serv->repo) == 3);
	assert(RepoGetSize(serv->undoList) == 3);

	DestroyService(serv);
}

void RunAllTests()
{
	TestTransaction();
	TestGetters();
	TestCompare();
	TestCopy();
	TestRepo();
	TestExpandRepo();
	TestAddInRepo();
	TestDeleteInRepo();
	TestUpdateInRepo();
	TestRepoGetTransactions();
	TestRepoGetSize();
	TestService();
	TestAddInService();
	TestDeleteInService();
	TestUpdateInService();
	TestServiceGetTransactionsNumber();
	TestServiceGetTransactions();
	TestServiceUndo();
}

