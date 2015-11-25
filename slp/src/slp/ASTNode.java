package slp;

/** The base class of all AST nodes in this package.
 */
public abstract class ASTNode {

	private int lineNum;

	/**
	 * Constructor
	 * 
	 * @param lineNum 
	 */
	protected ASTNode(int lineNum) {
		this.lineNum = lineNum;
	}

	/** Returns the code's line number where the node has appeared.
	 */
	public int getLineNum() {
		return lineNum;
	}

	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public abstract void accept(Visitor visitor);

	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	public abstract <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context);


	
}