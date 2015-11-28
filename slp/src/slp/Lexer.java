/* The following code was generated by JFlex 1.4.3 on 20:46 28/11/15 */

package slp;

import java_cup.runtime.*;


/**
 * This class is a scanner generated by 
 * <a href="http://www.jflex.de/">JFlex</a> 1.4.3
 * on 20:46 28/11/15 from the specification file
 * <tt>C:/CompilationCourse/slp/src/slp/slp.lex</tt>
 */
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
    "\2\0\1\43\1\40\1\0\1\23\1\24\1\4\1\22\1\20\1\42"+
    "\1\17\1\5\1\6\11\7\1\0\1\21\1\41\1\25\1\26\2\0"+
    "\32\12\1\27\1\14\1\60\1\0\1\11\1\0\1\46\1\44\1\51"+
    "\1\56\1\32\1\57\1\33\1\34\1\53\1\10\1\50\1\31\1\10"+
    "\1\15\1\45\2\10\1\47\1\52\1\16\1\54\1\62\1\35\1\55"+
    "\2\10\1\30\1\37\1\61\uff82\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\1\0\1\1\2\2\1\3\1\4\2\5\1\6\1\7"+
    "\1\10\2\6\1\11\1\12\1\13\1\14\1\15\1\16"+
    "\1\17\1\20\1\21\1\22\2\6\1\23\2\1\1\24"+
    "\1\25\1\26\6\6\1\27\1\30\1\6\1\31\1\2"+
    "\1\32\1\33\1\0\4\6\1\34\1\35\3\6\1\36"+
    "\1\37\1\40\1\41\7\6\1\42\2\6\1\31\1\0"+
    "\1\43\15\6\1\44\2\6\1\0\1\2\1\45\1\46"+
    "\1\47\1\6\1\50\11\6\1\51\2\0\3\6\1\52"+
    "\1\6\1\53\3\6\1\54\1\55\2\6\1\56\1\6"+
    "\1\57\1\60\1\61\1\62\1\6\1\63";

  private static int [] zzUnpackAction() {
    int [] result = new int[127];
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
    "\0\0\0\63\0\146\0\63\0\63\0\231\0\314\0\377"+
    "\0\u0132\0\u0165\0\u0198\0\u01cb\0\u01fe\0\63\0\63\0\63"+
    "\0\63\0\63\0\63\0\u0231\0\u0264\0\63\0\63\0\u0297"+
    "\0\u02ca\0\u02fd\0\u0330\0\u0363\0\u0396\0\63\0\63\0\u03c9"+
    "\0\u03fc\0\u042f\0\u0462\0\u0495\0\u04c8\0\63\0\63\0\u04fb"+
    "\0\u052e\0\u0561\0\u0594\0\63\0\u05c7\0\u05fa\0\u062d\0\u0660"+
    "\0\u0693\0\63\0\63\0\u06c6\0\u06f9\0\u072c\0\63\0\63"+
    "\0\63\0\63\0\u075f\0\u0792\0\u07c5\0\u07f8\0\u082b\0\u085e"+
    "\0\u0891\0\u0132\0\u08c4\0\u08f7\0\u092a\0\u095d\0\u0132\0\u0990"+
    "\0\u09c3\0\u09f6\0\u0a29\0\u0a5c\0\u0a8f\0\u0ac2\0\u0af5\0\u0b28"+
    "\0\u0b5b\0\u0b8e\0\u0bc1\0\u0bf4\0\u0132\0\u0c27\0\u0c5a\0\u0c8d"+
    "\0\u0cc0\0\u0132\0\u0132\0\u0132\0\u0cf3\0\u0132\0\u0d26\0\u0d59"+
    "\0\u0d8c\0\u0dbf\0\u0df2\0\u0e25\0\u0e58\0\u0e8b\0\u0ebe\0\u0132"+
    "\0\u0cc0\0\u0ef1\0\u0f24\0\u0f57\0\u0f8a\0\u0132\0\u0fbd\0\u0132"+
    "\0\u0ff0\0\u1023\0\u1056\0\u0132\0\u0132\0\u1089\0\u10bc\0\u0132"+
    "\0\u10ef\0\u0132\0\u0132\0\u0132\0\u0132\0\u1122\0\u0132";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[127];
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
    "\1\2\1\3\2\4\1\5\1\6\1\7\1\10\1\11"+
    "\1\2\1\12\1\13\1\2\1\14\1\15\1\16\1\17"+
    "\1\20\1\21\1\22\1\23\1\24\1\25\1\26\1\27"+
    "\1\30\1\31\3\11\1\32\1\33\1\34\1\35\1\36"+
    "\1\37\1\40\2\11\1\41\1\11\1\42\1\43\1\44"+
    "\3\11\1\45\1\46\1\47\1\50\65\0\1\4\64\0"+
    "\1\51\1\52\63\0\1\7\1\53\61\0\2\10\61\0"+
    "\5\11\2\0\2\11\12\0\5\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\12\2\0\2\12\12\0\5\12\6\0"+
    "\14\12\2\0\1\12\13\13\1\54\1\55\46\13\6\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\56\3\11\6\0"+
    "\10\11\1\57\3\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\3\11\1\60\1\11\6\0\3\11\1\61"+
    "\10\11\2\0\1\11\25\0\1\62\62\0\1\63\43\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\64\3\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\1\65\4\11\6\0\11\11\1\66\2\11\2\0\1\11"+
    "\25\0\1\67\74\0\1\70\63\0\1\71\47\0\1\72"+
    "\43\0\5\11\2\0\2\11\12\0\5\11\6\0\1\11"+
    "\1\73\1\11\1\74\10\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\1\11\1\75\3\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\1\76"+
    "\4\11\6\0\1\11\1\77\12\11\2\0\1\11\6\0"+
    "\5\11\2\0\1\11\1\100\12\0\5\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\1\101\1\11\12\0"+
    "\5\11\6\0\13\11\1\102\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\6\0\2\11\1\103\11\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\1\11\1\104\12\11\2\0\1\11\4\105\1\106"+
    "\56\105\1\52\1\3\1\4\60\52\6\0\2\53\66\0"+
    "\4\13\52\0\5\11\2\0\2\11\12\0\4\11\1\107"+
    "\6\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\1\110\4\11\6\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\5\11\6\0\7\11\1\111"+
    "\4\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\6\0\10\11\1\112\3\11\2\0\1\11\6\0"+
    "\5\11\2\0\1\113\1\11\12\0\5\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\6\11\1\114\5\11\2\0\1\11\6\0\5\11"+
    "\2\0\1\11\1\115\12\0\5\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\6\0"+
    "\1\11\1\116\12\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\117\3\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\1\11\1\120\12\0\5\11"+
    "\6\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\6\0\2\11\1\121\11\11\2\0\1\11"+
    "\6\0\5\11\2\0\1\122\1\11\12\0\5\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\6\0\2\11\1\123\1\124\10\11\2\0\1\11"+
    "\6\0\5\11\2\0\1\11\1\125\12\0\5\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\1\126\4\11\6\0\14\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\6\0\7\11\1\127\4\11"+
    "\2\0\1\11\4\105\1\130\62\105\1\130\1\131\55\105"+
    "\6\0\5\11\2\0\2\11\12\0\1\132\4\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\5\11\6\0\6\11\1\133\5\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\134\3\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\2\11\1\135\2\11\6\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\1\11\1\136\3\11\6\0"+
    "\14\11\2\0\1\11\6\0\5\11\2\0\2\11\12\0"+
    "\1\11\1\137\3\11\6\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\2\11\12\0\1\140\4\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\2\11\1\141\11\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\5\11\6\0\10\11\1\142\3\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\6\11\1\143\5\11\2\0\1\11\6\0\5\11"+
    "\2\0\1\11\1\144\12\0\5\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\1\11\1\145\12\0\5\11"+
    "\6\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\6\0\7\11\1\146\4\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\5\11\6\0\6\11"+
    "\1\147\5\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\6\0\12\11\1\150\1\11\2\0\1\11"+
    "\4\105\1\130\1\4\55\105\4\151\1\152\56\151\6\0"+
    "\5\11\2\0\1\11\1\153\12\0\5\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\1\154\1\11\12\0"+
    "\5\11\6\0\14\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\155\3\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\6\0"+
    "\4\11\1\156\7\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\6\0\3\11\1\157\10\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\6\0"+
    "\6\11\1\160\5\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\5\11\6\0\7\11\1\161\4\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\6\0"+
    "\7\11\1\162\4\11\2\0\1\11\6\0\5\11\2\0"+
    "\1\163\1\11\12\0\5\11\6\0\14\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\1\11\1\164\3\11"+
    "\6\0\14\11\2\0\1\11\4\151\1\152\1\4\55\151"+
    "\6\0\5\11\2\0\2\11\12\0\3\11\1\165\1\11"+
    "\6\0\14\11\2\0\1\11\6\0\5\11\2\0\2\11"+
    "\12\0\5\11\6\0\12\11\1\166\1\11\2\0\1\11"+
    "\6\0\5\11\2\0\2\11\12\0\5\11\6\0\2\11"+
    "\1\167\11\11\2\0\1\11\6\0\5\11\2\0\1\170"+
    "\1\11\12\0\5\11\6\0\14\11\2\0\1\11\6\0"+
    "\5\11\2\0\1\171\1\11\12\0\5\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\5\11\1\172\6\11\2\0\1\11\6\0\5\11"+
    "\2\0\2\11\12\0\2\11\1\173\2\11\6\0\14\11"+
    "\2\0\1\11\6\0\5\11\2\0\2\11\12\0\5\11"+
    "\6\0\6\11\1\174\5\11\2\0\1\11\6\0\5\11"+
    "\2\0\1\175\1\11\12\0\5\11\6\0\14\11\2\0"+
    "\1\11\6\0\5\11\2\0\2\11\12\0\5\11\6\0"+
    "\10\11\1\176\3\11\2\0\1\11\6\0\5\11\2\0"+
    "\2\11\12\0\1\11\1\177\3\11\6\0\14\11\2\0"+
    "\1\11";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4437];
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
    "\1\0\1\11\1\1\2\11\10\1\6\11\2\1\2\11"+
    "\6\1\2\11\6\1\2\11\4\1\1\11\1\0\4\1"+
    "\2\11\3\1\4\11\13\1\1\0\21\1\1\0\20\1"+
    "\2\0\25\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[127];
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
    private Symbol symbol(int type)               {return new Symbol(type, yyline+1, yycolumn);}
    private Symbol symbol(int type, Object value) {return new Symbol(type, yyline+1, yycolumn, value);}


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
    while (i < 130) {
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
  private void zzScanError(int errorCode) throws RuntimeException {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new RuntimeException(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  throws RuntimeException {
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
  public java_cup.runtime.Symbol next_token() throws java.io.IOException, RuntimeException {
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
          zzR = false;
          break;
        case '\r':
          yyline++;
          zzR = true;
          break;
        case '\n':
          if (zzR)
            zzR = false;
          else {
            yyline++;
          }
          break;
        default:
          zzR = false;
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
        case 9: 
          { return symbol(sym.DOT,".");
          }
        case 52: break;
        case 15: 
          { return symbol(sym.ASSIGN,"=");
          }
        case 53: break;
        case 12: 
          { return symbol(sym.PLUS,"+");
          }
        case 54: break;
        case 35: 
          { return symbol(sym.NEW,"new");
          }
        case 55: break;
        case 13: 
          { return symbol(sym.LP,"(");
          }
        case 56: break;
        case 39: 
          { return symbol(sym.TRUE,"true");
          }
        case 57: break;
        case 28: 
          { return symbol(sym.EQUAL,"==");
          }
        case 58: break;
        case 3: 
          { return symbol(sym.MULTIPLY,"*");
          }
        case 59: break;
        case 22: 
          { return symbol(sym.MOD,"%");
          }
        case 60: break;
        case 7: 
          { return symbol(sym.CLASS_ID, new String(yytext()));
          }
        case 61: break;
        case 6: 
          { return symbol(sym.ID, new String(yytext()));
          }
        case 62: break;
        case 32: 
          { return symbol(sym.LAND,"&&");
          }
        case 63: break;
        case 51: 
          { return symbol(sym.CONTINUE,"continue");
          }
        case 64: break;
        case 36: 
          { return symbol(sym.INT,"int");
          }
        case 65: break;
        case 10: 
          { return symbol(sym.COMMA,",");
          }
        case 66: break;
        case 46: 
          { return symbol(sym.RETURN,"return");
          }
        case 67: break;
        case 38: 
          { return symbol(sym.THIS,"this");
          }
        case 68: break;
        case 26: 
          { System.out.print(yyline+1  +": Leading zeroes in number '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 69: break;
        case 44: 
          { return symbol(sym.FALSE,"false");
          }
        case 70: break;
        case 23: 
          { return symbol(sym.RB,"]");
          }
        case 71: break;
        case 20: 
          { return symbol(sym.LT,"<");
          }
        case 72: break;
        case 2: 
          { /* just skip what was found, do nothing */
          }
        case 73: break;
        case 33: 
          { return symbol(sym.LTE,"<=");
          }
        case 74: break;
        case 49: 
          { return symbol(sym.EXTENDS,"extends");
          }
        case 75: break;
        case 8: 
          { System.out.print(yyline+1  +": Lexical Error: Unclosed quote missing '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 76: break;
        case 48: 
          { return symbol(sym.STRING,"string");
          }
        case 77: break;
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
							return symbol(sym.QUOTE, new String(yytext()));
						}else{
							System.out.print(yyline+1  +": Lexical error: ASCII char not between 32 and 126: '" +badChar+"'"); 
					  		System.exit(0);
						}
          }
        case 78: break;
        case 1: 
          { System.out.print(yyline+1  +": Lexical error: illegal character '" +yytext()+"'"); 
					  System.exit(0);
          }
        case 79: break;
        case 5: 
          { Integer num = 0;
						try{
							num = new Integer(yytext());
						}
						catch(NumberFormatException e){
							System.out.print(yyline+1  +": Lexical Error: number not within valid IC range '" +yytext()+"'"); 
					  		System.exit(0); 
						}
						return symbol(sym.NUMBER, num);
          }
        case 80: break;
        case 50: 
          { return symbol(sym.BOOLEAN,"boolean");
          }
        case 81: break;
        case 11: 
          { return symbol(sym.SEMI,";");
          }
        case 82: break;
        case 31: 
          { return symbol(sym.LOR,"||");
          }
        case 83: break;
        case 19: 
          { return symbol(sym.LNEG,"!");
          }
        case 84: break;
        case 16: 
          { return symbol(sym.GT,">");
          }
        case 85: break;
        case 43: 
          { return symbol(sym.CLASS,"class");
          }
        case 86: break;
        case 18: 
          { return symbol(sym.LCBR,"{");
          }
        case 87: break;
        case 42: 
          { return symbol(sym.BREAK,"break");
          }
        case 88: break;
        case 34: 
          { return symbol(sym.IF,"if");
          }
        case 89: break;
        case 41: 
          { return symbol(sym.VOID,"void");
          }
        case 90: break;
        case 30: 
          { return symbol(sym.NEQUAL,"!=");
          }
        case 91: break;
        case 24: 
          { return symbol(sym.RCBR,"}");
          }
        case 92: break;
        case 29: 
          { return symbol(sym.GTE,">=");
          }
        case 93: break;
        case 47: 
          { return symbol(sym.STATIC,"static");
          }
        case 94: break;
        case 21: 
          { return symbol(sym.MINUS,"-");
          }
        case 95: break;
        case 40: 
          { return symbol(sym.ELSE,"else");
          }
        case 96: break;
        case 37: 
          { return symbol(sym.NULL,"null");
          }
        case 97: break;
        case 14: 
          { return symbol(sym.RP,")");
          }
        case 98: break;
        case 4: 
          { return symbol(sym.DIVIDE,"/");
          }
        case 99: break;
        case 25: 
          { System.out.print(yyline+1  +": Lexical Error: Unclosed Comment"); 
					  System.exit(0);
          }
        case 100: break;
        case 17: 
          { return symbol(sym.LB,"[");
          }
        case 101: break;
        case 45: 
          { return symbol(sym.LENGTH,"length");
          }
        case 102: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
            zzDoEOF();
            switch (zzLexicalState) {
            case YYINITIAL: {
              return symbol(sym.EOF);
            }
            case 128: break;
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
