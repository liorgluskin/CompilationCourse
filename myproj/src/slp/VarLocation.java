package slp;

/** A class for variable reference AST node
 */

public class VarLocation extends Location{
	private String var_name;
	private Expr location = null; //default initialization
	
	/**
	 * Constructs a new variable reference node.
	 * @param name - variable name
	 */
	public VarLocation(int lineNum, String name){
		super(lineNum);
		this.var_name = name;
	}
	
	/**
	 * Constructs a new variable reference node.
	 * @param name - variable name
	 * @param location - variable location
	 */
	public VarLocation(int lineNum, String name, Expr location){
		this(lineNum,name);
		this.location = location;
	}
	
	public String getName() {
		return var_name;
	}
	
	public Expr getLocation() {
		return location;
	}
	
	
	public boolean hasExternalLocation(){
		return location!=null;
	}
	
	/** Accepts a visitor object as part of the visitor pattern.
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
