#include <string.h>
#include <stdlib.h>
#include "repo.h"
#include "service.h"
#include "transaction.h"
#include"sort.h"

service* CreateService(transactionRepo* TRepo)
{
	service* serv = malloc(sizeof(service));
	/*if (serv == NULL)
	{
		return NULL;
	}*/
	serv->repo = TRepo;
	serv->undoList = CreateRepo();
	return serv;
}

void DestroyService(service* service)
{
	DestroyRepo(service->repo, DestroyTransaction);
	DestroyRepo(service->undoList, DestroyList);
	free(service);
}

int ServiceAdd(service* serv, int TId, int TDay, int TSum, int TType, char* TDescription)
{
	int exitCode;
	transaction* t = CreateTransaction(TId, TDay, TSum, TType, TDescription);
	exitCode = AddRepo(serv->repo, serv->undoList);
	if (exitCode == 1)
	{
		serv->undoList = ExpandSizeRepo(serv->undoList);
		exitCode = AddRepo(serv->repo, serv->undoList);
	}
	exitCode = AddTransaction(t, serv->repo);
	if (exitCode == 1)
	{
		serv->repo = ExpandSize(serv->repo);
		exitCode = AddTransaction(t, serv->repo);
	}	
	DestroyTransaction(t);
	if (exitCode == 0)
	{
		return 0;}
	else
	{
		return 1;
	}
	//return 1;
}

int ServiceDelete(service* serv, int id)
{
	int exitCode;
	exitCode = AddRepo(serv->repo, serv->undoList);
	if (exitCode == 1)
	{
		serv->undoList = ExpandSizeRepo(serv->undoList);
		exitCode = AddRepo(serv->repo, serv->undoList);
	}
	exitCode = DeleteTransaction(id, serv->repo);

	return exitCode;
}

int ServiceUpdate(service* serv, int id, int day, int sum, int type, char* description)
{
	int exitCode;
	transaction* t = CreateTransaction(id, day, sum, type, description);
	exitCode = AddRepo(serv->repo, serv->undoList);
	if (exitCode == 1)
	{
		serv->undoList = ExpandSizeRepo(serv->undoList);
		exitCode = AddRepo(serv->repo, serv->undoList);
	}
	exitCode = UpdateTransaction(t, serv->repo);
	DestroyTransaction(t);
	if (exitCode == 0)
	{
		return 0;}
	else
	{
		return 1;}
	//return 1;
}

int ServiceGetTransactionNumber(service* service)
{
	int size;
	size = RepoGetSize(service->repo);
	return size;
}

pair ServiceGetTransactions(service* service, char* filter, int(* cmp)(transaction*, transaction*))
{
	transaction** transactions;
	transactions = RepoGetTransactions(service->repo);
	int size, skip, sum = 0, day = 0;
	if (filter[8] != 'n')
	{
		for (unsigned int i = 9; i < strlen(filter); i++)
		{
			sum *= 10;
			sum += filter[i] - '0';
		}
	}
	if (filter[5] != 'n') {
		for (unsigned int i = 6; i <= 7; i++)
		{
			if (filter[i] != '\0') {
				day *= 10;
				day += filter[i] - '0';
			}
		}
	}
	size = RepoGetSize(service->repo);
	if (filter[0] != 'n')
	{
		if (filter[2] == '1')
		{
			sort(transactions, size, cmp, 0);
		}
		if (filter[2] == '0')
		{
			sort(transactions, size, cmp, 1);
		}
		
	}

	for (int i = 0; i < size; i++)
	{
		skip = 0;
		if (filter[3] != 'n')
		{
			int type = filter[4] - '0';
			if (GetType(transactions[i]) != type)
			{
				skip = 1;
			}
		}

		if (filter[8] == 'a')
		{
			if (GetSum(transactions[i]) <= sum)
			{
				skip = 1;
			}
		}
		if (filter[8] == 'b')
		{
			if (GetSum(transactions[i]) >= sum)
			{
				skip = 1;
			}
		}
		if (filter[5] != 'n') {
			if (GetDay(transactions[i]) != day) {
				skip = 1;
			}
		}
		if (skip == 1)
		{
			DestroyTransaction(transactions[i]);
			size--;
			for (int j = i; j < size; j++)
			{
				transactions[j] = transactions[j + 1];
			}
			i--;
		}
	}

	pair result;
	result.filteredTransactions = transactions;
	result.number = size;

	return result;
}

int Undo(service* service) {
	if (service->undoList->usedSize == 0) {
		return 1;}
	else {
		DestroyRepo(service->repo, DestroyTransaction);
		service->repo = CopyRepo(service->undoList->transactions[service->undoList->usedSize-1]);
		DeleteRepo(service->undoList);
		return 0;
	}
}