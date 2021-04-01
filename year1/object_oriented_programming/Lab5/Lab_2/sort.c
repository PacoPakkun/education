#include "transaction.h"

void sort(transaction** transactions, int size, int(*cmp)(transaction*, transaction*), int reversed)
{
	for (int i = 0; i < size; i++)
	{
		for (int j = i; j < size; j++)
		{
			if (reversed == 0)
			{
				if (cmp(transactions[i], transactions[j]) > 0)
				{
					transaction* aux = transactions[i];
					transactions[i] = transactions[j];
					transactions[j] = aux;
				}
			}
			else 
			{
				if (cmp(transactions[j], transactions[i]) > 0)
				{
					transaction* aux = transactions[i];
					transactions[i] = transactions[j];
					transactions[j] = aux;
				}
			}
			
		}
	}
}