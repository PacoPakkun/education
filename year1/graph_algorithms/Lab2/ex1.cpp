#include <iostream>
#include <fstream>
#include <vector>
#include <queue>

using namespace std;

int main(){
        //citire
        ifstream fin("graf.txt");
        int n;
        fin>>n;
        vector<int> graf[n+1];
        int x,y;
        while(fin>>x>>y){
                graf[x].push_back(y);
                graf[y].push_back(x);
        }
        int s,d;
        cout<<"nod sursa: ";
        cin>>s;
        cout<<"nod destinatie: ";
        cin>>d;
        //moore
        queue<int> q;
        int dist[n+1]={0};
        int pr[n+1]={0};
        for(int i=0;i<=n;i++)
                dist[i]=-1;
        dist[s]=0;
        q.push(s);
        while(q.size()!=0){
                int v=q.front();
                q.pop();
                for(int i:graf[v])
                        if(dist[i]==-1){
                                dist[i]=dist[v]+1;
                                pr[i]=v;
                                q.push(i);
                        }
        }
        //afisare
        if(dist[d]==-1)
                cout<<"nu exista drum\n";
        else{
                int drum[dist[d]+1]={0};
                drum[dist[d]]=d;
                for(int i=1;i<=dist[d];i++)
                        drum[dist[d]-i]=pr[drum[dist[d]-i+1]];
                for(int i=0;i<dist[d];i++)
                        cout<<drum[i]<<"->";
                cout<<d<<"\n";
        }
        fin.close();
        return 0;
}