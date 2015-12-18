package slp;

/** An AST node for binary expressions.
 */
public class BinaryOpExpr extends Expr {
	private Expr lhs;
	private Expr rhs;
	private BinOperator op;
	
	public BinaryOpExpr(Expr lhs, Expr rhs, BinOperator op) {
		super(lhs.getLineNum());
		this.lhs = lhs;
		this.rhs = rhs;
		this.op = op;
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
	
	public Expr getLeftOperand(){
		return this.lhs;
	}
	
	public Expr getRightOperand(){
		return this.rhs;
	}
	
	public BinOperator getOp(){
		return this.op;
	}
	
	public boolean hasMathematicalOp(){
		switch(this.op){
		case PLUS: return true;
		case MINUS: return true;
		case MULTIPLY: return true;
		case DIVIDE: return true;
		case MOD: return true;
		}
		return false;
	}
	
	public String toString() {
		return lhs.toString() + op + rhs.toString();
	}	
}