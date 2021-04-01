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
	//ford-fulkerson
	int s = 0, t = n - 1;
	while (true) {
		//bfs
		queue<int> q;
		bool viz[100]{ false };
		std::pair<int, int> pred[100];
		bool found = false;
		q.push(s);
		viz[s] = true;
		pred[s].first = -1;
		pred[s].second = 10000;
		while (!q.empty()) {
			int vf = q.front();
			q.pop();
			for (nod i : graf[vf]) {
				if (!viz[i.vf] && i.w != 0) {
					q.push(i.vf);
					viz[i.vf] = true;
					pred[i.vf].first = vf;
					if (pred[vf].second > i.w)
						pred[i.vf].second = i.w;
					else
						pred[i.vf].second = pred[vf].second;
					if (i.vf == t) {
						found = true;
						break;
					}
				}
			}
		}
		if (!found)
			break;
		//reflux
		int min = pred[t].second;
		int vf = t;
		while (vf != s) {
			int p = pred[vf].first;
			nod* e = nullptr;
			for (nod& i : graf[p])
				if (i.vf == vf)
				{
					e = &i;
					break;
				}
			e->f += min;
			e->w -= min;
			found = false;
			for (nod& i : graf[vf])
				if (i.vf == p)
				{
					e = &i;
					found = true;
					break;
				}
			if (found)
				e->w += min;
			else
				graf[vf].push_back(nod{ p,min,0 });
			vf = p;
		}
	}
	//afisare fisier
	int f = 0;
	for (nod vf : graf[s])
		f += vf.f;
	fout << f << "\n";
	fin.close();
	fout.close();
}