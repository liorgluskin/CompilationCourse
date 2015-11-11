
//----------------------------------------------------
// The following code was generated by CUP v0.11a beta 20060608
// Wed Nov 11 11:17:15 IST 2015
//----------------------------------------------------

import java_cup.runtime.*;

/** CUP v0.11a beta 20060608 generated parser.
  * @version Wed Nov 11 11:17:15 IST 2015
  */
public class YcalcCup extends java_cup.runtime.lr_parser {

  /** Default constructor. */
  public YcalcCup() {super();}

  /** Constructor which sets the default scanner. */
  public YcalcCup(java_cup.runtime.Scanner s) {super(s);}

  /** Constructor which sets the default scanner. */
  public YcalcCup(java_cup.runtime.Scanner s, java_cup.runtime.SymbolFactory sf) {super(s,sf);}

  /** Production table. */
  protected static final short _production_table[][] = 
    unpackFromStrings(new String[] {
    "\000\016\000\002\002\004\000\002\002\004\000\002\002" +
    "\003\000\002\007\002\000\002\003\005\000\002\004\005" +
    "\000\002\004\005\000\002\004\003\000\002\005\005\000" +
    "\002\005\005\000\002\005\003\000\002\006\005\000\002" +
    "\006\003\000\002\006\003" });

  /** Access to production table. */
  public short[][] production_table() {return _production_table;}

  /** Parse-action table. */
  protected static final short[][] _action_table = 
    unpackFromStrings(new String[] {
    "\000\027\000\010\011\013\013\010\014\004\001\002\000" +
    "\016\004\ufff4\005\ufff4\006\ufff4\007\ufff4\010\ufff4\012\ufff4" +
    "\001\002\000\016\004\ufff7\005\ufff7\006\ufff7\007\ufff7\010" +
    "\ufff7\012\ufff7\001\002\000\012\002\uffff\011\uffff\013\uffff" +
    "\014\uffff\001\002\000\016\004\ufffa\005\ufffa\006\ufffa\007" +
    "\022\010\021\012\ufffa\001\002\000\016\004\ufff5\005\ufff5" +
    "\006\ufff5\007\ufff5\010\ufff5\012\ufff5\001\002\000\012\002" +
    "\031\011\013\013\010\014\004\001\002\000\010\004\ufffe" +
    "\005\016\006\017\001\002\000\010\011\013\013\010\014" +
    "\004\001\002\000\010\005\016\006\017\012\015\001\002" +
    "\000\016\004\ufff6\005\ufff6\006\ufff6\007\ufff6\010\ufff6\012" +
    "\ufff6\001\002\000\010\011\013\013\010\014\004\001\002" +
    "\000\010\011\013\013\010\014\004\001\002\000\016\004" +
    "\ufffb\005\ufffb\006\ufffb\007\022\010\021\012\ufffb\001\002" +
    "\000\010\011\013\013\010\014\004\001\002\000\010\011" +
    "\013\013\010\014\004\001\002\000\016\004\ufff9\005\ufff9" +
    "\006\ufff9\007\ufff9\010\ufff9\012\ufff9\001\002\000\016\004" +
    "\ufff8\005\ufff8\006\ufff8\007\ufff8\010\ufff8\012\ufff8\001\002" +
    "\000\016\004\ufffc\005\ufffc\006\ufffc\007\022\010\021\012" +
    "\ufffc\001\002\000\004\004\027\001\002\000\012\002\ufffd" +
    "\011\ufffd\013\ufffd\014\ufffd\001\002\000\012\002\001\011" +
    "\001\013\001\014\001\001\002\000\004\002\000\001\002" +
    "" });

  /** Access to parse-action table. */
  public short[][] action_table() {return _action_table;}

  /** <code>reduce_goto</code> table. */
  protected static final short[][] _reduce_table = 
    unpackFromStrings(new String[] {
    "\000\027\000\014\002\010\003\005\004\011\005\006\006" +
    "\004\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001\000\002\001\001\000\002\001\001\000\012\003" +
    "\027\004\011\005\006\006\004\001\001\000\004\007\025" +
    "\001\001\000\010\004\013\005\006\006\004\001\001\000" +
    "\002\001\001\000\002\001\001\000\006\005\024\006\004" +
    "\001\001\000\006\005\017\006\004\001\001\000\002\001" +
    "\001\000\004\006\023\001\001\000\004\006\022\001\001" +
    "\000\002\001\001\000\002\001\001\000\002\001\001\000" +
    "\002\001\001\000\002\001\001\000\002\001\001\000\002" +
    "\001\001" });

  /** Access to <code>reduce_goto</code> table. */
  public short[][] reduce_table() {return _reduce_table;}

  /** Instance of action encapsulation class. */
  protected CUP$YcalcCup$actions action_obj;

  /** Action encapsulation object initializer. */
  protected void init_actions()
    {
      action_obj = new CUP$YcalcCup$actions(this);
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
    return action_obj.CUP$YcalcCup$do_action(act_num, parser, stack, top);
  }

  /** Indicates start state. */
  public int start_state() {return 0;}
  /** Indicates start production. */
  public int start_production() {return 1;}

  /** <code>EOF</code> Symbol index. */
  public int EOF_sym() {return 0;}

