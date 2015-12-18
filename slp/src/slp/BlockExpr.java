package slp;

public class BlockExpr extends Expr{

	private Expr parenthesizedExpr;

	
	public BlockExpr(Expr parenthesizedExpr) {
		super(parenthesizedExpr.getLineNum());
		this.parenthesizedExpr = parenthesizedExpr;
	}

	public Expr getExpression() {
		return parenthesizedExpr;
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
	
	/** Accepts a propagating visitor parameterized by two types.
	 * 
	 * @param <DownType> The type of the object holding the context.
	 * @param <UpType> The type of the result object.
	 * @param visitor A propagating visitor.
	 * @param context An object holding context information.
	 * @return The result of visiting this node.
	 */
	@Override
	public <DownType, UpType> UpType accept(
			PropagatingVisitor<DownType, UpType> visitor, DownType context) {
		return visitor.visit(this, context);
	}
	
}
