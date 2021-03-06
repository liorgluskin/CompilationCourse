/***************************/
/* FILE NAME: LEX_FILE.lex */
/***************************/

/***************************/
/* AUTHOR: OREN ISH SHALOM */
/***************************/

/*************/
/* USER CODE */
/*************/
   
import java_cup.runtime.*;

/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/
      
%%
   
/************************************/
/* OPTIONS AND DECLARATIONS SECTION */
/************************************/
   
/*****************************************************/ 
/* Lexer is the name of the class JFlex will create. */
/* The code will be written to the file Lexer.java.  */
/*****************************************************/ 
%class Lexer

/********************************************************************/
/* The current line number can be accessed with the variable yyline */
/* and the current column number with the variable yycolumn.        */
/********************************************************************/
%line
%column
    
/******************************************************************/
/* CUP compatibility mode interfaces with a CUP generated parser. */
/******************************************************************/
%cup
   
/****************/
/* DECLARATIONS */
/****************/
/*****************************************************************************/   
/* Code between %{ and %}, both of which must be at the beginning of a line, */
/* will be copied letter to letter into the Lexer class code.                */
/* Here you declare member variables and functions that are used inside the  */
/* scanner actions.                                                          */  
/*****************************************************************************/   
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

<<<<<<< HEAD
LINE_COMMENT	= "//".*{LineTerminator}?
COMMENT			= {LINE_COMMENT} | "/*"~"*/"
COMMENT_ERROR	= "/*"([^\*]|(\*[^/]))*
=======
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
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3

   
/******************************/
/* DOLAR DOLAR - DON'T TOUCH! */
/******************************/

%%

/************************************************************/
/* LEXER matches regular expressions to actions (Java code) */
/************************************************************/
   
/**************************************************************/
/* YYINITIAL is the state at which the lexer begins scanning. */
/* So these regular expressions will only be matched if the   */
/* scanner is in the start state YYINITIAL.                   */
/**************************************************************/
   
