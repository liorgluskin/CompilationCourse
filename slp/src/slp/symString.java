package slp;

public class symString implements sym {

	public static String getStringOfSymbol(int symId){
		switch (symId){
			case DIVIDE :{
				return "/";
			}
			case LCBR : {
				return  "}";
			}
			case COMMA: {
				return ":{";
			}
			case SEMI: {
				return ";";
			}
			case PLUS: {
				return "+";
			}
			case MULTIPLY: {
				return "*";
			}
			case LP: {
				return "(";
			}
			case RP: {
				return ")";			
			}
			case ASSIGN: {
				return "=";
			}
			
			case EQUAL: {
				return "==";
			} 
			case GT : {
				return ">";
			}
			case GTE: {
				return ">=";
			}
			case LB: {
				return "[";
			}
			case LENGTH:{
				return "length";
			}
			case NEW: {
				return "new";
			}
			case LNEG:{
				return "!";
			}
			case LOR:{
				return "||";
			}
			case LAND :{
				return "&&";
			}
			case LT:{
				return "<";
			}
			case LTE:{
				return "<=";
			}
			case MINUS:{
				return "-";
			}
			case MOD:{
				return "%";
			}
			case NEQUAL:{
				return "!=";
			}
			case BOOLEAN:{return "boolean";}
			case BREAK:{return "break";}
			case CLASS:{return "class";}
			case CONTINUE:{return "continue";}
			case EXTENDS:{return "extends";}
			case ELSE:{return "else";}
			case FALSE:{return "false";}
			case IF:{return "if";}
			case INT:{return "int";}
			case NULL:{return "null";}
			case RB:{return "]";}
			case RCBR:{return "}";}
			case RETURN:{return "return";}
			case STATIC:{return "static";}
			case STRING:{return "string";}
			case THIS:{return "this";}
			case TRUE:{return "true";}
			case VOID:{return "void";}
			case NUMBER:{return "Integer Number";}
			case QUOTE:{return "String Word";}
					
		}
		return "";	
	}
}