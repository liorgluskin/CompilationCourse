package symbolTableHandler;

import semantic.SemanticError;
import slp.*;
import types.*;

public class ClassSymbol extends Symbol {
	private ClassDecl classdecl;

	public ClassSymbol(ClassDecl classdecl) throws SemanticError{
		super(classdecl.getName(), classdecl.getLineNum());
		TypeTable.addClass(classdecl);
		type = TypeTable.getClass(name);
		this.classdecl = classdecl;		
	}
	
	public ClassDecl getClassDecl(){
		return classdecl;
	}

}
