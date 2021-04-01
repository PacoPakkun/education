#include <iostream>
#include <fstream>
#include <vector>
#include <queue>
#include <functional>

using namespace std;

#define INF 10000

typedef std::pair<int,int> nod;

int main(int argc, char* argv[]){
        //citire fisier
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in);
        ofstream fout(out);
        int n,m;
        fin>>n>>m;
        vector<nod> graf[n+1];
        for(int i=0;i<m;i++){
                int x,y,w;
                fin>>x>>y>>w;
                graf[x].push_back(nod{y,w});
        }
        //bellman ford
        for(int i=0;i<n;i++)
                graf[n].push_back(nod{i,0});
        vector<int> aux(n+1);
        vector<vector<int>> dist(n+1,aux);
        for(int i=0;i<n+1;i++)
                for(int j=0;j<n+1;j++)
                        dist[i][j]=INF;
        dist[n][n]=0;
        for(int k=0;k<n;k++)
                for(int i=0;i<n+1;i++)
                        for(nod j:graf[i])
                                if(dist[n][j.first]>dist[n][i]+j.second)
                                        dist[n][j.first]=dist[n][i]+j.second;
        for(int i=0;i<n+1;i++)
                for(nod j:graf[i])
                        if(dist[n][j.first]>dist[n][i]+j.second){
                                fout<<-1<<"\n";
                                fin.close();
                                fout.close();
                                return 0;
                        }
        //reponderare
        for(int i=0;i<n;i++)
                for(nod& j:graf[i]){
                        j.second=j.second+dist[n][i]-dist[n][j.first];
                        fout<<i<<" "<<j.first<<" "<<j.second<<"\n";
                }
        //djikstra pt fiecare varf
        for(int s=0;s<n;s++){
                bool viz[n]={0};
                priority_queue<int,vector<int>,function<bool(int n1,int n2)>> q([s,&dist](int n1,int n2){return dist[s][n1]>dist[s][n2];});
                dist[s][s]=0;
                q.push(s);
                while(!q.empty()){
                        int v=q.top();
                        q.pop();
                        if(viz[v]==false){
                                for(nod i:graf[v])
                                        if(dist[s][i.first]>dist[s][v]+i.second){
                                                dist[s][i.first]=dist[s][v]+i.second;
                                                q.push(i.first);
                                        }
                                viz[v]=true;
                        }
                }
        }
        //afisare
        for(int i=0;i<n;i++){
                for(int j=0;j<n;j++){
                        if(dist[i][j]==INF)
                                fout<<"INF ";
                        else
                                fout<<dist[i][j]-dist[n][i]+dist[n][j]<<" ";
                        }
                fout<<"\n";
        }
        fin.close();
        fout.close();
        return 0;
}