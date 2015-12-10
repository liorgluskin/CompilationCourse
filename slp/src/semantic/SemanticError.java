package semantic;

/**
 * Exception thrown when semantic error found in program
 */
public class SemanticError extends Exception {
	
	private static final long serialVersionUID = 1L;
	private int lineNum;
	
	public SemanticError(String message, int lineNum){
		super(message);
		this.lineNum = lineNum;
	}
	
	public SemanticError(String message){
		super(message);
		this.lineNum = -1;
	}
	
	 /**
     * returns a string representation for the error with line number, message and the token that
     * caused the syntax error
     */
	public String toString(){
		return "Semantic error at line "+lineNum+": "+this.getMessage();
	}
	
	/**
	 * set the error's line
	 * @param line
	 */
	public void setLineNum(int lineNum){
		this.lineNum = lineNum;
	}
}