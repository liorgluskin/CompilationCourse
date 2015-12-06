package slp;

/** An AST node for return statements.
*
*/
public class ReturnStmt extends Stmt {
<<<<<<< HEAD
	private Expr expr;
	private boolean hasRetExpr = false;
	
	public ReturnStmt(int lineNum) {
		super(lineNum);
	}
	
	public ReturnStmt(int lineNum, Expr expr) {
		super(lineNum);
		this.expr = expr;
		hasRetExpr = true;
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

	public Expr getExpr() {
		return expr;
	}
	
	public boolean hasExpr(){
		return hasRetExpr;
=======
	
	public ReturnStmt(int line) {
		super(line);
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
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	}

}
