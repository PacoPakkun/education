#include "transaction.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

transaction* CreateTransaction(int id, int day, int sum, int type, char* desc)
{
	transaction* trans = (transaction*)malloc(sizeof(transaction));
	/*if (trans == NULL)
	{
		return NULL;
	}*/
	trans->id = id;
	trans->day = day;
	trans->sum = sum;
	trans->type = type;
	strncpy_s(trans->description, strlen(desc) + 1, desc, _TRUNCATE);

	return trans;
}

int GetId(transaction* transaction)
{
	return transaction->id;
}

int GetDay(transaction* transaction)
{
	return transaction->day;
}

int GetSum(transaction* transaction)
{
	return transaction->sum;
}

int GetType(transaction* transaction)
{
	return transaction->type;
}

char* GetDesc(transaction* transaction)
{
	return transaction->description;
}

void DestroyTransaction(transaction* transaction)
{
	free(transaction);
}

int CompareSum(transaction* a, transaction* b)
{
	return a->sum - b->sum;
}

int CompareDay(transaction* a, transaction* b)
{
	return a->day - b->day;
}

transaction* CopyTransaction(transaction* trans) {
	transaction* copie = CreateTransaction(GetId(trans), GetDay(trans), GetSum(trans), GetType(trans), GetDesc(trans));
	return copie;
}