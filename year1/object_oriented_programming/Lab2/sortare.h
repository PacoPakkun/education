#ifndef MYSORT_H_
#define MYSORT_H_
#include "repo.h"

typedef int(*FunctieComparare)(MateriePrima* o1, MateriePrima* o2);

void sort(MateriePrimaRepo* l, FunctieComparare cmpF, int ordine);

#endif

