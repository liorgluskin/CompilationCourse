package types;

public class TypeArray extends Type{
	private Type elementType;
	
	public TypeArray(Type elementType){
		super(elementType.getName() +"[]");
		this.elementType = elementType;
	}
	

	public Type getElementType(){
		return this.elementType;
	}


}
