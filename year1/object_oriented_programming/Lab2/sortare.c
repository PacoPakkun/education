#include "sortare.h"

void sort(MateriePrimaRepo* l, FunctieComparare cmpF, int ordine) {
	//sorteaza un repozitoriu dupa o functie de comparare data ca parametru
	int i, j;
	if (ordine == 1) {
		for (i = 0; i < getLength(*l); i++) {
			for (j = i + 1; j < getLength(*l); j++) {
				MateriePrima p1 = l->repo[i];
				MateriePrima p2 = l->repo[j];
				if (cmpF(&p1, &p2) > 0) {
					l->repo[i] = p2;
					l->repo[j] = p1;
				}
			}
		}
	}
	else if (ordine == -1) {
		for (i = 0; i < getLength(*l); i++) {
			for (j = i + 1; j < getLength(*l); j++) {
				MateriePrima p1 = l->repo[i];
				MateriePrima p2 = l->repo[j];
				if (cmpF(&p1, &p2) < 0) {
					l->repo[i] = p2;
					l->repo[j] = p1;
				}
			}
		}
	}
}