package slp;

/** A class of all AST node for literals.
 */

public class Literal extends Expr{
	private LiteralType ltype;
	private Object value;
	
	/**
	 * Constructs new literal node.
	 * 
	 * @param ltype Type of literal.
	 */
	public Literal(int line, LiteralType ltype){
		super(line);
		this.ltype = ltype;
		this.value = ltype.getValue(); // Literal is: INTEGER, STRING or boolean
	}
	
	/**
	 * Constructs new literal node.
	 * @param ltype - literal's type
	 * @param value - literal's value
	 */
	public Literal(int line, LiteralType ltype, Object value){
		this(line, ltype);
		this.value = value;
	}
	
	public LiteralType getType(){
		return this.ltype;
	}
	
	public Object getValue(){
		return this.value;
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
