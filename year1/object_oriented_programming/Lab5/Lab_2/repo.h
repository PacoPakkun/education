#pragma once
#include "transaction.h"

typedef void (*DestroyFunction)(void*);

typedef struct
{
	void** transactions;
	int arraySize;
	int usedSize;
} transactionRepo;

transactionRepo* ExpandSizeRepo(transactionRepo* repo);

transactionRepo* CreateRepo();

void DestroyList(transactionRepo* repo);

/*
	-transactionRepo* transactionRepo - the repo to destroy
	-DestroyFunction destFunc - function to destroy elements
*/
void DestroyRepo(transactionRepo* repo, DestroyFunction destFunc);

transactionRepo* CopyRepo(transactionRepo* repo);

transactionRepo* ExpandSize(transactionRepo* repo);

/*
	-transaction* transaction - the transaction to add
	-transactionRepo* transactionRepo - the repo in which to add
	-returns 0-if succesful, 1-if the repo is full, 2-if the id already existed
*/

int AddRepo(transactionRepo* toAdd, transactionRepo* transactionRepo);

int AddTransaction(transaction* toAdd, transactionRepo* transactionRepo);

/*
	-transactionRepo* transactionRepo - the repo from which to delete
	-deletes last entry in list
*/
void DeleteRepo(transactionRepo* repo);

/*
	-transactionRepo* transactionRepo - the repo from which to delete
	-int idToDelete - the i of the transaction to be deleted
	-returns 0-if successful, 1-if item did not exist
*/
int DeleteTransaction(int idToDelete, transactionRepo* transactionRepo);

/*
	-transaction* transaction - the transaction to add
	-transactionRepo* transactionRepo - the repo in which to add
	-returns 0-if succesful, 1-if the id did not exist
*/
int UpdateTransaction(transaction* newTransaction, transactionRepo* transactionRepo);

/*
	-transactionRepo* repo - the repo 
	-returns transaction** - a list of the repo's transactions, NULL-if empty
*/
transaction** RepoGetTransactions(transactionRepo* repo);

/*	
	-transactionRepo* repo - the repo 
	-returns int the number of transactions in the repo
*/
int RepoGetSize(transactionRepo* repo);