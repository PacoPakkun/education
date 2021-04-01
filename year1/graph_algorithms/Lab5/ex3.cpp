#include <iostream>
#include <fstream>
#include <vector>
#include <algorithm>
#include <stack>

using namespace std;

int main(int argc, char* argv[]) {
    //citire fisier
    string in = argv[1];
    string out = argv[2];
    ifstream fin(in);
    ofstream fout(out);
    int n, m;
    fin >> n >> m;
    vector<int> graf[10000];
    for (int i = 0; i < m; i++) {
        int x, y;
        fin >> x >> y;
        graf[x].push_back(y);
        graf[y].push_back(x);
    }
    //fleury
    int euler[10000];
    euler[0] = 0;
    int k = 1, v = 0;
    while (m != 0) {
        int i = 0;
        for (int j : graf[v]) {
            i = j;
            bool bridge = true;
            graf[v].erase(std::find(graf[v].begin(), graf[v].end(), i));
            graf[i].erase(std::find(graf[i].begin(), graf[i].end(), v));
            stack<int> s;
            int viz[10000]{ 0 };
            s.push(v);
            viz[v] = 1;
            while (!s.empty()) {
                int vf = s.top();
                s.pop();
                for (int aux : graf[vf]) {
                    if (viz[aux] == 0) {
                        s.push(aux);
                        viz[aux] = 1;
                        if (aux == i)
                            bridge = false;
                    }
                }
            }
            graf[v].push_back(i);
            graf[i].push_back(v);
            if (!bridge) {
                break;
            }
        }
        euler[k] = i;
        k++;
        graf[v].erase(std::find(graf[v].begin(), graf[v].end(), i));
        graf[i].erase(std::find(graf[i].begin(), graf[i].end(), v));
        m--;
        v = i;
    }
    //afisare fisier
    for (int i = 0; i < k - 2; i++)
        fout << euler[i] << "->";
    fout << euler[k - 2] << "\n";
    fin.close();
    fout.close();
    return 0;
}