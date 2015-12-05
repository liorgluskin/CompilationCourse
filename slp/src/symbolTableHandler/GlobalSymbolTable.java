package symbolTableHandler;

import java.util.Map;

import semantic.SemanticError;
import slp.ClassDecl;

import java.util.HashMap;

public class GlobalSymbolTable extends SymbolTable {
	private Map<String,ClassSymbol> tableEntries = new HashMap<>();
	private Map<String,ClassSymbolTable> kids = new HashMap<>(); 

	public GlobalSymbolTable() {
		super(null);
	}
	
	public void addClass(ClassDecl c) throws SemanticError{
		tableEntries.put(c.getName(), new ClassSymbol(c));
	}
	
	public ClassSymbol getClass(String name){
		return tableEntries.get(name);
	}
	
	
	public ClassSymbolTable getClassSymbolTable(String name){
		ClassSymbolTable csm = kids.get(name);
		if (csm != null) return csm;
		else {
			for (ClassSymbolTable csm_l: kids.values()){
				csm = csm_l.getClassSymbolTable(name);
				if (csm != null) return csm;
			}
		}
		return null;
	}
	
	
	

}
