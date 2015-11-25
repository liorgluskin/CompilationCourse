package slp;

/** A class for array reference AST node
 */

public class ArrLocation extends Location{
	private Expr arr_location;
	private Expr index;
	
	/**
	 * Constructs a new array reference node.
	 * 
	 * @param arr_location Array location.
	 * @param index Numeric index in the array.
	 */
	public ArrLocation(Expr arr_location, Expr index){
		super(arr_location.getLineNum());
		this.arr_location = arr_location;
		this.index = index;
	}
	
	public Expr getArrLocation(){
		return this.arr_location;
	}
	
	public Expr getIndex(){
		return this.index;
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
