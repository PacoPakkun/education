#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <algorithm>

using namespace std;

typedef struct nod {
	int vf;
	int w;
	int f;
};

int main(int argc, char* argv[]) {

	//citire fisier
	string in = argv[1];
	string out = argv[2];
	ifstream fin(in);
	ofstream fout(out);
	int n, c, d;
	fin >> n >> c >> d;
	vector<nod> graf[100];
	for (int i = 0; i < d; i++) {
		int x, y, z;
		fin >> x >> y >> z;
		graf[x].push_back(nod{ y,z,0 });
	}

	//initializare fluxuri
	int s = n, t = n - 1;
	for (int i = 0; i < c; i++) {
		graf[n].push_back(nod{ i,INT_MAX,0 }); //constructie supersursa
	}

	std::pair<int, int> h[100]{ std::pair<int, int>{0,0} }; //constructie inaltimi
	h[s].first = n;
	h[s].second = 0;
	for (nod& i : graf[s]) {
		i.f = i.w;
		h[i.vf].second = i.w;
		i.w -= i.f;
		bool found = false;
		for (nod& j : graf[i.vf])
			if (j.vf == s) {
				found = true;
				j.w += i.f;
			}
		if (!found)
			graf[i.vf].push_back(nod{ s,i.f,-1 }); //constructie graf rezidual
	}

	//push-relabel
	while (true) {
		bool ok = false;

		//pompare
		for (int i = 0; i < n; i++) {
			if (i != s && i != t && h[i].second > 0) //cautare nod supraincarcat
				for (nod& j : graf[i])
					if (j.w > 0 && h[j.vf].first + 1 == h[i].first) { //cautare nod adiacent de inaltime potrivita

						//modificare flux
						int aux = h[i].second;
						if (h[i].second > j.w) { //daca fluxul e mai mare decat capacitatea
							if (j.f == -1) { //daca muchia exista doar in graful rezidual
								for (nod& k : graf[j.vf])
									if (k.vf == i)
										k.f -= j.w;
							}
							else { //daca muchia exista in graful initial
								j.f += j.w;
							}
							h[j.vf].second += j.w;
							h[i].second -= j.w;
						}
						else { //daca capacitatea e suficenta
							if (j.f == -1) //daca muchia exista doar in graful rezidual
							{
								for (nod& k : graf[j.vf])
									if (k.vf == i)
										k.f -= h[i].second;
							}
							else { //daca muchia exista in graful initial
								j.f += h[i].second;
							}
							h[j.vf].second += h[i].second;
							h[i].second = 0;
						}

						//modificare capacitati
						j.w -= aux - h[i].second;
						bool found = false;
						for (nod& k : graf[j.vf])
							if (k.vf == i) {
								found = true;
								k.w += aux - h[i].second;
							}
						if (!found)
							graf[j.vf].push_back(nod{ i,aux - h[i].second,-1 });
						ok = true;
						break;
					}
			if (ok)
				break;
		}
		if (ok)
			continue;

		//inaltare
		for (int i = 0; i < n; i++) {
			if (i != s && i != t && h[i].second > 0) //cautare nod supraincarcat
				for (nod j : graf[i])
					if (h[j.vf].first >= h[i].first) { //cautare nod adiacent de inaltime mai mica
						h[i].first += 1; //ajustare inaltime
						ok = true;
						break;
					}
			if (ok)
				break;
		}
		if (ok)
			continue;
		break;
	}

	//afisare fisier
	int f = 0;
	for (nod vf : graf[s])
		f += vf.f;
	fout << f << "\n";
	for (nod vf : graf[s]) {
		fout << vf.f << " ";
	}
	fout << "\n";

	fin.close();
	fout.close();
}