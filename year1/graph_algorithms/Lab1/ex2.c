#include <stdio.h>
#include <stdlib.h>
int main(){
        //citire matrice
        int graf[10][10]={{0}};
        int n=0,m=0,i=0,j=0;
        FILE* file = fopen("in.txt","r");
        fscanf(file,"%d",&n);
        while (fscanf(file,"%d %d",&i,&j)!=-1){
                graf[i][j]=1;
                graf[j][i]=1;
                m++;
        }
        //noduri izolate
        printf("noduri izolate:\n");
        for (i=1;i<=n;i++){
                int sum=0;
                for (j=1;j<=n;j++){
                        sum+=graf[i][j];
                }
                if (sum==0){
                        printf(" %d",i);
                }
        }
        printf("\n");
        //graf regular
        printf("graf regular:\n");
        int regular=1;
        int sum1=-1;
        for (i=1;i<=n;i++){
                int sum2=0;
                for (j=1;j<=n;j++){
                        sum2+=graf[i][j];
                }
                if (sum1!=-1){
                        if (sum1!=sum2){
                                regular=0;
                        }
                }
                sum1=sum2;
        }
        if (regular==1) printf(" da\n");
        else printf(" nu\n");
        //matricea distantelor
        int dist[10][10]={{0}};
        for (int k=1;k<=n;k++){
                for (i=1;i<=n;i++){
                        for (j=1;j<=n;j++){
                                if (i!=j && graf[i][j]==0 && graf[i][k]!=0 && graf[k][j]!=0){
                                        if (dist[i][j]==0 || graf[i][k]+graf[k][j]<dist[i][j]){
                                                dist[i][j]=graf[i][k]+graf[k][j];
                                        }
                                }
                                else if(graf[i][j]!=0)
                                        dist[i][j]=graf[i][j];
                        }
                }
        }
        printf("matricea distantelor:\n");
        for (i=1;i<=n;i++){
                printf("( ");
                for (j=1;j<=n;j++){
                        printf("%d ",dist[i][j]);
                }
                printf(")\n");
        }
        //graf conex
        printf("graf conex:\n");
        int conex=1;
        for (i=1;i<=n;i++){
                for (j=1;j<=n;j++){
                        if (i!=j && dist[i][j]==0)
                                conex=0;
                }
        }
        if (conex==1) printf(" da\n");
        else printf(" nu\n");
        fclose(file);
        return 0;
}