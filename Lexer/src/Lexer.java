<<<<<<< HEAD
<<<<<<< HEAD
/* The following code was generated by JFlex 1.4.3 on 11/7/15 9:09 PM */
=======
/* The following code was generated by JFlex 1.4.3 on 15:40 11/11/15 */
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
=======
/* The following code was generated by JFlex 1.4.3 on 20:52 18/11/15 */
>>>>>>> origin/Lior_brunch

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
      

class Lexer implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0, 0
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\3\1\2\1\0\1\3\1\1\22\0\1\3\1\36\1\13"+
    "\2\0\1\42\2\0\1\23\1\24\1\4\1\22\1\20\1\41\1\17"+
    "\1\5\1\6\11\7\1\0\1\21\1\40\1\25\1\26\2\0\32\12"+
    "\1\27\1\14\1\57\1\0\1\11\1\0\1\45\1\43\1\50\1\55"+
    "\1\32\1\56\1\33\1\34\1\52\1\10\1\47\1\31\1\10\1\15"+
    "\1\44\2\10\1\46\1\51\1\16\1\53\1\61\1\35\1\54\2\10"+
    "\1\30\1\37\1\60\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
<<<<<<< HEAD
    "\1\0\2\1\1\2\1\3\2\4\1\5\1\6\1\7"+
    "\1\10\1\11\1\12\1\13\1\14\1\15\1\16\1\0"+
    "\5\5\1\1\1\17\1\20\1\21\1\22\1\23\7\5"+
    "\1\24\1\5\1\0\6\5\1\25\1\5\1\17\2\5"+
    "\1\26\4\5\1\1\1\5\1\27\2\5\1\30\1\31"+
    "\1\17\1\0\3\5\1\32\1\33\1\5\1\34";

  private static int [] zzUnpackAction() {
    int [] result = new int[71];
=======
    "\1\0\1\1\2\2\1\3\1\4\2\5\1\6\1\7"+
    "\1\10\2\6\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\3\6\1\23\1\1\1\24"+
    "\1\25\1\26\6\6\1\27\1\30\1\6\1\31\1\2"+
    "\1\32\1\33\1\0\4\6\1\34\1\35\4\6\1\36"+
    "\1\37\1\40\7\6\1\41\2\6\1\31\1\0\1\42"+
    "\16\6\1\43\2\6\1\0\1\2\1\44\1\45\1\46"+
    "\1\6\1\47\12\6\1\50\2\0\2\6\1\51\1\6"+
    "\1\52\1\6\1\53\3\6\1\54\1\55\2\6\1\56"+
    "\1\6\1\57\1\60\1\61\1\62\1\6\1\63";

  private static int [] zzUnpackAction() {
    int [] result = new int[130];
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
<<<<<<< HEAD
    "\0\0\0\44\0\110\0\154\0\110\0\220\0\264\0\330"+
    "\0\374\0\110\0\110\0\110\0\110\0\110\0\110\0\u0120"+
    "\0\u0144\0\u0168\0\u018c\0\u01b0\0\u01d4\0\u01f8\0\u021c\0\u0240"+
    "\0\u0264\0\220\0\110\0\110\0\110\0\u0288\0\u02ac\0\u02d0"+
    "\0\u02f4\0\u0318\0\u033c\0\u0360\0\330\0\u0384\0\u03a8\0\u03cc"+
    "\0\u03f0\0\u0414\0\u0438\0\u045c\0\u0480\0\330\0\u04a4\0\u04c8"+
    "\0\u04ec\0\u0510\0\330\0\u0534\0\u0558\0\u057c\0\u05a0\0\u05c4"+
    "\0\u05e8\0\330\0\u060c\0\u0630\0\330\0\330\0\u05c4\0\u0654"+
    "\0\u0678\0\u069c\0\u06c0\0\330\0\330\0\u06e4\0\330";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[71];
=======
    "\0\0\0\62\0\144\0\62\0\62\0\226\0\310\0\372"+
    "\0\u012c\0\u015e\0\u0190\0\u01c2\0\u01f4\0\62\0\62\0\62"+
    "\0\62\0\62\0\62\0\u0226\0\u0258\0\62\0\62\0\u028a"+
    "\0\u02bc\0\u02ee\0\u0320\0\u0352\0\u0384\0\62\0\62\0\u03b6"+
    "\0\u03e8\0\u041a\0\u044c\0\u047e\0\u04b0\0\62\0\62\0\u04e2"+
    "\0\u0514\0\u0546\0\u0578\0\62\0\u05aa\0\u05dc\0\u060e\0\u0640"+
    "\0\u0672\0\62\0\62\0\u06a4\0\u06d6\0\u0708\0\u073a\0\62"+
    "\0\62\0\62\0\u076c\0\u079e\0\u07d0\0\u0802\0\u0834\0\u0866"+
    "\0\u0898\0\u012c\0\u08ca\0\u08fc\0\u092e\0\u0960\0\u012c\0\u0992"+
    "\0\u09c4\0\u09f6\0\u0a28\0\u0a5a\0\u0a8c\0\u0abe\0\u0af0\0\u0b22"+
    "\0\u0b54\0\u0b86\0\u0bb8\0\u0bea\0\u0c1c\0\u012c\0\u0c4e\0\u0c80"+
    "\0\u0cb2\0\u0ce4\0\u012c\0\u012c\0\u012c\0\u0d16\0\u012c\0\u0d48"+
    "\0\u0d7a\0\u0dac\0\u0dde\0\u0e10\0\u0e42\0\u0e74\0\u0ea6\0\u0ed8"+
    "\0\u0f0a\0\u012c\0\u0ce4\0\u0f3c\0\u0f6e\0\u0fa0\0\u012c\0\u0fd2"+
    "\0\u012c\0\u1004\0\u012c\0\u1036\0\u1068\0\u109a\0\u012c\0\u012c"+
    "\0\u10cc\0\u10fe\0\u012c\0\u1130\0\u012c\0\u012c\0\u012c\0\u012c"+
    "\0\u1162\0\u012c";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[130];
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
<<<<<<< HEAD
    "\1\0\1\2\2\3\1\4\1\5\1\6\1\7\1\10"+
    "\1\0\1\11\1\12\1\13\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\2\10\1\24\4\10\1\25"+
    "\2\10\1\26\3\10\1\27\2\0\1\3\111\0\1\30"+
    "\1\31\44\0\2\32\42\0\2\7\42\0\5\10\11\0"+
    "\20\10\6\0\5\11\11\0\20\11\21\0\1\33\43\0"+
    "\1\34\45\0\1\35\26\0\5\10\11\0\1\10\1\36"+
    "\4\10\1\37\11\10\6\0\5\10\11\0\2\10\1\40"+
    "\12\10\1\41\2\10\6\0\5\10\11\0\1\10\1\42"+
    "\1\43\15\10\6\0\5\10\11\0\5\10\1\44\11\10"+
    "\1\45\6\0\5\10\11\0\4\10\1\46\13\10\2\30"+
    "\1\3\41\30\5\31\1\47\36\31\6\0\5\10\11\0"+
    "\1\10\1\50\16\10\6\0\5\10\11\0\3\10\1\51"+
    "\14\10\6\0\5\10\11\0\11\10\1\52\6\10\6\0"+
    "\5\10\11\0\12\10\1\53\5\10\6\0\5\10\11\0"+
    "\5\10\1\54\12\10\6\0\5\10\11\0\4\10\1\55"+
    "\13\10\6\0\5\10\11\0\12\10\1\56\5\10\6\0"+
    "\5\10\11\0\2\10\1\57\15\10\4\31\1\3\1\60"+
    "\36\31\6\0\5\10\11\0\2\10\1\61\15\10\6\0"+
    "\5\10\11\0\4\10\1\62\13\10\6\0\5\10\11\0"+
    "\3\10\1\63\14\10\6\0\5\10\11\0\3\10\1\64"+
    "\14\10\6\0\5\10\11\0\12\10\1\65\5\10\6\0"+
    "\5\10\11\0\11\10\1\66\6\10\6\0\5\10\11\0"+
    "\11\10\1\67\6\10\4\31\1\70\1\47\36\31\6\0"+
    "\5\10\11\0\3\10\1\71\14\10\6\0\5\10\11\0"+
    "\7\10\1\72\10\10\6\0\5\10\11\0\5\10\1\73"+
    "\12\10\6\0\5\10\11\0\13\10\1\74\4\10\6\0"+
    "\5\10\11\0\11\10\1\75\6\10\6\0\5\10\11\0"+
    "\3\10\1\76\14\10\5\77\1\100\36\77\6\0\5\10"+
    "\11\0\4\10\1\101\13\10\6\0\5\10\11\0\16\10"+
    "\1\102\1\10\6\0\5\10\11\0\5\10\1\103\12\10"+
    "\4\77\1\0\37\77\6\0\5\10\11\0\5\10\1\104"+
    "\12\10\6\0\5\10\11\0\11\10\1\105\6\10\6\0"+
    "\5\10\11\0\14\10\1\106\3\10\6\0\5\10\11\0"+
    "\3\10\1\107\14\10";

  private static int [] zzUnpackTrans() {
    int [] result = new int[1800];
=======
    "\1\2\1\3\2\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\2\1\12\1\13\1\2\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\2\11\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\2\11\1\41\1\11\1\42\1\43\1\44"+
    "\3\11\1\45\1\46\1\47\1\50\64\0\1\4\63\0"+
    "\1\51\1\52\62\0\1\7\1\53\60\0\2\10\60\0"+
    "\5\11\2\0\2\11\12\0\5\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\12\2\0\2\12\12\0\5\12\5\0"+
    "\14\12\2\0\1\12\13\13\1\54\1\55\45\13\6\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\56\3\11\5\0"+
    "\10\11\1\57\3\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\3\11\1\60\1\11\5\0\3\11\1\61"+
    "\10\11\2\0\1\11\25\0\1\62\61\0\1\63\42\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\64\3\11\5\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\1\65\4\11\5\0\11\11\1\66\2\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\3\11\1\67\1\11"+
    "\5\0\14\11\2\0\1\11\25\0\1\70\73\0\1\71"+
    "\47\0\1\72\42\0\5\11\2\0\2\11\12\0\5\11"+
    "\5\0\1\11\1\73\1\11\1\74\10\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\1\11\1\75\3\11"+
    "\5\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\1\76\4\11\5\0\1\11\1\77\12\11\2\0"+
    "\1\11\6\0\5\11\2\0\1\11\1\100\12\0\5\11"+
    "\5\0\14\11\2\0\1\11\6\0\5\11\2\0\1\101"+
    "\1\11\12\0\5\11\5\0\13\11\1\102\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\5\11\5\0\2\11"+
    "\1\103\11\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\5\0\1\11\1\104\12\11\2\0\1\11"+
    "\4\105\1\106\55\105\1\52\1\3\1\4\57\52\6\0"+
    "\2\53\65\0\4\13\51\0\5\11\2\0\2\11\12\0"+
    "\4\11\1\107\5\0\14\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\1\110\4\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\5\0"+
    "\7\11\1\111\4\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\5\0\10\11\1\112\3\11\2\0"+
    "\1\11\6\0\5\11\2\0\1\113\1\11\12\0\5\11"+
    "\5\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\5\0\6\11\1\114\5\11\2\0\1\11"+
    "\6\0\5\11\2\0\1\11\1\115\12\0\5\11\5\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\5\0\7\11\1\116\4\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\5\11\5\0\1\11\1\117"+
    "\12\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\1\11\1\120\3\11\5\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\1\11\1\121\12\0\5\11\5\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\5\0\2\11\1\122\11\11\2\0\1\11\6\0\5\11"+
    "\2\0\1\123\1\11\12\0\5\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\5\0"+
    "\2\11\1\124\1\125\10\11\2\0\1\11\6\0\5\11"+
    "\2\0\1\11\1\126\12\0\5\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\1\127\4\11"+
    "\5\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\5\0\7\11\1\130\4\11\2\0\1\11"+
    "\4\105\1\131\61\105\1\131\1\132\54\105\6\0\5\11"+
    "\2\0\2\11\12\0\1\133\4\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\5\0"+
    "\6\11\1\134\5\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\135\3\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\2\11\1\136"+
    "\2\11\5\0\14\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\137\3\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\1\11\1\140"+
    "\3\11\5\0\14\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\141\4\11\5\0\14\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\1\142\4\11\5\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\5\0\2\11\1\143\11\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\5\11\5\0\10\11\1\144"+
    "\3\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\5\0\6\11\1\145\5\11\2\0\1\11\6\0"+
    "\5\11\2\0\1\11\1\146\12\0\5\11\5\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\1\11\1\147\12\0"+
    "\5\11\5\0\14\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\5\0\7\11\1\150\4\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\5\0"+
    "\6\11\1\151\5\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\5\0\12\11\1\152\1\11\2\0"+
    "\1\11\4\105\1\131\1\4\54\105\4\153\1\154\55\153"+
    "\6\0\5\11\2\0\1\11\1\155\12\0\5\11\5\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\1\156\1\11"+
    "\12\0\5\11\5\0\14\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\1\11\1\157\3\11\5\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\1\11"+
    "\1\160\3\11\5\0\14\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\5\0\4\11\1\161\7\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\5\0\3\11\1\162\10\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\5\0\6\11\1\163\5\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\5\0\7\11\1\164\4\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\5\0\7\11\1\165\4\11"+
    "\2\0\1\11\6\0\5\11\2\0\1\166\1\11\12\0"+
    "\5\11\5\0\14\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\167\3\11\5\0\14\11\2\0"+
    "\1\11\4\153\1\154\1\4\54\153\6\0\5\11\2\0"+
    "\2\11\12\0\3\11\1\170\1\11\5\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\5\0"+
    "\12\11\1\171\1\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\5\0\2\11\1\172\11\11\2\0"+
    "\1\11\6\0\5\11\2\0\1\173\1\11\12\0\5\11"+
    "\5\0\14\11\2\0\1\11\6\0\5\11\2\0\1\174"+
    "\1\11\12\0\5\11\5\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\5\11\5\0\5\11\1\175"+
    "\6\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\2\11\1\176\2\11\5\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\5\11\5\0\6\11\1\177"+
    "\5\11\2\0\1\11\6\0\5\11\2\0\1\200\1\11"+
    "\12\0\5\11\5\0\14\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\5\0\10\11\1\201\3\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\1\11"+
    "\1\202\3\11\5\0\14\11\2\0\1\11";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4500];
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
<<<<<<< HEAD
    "\1\0\1\1\1\11\1\1\1\11\4\1\6\11\2\1"+
    "\1\0\10\1\3\11\11\1\1\0\30\1\1\0\7\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[71];
=======
    "\1\0\1\11\1\1\2\11\10\1\6\11\2\1\2\11"+
    "\6\1\2\11\6\1\2\11\4\1\1\11\1\0\4\1"+
    "\2\11\4\1\3\11\13\1\1\0\22\1\1\0\21\1"+
    "\2\0\26\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[130];
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
    /*********************************************************************************/
    /* Create a new java_cup.runtime.Symbol with information about the current token */
    /*********************************************************************************/
    private Symbol symbol(int type)               {return new Symbol(type, yyline, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline, yycolumn, value);}


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  Lexer(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  Lexer(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 128) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Contains user EOF-code, which will be executed exactly once,
   * when the end of file is reached
   */
  private void zzDoEOF() throws java.io.IOException {
    if (!zzEOFDone) {
      zzEOFDone = true;
      yyclose();
    }
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      boolean zzR = false;
      for (zzCurrentPosL = zzStartRead; zzCurrentPosL < zzMarkedPosL;
                                                             zzCurrentPosL++) {
        switch (zzBufferL[zzCurrentPosL]) {
        case '\u000B':
        case '\u000C':
        case '\u0085':
        case '\u2028':
        case '\u2029':
          yyline++;
          yycolumn = 0;
          zzR = false;
          break;
        case '\r':
          yyline++;
          yycolumn = 0;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
            yycolumn = 0;
          }
          break;
        default:
          zzR = false;
          yycolumn++;
        }
      }

      if (zzR) {
        // peek one character ahead if it is \n (if we have counted one line too much)
        boolean zzPeek;
        if (zzMarkedPosL < zzEndReadL)
          zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        else if (zzAtEOF)
          zzPeek = false;
        else {
          boolean eof = zzRefill();
          zzEndReadL = zzEndRead;
          zzMarkedPosL = zzMarkedPos;
          zzBufferL = zzBuffer;
          if (eof) 
            zzPeek = false;
          else 
            zzPeek = zzBufferL[zzMarkedPosL] == '\n';
        }
        if (zzPeek) yyline--;
      }
      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 27: 
          { String str = new String(yytext());
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
        case 52: break;
        case 4: 
          { System.out.print(yyline+1+": DIVIDE\n");    return symbol(sym.DIVIDE);
          }
        case 53: break;
        case 13: 
          { System.out.print(yyline+1+": LP\n");    	  return symbol(sym.LP);
          }
        case 54: break;
        case 17: 
          { System.out.print(yyline+1+": LB\n");	  	  return symbol(sym.LB);
          }
        case 55: break;
        case 22: 
          { System.out.print(yyline+1+": MOD\n");	  	  return symbol(sym.MOD);
          }
        case 56: break;
        case 20: 
          { System.out.print(yyline+1+": LT\n");	  	  return symbol(sym.LT);
          }
        case 57: break;
        case 11: 
          { System.out.print(yyline+1+": SEMI\n"); 	  return symbol(sym.SEMI);
          }
        case 58: break;
        case 16: 
          { System.out.print(yyline+1+": GT\n");		  return symbol(sym.GT);
          }
        case 59: break;
        case 36: 
          { System.out.print(yyline+1+": NULL\n");	  return symbol(sym.NULL);
          }
        case 60: break;
        case 6: 
          { System.out.print(yyline+1+": ID(");
						System.out.print(yytext());
						System.out.print(")\n");
						return symbol(sym.ID, new String(yytext()));
          }
        case 61: break;
        case 31: 
          { System.out.print(yyline+1+": LOR\n");	  	  return symbol(sym.LOR);
          }
        case 62: break;
        case 26: 
          { System.out.print(yyline+1  +": Leading zeroes in number '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 63: break;
        case 47: 
          { System.out.print(yyline+1+": STATIC\n");	  return symbol(sym.STATIC);
          }
        case 64: break;
        case 10: 
          { System.out.print(yyline+1+": COMMA\n");	  return symbol(sym.COMMA);
          }
<<<<<<< HEAD
        case 36: break;
        case 9: 
          { System.out.print(yyline+1+": SEMI\n"); 	  return symbol(sym.SEMI);
          }
        case 37: break;
        case 25: 
          { System.out.print(yyline+1+": FALSE\n");	  return symbol(sym.FALSE);
          }
        case 38: break;
        case 27: 
          { System.out.print(yyline+1+": EXTENDS\n");	  return symbol(sym.EXTENDS);
          }
        case 39: break;
        case 3: 
          { System.out.print(yyline+1+": MULTIPLY\n");  return symbol(sym.MULTIPLY);
          }
        case 40: break;
=======
        case 65: break;
        case 29: 
          { System.out.print(yyline+1+": GTE\n");		  return symbol(sym.GTE);
          }
        case 66: break;
        case 38: 
          { System.out.print(yyline+1+": TRUE\n");	  return symbol(sym.TRUE);
          }
        case 67: break;
        case 15: 
          { System.out.print(yyline+1+": ASSIGN\n");    return symbol(sym.ASSIGN);
          }
        case 68: break;
        case 2: 
          { /* just skip what was found, do nothing */
          }
        case 69: break;
        case 48: 
          { System.out.print(yyline+1+": STRING\n");	  return symbol(sym.STRING);
          }
        case 70: break;
        case 23: 
          { System.out.print(yyline+1+": RB\n");	 	  return symbol(sym.RB);
          }
        case 71: break;
        case 40: 
          { System.out.print(yyline+1+": VOID\n");	  return symbol(sym.VOID);
          }
        case 72: break;
        case 39: 
          { System.out.print(yyline+1+": ELSE\n");	  return symbol(sym.ELSE);
          }
        case 73: break;
        case 34: 
          { System.out.print(yyline+1+": NEW\n");	  	  return symbol(sym.NEW);
          }
        case 74: break;
        case 14: 
          { System.out.print(yyline+1+": RP\n");    	  return symbol(sym.RP);
          }
        case 75: break;
        case 43: 
          { System.out.print(yyline+1+": CLASS\n");	  return symbol(sym.CLASS);
          }
        case 76: break;
        case 18: 
          { System.out.print(yyline+1+": LCBR\n");	  return symbol(sym.LCBR);
          }
        case 77: break;
        case 30: 
          { System.out.print(yyline+1+": NEQUAL\n");	  return symbol(sym.NEQUAL);
          }
        case 78: break;
        case 5: 
          { Integer num = 0;
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
        case 79: break;
        case 8: 
          { System.out.print(yyline+1  +": Lexical Error: Unclosed quote missing '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 80: break;
        case 44: 
          { System.out.print(yyline+1+": FALSE\n");	  return symbol(sym.FALSE);
          }
        case 81: break;
        case 49: 
          { System.out.print(yyline+1+": EXTENDS\n");	  return symbol(sym.EXTENDS);
          }
        case 82: break;
        case 24: 
          { System.out.print(yyline+1+": RCBR\n");	  return symbol(sym.RCBR);
          }
        case 83: break;
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
        case 12: 
          { System.out.print(yyline+1+": PLUS\n");      return symbol(sym.PLUS);
          }
<<<<<<< HEAD
        case 41: break;
        case 28: 
          { System.out.print(yyline+1+": CONTINUE\n");  return symbol(sym.CONTINUE);
          }
        case 42: break;
        case 16: 
          { System.out.print(yyline+1+": Leading zeroes");
          }
        case 43: break;
        case 6: 
=======
        case 84: break;
        case 41: 
          { System.out.print(yyline+1+": WHILE\n");	  return symbol(sym.WHILE);
          }
        case 85: break;
        case 1: 
          { System.out.print(yyline+1  +": Lexical error: illegal character '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 86: break;
        case 33: 
          { System.out.print(yyline+1+": IF\n");		  return symbol(sym.IF);
          }
        case 87: break;
        case 45: 
          { System.out.print(yyline+1+": LENGTH\n");	  return symbol(sym.LENGTH);
          }
        case 88: break;
        case 7: 
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
          { System.out.print(yyline+1+": CLASS_ID(");
						System.out.print(yytext());
						System.out.print(")\n");
						return symbol(sym.CLASS_ID, new String(yytext()));
          }
        case 89: break;
        case 50: 
          { System.out.print(yyline+1+": BOOLEAN\n");	  return symbol(sym.BOOLEAN);
          }
        case 90: break;
        case 19: 
          { System.out.print(yyline+1+": LNEG\n");	  return symbol(sym.LNEG);
          }
<<<<<<< HEAD
        case 47: break;
        case 13: 
          { System.out.print(yyline+1+": ASSIGN\n");    return symbol(sym.ASSIGN);
          }
        case 48: break;
        case 15: 
          { System.out.print(yyline+1+": Unclosed comment");
          }
        case 49: break;
        case 23: 
          { System.out.print(yyline+1+": BREAK\n");	  return symbol(sym.BREAK);
=======
        case 91: break;
        case 46: 
          { System.out.print(yyline+1+": RETURN\n");	  return symbol(sym.RETURN);
          }
        case 92: break;
        case 28: 
          { System.out.print(yyline+1+": EQUAL\n");     return symbol(sym.EQUAL);
          }
        case 93: break;
        case 32: 
          { System.out.print(yyline+1+": LTE\n");	   	  return symbol(sym.LTE);
>>>>>>> 0224c767705086e59d9fd80e4591a548bbd02ca3
          }
        case 94: break;
        case 51: 
          { System.out.print(yyline+1+": CONTINUE\n");  return symbol(sym.CONTINUE);
          }
        case 95: break;
        case 35: 
          { System.out.print(yyline+1+": INT\n");		  return symbol(sym.INT);
          }
        case 96: break;
        case 21: 
          { System.out.print(yyline+1+": MINUS\n");	  return symbol(sym.MINUS);
          }
        case 97: break;
        case 42: 
          { System.out.print(yyline+1+": BREAK\n");	  return symbol(sym.BREAK);
          }
        case 98: break;
        case 9: 
          { System.out.print(yyline+1+": DOT\n");		  return symbol(sym.DOT);
          }
        case 99: break;
        case 25: 
          { System.out.print(yyline+1  +": Lexical Error: Unclosed Comment"); 
					  System.exit(0);
          }
        case 100: break;
        case 37: 
          { System.out.print(yyline+1+": THIS\n");	  return symbol(sym.THIS);
          }
        case 101: break;
        case 3: 
          { System.out.print(yyline+1+": MULTIPLY\n");  return symbol(sym.MULTIPLY);
          }
        case 102: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case YYINITIAL: {
              System.out.print(yyline+2+": EOF");	  return symbol(sym.EOF);
            }
            case 131: break;
            default:
              { return new java_cup.runtime.Symbol(sym.EOF); }
            }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
