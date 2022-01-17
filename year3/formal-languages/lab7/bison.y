%{
#include <stdio.h>
#include <stdlib.h>
%}

%error-verbose
%token void_token
%token main_token
%token read_token
%token print_token
%token if_token
%token else_token
%token for_token
%token while_token
%token int_token
%token bool_token
%token string_token
%token float_token
%token lte_token
%token gte_token
%token ne_token
%token const_token
%token id_token
%token switch_token
%token case_token
%token break_token
%token default_token

%%

program		:	void_token main_token '(' ')' '{' lista_instr '}'
		
lista_instr	:	instr
		|	instr lista_instr
		
instr		:	declarare
		|	citire
		|	scriere
		|	operatie
		|	conditionala
		|	repetitie_fixa
		|	repetitie_var
		|	switch
		
citire		:	read_token '(' id_token ')' ';'
		
scriere		:	print_token '(' id_token ')' ';'
		
operatie	:	id_token '=' id_token operator id_token ';'
		|	id_token '=' id_token operator const_token ';'
		
operator	:	'+'
		|	'-'
		|	'*'
		
conditionala	:	if_token '(' conditie ')' '{' lista_instr '}' else_token '{' lista_instr '}'
		|	if_token '(' conditie ')' '{' lista_instr '}'
		
conditie	:	id_token operator_rel id_token
		|	id_token operator_rel const_token
		
operator_rel	:	'<'
		|	'>'
		|	'='
		|	lte_token
		|	gte_token
		|	ne_token
		
repetitie_fixa	:	for_token '(' declarare conditie ';' pas ')' '{' lista_instr '}'
		
declarare	:	tip id_token '=' const_token ';'
		
tip		:	int_token
		|	bool_token
		|	string_token
		|	float_token
		
pas		:	id_token operator operator
		
repetitie_var	:	while_token '(' conditie ')' '{' lista_instr '}'

switch		:	switch_token '(' id_token ')' '{' lista_case default_token ':' lista_instr '}'

lista_case	:	case 
		|	case lista_case

case		:	case_token ':' lista_instr break_token ';'

%%

yyerror()
{
    printf("syntax error\n");
}

int main()
{
    if(0==yyparse()) printf("Result yyparse: OK\n");
}
