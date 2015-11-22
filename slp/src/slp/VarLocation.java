package slp;

/** A class for variable reference AST node
 */

public class VarLocation extends Location{
	private String var_name;
	private Expr location = null;			//default initialization
	
	/**
	 * Constructs a new variable reference node.
	 * 
	 * @param name Name of variable.
	 */
	public VarLocation(String name){
		this.var_name = name;
	}
	
	/**
	 * Constructs a new variable reference node.
	 * 
	 * @param name Name of variable.
	 * @param location Location of variable.
	 */
	public VarLocation(String name, Expr location){
		this(name);
		this.location = location;
	}
	
	public String getName() {
		return var_name;
	}
	
	public Expr getLocation() {
		return location;
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
