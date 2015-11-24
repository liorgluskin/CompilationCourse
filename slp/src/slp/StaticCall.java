package slp;

import java.util.List;

/** A class for AST nodes for static method calls.
 */

public class StaticCall extends Call{
	private String class_name;
	
	/**
	 * Constructs a new static method call node.
	 * 
	 * @param method_name Name of method.
	 * @param args List of method arguments.
	 * @param class_name Class name.
	 */
	public StaticCall(int line, String method_name,List<Expr> args, String class_name){
		super(line, method_name,args);
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
