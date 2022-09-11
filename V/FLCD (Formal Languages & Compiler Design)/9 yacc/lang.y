%{
#include <stdio.h>
#include <stdlib.h>
#define YYDEBUG 1

#define TYPE_INT 1
#define TYPE_STRING 2
%}

%token MAIN
%token RETURN
%token READ
%token WRITE
%token TRUE
%token FALSE

%token EQUALS
%token LESS_EQUAL
%token LESS_THAN
%token GREATER_EQUAL
%token GREATER_THAN

%token IF
%token ELSE
%token FOR

%token ID
%token NOT_ID

%token INT
%token STRING
%token CONST_INT
%token CONST_STRING
%token CONST_SIR

%token PLUS
%token MINUS
%token MUL
%token DIV
%token MOD
%token EQ

%token SEMICOLON
%token OPEN_BRACE
%token CLOSED_BRACE
%token OPEN_BRACKET
%token CLOSED_BRACKET
%token OPEN_SQUARE_BRACKET
%token CLOSED_SQUARE_BRACKET

%token UNRECOGNIZED

%start program

%%

program:	INT MAIN OPEN_BRACKET CLOSED_BRACKET OPEN_BRACE statementList CLOSED_BRACE
		;
relation:	EQUALS
		| LESS_EQUAL
		| LESS_THAN
		| GREATER_EQUAL
		| GREATER_THAN
		;
operand:	ID
		| CONST_INT
		| CONST_STRING
		| ID OPEN_SQUARE_BRACKET ID CLOSED_SQUARE_BRACKET
		| ID OPEN_SQUARE_BRACKET CONST_INT CLOSED_SQUARE_BRACKET
		;
operator: PLUS
		| MINUS
		| MUL
		| DIV
		| MOD
		| EQ
		;
statementList:	statement
		| statement statementList
		;
statement:	simpleStatement SEMICOLON
		| structuredStatement
		;
simpleStatement:	declaration
		| io
		| assignment
		| condition
		| return
		;
declaration: INT ID
		| STRING ID
		| INT ID EQ CONST_INT
		| STRING ID EQ CONST_STRING
		| INT ID OPEN_SQUARE_BRACKET ID CLOSED_SQUARE_BRACKET
		| STRING ID OPEN_SQUARE_BRACKET ID CLOSED_SQUARE_BRACKET
		| INT ID OPEN_SQUARE_BRACKET CONST_STRING CLOSED_SQUARE_BRACKET
		| STRING ID OPEN_SQUARE_BRACKET CONST_STRING CLOSED_SQUARE_BRACKET
		;
io:	READ ID
	| READ ID OPEN_SQUARE_BRACKET ID CLOSED_SQUARE_BRACKET
	| READ ID OPEN_SQUARE_BRACKET CONST_INT CLOSED_SQUARE_BRACKET
	| WRITE ID
	| WRITE CONST_INT
	| WRITE CONST_STRING
	| WRITE ID OPEN_SQUARE_BRACKET ID CLOSED_SQUARE_BRACKET
	| WRITE ID OPEN_SQUARE_BRACKET CONST_INT CLOSED_SQUARE_BRACKET
	;
assignment:	ID EQ expression
			| ID EQ ID
			| ID EQ CONST_INT
			| ID EQ CONST_STRING
			;
expression: operand operator operand
			| ID operator ID
			| ID operator CONST_INT
			| ID operator CONST_STRING
			| CONST_INT operator CONST_INT
			| CONST_STRING EQ CONST_STRING
			;
condition: operand relation operand
			| expression relation operand
			| operand relation expression
			| expression relation expression
			;
return: RETURN CONST_INT
		;
structuredStatement:	if
					| for
					;
if:	IF OPEN_BRACKET condition CLOSED_BRACKET OPEN_BRACE statementList CLOSED_BRACE
	;
for:	FOR OPEN_BRACKET assignment SEMICOLON condition SEMICOLON assignment CLOSED_BRACKET OPEN_BRACE statementList CLOSED_BRACE


%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}

