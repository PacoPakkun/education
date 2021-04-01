
#include "ui.h"
#include "service.h"
#include <string.h>
#include <stdlib.h>
#include <stdio.h>

int UiAddTransaction(service* service)
{
	int index = 0, exitCode, err = 0;
	int id, zi, suma, tip;
	char desc[200], ch;
	desc[0] = '\0';
	desc[199] = '\0';

	printf("Introduceti ID: ");
	scanf_s("%d", &id);
	while ((getchar()) != '\n');
	printf("\n");
	printf("Introduceti zi: ");
	scanf_s("%d", &zi);
	while ((getchar()) != '\n');
	printf("\n");
	printf("introduceti suma: ");
	scanf_s("%d", &suma);
	while ((getchar()) != '\n');
	printf("\n");
	printf("Introduceti tip (0 - intrare, 1 - iesire): ");
	scanf_s("%d", &tip);
	while ((getchar()) != '\n');
	printf("\n");
	printf("Introduceti descriere: ");

	while ((ch = (char)getchar()) != '\n')
	{
		if (index < 199)
		{
			desc[index] = ch;
			desc[index + 1] = '\0';
			index++;
		}
	}
	printf("\n");


	if (id < 1)
	{
		printf("ID invalid ");
		err = 1;
	}
	if (zi < 1 || zi > 31)
	{
		printf("Zi invalida ");
		err = 1;
	}
	if (suma < 1)
	{
		printf("Suma invalida ");
		err = 1;
	}
	if (tip != 0 && tip != 1)
	{
		printf("Tip invalid ");
		err = 1;
	}
	if (strlen(desc) == 0)
	{
		printf("Descriere invalida ");
		err = 1;
	}

	if (err == 1)
	{
		printf("\n");
		return 2;
	}
	else
	{
		exitCode = ServiceAdd(service, id, zi, suma, tip, desc);
		return exitCode;
	}
}

int UiDeleteTransaction(service* service)
{
	int id, exitCode, err = 0;
	printf("Introduceti ID-ul tranzactiei de sters: ");
	
	scanf_s("%d", &id);
	while ((getchar()) != '\n');

	if (id < 1)
	{
		printf("ID invalid ");
		err = 1;
	}
	if (err == 1)
	{
		printf("\n");
		return 2;
	}
	else
	{
		exitCode = ServiceDelete(service, id);
		return exitCode;
	}
}

int UiUpdateTransaction(service* service)
{
	int id, tip, suma, zi, exitCode, err = 0;
	char desc[200], ch;
	desc[0] = '\0';
	desc[199] = '\0';
	printf("Introduceti ID-ul tranzactiei de modificat: ");

	scanf_s("%d", &id);
	while ((getchar()) != '\n');

	if (id < 1)
	{
		printf("ID invalid ");
		err = 1;
	}
	printf("\n");

	printf("Introduceti zi: ");
	scanf_s("%d", &zi);
	while ((getchar()) != '\n');
	printf("\n");
	printf("introduceti suma: ");
	scanf_s("%d", &suma);
	while ((getchar()) != '\n');
	printf("\n");
	printf("Introduceti tip (0 - intrare, 1 - iesire): ");
	scanf_s("%d", &tip);
	while ((getchar()) != '\n');
	printf("\n");
	printf("Introduceti descriere: ");

	int index = 0;
	while ((ch = (char)getchar()) != '\n')
	{
		if (index < 199)
		{
			desc[index] = ch;
			desc[index + 1] = '\0';
			index++;
		}
	}
	printf("\n");
	if (zi < 1 || zi > 31)
	{
		printf("Zi invalida ");
		err = 1;
	}
	if (suma < 1)
	{
		printf("Suma invalida ");
		err = 1;
	}
	if (tip != 0 && tip != 1)
	{
		printf("Tip invalid ");
		err = 1;
	}
	if (strlen(desc) == 0)
	{
		printf("Descriere invalida ");
		err = 1;
	}

	if (err == 1)
	{
		printf("\n");
		return 2;
	}
	else
	{
		exitCode = ServiceUpdate(service, id, zi, suma, tip, desc);
		return exitCode;
	}
}

