package slp;

public abstract class Type extends ASTNode{
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public abstract void accept(Visitor visitor);
}
