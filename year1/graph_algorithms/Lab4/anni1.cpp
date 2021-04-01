#include <algorithm>
#include <vector>
#include "main.h"

using namespace std;

vector<int> codare_prufer(vector<int> T, int N) {
	vector<int> P; // secventa prufer, rezultatul final
	unsigned int min;
	// cat timp exista mai multe elemente decat radacina in vector
	while (N > 1) {
		// aflam frunza minima
		min = T.size();
		for (unsigned int node = 0; node < T.size(); node++) {
			if (T.at(node) != INT_MIN && find(T.begin(), T.end(), node) == T.end() && node < min) {
				min = node;
			}
		}
		// adaugam predecesorul ei in vector
		P.push_back(T.at(min));
		// "stergem" frunza din vector
		T.at(min) = INT_MIN;
		N--;
	}
	return P;
}

int main2() {
	vector<int> T;
	int N;
	citire_fisier(T, N);
	auto P = codare_prufer(T, N);
	afisare_fisier(P);
	return 0;
}