char* UiChangeFilter(char* filter)
{
	printf("Schimbare filtru\n");
	while (1) {
		printf("Introduceti 'o' pentru sortare sau 'n' altfel: ");
		filter[0] = (char)getchar();
		while ((getchar()) != '\n');
		if (filter[0] == 'o')
		{
			while (1)
			{
				printf("Alegeti criteriul de sortare('s' - suma, 'z' - zi): ");
				filter[1] = (char)getchar();
				while (((char)getchar()) != '\n');
				if (filter[1] == 's' || filter[1] == 'z')
				{
					break;
				}
				else
				{
					printf("Caracter invalid!\n");
				}
			}

			while (1)
			{
				printf("Alegeti modul de sortare('1' - crescator, '0' - descrescator): ");
				filter[2] = (char)getchar();
				while (((char)getchar()) != '\n');
				if (filter[2] == '1' || filter[2] == '0')
				{
					break;
				}
				else
				{
					printf("Caracter invalid!\n");
				}
			}

			break;
		}
		else if (filter[0] == 'n')
		{
			filter[1] = 'n';
			filter[2] = 'n';
			break;
		}
		else
		{
			printf("Caracter invalid!\n");
		}
	}

	while (1)
	{
		printf("Introduceti 't' pentru filtrare dupa tip sau 'n' altfel: ");
		filter[3] = (char)getchar();
		while (((char)getchar()) != '\n');
		if (filter[3] == 't')
		{
			printf("Alegeti tipul(1 - iesire, 0 - intrare): ");
			filter[4] = (char)getchar();
			while (((char)getchar()) != '\n');
			if (filter[4] == '1' || filter[4] == '0')
			{
				break;
			}
			else
			{
				printf("Caracter invalid!\n");
			}
		}
		if( filter[3] == 'n')
		{
			filter[4] = 'n';
			break;
		}
		else
		{
			printf("Caracter invalid!\n");
		}
	}

	while (1)
	{
		printf("Alegeti tipul de filtrare dupa suma(a - peste o suma, b - sum o suma, n - fara): ");
		filter[8] = (char)getchar();
		while (((char)getchar()) != '\n');
		if (filter[8] == 'a' || filter[8] == 'b')
		{
			while (1)
			{
				char aux;
				int i = 0, ok = 1;
				printf("Introduceti un numar pozitiv de maxim 8 cifre: ");
				while ((aux = (char)getchar()) != '\n')
				{
					if (aux < '0' || aux>'9')
					{
						printf("Caracter invalid!\n");
						ok = 0;
						break;
					}
					if (i < 8)
					{
						filter[9 + i] = aux;
						i++;
					}
				}
				if (ok)
				{
					filter[9+i] = '\0';
					break;
				}
			}
			break;
		}
		else if (filter[8] == 'n')
		{
			filter[9] = 'n';
			filter[10] = '\0';
			break;
		}
		else
		{
			printf("Caracter invalid!\n");
		}
	}

	while (1)
	{
		printf("Introduceti 'z' pentru filtrare dupa zi sau 'n' altfel: ");
		filter[5] = (char)getchar();
		while (((char)getchar()) != '\n');
		if (filter[5] == 'z')
		{
			while (1)
			{
				char aux;
				int i = 0, ok = 1;
				printf("Introduceti un numar pozitiv de maxim 2 cifre: ");
				while ((aux = (char)getchar()) != '\n')
				{
					if (aux < '0' || aux>'9')
					{
						printf("Caracter invalid!\n");
						ok = 0;
						break;
					}
					if (i < 2)
					{
						filter[6 + i] = aux;
						i++;
					}
				}
				if (ok)
				{
					filter[6 + i] = '\0';
					int day = 0;
					for (unsigned int j = 6; j <= 7; j++)
					{
						if (filter[j] != '\0') {
							day *= 10;
							day += filter[j] - '0';
						}
					}
					if (day < 1 || day > 31) {
						printf("Zi invalida!\n");
					}
					else break;
				}
			}
			break;
		}
		else if (filter[8] == 'n')
		{
			filter[9] = 'n';
			filter[10] = '\0';
			break;
		}
		else
		{
			printf("Caracter invalid!\n");
		}
	}

	return filter;
}

