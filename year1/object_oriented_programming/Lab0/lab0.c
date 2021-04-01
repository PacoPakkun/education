#include <stdio.h>
int main(){
	int n = 0;
	int sum = 0;
	int var = 0;
	scanf_s("%d", &n);
	for (int i = 0; i < n; i += 1) {
		scanf_s("%d", &var);
		sum += var;
	}
	printf("Suma este %d", sum);
	return 0;
}