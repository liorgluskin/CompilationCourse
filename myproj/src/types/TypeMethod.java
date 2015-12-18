package types;

import java.util.Iterator;
import java.util.List;


public class TypeMethod extends Type {	

	private List<Type> parametersTypes;
	private Type returnType;
	
	public TypeMethod(Type returnType, List<Type> parametersTypes){
		super(null);
		this.returnType = returnType;
		this.parametersTypes = parametersTypes;
	}
	

	public Type getReturnType(){
		return this.returnType;
	}
	

	public List<Type> getParamTypes(){
		return this.parametersTypes;
	}
	
	public boolean equals(TypeMethod methodType){
		if (this.getName() != methodType.getName()) return false;
		else if (this.returnType != methodType.getReturnType()) return false;
		else{
			Iterator<Type> paramIterator = this.parametersTypes.iterator();
			Iterator<Type> secondParamIter = methodType.parametersTypes.iterator();
			
			while (paramIterator.hasNext() && secondParamIter.hasNext()){
				if (paramIterator.next() != secondParamIter.next()) return false; 
			}
			if (paramIterator.hasNext() || secondParamIter.hasNext()) return false;
			else return true;
		}
	}
	
	public boolean extendsType(Type type){
		if(type == this){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String toString(){
		String methodStringRep = "{ name: "+ this.getName();
		Iterator<Type> paramIter = parametersTypes.iterator();
		
		if (paramIter.hasNext()){
			methodStringRep += paramIter.next().getName();
		}
		while (paramIter.hasNext()){
			methodStringRep += ", "+paramIter.next().getName();
		}
		
		methodStringRep += " -> "+this.returnType.getName()+"}";
		return methodStringRep;
	}
}