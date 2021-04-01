#include <stdio.h>
#include <stdlib.h>
int main(){
        int i,j,n,m=0;
        int mat_ad[100][100]={{0}};
        int mat_in[100][100]={{0}};
        int mat_conv_ad[100][100]={{0}};
        int mat_conv_in[100][100]={{0}};
        int lista[100][2]={{0}};
        //citire fisier
        FILE *file=fopen("in.txt","r");
        fscanf(file,"%d",&n);
        while(fscanf(file,"%d %d",&i,&j)!=-1){
                mat_ad[i][j]=1;
                mat_ad[j][i]=1;
                m++;
                mat_in[m][i]=1;
                mat_in[m][j]=1;
        }
        //afisare matrice adiacenta
        printf("matrice adiacenta originala:\n");
        for (i=1;i<=n;i++){
                printf("( ");
                for (j=1;j<=n;j++){
                        printf("%d ",mat_ad[i][j]);
                }
                printf(")\n");
        }
        //afisare matrice incidenta
        printf("matrice incidenta originala:\n");
        for (i=1;i<=m;i++){
                printf("( ");
                for (j=1;j<=n;j++){
                        printf("%d ",mat_in[i][j]);
                }
                printf(")\n");
        }
        //conversie adiacenta->incidenta
        m=0;
        for(i=1;i<=n;i++){
                for (j=1;j<=i;j++){
                        if(mat_ad[i][j]==1){
                                m++;
                                mat_conv_in[m][i]=1;
                                mat_conv_in[m][j]=1;
                        }
                }
        }
        //afisare matrice incidenta convertita
        printf("adiacenta->incidenta:\n");
        for (i=1;i<=m;i++){
                printf("( ");
                for (j=1;j<=n;j++){
                        printf("%d ",mat_conv_in[i][j]);
                }
                printf(")\n");
        }
        //conversie incidenta->adiacenta
        int n1,n2,ok=0;
        for (i=1;i<=m;i++){
                for (j=1;j<=n;j++){
                        if ((mat_conv_in[i][j]==1)&&(ok==0)){
                                n1=j;
                                ok=1;
                        }
                        else if ((mat_conv_in[i][j]==1)&&(ok==1)){
                                n2=j;
                                mat_conv_ad[n1][n2]=1;
                                mat_conv_ad[n2][n1]=1;
                                ok=0;
                        }
                }
        }
        //afisare matrice adiacenta convertita
        printf("incidenta->adiacenta:\n");
        for (i=1;i<=n;i++){
                printf("( ");
                for (j=1;j<=n;j++){
                        printf("%d ",mat_conv_ad[i][j]);
                }
                printf(")\n");
        }
        //conversie adiacenta->lista
        int next=n+1;
        for (i=1;i<=n;i++){
                lista[i][0]=i;
                int pos=i;
                for (j=1;j<=n;j++){
                        if (mat_conv_ad[i][j]==1){
                                lista[pos][1]=next;
                                lista[next][0]=j;
                                pos=next;
                                next++;
                        }
                }
                lista[pos][1]=0;
        }
        //afisare lista adiacenta
        printf("adiacenta->lista:\n");
        for(i=1;i<=n;i++){
                printf("%d ( ",lista[i][0]);
                int pos=lista[i][1];
                while (pos!=0){
                        printf("%d ",lista[pos][0]);
                        pos=lista[pos][1];
                }
                printf(")\n");
        }
        return 0;
}