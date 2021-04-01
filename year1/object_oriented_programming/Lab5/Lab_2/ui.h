#pragma once
#include "repo.h"
#include "service.h"

int UiAddTransaction(service* service);

int UiDeleteTransaction(service* service);

int UiUpdateTransaction(service* service);

char* UiChangeFilter(char* filter);

void UiPrintTransactions(service* service, char* filter);

int UIUndo(service* serv);

void UiRun(service* service);