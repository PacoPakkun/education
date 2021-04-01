#include <iostream>
#include <fstream>
#include <vector>
#include <stack>

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
        }
        //inchidere tranzitiva
        stack<int> s;
        int dist[n+1][n+1]={{0}};
        for(int i=0;i<=n;i++)
                for(int j=0;j<=n;j++)
                        dist[i][j]=-1;
        for(int i=1;i<=n;i++){
                dist[i][i]=0;
                s.push(i);
                while(s.size()!=0){
                        int v=s.top();
                        s.pop();
                        for(int j:graf[v])
                                if(dist[i][j]==-1){
                                        dist[i][j]=dist[i][v]+1;
                                        s.push(j);
                                }
                }
        }
        //afisare
        for(int i=1;i<=n;i++){
                cout<<"( ";
                for(int j=1;j<=n;j++)
                        cout<<dist[i][j]<<" ";
                cout<<")\n";
        }
        fin.close();
        return 0;
}