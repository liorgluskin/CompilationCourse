package slp;

import java.util.List;

/** A class for AST nodes for virtual method calls.
 */

public class VirtualCall extends Call{
	private Expr object_ref = null;			//default value for object reference
	
	/**
	 * Constructs a new static method call node.
	 * 
	 * @param method_name Name of method.
	 * @param args List of method arguments.
	 */
	public VirtualCall(String method_name, List<Expr> args) {
		super(method_name, args);
	}
	
	/**
	 * Constructs a new static method call node.
	 * 
	 * @param method_name Name of method.
	 * @param args List of method arguments.
	 * @param object_ref Expression denoting an object reference.
	 */
	public VirtualCall(String method_name, List<Expr> args, Expr object_ref) {
		super(method_name, args);
		this.object_ref = object_ref;
	}
	
	public Expr getObjectReference(){
		return this.object_ref;
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
