package types;

import semantic.SemanticError;
import slp.ClassDecl;

public class TypeClass extends Type {
	private String superClass;
	private ClassDecl classNode;

	public TypeClass(ClassDecl classNode){
		super(classNode.getName());
		this.superClass = classNode.getSuperClassName();
		this.classNode = classNode;
	}
	
	
	public ClassDecl getClassNode(){
		return classNode;
	}
	

	@Override
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
			return TypeTable.getClass(superClass).extendsType(type);
		}catch(SemanticError error){
			return false;
		}
	}



}
