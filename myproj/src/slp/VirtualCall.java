package slp;

import java.util.List;

/** A class for AST nodes for virtual method calls.
 */

public class VirtualCall extends Call{
	private Expr object_ref = null;//default value for object reference
	
	/**
	 * Constructs a new virtual method call node.
	 * @param method_name - method's name
	 * @param args - method's arguments
	 */
	public VirtualCall(int lineNum, String method_name, List<Expr> args) {
		super(lineNum, method_name, args);
	}
	
	/**
	 * Constructs a new virtual method call AST node
	 * @param method_name - method's name
	 * @param args - method's arguments
	 * @param object_ref - Expression denoting an object reference
	 */
	public VirtualCall(int lineNum, String method_name, List<Expr> args, Expr object_ref) {
		super(lineNum, method_name, args);
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
