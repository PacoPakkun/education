#include "repo.h"
#include <stdlib.h>
#include <string.h>
#include "transaction.h"

transactionRepo* CreateRepo()
{
	transactionRepo* repo = (transactionRepo*)malloc(sizeof(transactionRepo));
	/*if (repo == NULL)
	{
		return NULL;
	}*/
	repo->usedSize = 0;
	repo->arraySize = 1;
	repo->transactions = (void**)malloc(sizeof(void*));
	return repo;
}

void DestroyList(transactionRepo* repo) {
	for (int i = 0; i < repo->usedSize; i++)
	{
		DestroyTransaction(repo->transactions[i]);
	}
	free(repo->transactions);
	free(repo);
}

void DestroyRepo(transactionRepo* repo, DestroyFunction destFunc)
{
	for (int i = 0; i < repo->usedSize; i++)
	{
		destFunc(repo->transactions[i]);
	}
	free(repo->transactions);
	free(repo);
}

transactionRepo* CopyRepo(transactionRepo* repo) {
	transactionRepo* copie = CreateRepo();
	for (int i = 0; i < repo->usedSize; i++) {
		AddTransaction(repo->transactions[i],copie);
	}
	return copie;
}

int AddRepo(transactionRepo* toAdd, transactionRepo* trans)
{
	if (trans->usedSize == trans->arraySize)
		return 1;
	else
	{
		transactionRepo* t;
		t = CopyRepo(toAdd);
		trans->transactions[trans->usedSize] = t;
		trans->usedSize++;
		return 0;
	}
}

int AddTransaction(transaction* toAdd, transactionRepo* transactionRepo)
{
	if (transactionRepo->usedSize == transactionRepo->arraySize)
		return 1;
	else
	{
		for (int i = 0; i < transactionRepo->usedSize; i++)
		{
			if (GetId((transactionRepo->transactions[i])) == toAdd->id)
			{
				return 2;
			}
		}
		transaction* t;
		t = CreateTransaction(GetId(toAdd), GetDay(toAdd), GetSum(toAdd), GetType(toAdd), GetDesc(toAdd));
		transactionRepo->transactions[transactionRepo->usedSize] = t;
		transactionRepo->usedSize++;
		return 0;
	}
}

void DeleteRepo(transactionRepo* repo) {
	DestroyRepo(repo->transactions[repo->usedSize-1], DestroyTransaction);
	repo->usedSize--;
}

int DeleteTransaction(int idToDelete, transactionRepo* repo)
{
	for (int i = 0; i < repo->usedSize; i++)
	{
		if (GetId(repo->transactions[i]) == idToDelete)
		{
			DestroyTransaction(repo->transactions[i]);
			for (int j = i; j < repo->usedSize - 1; j++)
			{
				repo->transactions[j] = repo->transactions[j + 1];
			}
			repo->usedSize--;

			return 0;
		}
	}
	return 1;
}

int UpdateTransaction(transaction* newTransaction, transactionRepo* transactionRepo)
{
	for (int i = 0; i < transactionRepo->usedSize; i++)
	{
		if (GetId(transactionRepo->transactions[i]) == GetId(newTransaction))
		{
			DestroyTransaction(transactionRepo->transactions[i]);
			transaction * t;
			t = CreateTransaction(GetId(newTransaction), GetDay(newTransaction), GetSum(newTransaction), GetType(newTransaction), GetDesc(newTransaction));
			transactionRepo->transactions[i] = t;
			return 0;}}
	return 1;
}

transactionRepo* ExpandSizeRepo(transactionRepo* repo)
{
	repo->arraySize *= 2;
	void** temp = malloc(repo->arraySize * sizeof(void*));
	/*if (temp == NULL)
	{
		return NULL;
	}*/
	for (int i = 0; i < repo->usedSize; i++)
	{
		temp[i] = CopyRepo(repo->transactions[i]);
		free(repo->transactions[i]);
	}
	free(repo->transactions);
	repo->transactions = temp;
	return repo;
}

transactionRepo* ExpandSize(transactionRepo* repo)
{
	void** temp;
	temp = (void**)malloc(repo->arraySize * 2 * sizeof(void*));
	/*if (temp == NULL)
	{
		return NULL;
	}*/
	for (int i = 0; i < repo->arraySize; i++)
	{
		temp[i] = repo->transactions[i];
	}
	free(repo->transactions);
	repo->transactions = temp;
	repo->arraySize *= 2;
	return repo;
}

transaction** RepoGetTransactions(transactionRepo* repo)
{
	transaction** temp;

	if (repo->usedSize == 0)
	{
		return NULL;
	}

	temp = (transaction**)malloc(sizeof(transaction*) * repo->usedSize);
	/*if (temp == NULL)
	{
		return NULL;
	}*/
	for (int i = 0; i < repo->usedSize; i++)
	{
		int id, day, sum, type;
		char desc[200];
		id = GetId(repo->transactions[i]);
		day = GetDay(repo->transactions[i]);
		sum = GetSum(repo->transactions[i]);
		type = GetType(repo->transactions[i]);
		strcpy_s(desc, strlen(GetDesc(repo->transactions[i])) + 1, GetDesc(repo->transactions[i]));
		temp[i] = CreateTransaction(id, day, sum, type, desc);
	}
	return temp;
}

int RepoGetSize(transactionRepo* repo)
{
	return repo->usedSize;
}
