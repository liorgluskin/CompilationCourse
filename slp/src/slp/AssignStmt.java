package slp;

/**
 * An AST node for assignment statements.
 */
public class AssignStmt extends Stmt {
	public final VarLocation varLocation;
	public final Expr rhs;

	public AssignStmt(VarLocation varLocation, Expr rhs) {
		super(varLocation.getLineNum());
		this.varLocation = varLocation;
		this.rhs = rhs;
	}

	/**
	 * Accepts a visitor object as part of the visitor pattern.
	 * 
	 * @param visitor
	 *            A visitor.
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
	
	/** return the assignment Variable Expression*/
	public VarLocation getVarLocation(){
		return varLocation;
	}
	
	/** return the Expression assigned to the variable*/
	public Expr getRhs(){
		return rhs;
	}

}