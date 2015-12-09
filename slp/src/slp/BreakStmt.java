package slp;

/** An AST node for break statements.
*
*/
public class BreakStmt extends Stmt {

<<<<<<< HEAD
	public BreakStmt(int lineNum) {
		super(lineNum);
=======
	public BreakStmt(int line) {
		super(line);
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
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
