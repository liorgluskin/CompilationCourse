
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Wed Nov 18 12:49:38 IST 2015
//----------------------------------------------------

package slp;

import java_cup.runtime.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Wed Nov 18 12:49:38 IST 2015
  */
public class Slp_badCup extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public Slp_badCup() {super();}

  /** Constructor which sets the default scanner. */
  public Slp_badCup(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public Slp_badCup(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\025\000\002\005\003\000\002\002\004\000\002\005" +
    "\004\000\002\004\006\000\002\004\007\000\002\003\005" +
    "\000\002\003\004\000\002\003\005\000\002\003\003\000" +
    "\002\003\005\000\002\003\003\000\002\002\003\000\002" +
    "\002\003\000\002\002\003\000\002\002\003\000\002\002" +
    "\003\000\002\002\003\000\002\002\003\000\002\002\003" +
    "\000\002\002\003\000\002\002\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\044\000\006\023\005\025\004\001\002\000\004\007" +
    "\044\001\002\000\004\004\012\001\002\000\010\002\001" +
    "\023\001\025\001\001\002\000\010\002\011\023\005\025" +
    "\004\001\002\000\010\002\uffff\023\uffff\025\uffff\001\002" +
    "\000\004\002\000\001\002\000\014\004\016\011\015\022" +
    "\020\024\014\025\013\001\002\000\032\005\ufff7\006\ufff7" +
    "\010\ufff7\011\ufff7\012\ufff7\013\ufff7\014\ufff7\015\ufff7\016" +
    "\ufff7\017\ufff7\020\ufff7\021\ufff7\001\002\000\032\005\ufff9" +
    "\006\ufff9\010\ufff9\011\ufff9\012\ufff9\013\ufff9\014\ufff9\015" +
    "\ufff9\016\ufff9\017\ufff9\020\ufff9\021\ufff9\001\002\000\014" +
    "\004\016\011\015\022\020\024\014\025\013\001\002\000" +
    "\014\004\016\011\015\022\020\024\014\025\013\001\002" +
    "\000\030\005\035\010\027\011\023\012\025\013\034\014" +
    "\033\015\036\016\026\017\032\020\024\021\030\001\002" +
    "\000\004\004\021\001\002\000\004\005\022\001\002\000" +
    "\032\005\ufff8\006\ufff8\010\ufff8\011\ufff8\012\ufff8\013\ufff8" +
    "\014\ufff8\015\ufff8\016\ufff8\017\ufff8\020\ufff8\021\ufff8\001" +
    "\002\000\014\004\ufff5\011\ufff5\022\ufff5\024\ufff5\025\ufff5" +
    "\001\002\000\014\004\ufff2\011\ufff2\022\ufff2\024\ufff2\025" +
    "\ufff2\001\002\000\014\004\ufff4\011\ufff4\022\ufff4\024\ufff4" +
    "\025\ufff4\001\002\000\014\004\uffed\011\uffed\022\uffed\024" +
    "\uffed\025\uffed\001\002\000\014\004\ufff6\011\ufff6\022\ufff6" +
    "\024\ufff6\025\ufff6\001\002\000\014\004\ufff1\011\ufff1\022" +
    "\ufff1\024\ufff1\025\ufff1\001\002\000\014\004\016\011\015" +
    "\022\020\024\014\025\013\001\002\000\014\004\uffee\011" +
    "\uffee\022\uffee\024\uffee\025\uffee\001\002\000\014\004\uffef" +
    "\011\uffef\022\uffef\024\uffef\025\uffef\001\002\000\014\004" +
    "\ufff3\011\ufff3\022\ufff3\024\ufff3\025\ufff3\001\002\000\004" +
    "\006\037\001\002\000\014\004\ufff0\011\ufff0\022\ufff0\024" +
    "\ufff0\025\ufff0\001\002\000\010\002\ufffd\023\ufffd\025\ufffd" +
    "\001\002\000\032\005\ufffc\006\ufffc\010\027\011\023\012" +
    "\025\013\034\014\033\015\036\016\026\017\032\020\024" +
    "\021\030\001\002\000\030\005\042\010\027\011\023\012" +
    "\025\013\034\014\033\015\036\016\026\017\032\020\024" +
    "\021\030\001\002\000\032\005\ufffa\006\ufffa\010\ufffa\011" +
    "\ufffa\012\ufffa\013\ufffa\014\ufffa\015\ufffa\016\ufffa\017\ufffa" +
    "\020\ufffa\021\ufffa\001\002\000\032\005\ufffb\006\ufffb\010" +
    "\ufffb\011\ufffb\012\ufffb\013\ufffb\014\033\015\036\016\026" +
    "\017\032\020\ufffb\021\ufffb\001\002\000\014\004\016\011" +
    "\015\022\020\024\014\025\013\001\002\000\030\006\046" +
    "\010\027\011\023\012\025\013\034\014\033\015\036\016" +
    "\026\017\032\020\024\021\030\001\002\000\010\002\ufffe" +
    "\023\ufffe\025\ufffe\001\002" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\044\000\006\004\005\005\006\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\004\004\007" +
    "\001\001\000\002\001\001\000\002\001\001\000\004\003" +
    "\016\001\001\000\002\001\001\000\002\001\001\000\004" +
    "\003\042\001\001\000\004\003\040\001\001\000\004\002" +
    "\030\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\004\003\037\001\001\000\002\001\001\000\002\001" +
    "\001\000\002\001\001\000\002\001\001\000\002\001\001" +
    "\000\002\001\001\000\004\002\030\001\001\000\004\002" +
    "\030\001\001\000\002\001\001\000\004\002\030\001\001" +
    "\000\004\003\044\001\001\000\004\002\030\001\001\000" +
    "\002\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$Slp_badCup$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$Slp_badCup$actions(this);
    }

  /** Invoke a user supplied parse action. */
  public java_cup.runtime.Symbol do_action(
    int                        act_num,
    java_cup.runtime.lr_parser parser,
    java.util.Stack            stack,
    int                        top)
    throws java.lang.Exception
  {
    /* call code in generated class */
    return action_obj.CUP$Slp_badCup$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}


  /** Scan to get the next Symbol. */
  public java_cup.runtime.Symbol scan()
    throws java.lang.Exception
    {

	Token t = lexer.next_token();
	if (printTokens)
		System.out.println(t.getLine() + ":" + t);
	return t; 

    }


	/** Causes the parsr to print every token it reads.
	 * This is useful for debugging.
	 */
	public boolean printTokens;
	
	private Lexer lexer;

	public Parser(Lexer lexer) {
		super(lexer);
		this.lexer = lexer;
	}
	
	public int getLine() {
		return lexer.getLineNumber();
	}
	
	public void syntax_error(Symbol s) {
		Token tok = (Token) s;
		System.out.println("Line " + tok.getLine()+": Syntax error; unexpected " + tok);
	}

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$Slp_badCup$actions {
  private final Slp_badCup parser;

  /** Constructor */
  CUP$Slp_badCup$actions(Slp_badCup parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$Slp_badCup$do_action(
    int                        CUP$Slp_badCup$act_num,
    java_cup.runtime.lr_parser CUP$Slp_badCup$parser,
    java.util.Stack            CUP$Slp_badCup$stack,
    int                        CUP$Slp_badCup$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$Slp_badCup$result;

      /* select the action based on the action number */
      switch (CUP$Slp_badCup$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 20: // binop ::= GE 
            {
              Operator RESULT =null;
		 RESULT=Operator.GE; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 19: // binop ::= LE 
            {
              Operator RESULT =null;
		 RESULT=Operator.LE; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 18: // binop ::= GT 
            {
              Operator RESULT =null;
		 RESULT=Operator.GT; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 17: // binop ::= LT 
            {
              Operator RESULT =null;
		 RESULT=Operator.LT; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 16: // binop ::= LOR 
            {
              Operator RESULT =null;
		 RESULT=Operator.LOR; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 15: // binop ::= LAND 
            {
              Operator RESULT =null;
		 RESULT=Operator.LAND; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 14: // binop ::= DIV 
            {
              Operator RESULT =null;
		 RESULT=Operator.DIV; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // binop ::= MULT 
            {
              Operator RESULT =null;
		 RESULT=Operator.MULT; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // binop ::= MINUS 
            {
              Operator RESULT =null;
		 RESULT=Operator.MINUS; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // binop ::= PLUS 
            {
              Operator RESULT =null;
		 RESULT=Operator.PLUS; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("binop",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // expr ::= VAR 
            {
              Expr RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int vright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		String v = (String)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 RESULT = new VarExpr(v);
		   System.out.println("Reduced rule VAR:" + v);
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // expr ::= READI LPAREN RPAREN 
            {
              Expr RESULT =null;
		 RESULT = new ReadIExpr();
		   System.out.println("Reduced rule READI()");
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // expr ::= NUMBER 
            {
              Expr RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 RESULT = new NumberExpr(n.intValue());
		   System.out.println("Reduced rule NUMBER: " + n);
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr ::= LPAREN expr RPAREN 
            {
              Expr RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).value;
		 RESULT = e;  
		   System.out.println("Reduced rule (e): e=" + e);
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // expr ::= MINUS expr 
            {
              Expr RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 RESULT = new UnaryOpExpr(e, Operator.MINUS); 
		   System.out.println("Reduced rule -e: e=" + e);
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // expr ::= expr binop expr 
            {
              Expr RESULT =null;
		int e1left = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).left;
		int e1right = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).right;
		Expr e1 = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).value;
		int opleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).left;
		int opright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).right;
		Operator op = (Operator)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).value;
		int e2left = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int e2right = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		Expr e2 = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 RESULT = new BinaryOpExpr(e1, e2, op);
		   System.out.println("Reduced rule e1 " + op + " e2 for e1=" + e1 + " and e2="+e2);
   	    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("expr",1, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // stmt ::= PRINT LPAREN expr RPAREN SEMI 
            {
              Stmt RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-2)).value;
		 RESULT = new PrintStmt(e);
	   System.out.println("print(e): e=" + e);
    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("stmt",2, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-4)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // stmt ::= VAR ASSIGN expr SEMI 
            {
              Stmt RESULT =null;
		int vleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-3)).left;
		int vright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-3)).right;
		String v = (String)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-3)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).right;
		Expr e = (Expr)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).value;
		 VarExpr ve = new VarExpr(v); 
	   RESULT = new AssignStmt(ve, e);
	   System.out.println("v=e: v=" + v + " e=" + e);
    
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("stmt",2, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-3)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // stmt_list ::= stmt_list stmt 
            {
              StmtList RESULT =null;
		int slleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).left;
		int slright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).right;
		StmtList sl = (StmtList)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).value;
		int sleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		Stmt s = (Stmt)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 sl.addStmt(s); RESULT = sl; 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("stmt_list",3, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= stmt_list EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).right;
		StmtList start_val = (StmtList)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)).value;
		RESULT = start_val;
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.elementAt(CUP$Slp_badCup$top-1)), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$Slp_badCup$parser.done_parsing();
          return CUP$Slp_badCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // stmt_list ::= stmt 
            {
              StmtList RESULT =null;
		int sleft = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).left;
		int sright = ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()).right;
		Stmt s = (Stmt)((java_cup.runtime.Symbol) CUP$Slp_badCup$stack.peek()).value;
		 RESULT = new StmtList(s); 
              CUP$Slp_badCup$result = parser.getSymbolFactory().newSymbol("stmt_list",3, ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$Slp_badCup$stack.peek()), RESULT);
            }
          return CUP$Slp_badCup$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

