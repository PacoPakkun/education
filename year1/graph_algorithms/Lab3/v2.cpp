#include <iostream>
#include <fstream>
#include <string>

using namespace std;

#define INF 10000

int main(int argc, char* argv[]){
        //citire fisier
        int n,m;
        int graf[20][20]={{0}};
        int grafrep[20][20]={{0}};
        int dist[20][20]={{0}};
        bool viz[20]={false};
        for(int i=0;i<20;i++)
                for(int j=0;j<20;j++)
                        graf[i][j]=INF;
        for(int i=0;i<20;i++)
                for(int j=0;j<20;j++)
                        grafrep[i][j]=INF;
        for(int i=0;i<20;i++)
                for(int j=0;j<20;j++)
                        dist[i][j]=INF;
	string in=argv[1];
	string out=argv[2];
        ifstream fin(in.c_str());
        ofstream fout(out.c_str());
        fin>>n>>m;
        for(int i=0;i<m;i++){
                int x,y,w;
                fin>>x>>y>>w;
                graf[x][y]=w;
        }
        for(int i=0;i<n+1;i++)
                graf[n][i]=0;
        //bellman ford
        dist[n][n]=0;
        for(int k=0;k<n;k++)
                for(int i=0;i<n+1;i++)
                        for(int j=0;j<n+1;j++)
                                if(graf[i][j]!=INF && dist[n][j]>dist[n][i]+graf[i][j])
                                        dist[n][j]=dist[n][i]+graf[i][j];
        for(int i=0;i<n+1;i++)
                for(int j=0;j<n+1;j++)
                        if(graf[i][j]!=INF && dist[n][j]>dist[n][i]+graf[i][j]){
                                fout<<-1<<"\n";
                                fin.close();
                                fout.close();
                                return 0;
                        }
        //reponderare
        for(int i=0;i<n;i++)
                for(int j=0;j<n;j++)
                        if(graf[i][j]!=INF){
                                grafrep[i][j]=graf[i][j]+dist[n][i]-dist[n][j];
                                fout<<i<<" "<<j<<" "<<grafrep[i][j]<<"\n";
                                }
        //djikstra pt fiecare varf
        for(int s=0;s<n;s++){
                for(int i=0;i<n;i++)
                        viz[i]=false;
                dist[s][s]=0;
                viz[s]=true;
                int v=s;
                for(int k=0;k<n-1;k++){
                        for(int i=0;i<n;i++)
                                if(grafrep[v][i]!=INF && viz[i]==false && dist[s][i]>dist[s][v]+grafrep[v][i])
                                        dist[s][i]=dist[s][v]+grafrep[v][i];
                        viz[v]=true;
                        int min=INF;
                        int vmin=INF;
                        for(int i=0;i<n;i++)
                                if(viz[i]==false && dist[s][i]<min){
                                        min=dist[s][i];
                                        vmin=i;
                                }
                        v=vmin;
                }
        }
        //
        for(int i=0;i<n;i++){
                for(int j=0;j<n;j++)
                        if(dist[i][j]==INF)
                                fout<<"INF ";
                        else
                                fout<<dist[i][j]-dist[n][i]+dist[n][j]<<" ";
                fout<<"\n";
        }
        fin.close();
        fout.close();
        return 0;
}