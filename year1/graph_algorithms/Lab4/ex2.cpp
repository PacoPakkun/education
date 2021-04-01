#include <iostream>
#include <fstream>

using namespace std;

int main(int argc,char* argv[]){
        //citire fisier
        int prufer[20]={0};
        int p[20]={0};
        int m=0;
        for(int i=0;i<20;i++)
                p[i]=-1;
        string in=argv[1];
        string out=argv[2];
        ifstream fin(in.c_str());
        ofstream fout(out.c_str());
        fin>>m;
        for(int i=0;i<m;i++)
                fin>>prufer[i];
        //decodare prufer
        int pos=0,max=m;
        for(int k=0;k<m;k++){
                int v=prufer[pos];
                int y=-1;
                for(int i=0;i<m+1;i++){
                        bool ok=true;
                        for(int j=pos;j<max;j++)
                                if(prufer[j]==i){
                                        ok=false;
                                        break;
                                }
                        if(ok==true){
                                y=i;
                                break;
                        }
                }
                p[y]=v;
                pos++;
                prufer[max]=y;
                max++;
        }
        //afisare fisier
        fout<<m+1<<"\n";
        for(int i=0;i<m+1;i++)
                fout<<p[i]<<" ";
        fin.close();
        fout.close();
        return 0;
}