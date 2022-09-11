
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     MAIN = 258,
     RETURN = 259,
     READ = 260,
     WRITE = 261,
     TRUE = 262,
     FALSE = 263,
     EQUALS = 264,
     LESS_EQUAL = 265,
     LESS_THAN = 266,
     GREATER_EQUAL = 267,
     GREATER_THAN = 268,
     IF = 269,
     ELSE = 270,
     FOR = 271,
     ID = 272,
     NOT_ID = 273,
     INT = 274,
     STRING = 275,
     CONST_INT = 276,
     CONST_STRING = 277,
     CONST_SIR = 278,
     PLUS = 279,
     MINUS = 280,
     MUL = 281,
     DIV = 282,
     MOD = 283,
     EQ = 284,
     SEMICOLON = 285,
     OPEN_BRACE = 286,
     CLOSED_BRACE = 287,
     OPEN_BRACKET = 288,
     CLOSED_BRACKET = 289,
     OPEN_SQUARE_BRACKET = 290,
     CLOSED_SQUARE_BRACKET = 291,
     UNRECOGNIZED = 292
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


