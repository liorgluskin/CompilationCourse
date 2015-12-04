package types;

public class TypeArray extends Type{
	private Type elemType;
	
	public TypeArray(Type elemtype){
		super(elemtype.getName() +"[]");
		this.elemType = elemType;
	}
	
	/** Returns the type of the array's elements
	 * **/
	public Type getElemType(){
		return this.elemType;
	}


}
