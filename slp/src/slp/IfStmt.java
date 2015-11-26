package slp;

/** An AST node for if statements.
 *
 */
public class IfStmt extends Stmt {
	boolean hasElse = false;
	Expr expr;
	Stmt stmt;
	Stmt elseStmt;

	public IfStmt(Expr expr, Stmt stmt) {
		super(expr.getLineNum());
	}

	public IfStmt(Expr expr, Stmt stmt, Stmt elseStmt) {
		this(expr, stmt);
		this.elseStmt = elseStmt;
		hasElse = true;
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
	
	public Expr getCond(){
		return expr;
	}
	
	public Stmt getBody(){
		return stmt;
	}
	
	public Stmt getElseStmt(){
		return elseStmt;
	}
	
	public boolean hasElse(){
		return hasElse;
	}

}