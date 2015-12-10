package slp;

/** A base class for AST nodes for expressions.
 */

public abstract class Expr extends ASTNode {
	
	protected Expr(int lineNum) {
		super(lineNum);
	}
	
}