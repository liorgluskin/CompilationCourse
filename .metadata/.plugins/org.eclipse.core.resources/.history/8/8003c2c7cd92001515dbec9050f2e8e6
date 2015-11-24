package slp;


public class ClassType extends Type {

	private String name;

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

	/**
	 * Constructs a new user-defined data type node.
	 * 
	 * @param line
	 *            Line number of type declaration.
	 * @param name
	 *            Name of data type.
	 */
	public ClassType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

