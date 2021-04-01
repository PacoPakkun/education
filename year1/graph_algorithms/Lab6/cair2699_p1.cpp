#include <iostream>
#include <fstream>
#include <vector>
#include <stack>

using namespace std;

//kempe
int main(int argc, char* argv[]) {

	//citire fisier, initializare culori
	string in = argv[1];
	string out = argv[2];
	ifstream fin(in);
	ofstream fout(out);
	int n, m;
	vector<vector<int>> graf;
	vector<bool> viz;
	vector<int> culori;
	fin >> n >> m;
	for (int i = 0; i < n; i++) {
		vector<int> v;
		graf.push_back(v);
		culori.push_back(0);
		viz.push_back(false);
	}
	for (int i = 0; i < m; i++) {
		int x, y;
		fin >> x >> y;
		graf.at(x).push_back(y);
		graf.at(y).push_back(x);
	}

	//constructie stiva in ordine crescatoare a gradurilor
	stack<int> s;
	while (s.size() != n) {
		int min = INT_MAX;
		int vf = -1;
		for (int i = 0; i < n; i++) {
			if (!viz.at(i) && graf.at(i).size() < min) {
				min = graf.at(i).size();
				vf = i;
			}
		}
		viz.at(vf) = true;
		s.push(vf);
	}

	//kempe
	int c = 0; //pornim cu nr cromatic 0
	while (!s.empty()) {
		int vf = s.top();
		bool found = false;
		for (int i = 1; i <= c; i++) {
			bool ok = true;
			for (int v : graf.at(vf)) {
				if (culori.at(v) == i) {
					ok = false;
					break;
				}
			}
			if (ok) { //daca vf poate avea culoarea i
				culori.at(vf) = i;
				found = true;
				break;
			}
		}
		if (found) //daca am gasit culoare pt vf
			s.pop();
		else { //daca nu am gasit culoare, incrementam nr cromatic
			c++;
			culori.at(vf) = c;
			s.pop();
		}
	}

	//afisare fisier
	fout << c << "\n";
	for (int i = 0; i < n; i++)
		fout << culori.at(i) << " ";
	fout << "\n";
	fin.close();
	fout.close();
	return 0;
}