void UiPrintTransactions(service* service, char* filter)
{
	// "0:o/n 1:s/z/n 2:1/0/n 3:t/n 4:1/0/n 5:a/b/n 6:n/________"

	printf("\nTranzactii:\n");
	pair result;
	if (filter[1] == 's')
	{
		int (*cmp)(transaction*, transaction*) = CompareSum;
		result = ServiceGetTransactions(service, filter, cmp);
	}
	else if(filter[1]=='z')
	{
		int (*cmp)(transaction*, transaction*) = CompareDay;
		result = ServiceGetTransactions(service, filter, cmp);
	}
	else
	{
		result = ServiceGetTransactions(service, filter, NULL);
	}
	for (int i = 0; i < result.number; i++)
	{
		printf("ID: %d Zi: %d, Suma: %d, Tip: %d, Desc: %s\n", result.filteredTransactions[i]->id, result.filteredTransactions[i]->day, result.filteredTransactions[i]->sum, result.filteredTransactions[i]->type, result.filteredTransactions[i]->description);
		DestroyTransaction(result.filteredTransactions[i]);
	}
	free(result.filteredTransactions);
}

int UIUndo(service* serv) {
	int exitcode = Undo(serv);
	return exitcode;
}

void UiRun(service* serv)
{
	int exitCode;
	char comanda;
	char* filtru = (char*)malloc(sizeof(char) * 15);
	if (filtru == NULL)
	{
		printf("Eroare in memorie\n");
	}
	strcpy_s(filtru, 15, "nnnnnnn");

	while (1)
	{

		printf("Lista comenzi:\n");
		printf("0 - Iesire\n");
		printf("1 - Adaugare tranzactie\n");
		printf("2 - Stergere tranzactie\n");
		printf("3 - Modificare tranzactie\n");
		printf("4 - Modificare filtru\n");
		printf("5 - Undo\n");

		printf("Introduceti codul comenzii: ");
		scanf_s(" %c", &comanda, 1);
		while ((getchar()) != '\n');
		printf("\n");
		if (comanda == '0')
		{
			free(filtru);
			DestroyService(serv);
			printf("Exiting");
			break;
		}
		if (comanda == '1')
		{
			exitCode = UiAddTransaction(serv);
			if (exitCode == 0)
			{
				printf("Succes!\n");
			}
			else if (exitCode == 1)
			{
				printf("ID-ul introdus exista deja!\n");
			}
		}
		else if (comanda == '2')
		{
			exitCode = UiDeleteTransaction(serv);
			if (exitCode == 0)
			{
				printf("Succes!\n");
			}
			else if (exitCode == 1)
			{
				printf("Tranzactia nu exista!\n");
			}
		}
		else if (comanda == '3')
		{
			exitCode = UiUpdateTransaction(serv);
			if (exitCode == 0)
			{
				printf("Succes!\n");
			}
			else if (exitCode == 1)
			{
				printf("ID-ul nu exista!\n");
			}
		}
		else if (comanda == '4')
		{
			filtru = UiChangeFilter(filtru);
		}
		else if (comanda == '5')
		{
			exitCode = UIUndo(serv);
			if (exitCode == 0)
			{
				printf("Succes!\n");
			}
			else if (exitCode == 1)
			{
				printf("Nu exista operatii anterioare!\n");
			}
		}
		else
		{
			printf("Comanda invalida!\n");
		}

		printf("Filtru curent: %s", filtru);
		UiPrintTransactions(serv, filtru);
	}
}
