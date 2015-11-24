package slp;

import java_cup.runtime.*;

%%

%cup
%class Lexer

%line
%scanerror RuntimeException

%{   
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}
%}


/***********************/
/* MACRO DECALARATIONS */
/***********************/
LineTerminator			= \r|\n|\r\n
WhiteSpace				= {LineTerminator} | [ \t\f]
InputCharacter 			= [^\r\n]

COMMENT_CONTENT    		= ( [^*] | \*+ [^/*] )* 
DOCUMENTATION_COMMENT = "/**" {COMMENT_CONTENT} "*"+ "/"
TRADITIONAL_COMMENT   	= "/*" [^*] ~"*/" | "/*" "*"+ "/"
END_OF_LINE_COMMENT     = "//" {InputCharacter}* {LineTerminator}?
COMMENT 				= {TRADITIONAL_COMMENT} | {END_OF_LINE_COMMENT} | {DOCUMENTATION_COMMENT }
UNCLOSED_COMMENT		= "/*"{COMMENT_CONTENT}

LEADING_ZEROES			= 0[0-9]+
INTEGER					= 0+ | [1-9][0-9]*

IDENTIFIER				= [a-z][A-Za-z_0-9]*
CLASS_ID				= [A-Z][A-Za-z_0-9]*

QUOTE_MARK				= "\""
CHAR 					= (\\n|\\t|\\\\|\\\"|[^\\\"])
QUOTE					= {QUOTE_MARK}{CHAR}*{QUOTE_MARK}

QUOTE_ERROR				= {QUOTE_MARK}{CHAR}* 

//error fallback - matches any character in any state that has not been matched by another rule
ERROR					= [^]

%%

<YYINITIAL> {

"."					{ return symbol(sym.DOT);}
","					{ return symbol(sym.COMMA);}
";"					{ return symbol(sym.SEMI);}
"+"					{ return symbol(sym.PLUS);}
"*"					{ return symbol(sym.MULTIPLY);}
"/"					{ return symbol(sym.DIVIDE);}
"("					{ return symbol(sym.LP);}
")"					{ return symbol(sym.RP);}
"="					{ return symbol(sym.ASSIGN);}
"=="				{ return symbol(sym.EQUAL);}
">"					{ return symbol(sym.GT);}
">="				{ return symbol(sym.GTE);}


//Lior

"["					{ return symbol(sym.LB);}
"{"					{ return symbol(sym.LCBR);}
"length"			{ return symbol(sym.LENGTH);}
"new"				{ return symbol(sym.NEW);}
"!"					{ return symbol(sym.LNEG);}
"||"				{ return symbol(sym.LOR);}
"&&"				{ return symbol(sym.LAND);}
"<"					{ return symbol(sym.LT);}
"<="				{ return symbol(sym.LTE);}
"-"					{ return symbol(sym.MINUS);}
"%"					{ return symbol(sym.MOD);}
"!="				{ return symbol(sym.NEQUAL);}


//Keren

"boolean"			{ return symbol(sym.BOOLEAN);}
"break"				{ return symbol(sym.BREAK);}
"class"				{ return symbol(sym.CLASS);}
"continue"			{ return symbol(sym.CONTINUE);}
"extends"			{ return symbol(sym.EXTENDS);}
"else"				{ return symbol(sym.ELSE);}
"false"				{ return symbol(sym.FALSE);}
"if"				{ return symbol(sym.IF);}
"int"				{ return symbol(sym.INT);}


//Tomer 

"null"				{ return symbol(sym.NULL);}
"+"					{ return symbol(sym.PLUS);}
"]"					{ return symbol(sym.RB);}
"}"					{ return symbol(sym.RCBR);}
"return"			{ return symbol(sym.RETURN);}
")"					{ return symbol(sym.RP);}
";"					{ return symbol(sym.SEMI);}
"static"			{ return symbol(sym.STATIC);}
"string"			{ return symbol(sym.STRING);}
"this"				{ return symbol(sym.THIS);}
"true"				{ return symbol(sym.TRUE);}
"void"				{ return symbol(sym.VOID);}
"while"				{ return symbol(sym.WHILE);}

// Tomer: Dealing with end of file
<<EOF>> 			{ return symbol(sym.EOF);}

{INTEGER}			{
						Integer num = 0;
						try{
							num = new Integer(yytext());
						}
						catch(NumberFormatException e){
							System.out.print(yyline+1  +": Lexical Error: number not within valid IC range '" +yytext()+"'"); 
					  		System.exit(0); 
						}
						return symbol(sym.NUMBER, num);
					}   
{IDENTIFIER}		{ return symbol(sym.ID, new String(yytext()));}
{CLASS_ID}			{ return symbol(sym.CLASS_ID, new String(yytext()));}
{QUOTE}				{
						String str = new String(yytext());
						boolean validStr = true;
						char badChar = 0;
						for(int i=0; i < str.length(); i++){
							int asciiVal = (int) str.charAt(i);
							if(asciiVal < 32 || asciiVal > 126 ){
								validStr = false;
								badChar = str.charAt(i);
								break;
							}
						}
						if(validStr){					
							return symbol(sym.QUOTE, new String(yytext()));
						}else{
							System.out.print(yyline+1  +": Lexical error: ASCII char not between 32 and 126: '" +badChar+"'"); 
					  		System.exit(0);
						}
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
{COMMENT}			{ /* just skip what was found, do nothing */ }


//ERRORS


{QUOTE_ERROR} 		{ System.out.print(yyline+1  +": Lexical Error: Unclosed quote missing '" +yytext()+"'"); 
					  System.exit(0); }

{UNCLOSED_COMMENT}	{ System.out.print(yyline+1  +": Lexical Error: Unclosed Comment"); 
					  System.exit(0); }
					  
{LEADING_ZEROES}	{ System.out.print(yyline+1  +": Leading zeroes in number '" +yytext()+"'"); 
					  System.exit(0); }

{ERROR}				{ System.out.print(yyline+1  +": Lexical error: illegal character '" +yytext()+"'"); 
					  System.exit(0); }

}
