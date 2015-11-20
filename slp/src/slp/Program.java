package slp;

/** A class for AST nodes for programs.
 */
public abstract class Program extends ASTNode{
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public abstract void accept(Visitor visitor);
}
