package slp;

import java.util.ArrayList;
import java.util.List;

/** Class declaration AST node.
*/

public class ClassDecl extends ASTNode {
	private String name;
	private String superClassName = null;	//Default value for super class - null
	private List<FieldOrMethod> fieldsOrMethods = null;
	
	public ClassDecl(int lineNum, String name) {
		super(lineNum);
		this.name = name;
		fieldsOrMethods = new ArrayList<FieldOrMethod>();
	}
	public ClassDecl(int lineNum, String name, String superClass) {
		super(lineNum);
		this.name = name;
		this.superClassName = superClass;
		fieldsOrMethods = new ArrayList<FieldOrMethod>();
	}
	
	/**
	 * Constructs a new class declaration node
	 */
	public ClassDecl(int lineNum, String name,List<FieldOrMethod> fieldsOrMethods) {
		super(lineNum);
		this.name = name;
		this.fieldsOrMethods = fieldsOrMethods;
	}
	
	public ClassDecl(int lineNum, String name, String superClassName,List<FieldOrMethod> fieldsOrMethods) {
		this(lineNum,name, fieldsOrMethods);
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
