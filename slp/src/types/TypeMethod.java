package types;

import java.util.Iterator;
import java.util.List;

/**
 * Method Type
 * Holds the input types and output type
 */
public class TypeMethod extends Type {	

	private List<Type> paramTypes;
	private Type returnType;
	
	
	public TypeMethod( Type returnType, List<Type> paramTypes){
		super(null);
		this.returnType = returnType;
		this.paramTypes = paramTypes;
	}
	
	public boolean subtypeOf(Type t){
		if (t == this) return true;
		//if (t.getName() == this.getName()) return true;
		else return false;
	}
	
	/**
	 * getter for the output type
	 */
	public Type getReturnType(){
		return this.returnType;
	}
	
	/**
	 * getter for the input types list
	 */
	public List<Type> getParamTypes(){
		return this.paramTypes;
	}
	
	/**
	 * returns the string representation for method type
	 */
	public String toString(){
		String str = "{ name: "+ this.getName();

		// parameter types
		Iterator<Type> paramIter = paramTypes.iterator();
		if (paramIter.hasNext()) str += paramIter.next().getName(); // put first parameter if exists
		while (paramIter.hasNext()) str += ", "+paramIter.next().getName(); // put others if exist
		
		// return type
		str += " -> "+this.returnType.getName()+"}";
		
		return str;
	}
}
