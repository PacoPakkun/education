#include <iostream>
#include <fstream>
#include <utility>
#include <vector>
#include <queue>
#include <functional>

using namespace std;

typedef std::pair<int,int> nod;

int main(int argc,char* argv[]){
        //citire
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in);
        ofstream fout(out);
        int n,m,s;
        fin>>n>>m>>s;
        vector<nod> graf[n];
        int x,y,w;
        while(fin>>x>>y>>w){
                graf[x].push_back(nod{y,w});
        }
        //djikstra
        int dist[n]={0};
        bool viz[n]={0};
        priority_queue<int,vector<int>,function<bool(int n1,int n2)>> q([&dist](int n1,int n2){return dist[n1]>dist[n2];});
        for(int i=0;i<=n;i++)
                dist[i]=10000;
        dist[s]=0;
        q.push(s);
        while(q.size()!=0){
                int v=q.top();
                q.pop();
                if(viz[v]==false){
                        for(nod i:graf[v])
                                if(dist[i.first]>dist[v]+i.second){
                                        dist[i.first]=dist[v]+i.second;
                                        q.push(i.first);
                                }
                        viz[v]=true;
                }
        }
        //afisare
        for(int i=0;i<n;i++)
                if(dist[i]==10000)
                        fout<<"INF ";
                else
                        fout<<dist[i]<<" ";
        fout<<"\n";
        fin.close();
        fout.close();
        return 0;
}