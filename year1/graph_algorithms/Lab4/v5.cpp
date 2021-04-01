#include <iostream>
#include <fstream>

using namespace std;

#define INF 10000

int main(int argc, char* argv[]){
        //citire fisier
        int graf[20][20]={{0}};
        int dist[20]={0};
        int sum=0,count=0;
        int mst[20]={0};
        bool viz[20]={false};
        int p[20]={0};
        for(int i=0;i<20;i++){
                dist[i]=INF;
                for(int j=0;j<20;j++)
                        graf[i][j]=INF;
        }
        int n=0,m=0;
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in.c_str());
        ofstream fout(out.c_str());
        fin>>n;
        fin>>m;
        for(int i=0;i<m;i++){
                int x=0,y=0,w=0;
                fin>>x>>y>>w;
                graf[x][y]=w;
                graf[y][x]=w;
        }
        //prim
        int v=0;
        dist[v]=0;
        viz[v]=true;
        for(int k=0;k<n;k++){
                for(int i=0;i<n;i++)
                        if(graf[v][i]!=INF && viz[i]==false && graf[v][i]<dist[i]){
                                dist[i]=graf[v][i];
                                p[i]=v;
                        }
                viz[v]=true;
                int min=INF,vmin=0;
                for(int i=0;i<n;i++)
                        if(viz[i]==false && dist[i]<min){
                                min=dist[i];
                                vmin=i;
                        }
                sum+=graf[p[v]][v];
                mst[count]=v;
                count++;
                v=vmin;
        }
        sum-=INF;
        //afisare fisier
        fout<<sum<<" "<<count<<"\n";
        for(int i=1;i<count;i++)
                fout<<p[mst[i]]<<" "<<mst[i]<<"\n";
        fin.close();
        fout.close();
        return 0;
}