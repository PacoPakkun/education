#include <iostream>
#include <fstream>

using namespace std;

int main(int argc, char* argv[]) {
    //citire fisier
    int p[20] = { 0 };
    int n = 0, v = 0;
    string in = argv[1];
    string out = argv[2];
    ifstream fin(in.c_str());
    ofstream fout(out.c_str());
    fin >> n;
    for (int i = 0; i < n; i++)
        fin >> p[i];
    //codare prufer
    fout<<n-1<<"\n";
    for (int i = 0; i < n; i++) {
        bool ok = true;
        for (int j = 0; j < n; j++)
            if (p[j] == i) {
                ok = false;
                break;
            }
        if (ok == true) {
            v = i;
            break;
        }
    }
    fout << p[v] << " ";
    for (int i = 0; i < n - 2; i++) {
        bool ok = true;
        for (int j = 0; j < n; j++)
            if (j != v && p[j] == p[v]) {
                ok = false;
                break;
            }
        if (ok == true) {
            if (p[v] < v) {
                int aux = p[v];
                p[v] = -2;
                v = aux;
                fout << p[v] << " ";
            }
            else {
                int aux = p[v];
                p[v] = -2;
                v = aux;
                for (int j = v + 1; j < n; j++) {
                    if (p[j] != -2) {
                        ok = true;
                        for (int k = 0; k < n; k++)
                            if (p[k] == j)
                                ok = false;
                        if (ok == true) {
                            v = j;
                            break;
                        }
                    }
                }
                fout << p[v] << " ";
            }
        }
        else {
            p[v] = -2;
            for (int j = v + 1; j < n; j++) {
                if (p[j] != -2) {
                    ok = true;
                    for (int k = 0; k < n; k++)
                        if (p[k] == j)
                            ok = false;
                    if (ok == true) {
                        v = j;
                        break;
                    }
                }
            }
            fout << p[v] << " ";
        }
    }
    fin.close();
    fout.close();
    return 0;
}