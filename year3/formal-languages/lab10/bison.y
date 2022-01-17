
%{
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <string.h>
int yylex(void);
int yyerror(const char *s);
FILE * output;

void addDataSegment(char segment[1000]);
void addCodeSegment(char segment[1000]);
%}

%union {
  int number;
  char string[2000];
}
%error-verbose
%token ID
%token int_token
%token cin_token
%token cout_token
%token CONST_INTREAGA
%token main_token
%token cin_operator
%token cout_operator
%token ge_token
%token le_token
%token equal_token
%token different_token
%token operator_token
%token operator_rel
%type<string> CONST_INTREAGA ID operand expresie
%%
program		:	int_token  main_token '(' ')'  '{' lista_instr  sfarsit				
		;

sfarsit: '}' {
	addCodeSegment("\tpush dword 0\n");
        addCodeSegment("\tcall [exit]\n");
}

decl_var	:	int_token ID ';'{
				char code[1000];
				code[0] = 0;
				strcat(code, "\t");
				strcat(code, $2);
				strcat(code, " dd 0\n");
				addDataSegment(code);
			}
		;


lista_instr	:	instr
			|	instr lista_instr
			

instr		:	ID '=' expresie ';' {
				char code[1000];
				code[0] = 0;
				strcat(code, "\tmov eax, 0\n");
				strcat(code, $3);
				strcat(code, "\tmov dword [");
				strcat(code, $1);
				strcat(code, "], ");
				strcat(code, "eax\n");
				addCodeSegment(code);
			}

			|	instr_iesire
			|	cin_token cin_operator ID ';'{
					char code[1000];
					code[0] = 0;
					strcat(code, "\tpush ");
					strcat(code, "dword ");
					strcat(code, $3);
					strcat(code, "\n");
					addCodeSegment(code);
					addCodeSegment("\tpush dword _sformat\n");
					addCodeSegment("\tcall [scanf]\n");
					addCodeSegment("\tadd esp, 4 * 2\n");
				}
			|	decl_var
		;
expresie	:   expresie '+'  operand {
				char code[1000];
				code[0] = 0;
				strcat(code, "\tadd eax, ");
				if(isdigit($3[0])) {
				  strcat(code, $3);
				} else {
				  strcat(code, "[");
				  strcat(code, $3);
				  strcat(code, "]");
				}
				strcat(code, "\n");
				strcpy($$, $1);
				strcat($$, code);
			}

                        |       expresie '-'  operand {
					char code[1000];
					code[0] = 0;
					strcat(code, "\tsub eax, ");
					if(isdigit($3[0])) {
					  strcat(code, $3);
					} else {
					  strcat(code, "[");
					  strcat(code, $3);
					  strcat(code, "]");
					}
					strcat(code, "\n");

					strcpy($$, $1);
					strcat($$, code);
				}
			| operand  {

                                char code[1000];
                                code[0] = 0;
                                strcat(code, "\tadd eax, ");
                                if(isdigit($1[0])) {
                                  strcat(code, $1);
                                } else {
                                  strcat(code, "[");
                                  strcat(code, $1);
                                  strcat(code, "]");
                                }
                                strcat(code, "\n");
                                strcpy($$, code);

                        }
			;

			




instr_iesire	:	cout_token cout_operator ID ';'{
				char code[1000];
				code[0] = 0;
				strcat(code, "\n");
				strcat(code, "\tpush ");

				if(isdigit($3[0])) {
					strcat(code, $3);
				} else {
					strcat(code, "dword [");
					strcat(code, $3);
					strcat(code, "]");
				}
				strcat(code, "\n");
				addCodeSegment(code);
				addCodeSegment("\tpush dword _format\n");
				addCodeSegment("\tcall [printf]\n");
				addCodeSegment("\tadd esp, 4 * 2\n");
				addCodeSegment("\n")
			}	
			;

operand			: ID	{ strcpy($$, $1); }
			| CONST_INTREAGA		{ strcpy($$, $1); }
			;
%%



