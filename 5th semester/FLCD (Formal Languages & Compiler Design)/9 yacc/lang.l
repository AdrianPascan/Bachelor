%{
	#include "lang.tab.h"
%} 

%option noyywrap

ID [A-Za-z][A-Za-z0-9]*
NOT_ID [^ ][A-Za-z][A-Za-z0-9]*
INTEGER [+-]?(0|[1-9][0-9]*)
STRING \"[A-Za-z0-9 _\.,:;!\?\'#]*\"


%%

{INTEGER} 	{return CONST_INT;} 

{STRING}	{return CONST_STRING;} 

main	{return MAIN;}
return	{return RETURN;}
read	{return READ;}
write	{return WRITE;}
true	{return TRUE;}
false	{return FALSE;}
equals	{return EQUALS;}
lessEqual	{return LESS_EQUAL;}
lessThan	{return LESS_THAN;}
greaterEqual	{return GREATER_EQUAL;}
greaterThan	{return GREATER_THAN;}
if	{return IF;}
else	{return ELSE;}
for	{return FOR;}
int	{return INT;}
string	{return STRING;}

{ID}	{return ID;}

{NOT_ID} 	{return NOT_ID;}

"+"		{return PLUS;}
"-"		{return MINUS;}
"*"		{return MUL;}
"/"		{return DIV;}
"%"		{return MOD;}
"="		{return EQ;}

";"		{return SEMICOLON;}
"{"		{return OPEN_BRACE;}
"}"		{return CLOSED_BRACE;}
"("		{return OPEN_BRACKET;}
")"		{return CLOSED_BRACKET;}
"["		{return OPEN_SQUARE_BRACKET;}
"]"		{return CLOSED_SQUARE_BRACKET;}

[ \t\n]+ 	/* eat up whitespace */ 

.	{return UNRECOGNIZED;}


%%
