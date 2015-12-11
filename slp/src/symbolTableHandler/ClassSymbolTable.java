package symbolTableHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.SemanticError;
import types.*;

public class ClassSymbolTable extends SymbolTable{

	
	private Map<String,MethodSymbol> methodsSymbols = new HashMap<String,MethodSymbol>();
	private Map<String,FieldSymbol> fieldsSymbols = new HashMap<String,FieldSymbol>();
	private Map<String,ClassSymbolTable> kidsClassSymbolTables = new HashMap<String,ClassSymbolTable>();
	private Map<String,MethodSymbolTable> methodSymbolTables = new HashMap<String,MethodSymbolTable>();
	
	private ClassSymbol symbol;
	private boolean is_extends;
	
	public ClassSymbolTable(ClassSymbolTable parent, ClassSymbol symbol) {
		super(parent);
		this.symbol = symbol;
		is_extends = true;		
	}
	
	public ClassSymbolTable(GlobalSymbolTable parent,ClassSymbol symbol){
		super(parent);
		this.symbol = symbol;
		is_extends = false;
	}
	
	public MethodSymbol getMethodSymbol(String name){
		MethodSymbol ms = methodsSymbols.get(name);
		if (ms == null){
			if(is_extends){
				ms =((ClassSymbolTable) parent).getMethodSymbol(name);				
			}else return null;			
		}
		return ms;		
	}
	

	
	public void addMethodSymbol(MethodSymbol ms){
		this.methodsSymbols.put(ms.getName(), ms);
	}
	
	public FieldSymbol getFieldSymbol(String name){
		FieldSymbol fs = fieldsSymbols.get(name);
		if (fs == null) {
			if (is_extends){
				fs = ((ClassSymbolTable) parent).getFieldSymbol(name);
			} else {
				return null;
			}
		}
		return fs;
	}
	
	public void addFieldSymbol(String name, String type_name) throws SemanticError{
		this.fieldsSymbols.put(name,new FieldSymbol(name, type_name));
	}
	
	/**
	 * a getter for the current class symbol table's symbol in the global symbol table
	 * @return
	 */
	public ClassSymbol getSymbol(){
		return this.symbol;
	}
	

	public void addMethodSymbolTable(MethodSymbolTable mst){
		methodSymbolTables.put(mst.getName(), mst);
	}
	

	public MethodSymbolTable getMethodSymbolTable(String name){
		return methodSymbolTables.get(name);
	}
	

	public void addClassSymbolTable(ClassSymbolTable cst){
		kidsClassSymbolTables.put(cst.getSymbol().getName(), cst);
	}

	
	public ClassSymbolTable getClassSymbolTable(String name){
		ClassSymbolTable csm = kidsClassSymbolTables.get(name);
		if (csm != null) return csm;
		else {
			for (ClassSymbolTable csm_l: kidsClassSymbolTables.values()){
				csm = csm_l.getClassSymbolTable(name);
				if (csm != null) return csm;
			}
		}
		return null;
	}
	
	

}
