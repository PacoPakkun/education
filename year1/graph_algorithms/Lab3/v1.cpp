#include <iostream>
#include <fstream>
#include <string>

using namespace std;

#define INF 100000

int main(int argc, char* argv[]){
        //citire fisier
        int n,m,s;
        int graf[100][100]={{0}};
        int l[100]={0};
        bool viz[100]={false};
        for(int i=0;i<100;i++){
                l[i]=INF;
        }
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in.c_str());
        ofstream fout(out.c_str());
        fin>>n>>m>>s;
        for(int i=0;i<m;i++){
                int x,y,w;
                fin>>x>>y>>w;
                graf[x][y]=w;
        }
        //djikstra
        l[s]=0;
        viz[s]=true;
        int v=s;
        for(int i=0;i<n-1;i++){
                for(int j=0;j<n;j++){
                        if(graf[v][j]!=0 && viz[j]==false && l[j]>l[v]+graf[v][j]){
                                l[j]=l[v]+graf[v][j];
                        }
                }
                int vmin, min=INF;
                for(int j=0;j<n;j++){
                        if(viz[j]==false && l[j]<min){
                                vmin=j;
                                min=l[j];
                        }
                }
                viz[v]=true;
                v=vmin;
        }
        //afisare fisier
        for(int i=0;i<n;i++){
                if(l[i]==INF){
                        fout<<"inf ";
                }
                else{
                        fout<<l[i]<<" ";
                }
        }
        fin.close();
        fout.close();
        return 0;
}