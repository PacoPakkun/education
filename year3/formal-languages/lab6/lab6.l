%{
#include <stdio.h>
#include <string.h>

int token=0;
int cod_ts=0;
char ts[100][100];
int len=0;

int getTs(char atom[]) {
	for (int i = 0; i < len; i++) {
		if (strcmp(ts[i], atom) == 0) {
			return i;
		}
	}
	strcpy(ts[len],atom);
	len++;
	return len - 1;
}

void show(){
	printf("\n\n-- ts --\n");
	for (int i = 0; i < len; i++) {
		printf("%d: %s\n",i,ts[i]);
	}
}
%}

%%
"void" {token=3; printf("%d ",token);}
"main" {token=4; printf("%d ",token);}
"read" {token=5; printf("%d ",token);}
"print" {token=6; printf("%d ",token);}
"if" {token=7; printf("%d ",token);}
"else" {token=8; printf("%d ",token);}
"for" {token=9; printf("%d ",token);}
"while" {token=10; printf("%d ",token);}
"int" {token=11; printf("%d ",token);}
"bool" {token=12; printf("%d ",token);}
"string" {token=13; printf("%d ",token);}
"float" {token=14; printf("%d ",token);}
"=" {token=15; printf("%d ",token);}
"+" {token=16; printf("%d ",token);}
"-" {token=17; printf("%d ",token);}
"*" {token=18; printf("%d ",token);}
"<" {token=19; printf("%d ",token);}
">" {token=20; printf("%d ",token);}
"<=" {token=21; printf("%d ",token);}
">=" {token=22; printf("%d ",token);}
"!=" {token=23; printf("%d ",token);}
"(" {token=24; printf("%d ",token);}
")" {token=25; printf("%d ",token);}
"{" {token=26; printf("%d ",token);}
"}" {token=27; printf("%d ",token);}
";" {token=28; printf("%d ",token);}
"new" {token=29; printf("%d ",token);}
"malloc" {token=30; printf("%d ",token);}
"null" {token=31; printf("%d ",token);}
"sizeof" {token=32; printf("%d ",token);}
[+-]?([0-9]*[.])?[0-9]+ {token=2; cod_ts=getTs(yytext); printf("%d;%d ",token,cod_ts);} // constante
[a-zA-Z]{1,8} {token=1; cod_ts=getTs(yytext); printf("%d;%d ",token,cod_ts);} // identificatori
[\t ] {}
. {printf("ERROR ");}
%%

int yywrap()
{
return 1;
}

int main()
{
	FILE* fp;
	fp = fopen("date.txt","r");
	yyin=fp;
	printf("\n-- fip --\n");
	while(yylex());
	fclose(fp);
	show();
}
