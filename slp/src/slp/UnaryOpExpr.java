package slp;

/** An AST node for unary expressions.
 */

public class UnaryOpExpr extends Expr {
<<<<<<< HEAD
	private UnOperator op;
	private Expr operand;
=======
	public final UnOperator op;
	public final Expr operand;
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	
	public UnaryOpExpr(Expr operand, UnOperator op) {
		super(operand.getLineNum());
		this.operand = operand;
		this.op = op;
	}

	public UnOperator getOp(){
		return this.op;
	}
	
	public Expr getOperand(){
		return this.operand;
	}
	
	public boolean hasMathematicalOp(){
		if(this.op == UnOperator.UMINUS)
			return true;
		return false;
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