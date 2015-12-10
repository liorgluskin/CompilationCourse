package slp;

/** Abstract base class for AST nodes for 'new' keyword.
 */

public abstract class New extends Expr{

	protected New(int lineNum) {
		super(lineNum);
	}
	
}
