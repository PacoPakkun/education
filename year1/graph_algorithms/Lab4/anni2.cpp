#include <algorithm>
#include <vector>
#include "main.h"

using namespace std;

vector<int> decodare_prufer(vector<int> P, int M) {
	vector<int> T;
	for (int i = 0; i < 100; i++) {
		T.push_back(-1);
	}
	int x;
	int y;
	for (int i = 0; i < M; i++) {
		x = P.at(0); 
		y = 0; 
		while (find(P.begin(), P.end(), y) != P.end()) {
			y++;
		}
		T.at(y) = x;
		P.erase(P.begin());
		P.push_back(y);
	}
	auto radacina = find(T.begin(), T.end(), -1);
	while (find(radacina + 1, T.end(), -1) != T.end()) {
		T.erase(find(radacina + 1, T.end(), -1));
	}
	return T;
}

int main3() {
	vector<int> P;
	int M;
	citire_fisier(P, M);
	auto T = decodare_prufer(P, M);
	afisare_fisier(T);
	return 0;
}