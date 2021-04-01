#include <stdio.h>
#include <stdbool.h>
#include <math.h>

bool prim(int n) {
	if (n == 0) return false;
	if (n == 1) return false;
	for (int i = 2; i <= sqrt(n); i += 1) {
		if (n % i == 0) return false;
	}
	return true;
}

int main() {
	int n = 0;
	printf("Introduceti o valoare:\n");
	scanf_s("%d", &n);
	for (int i = 0; i < n; i += 1) {
		if (prim(i)) {
			printf("%d\n", i);
		}
	}
	return 0;
}