
%option noyywrap

ID [A-Za-z][A-Za-z0-9]*
NOT_ID [^ ][A-Za-z][A-Za-z0-9]*
INTEGER [+-]?(0|[1-9][0-9]*)
STRING \"[A-Za-z0-9 _\.,:;!\?\'#]*\"


%%

{INTEGER} 	printf( "An integer: %s (%d)\n", yytext, atoi( yytext ) ); 

{STRING}	printf( "A string: %s\n", yytext ); 

main|return|read|write|true|false|equals|lessEqual|lessThan|greaterEqual|greaterThan|if|else|for|int|string 	printf( "A keyword: %s\n", yytext ); 

{ID} 		printf( "An identifier: %s\n", yytext ); 

{NOT_ID} 		printf( "Unrecognized identifier: %s\n", yytext ); 

"+"|"-"|"*"|"/"|"%"|"="		printf( "An operator: %s\n", yytext );

";"|"{"|"}"|"("|")"|"["|"]"		printf( "A separator: %s\n", yytext );

[ \t\n]+ 	/* eat up whitespace */ 

.			printf( "Unrecognized character: %s\n", yytext ); 


%%

int main(int argc, char* argv[]) 
{ 
    ++argv, --argc; /* skip over program name */ 
    if ( argc > 0 ) 
    	yyin = fopen( argv[0], "r" ); 
    else 
    	yyin = stdin; 
    yylex(); 
} 

