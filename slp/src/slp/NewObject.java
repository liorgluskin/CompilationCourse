package slp;

/** A class for AST nodes for a new object.
 */

public class NewObject extends New{
	private String class_name;
	
	/**
	 * Constructs a new node for a new object.
	 * 
	 * @param class_name Object class name.
	 */
	public NewObject(String class_name){
		this.class_name = class_name;
	}
	
	public String getClassName(){
		return this.class_name;
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
