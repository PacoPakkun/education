#include <stdio.h>
int main(){
        int n=0,m=0,i=0,j=0,s=0,d=0,len=0,entry=0;
        int graf[20][20]={{0}};
        int l[20]={0};
        int p[20]={0};
        int q[20]={0};
        int drum[20]={0};
        for (i=1;i<20;i++){
                l[i]=-1;
        }
        FILE* file=fopen("graf.txt","r");
        fscanf(file,"%d",&n);
        while (fscanf(file,"%d %d",&i,&j)!=-1){
                graf[i][j]=1;
                m++;
        }
        printf("Introduceti nodul sursa: ");
        scanf("%d",&s);
        printf("Introduceti nodul destinatie: ");
        scanf("%d",&d);
        //moore
        l[s]=0;
        entry++;
        len++;
        q[len]=s;
        while (entry<=len){
                int x=q[entry];
                entry++;
                for(i=1;i<=n;i++){
                        if(graf[x][i]==1 && l[i]==-1){
                                len++;
                                q[len]=i;
                                p[i]=x;
                                l[i]=l[x]+1;
                        }
                }
        }
        //drum minim
        if(l[d]==-1)
                printf("nu exista drum\n");
        else{
                len=l[d];
                drum[len]=d;
                while(len!=0){
                        drum[len-1]=p[drum[len]];
                        len-=1;
                }
                printf("%d ",drum[0]);
                i=1;
                while(drum[i]!=0){
                        printf("-> %d ",drum[i]);
                        i++;
                }
                printf("\n");
        }
        return 0;
}