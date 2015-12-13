package types;

public class TypeNull extends Type {
	public TypeNull(){
		super("null");
	}
	
	@Override
	public boolean extendsType(Type t) {
		// null does not extend literal-types: int, boolean and void
		if(t instanceof TypeInt){
			return false;
		}else if(t instanceof TypeBoolean){
			return false;
		}else if(t instanceof TypeVoid){
			return false;
		}
		return true;
	}
	
}
