#pragma once
#include "transaction.h"

typedef struct 
{
	transactionRepo* repo;
	transactionRepo* undoList;
}service;

typedef struct
{
	transaction** filteredTransactions;
	int number;
} pair;

/*
	-transactionRepo TRepo - the repo on which the service will operate
	-returns service* service
*/
service* CreateService(transactionRepo* TRepo);

/*
	-service* service - the service to destroy
*/
void DestroyService(service* service);

/*
	-service* service - the curent service
	-int TId > 0
	-int TDay <31 && >0
	-int TSum >0
	-int TType 0-incoming, 1-outgoing
	-char* TDesc - a string with less that 200 characters
	-returns 0-if succesful, 1-if the id already existed
*/
int ServiceAdd(service* serv, int TId, int TDay, int TSum, int TType, char* TDescription);

/*
	-service* service - the curent service
	-int id - the i of the transaction to be deleted
	-returns 0-if successful, 1-if item did not exist
*/
int ServiceDelete(service* serv, int id);

/*
	-service* transaction - the transaction to add
	-int TId > 0 - the id of the transaction to be updated
	-int TDay <31 && >0
	-int TSum >0
	-int TType 0-incoming, 1-outgoing
	-char* TDesc - a string with less that 200 characters
	-returns 0-if succesful, 1-if the id did not exist
*/
int ServiceUpdate(service* serv, int id, int day, int sum, int type, char* description);

/*
	-service* service
	-returns int the number of tranzactions in the working repo
*/
int ServiceGetTransactionNumber(service* service);

/*
	-service* repo - the repo
	-char* filter - the filter to apply
	*The filter format should be: 
	*n/o s/z/n 0/1/n:  o-for ordered list, n-otherwise; s-ordered by sum, z-ordered by day, n-no sorting; 1-ascending, 0-descending, n-no sorting; 
	*n/t 0/1/n: t-filter by type; n-not aplied; 0-incoming only, 1-outgoing only, n-no filter
	*a/b/n n/...: a-filter by sum above;b-filter by sum below, n-not aplied; n-no filter, ........-a four digit number to compare against
	*n/z n/...: z-filter by day; n-otherwise; n-no filter; ...-day value
	-int* cmp
	-returns pair - struct which contains a list of the repo's transactions, NULL-if empty, and the number of transactions
*/
pair ServiceGetTransactions(service* service, char* filter, int(* cmp)(transaction*, transaction*));


/*
	-service* service - the curent service
	-returns 0-if succesful, 1-if no further undo operations can be made
*/
int Undo(service* service);