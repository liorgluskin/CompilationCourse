package types;

import semantic.SemanticError;
import slp.ClassDecl;

public class TypeClass extends Type {
	private String superClass;
	private ClassDecl classDecl;

	public TypeClass(ClassDecl classDecl){
		super(classDecl.getName());
		this.superClass = classDecl.getSuperClassName();
		this.classDecl = classDecl;
	}
	
	
	public ClassDecl getClassDecl(){
		return classDecl;
	}
	

	public boolean extendsType(Type type){
		if(!(type instanceof TypeClass)){
			return false;
		}
		if(type == this){
			return true;
		}
		if(this.superClass == null){
			return false;
		}

		try{
			return TypeTable.getTypeTable().getClassType(superClass).extendsType(type);
		}catch(SemanticError error){
			return false;
		}
	}



}