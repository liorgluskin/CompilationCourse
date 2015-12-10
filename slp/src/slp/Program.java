package slp;

import java.util.List;

/** A class for the AST root for an ic program.
 */
public class Program extends ASTNode{
	private List<ClassDecl> classes;
	
	/**
	 * Constructs a new program node.
	 * 
	 * @param classes - List of the program classes
	 */
	public Program(List<ClassDecl> classes) {
		super(0);
		this.classes = classes;
	}

	public List<ClassDecl> getClasses() {
		return classes;
	}
	
	/**
	 * Adds a class to the program.
	 * @param class_decl - class 
	 */
	public void addClass(ClassDecl class_decl){
		classes.add(class_decl);
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