  /** <code>error</code> Symbol index. */
  public int error_sym() {return 1;}



    
    /* Change the method report_error so it will display the line and
       column of where the error occurred in the input as well as the
       reason for the error which is passed into the method in the
       String 'message'. */
    public void report_error(String message, Object info) {
   
        /* Create a StringBuilder called 'm' with the string 'Error' in it. */
        StringBuilder m = new StringBuilder("Error");
   
        /* Check if the information passed to the method is the same
           type as the type java_cup.runtime.Symbol. */
        if (info instanceof java_cup.runtime.Symbol) {
            /* Declare a java_cup.runtime.Symbol object 's' with the
               information in the object info that is being typecasted
               as a java_cup.runtime.Symbol object. */
            java_cup.runtime.Symbol s = ((java_cup.runtime.Symbol) info);
   
            /* Check if the line number in the input is greater or
               equal to zero. */
            if (s.left >= 0) {                
                /* Add to the end of the StringBuilder error message
                   the line number of the error in the input. */
                m.append(" in line "+(s.left+1));   
                /* Check if the column number in the input is greater
                   or equal to zero. */
                if (s.right >= 0)                    
                    /* Add to the end of the StringBuilder error message
                       the column number of the error in the input. */
                    m.append(", column "+(s.right+1));
            }
        }
   
        /* Add to the end of the StringBuilder error message created in
           this method the message that was passed into this method. */
        m.append(" : "+message);
   
        /* Print the contents of the StringBuilder 'm', which contains
           an error message, out on a line. */
        System.err.println(m);
    }
   
    /* Change the method report_fatal_error so when it reports a fatal
       error it will display the line and column number of where the
       fatal error occurred in the input as well as the reason for the
       fatal error which is passed into the method in the object
       'message' and then exit.*/
    public void report_fatal_error(String message, Object info) {
        report_error(message, info);
        System.exit(1);
    }

}

/** Cup generated class to encapsulate user supplied action code.*/
class CUP$YcalcCup$actions {
  private final YcalcCup parser;

  /** Constructor */
  CUP$YcalcCup$actions(YcalcCup parser) {
    this.parser = parser;
  }

  /** Method with the actual generated action code. */
  public final java_cup.runtime.Symbol CUP$YcalcCup$do_action(
    int                        CUP$YcalcCup$act_num,
    java_cup.runtime.lr_parser CUP$YcalcCup$parser,
    java.util.Stack            CUP$YcalcCup$stack,
    int                        CUP$YcalcCup$top)
    throws java.lang.Exception
    {
      /* Symbol object for return from actions */
      java_cup.runtime.Symbol CUP$YcalcCup$result;

      /* select the action based on the action number */
      switch (CUP$YcalcCup$act_num)
        {
          /*. . . . . . . . . . . . . . . . . . . .*/
          case 13: // term ::= ID 
            {
              Integer RESULT =null;
		int ileft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int iright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer i = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = i; 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("term",4, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 12: // term ::= NUMBER 
            {
              Integer RESULT =null;
		int nleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int nright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer n = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = n; 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("term",4, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 11: // term ::= LPAREN expr RPAREN 
            {
              Integer RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).value;
		 RESULT = e; 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("term",4, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 10: // factor ::= term 
            {
              Integer RESULT =null;
		int tleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer t = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(t.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("factor",3, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 9: // factor ::= factor DIVIDE term 
            {
              Integer RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).right;
		Integer f = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).value;
		int tleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer t = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(f.intValue() / t.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("factor",3, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 8: // factor ::= factor TIMES term 
            {
              Integer RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).left;
		int fright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).right;
		Integer f = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).value;
		int tleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int tright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer t = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(f.intValue() * t.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("factor",3, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 7: // expr ::= factor 
            {
              Integer RESULT =null;
		int fleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer f = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(f.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 6: // expr ::= expr MINUS factor 
            {
              Integer RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer f = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(e.intValue() - f.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 5: // expr ::= expr PLUS factor 
            {
              Integer RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).value;
		int fleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int fright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer f = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
		 RESULT = new Integer(e.intValue() + f.intValue()); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr",2, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 4: // expr_part ::= expr NT$0 SEMI 
            {
              Object RESULT =null;
              // propagate RESULT from NT$0
                RESULT = (Object) ((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).value;
		int eleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).left;
		int eright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)).value;

              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr_part",1, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-2)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 3: // NT$0 ::= 
            {
              Object RESULT =null;
		int eleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).left;
		int eright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()).right;
		Integer e = (Integer)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.peek()).value;
 System.out.println(" = " + e); 
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("NT$0",5, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 2: // expr_list ::= expr_part 
            {
              Object RESULT =null;

              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr_list",0, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 1: // $START ::= expr_list EOF 
            {
              Object RESULT =null;
		int start_valleft = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).left;
		int start_valright = ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).right;
		Object start_val = (Object)((java_cup.runtime.Symbol) CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)).value;
		RESULT = start_val;
              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("$START",0, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          /* ACCEPT */
          CUP$YcalcCup$parser.done_parsing();
          return CUP$YcalcCup$result;

          /*. . . . . . . . . . . . . . . . . . . .*/
          case 0: // expr_list ::= expr_list expr_part 
            {
              Object RESULT =null;

              CUP$YcalcCup$result = parser.getSymbolFactory().newSymbol("expr_list",0, ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.elementAt(CUP$YcalcCup$top-1)), ((java_cup.runtime.Symbol)CUP$YcalcCup$stack.peek()), RESULT);
            }
          return CUP$YcalcCup$result;

          /* . . . . . .*/
          default:
            throw new Exception(
               "Invalid action number found in internal parse table");

        }
    }
}

