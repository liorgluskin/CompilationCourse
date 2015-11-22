package slp;

import java.util.List;

/** Abstract base class for AST nodes for method calls.
 */

public abstract class Call extends Expr{
	private String method_name;
	private List<Expr> args;
	
	/**
	 * Constructs a new method call node.
	 * 
	 * @param method_name Name of method.
	 * @param args List of method arguments.
	 */
	public Call(String method_name, List<Expr> args){
		this.method_name = method_name;
		this.args = args;
	}
	
	public String getMethodName(){
		return this.method_name;
	}
	
	public List<Expr> getArguments(){
		return this.args;
	}
}
