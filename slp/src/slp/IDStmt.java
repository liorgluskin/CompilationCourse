package slp;

/** An AST node for Variable Declaration Statements.
*
*/
public class IDStmt extends Stmt {
	
<<<<<<< HEAD
	private Type type;
	private String name;
	private Expr value = null;
	
	/**Constructor for variable declaration without value
	 * */
	public IDStmt(Type type, String name) {
		super(type.getLineNum());
		this.type = type;
		this.name = name;
	}
	
	/**Constructor for variable declaration and value assignment
	 * */
	public IDStmt(Type type, String name, Expr value) {
		this(type, name);
		this.value = value;
=======
	public IDStmt(int line) {
		super(line);
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
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
<<<<<<< HEAD
	}
	
	public Type getType(){
		return type;
	}
	
	public String getName(){
		return name;
	}
	
	public Expr getValue(){
		return value;
	}
	
	public boolean hasValue(){
		return (value != null);
=======
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	}
}
