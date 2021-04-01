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
        int n,m;
        fin>>n>>m;
        vector<nod> graf[n];
        int x,y,w;
        while(fin>>x>>y>>w){
                graf[x].push_back(nod{y,w});
                graf[y].push_back(nod{x,w});
        }
        //prim
        int dist[n+1]={0};
        int pr[n]={0};
        bool viz[n]={0};
        int mst[n]={0};
        priority_queue<int,vector<int>,function<bool(int n1,int n2)>> q([&dist](int n1,int n2){return dist[n1]>dist[n2];});
        for(int i=0;i<=n;i++){
                dist[i]=10000;
                pr[i]=-1;
                mst[i]=-1;
        }
        int count=0,sum=0;
        dist[0]=0;
        q.push(0);
        while(!q.empty()){
                int v=q.top();
                q.pop();
                if(viz[v]==false){
                for(nod i:graf[v])
                        if(viz[i.first]==false && dist[i.first]>i.second){
                                dist[i.first]=i.second;
                                pr[i.first]=v;
                                q.push(i.first);
                                }
                viz[v]=true;
                mst[count]=v;
                count++;
                sum+=dist[v];}
        }
        //afisare
        fout<<sum<<"\n";
        fout<<count-1<<"\n";
        for(int i=1;i<count;i++)
                fout<<pr[mst[i]]<<" "<<mst[i]<<"\n";
        fin.close();
        fout.close();
        return 0;
}