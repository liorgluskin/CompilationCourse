package slp;

import symbolTableHandler.ClassSymbolTable;
import symbolTableHandler.Symbol;
import symbolTableHandler.VariableSymbol;

public class Field extends FieldOrMethod{
	private Type type;
	private String name;
	
	/**
	 * Constructs a new field node.
	 * @param type - field's data type
	 * @param name - field's name
	 */
	public Field(Type type, String name) {
		super(type.getLineNum());
		this.type = type;
		this.name = name;
	}
	
	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
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
