#include <stdio.h>
#include <math.h>

int prim(int n) {
	if (n == 0) return 0;
	if (n == 1) return 1;
	for (int i = 2; i <= sqrt(n); i += 1) {
		if (n % i == 0) return i;
	}
	return n;
}

int main() {
	int n;
	printf("Introduceti un nr de termeni:\n");
	scanf_s("%d", &n);
	printf("Sirul obtinut este:\n");
	for (int i = 1; i <= n; i += 1) {
		if (prim(i)==i) {
			for (int j = i; j > 0; j -= 1) {
				printf("%d ", j);
			}
		}
		else {
			printf("%d ", i);
			int aux = i;
			while (aux != 1) {
				int div = prim(aux);
				for (int j = 1; j <= div; j += 1) {
					printf("%d ", div);
				}
				while (div == prim(aux)) {
					aux = aux/prim(aux);
				}
			}
		}
		printf("\n");
	}
	return 0;
}