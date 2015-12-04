package types;

/**
 * Abstract Type class for all IC program types
 */
public abstract class Type {
	private String typeName;
	private int typeID;
	
	public Type(String typeName){
		this.typeName = typeName;
		this.typeID = TypeTable.idCounter++;
	}
	

	public String getName(){
		return this.typeName;
	}
		
	public int getTypeID(){
		return this.typeID;
	}
	
	public String toString(){
		return this.typeName;
	}
	
	/**
	 * Returns if current type is equal to, or extends the Type t
	 */
	public abstract boolean extendsType(Type t);
}
