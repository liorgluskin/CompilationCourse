package types;

public abstract class Type {
	private String typeName;

	public Type(String typeName){
		this.typeName = typeName;
	}

	public String getName(){
		return this.typeName;
	}
	
	public boolean extendsType(Type t) {
		if(t.toString() == this.toString()){
			return true;
		}
		return false;
	}
	
	public String toString(){
		return this.typeName;
	}


}