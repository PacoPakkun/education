#include <stdio.h>
#include <stdlib.h>
int main(){
        int graf[20][20]={{0}};
        int inc[20][20]={{0}};
        int s[20]={0};
        int n=0,m=0,i=0,j=0,len=0;
        //citire fisier
        FILE* file=fopen("graf.txt","r");
        fscanf(file,"%d",&n);
        while(fscanf(file,"%d %d",&i,&j)!=-1){
                graf[i][j]=1;
                m++;
        }
        //matricea inchiderii tranzitive
        for(i=1;i<=n;i++){
                len++;
                s[len]=i;
                inc[i][i]=1;
                while(len!=0){
                        int x=s[len];
                        len--;
                        for(j=1;j<=n;j++){
                                if(graf[x][j]==1 && inc[i][j]==0){
                                        len++;
                                        s[len]=j;
                                        inc[i][j]=1;
                                }
                        }
                }
        }
        //afisare
        printf("matricea inchiderii tranzitive:\n");
        for(i=1;i<=n;i++){
                printf("( ");
                for(j=1;j<=n;j++){
                        printf("%d ",inc[i][j]);
                }
                printf(")\n");
        }
        fclose(file);
        return 0;
}