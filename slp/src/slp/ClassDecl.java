package slp;

import java.util.ArrayList;
import java.util.List;

/** Class declaration AST node.
*/

public class ClassDecl extends ASTNode {
	private String name;
	private String superClassName = null;	//Default value for super class - null
	private List<FieldOrMethod> fieldsOrMethods = new ArrayList<FieldOrMethod>();;
	
	public ClassDecl(int line, String name) {
		super(line);
		this.name = name;
		

	}
	public ClassDecl(int line, String name, String superClass) {
		super(line);
		this.name = name;
		this.superClassName = superClass;
		

	}
	
	/**
	 * Constructs a new class declaration node.
	 * 
	 * @param
	 */
	public ClassDecl(int line,String name,List<FieldOrMethod> fieldsOrMethods) {
		super(line);
		this.name = name;
		this.fieldsOrMethods = fieldsOrMethods;
	}
	
	public ClassDecl(int line,String name, String superClassName,List<FieldOrMethod> fieldsOrMethods) {
		this(line,name, fieldsOrMethods);
		this.superClassName = superClassName;
	}
	
	public String getName() {
		return name;
	}

	public String getSuperClassName() {
		return superClassName;
	}
	
	public List<FieldOrMethod> getFieldsOrMethods() {
		return fieldsOrMethods;
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
