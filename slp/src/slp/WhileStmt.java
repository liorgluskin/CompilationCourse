package slp;

/** An AST node for while statements.
 *
 */
public class WhileStmt extends Stmt {

	private Expr expr;
	private Stmt stmt;

	public WhileStmt(Expr expr, Stmt stmt) {
		super(expr.getLineNum());
		this.expr = expr;
		this.stmt = stmt;
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
	
	public Expr getExpr(){
		return expr;
	}
	
	public Stmt getStmt(){
		return stmt;
	}
}
