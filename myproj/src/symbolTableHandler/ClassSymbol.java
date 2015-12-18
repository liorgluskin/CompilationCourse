package symbolTableHandler;

import semantic.SemanticError;
import slp.*;
import types.*;

/**
 * Symbol of type 'class'. 
 *
 */

public class ClassSymbol extends Symbol {
	private ClassDecl classdecl;

	public ClassSymbol(ClassDecl classdecl) throws SemanticError{
		super(classdecl.getName(), classdecl.getLineNum());
		TypeTable.getTypeTable().addClassType(classdecl);
		type = TypeTable.getTypeTable().getClassType(sym_name);
		this.classdecl = classdecl;	
		
	}
	
	public ClassDecl getClassDecl(){
		return classdecl;
	}

}
