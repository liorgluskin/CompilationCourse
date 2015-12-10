package slp;

/** An AST node for if statements.
 *
 */
public class IfStmt extends Stmt {
	boolean hasElse = false;
	Expr expr;
	Stmt stmt;
	Stmt elseStmt;

<<<<<<< HEAD
	public IfStmt(Expr expr, Stmt stmt) {
		super(expr.getLineNum());
		this.expr = expr;
		this.stmt = stmt;
	}

	public IfStmt(Expr expr, Stmt stmt, Stmt elseStmt) {
		this(expr, stmt);
		this.elseStmt = elseStmt;
		hasElse = true;
	}		

=======
	public IfStmt(int line) {
		super(line);
	}		
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	@Override
	public void accept(Visitor visitor) {
		visitor.visit(this);
	}
<<<<<<< HEAD

=======
	
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
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
<<<<<<< HEAD
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
=======
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	}

}
