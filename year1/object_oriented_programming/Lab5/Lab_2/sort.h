#include "transaction.h"

void sort(transaction** transactions, int size, int(*cmp)(transaction*, transaction*), int reversed);