<YYINITIAL> {

"."					{ System.out.print(yyline+1+": DOT\n");		  return symbol(sym.DOT);}
","					{ System.out.print(yyline+1+": COMMA\n");	  return symbol(sym.COMMA);}
";"					{ System.out.print(yyline+1+": SEMI\n"); 	  return symbol(sym.SEMI);}
"+"					{ System.out.print(yyline+1+": PLUS\n");      return symbol(sym.PLUS);}
"*"					{ System.out.print(yyline+1+": MULTIPLY\n");  return symbol(sym.MULTIPLY);}
"/"					{ System.out.print(yyline+1+": DIVIDE\n");    return symbol(sym.DIVIDE);}
"("					{ System.out.print(yyline+1+": LP\n");    	  return symbol(sym.LP);}
")"					{ System.out.print(yyline+1+": RP\n");    	  return symbol(sym.RP);}
"="					{ System.out.print(yyline+1+": ASSIGN\n");    return symbol(sym.ASSIGN);}
"=="				{ System.out.print(yyline+1+": EQUAL\n");     return symbol(sym.EQUAL);}
">"					{ System.out.print(yyline+1+": GT\n");		  return symbol(sym.GT);}
">="				{ System.out.print(yyline+1+": GTE\n");		  return symbol(sym.GTE);}


//Lior

"["					{ System.out.print(yyline+1+": LB\n");	  	  return symbol(sym.LB);}
"{"					{ System.out.print(yyline+1+": LCBR\n");	  return symbol(sym.LCBR);}
"length"			{ System.out.print(yyline+1+": LENGTH\n");	  return symbol(sym.LENGTH);}
"new"				{ System.out.print(yyline+1+": NEW\n");	  	  return symbol(sym.NEW);}
"!"					{ System.out.print(yyline+1+": LNEG\n");	  return symbol(sym.LNEG);}
"||"				{ System.out.print(yyline+1+": LOR\n");	  	  return symbol(sym.LOR);}
"<"					{ System.out.print(yyline+1+": LT\n");	  	  return symbol(sym.LT);}
"<="				{ System.out.print(yyline+1+": LTE\n");	   	  return symbol(sym.LTE);}
"-"					{ System.out.print(yyline+1+": MINUS\n");	  return symbol(sym.MINUS);}
"%"					{ System.out.print(yyline+1+": MOD\n");	  	  return symbol(sym.MOD);}
"!="				{ System.out.print(yyline+1+": NEQUAL\n");	  return symbol(sym.NEQUAL);}


//Keren

"boolean"			{ System.out.print(yyline+1+": BOOLEAN\n");	  return symbol(sym.BOOLEAN);}
"break"				{ System.out.print(yyline+1+": BREAK\n");	  return symbol(sym.BREAK);}
"class"				{ System.out.print(yyline+1+": CLASS\n");	  return symbol(sym.CLASS);}
"continue"			{ System.out.print(yyline+1+": CONTINUE\n");  return symbol(sym.CONTINUE);}
"extends"			{ System.out.print(yyline+1+": EXTENDS\n");	  return symbol(sym.EXTENDS);}
"else"				{ System.out.print(yyline+1+": ELSE\n");	  return symbol(sym.ELSE);}
"false"				{ System.out.print(yyline+1+": FALSE\n");	  return symbol(sym.FALSE);}
"if"				{ System.out.print(yyline+1+": IF\n");		  return symbol(sym.IF);}
"int"				{ System.out.print(yyline+1+": INT\n");		  return symbol(sym.INT);}


//Tomer 

"null"				{ System.out.print(yyline+1+": NULL\n");	  return symbol(sym.NULL);}
"+"					{ System.out.print(yyline+1+": PLUS\n");	  return symbol(sym.PLUS);}
"]"					{ System.out.print(yyline+1+": RB\n");	 	  return symbol(sym.RB);}
"}"					{ System.out.print(yyline+1+": RCBR\n");	  return symbol(sym.RCBR);}
"return"			{ System.out.print(yyline+1+": RETURN\n");	  return symbol(sym.RETURN);}
")"					{ System.out.print(yyline+1+": RP\n");	  	  return symbol(sym.RP);}
";"					{ System.out.print(yyline+1+": SEMI\n");	  return symbol(sym.SEMI);}
"static"			{ System.out.print(yyline+1+": STATIC\n");	  return symbol(sym.STATIC);}
"string"			{ System.out.print(yyline+1+": STRING\n");	  return symbol(sym.STRING);}
"this"				{ System.out.print(yyline+1+": THIS\n");	  return symbol(sym.THIS);}
"true"				{ System.out.print(yyline+1+": TRUE\n");	  return symbol(sym.TRUE);}
"void"				{ System.out.print(yyline+1+": VOID\n");	  return symbol(sym.VOID);}
"while"				{ System.out.print(yyline+1+": WHILE\n");	  return symbol(sym.WHILE);}

// Tomer: Dealing with end of file
<<EOF>> 			{ System.out.print(yyline+2+": EOF");	  return symbol(sym.EOF);}

{INTEGER}			{
						Integer num = 0;
						try{
							num = new Integer(yytext());
						}
						catch(NumberFormatException e){
							System.out.print(yyline+1  +": Lexical Error: number not within valid IC range '" +yytext()+"'"); 
					  		System.exit(0); 
						}
						
						System.out.print(yyline+1+": INTEGER(");
						System.out.print(yytext());
						System.out.print(")\n");
						return symbol(sym.NUMBER, num);
					}   
{IDENTIFIER}		{
						System.out.print(yyline+1+": ID(");
						System.out.print(yytext());
						System.out.print(")\n");
						return symbol(sym.ID, new String(yytext()));
					}
{CLASS_ID}			{
						System.out.print(yyline+1+": CLASS_ID(");
						System.out.print(yytext());
						System.out.print(")\n");
						return symbol(sym.CLASS_ID, new String(yytext()));
					}
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
							System.out.print(yyline+1+": QUOTE(");
							System.out.print(yytext());
							System.out.print(")\n");
							return symbol(sym.QUOTE, new String(yytext()));
						}else{
							System.out.print(yyline+1  +": Lexical error: ASCII char not between 32 and 126: '" +badChar+"'"); 
					  		System.exit(0);
						}
					}
{WhiteSpace}		{ /* just skip what was found, do nothing */ }
{COMMENT}			{ /* just skip what was found, do nothing */ }

<<<<<<< HEAD
{COMMENT_ERROR}		{ System.out.print(yyline+1+": Unclosed comment"); }
{LEADING_ZEROES}	{ System.out.print(yyline+1+": Leading zeroes"); }
=======

//ERRORS


{QUOTE_ERROR} 		{ System.out.print(yyline+1  +": Lexical Error: Unclosed quote missing '" +yytext()+"'"); 
					  System.exit(0); }

{UNCLOSED_COMMENT}	{ System.out.print(yyline+1  +": Lexical Error: Unclosed Comment"); 
					  System.exit(0); }
					  
{LEADING_ZEROES}	{ System.out.print(yyline+1  +": Leading zeroes in number '" +yytext()+"'"); 
					  System.exit(0); }

{ERROR}				{ System.out.print(yyline+1  +": Lexical error: illegal character '" +yytext()+"'"); 
					  System.exit(0); }
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3

}
