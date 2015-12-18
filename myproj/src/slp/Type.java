package slp;

public abstract class Type extends ASTNode{
	/** Accepts a visitor object as part of the visitor pattern.
	 * @param visitor A visitor.
	 */
	public abstract void accept(Visitor visitor);
	
	/**
	 * dimension of array: string[][][] is of dimension 3.
	 */
	private int ArrayDimenstion = 0;

	/**
	 * Constructor for new Type ASTnode
	 * @param lineNum - line number of type declaration.
	 */
	protected Type(int lineNum) {
		super(lineNum);
	}

	public abstract String getName();
	
	public String getFullName(){
		String str = getName();
		for(int i = 0; i < getDimension(); i++) str += "[]";
		return str;
	}

	public int getDimension() {
		return ArrayDimenstion;
	}

	public void incrementDimension() {
		++ArrayDimenstion;
	}


}
