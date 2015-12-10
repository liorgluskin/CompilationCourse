package slp;

import java.util.List;

public class ClassMethod extends Method {

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

	/**
	 * Constructs new Class Method AST node
	 * @param type - method's return type
	 * @param name - method name
	 * @param formals - method parameters
	 * @param statements - method's statements
	 */
	public ClassMethod(Type type, String name, List<Formal> formals, StmtList statements){
		super(type, name, formals, statements);
		this.is_static = false;
	}

}