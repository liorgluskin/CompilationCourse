package slp;

/**	Abstract base class for reference AST nodes.
 */
public abstract class Location extends Expr{
	
	protected Location(int lineNum) {
		super(lineNum);
	}
	
}
