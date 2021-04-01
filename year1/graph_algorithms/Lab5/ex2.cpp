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
	int n, m;
	fin >> n >> m;
	vector<nod> graf[100];
	for (int i = 0; i < m; i++) {
		int x, y, z;
		fin >> x >> y >> z;
		graf[x].push_back(nod{ y,z,0 });
	}
	//pompare preflux
	int s = 0, t = n - 1;
	std::pair<int, int> h[100]{ std::pair<int, int>{0,0} };
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
			graf[i.vf].push_back(nod{ s,i.f,-1 });
	}
	while (true) {
		bool ok = false;
		//pompare
		for (int i = 0; i < n; i++) {
			if (i != s && i != t && h[i].second > 0)
				for (nod& j : graf[i])
					if (j.w > 0 && h[j.vf].first + 1 == h[i].first) {
						int aux = h[i].second;
						if (h[i].second > j.w) {
							if (j.f == -1) {
								for (nod& k : graf[j.vf])
									if (k.vf == i)
										k.f -= j.w;
							}
							else {
								j.f += j.w;
							}
							h[j.vf].second += j.w;
							h[i].second -= j.w;
						}
						else {
							if (j.f == -1)
							{
								for (nod& k : graf[j.vf])
									if (k.vf == i)
										k.f -= h[i].second;
							}
							else {
								j.f += h[i].second;
							}
							h[j.vf].second += h[i].second;
							h[i].second = 0;
						}
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
			if (i != s && i != t && h[i].second > 0)
				for (nod j : graf[i])
					if (h[j.vf].first >= h[i].first) {
						h[i].first += 1;
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
	fin.close();
	fout.close();
}