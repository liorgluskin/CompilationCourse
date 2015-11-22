package slp;

/** A class for AST nodes for a new array.
 */

public class NewArray extends New{
	private Type type;
	private Expr len;
	
	/**
	 * Constructs a new node for a new array.
	 * 
	 * @param type Array type.
	 * @param len Number of elements in the array.
	 */
	public NewArray(Type type, Expr len){
		this.type = type;
		this.len = len;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public Expr getArrayLength(){
		return this.len;
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
