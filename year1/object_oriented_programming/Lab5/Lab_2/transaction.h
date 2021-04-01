#pragma once
#include <stdio.h>

typedef struct
{
	int id;
	int day;
	int sum;
	int type;
	char description[200];
} transaction;

/*
	-Creates a transaction
	-int id > 0
	-int day <31 && >0
	-int sum >0
	-int type 0-incoming, 1-outgoing
	-char* desc - a string with less that 200 characters
	-returns transaction* pointer to the transaction
*/
transaction* CreateTransaction(int id, int day, int sum, int type, char* desc);

/*
	-transaction* trans - a transaction
*/
void DestroyTransaction(transaction* trans);

transaction* CopyTransaction(transaction* trans);


/*
	-transaction* trans - a transaction
	-returns int - the transaction's id
*/
int GetId(transaction* transaction);

/*
	-transaction* trans - a transaction
	-returns int - the transaction's day
*/
int GetDay(transaction* transaction);

/*
	-transaction* trans - a transaction
	-returns int - the transaction's sum
*/
int GetSum(transaction* transaction);

/*
	-transaction* trans - a transaction
	-returns int - the transaction's type
*/
int GetType(transaction* transaction);

/*
	-transaction* trans - a transaction
	-returns char* - the transaction's description
*/
char* GetDesc(transaction* transaction);

/*
	-transaction* trans - a transaction
	-returns int 0 if equal, positive if a>b, negative if a<b
*/
int CompareSum(transaction* a, transaction* b);

/*
	-transaction* trans - a transaction
	-returns int 0 if equal, positive if a>b, negative if a<b
*/
int CompareDay(transaction* a, transaction* b);

