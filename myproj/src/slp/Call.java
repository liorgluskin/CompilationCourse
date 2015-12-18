package slp;

import java.util.List;

/** Abstract class representing AST nodes of Method Calls.
 */

public abstract class Call extends Expr{
	private String method_name;
	private List<Expr> args;
	
	/**
	 * Constructs a new method call node.
	 * 
	 * @param lineNum - line number of method call.
	 * @param method_name - name of method.
	 * @param args - method arguments.
	 */
	public Call(int lineNum, String method_name, List<Expr> args){
		super(lineNum);
		this.method_name = method_name;
		this.args = args;
	}
	
	/**
	 * @return method name
	 */
	public String getMethodName(){
		return this.method_name;
	}
	
	/**
	 * @return method arguments
	 */
	public List<Expr> getArguments(){
		return this.args;
	}
}
