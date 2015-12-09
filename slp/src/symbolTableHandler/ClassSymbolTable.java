package symbolTableHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.SemanticError;
import types.*;

public class ClassSymbolTable extends SymbolTable{

	
	private Map<String,MethodSymbol> methodsSymbols = new HashMap<>();
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
	
	public MethodSymbol getMethodSymbol(String name) throws SemanticError{
		MethodSymbol ms = methodsSymbols.get(name);
		if (ms == null){
			if(is_extends){
				ms =((ClassSymbolTable) parent).getMethodSymbol(name);				
			}else throw new SemanticError("method does not exist in "+this.symbol.getName()+": name");			
		}
		return ms;		
	}
	
	public void addMethodSymbol(String name, Type return_type, List<Type> param_types, boolean is_static){
		this.methodsSymbols.put(name, new MethodSymbol(name,return_type, param_types, is_static));
	}
	
	public void addMethodSymbol(String name, MethodSymbol ms){
		this.methodsSymbols.put(name, ms);
	}
	
	public FieldSymbol getFieldSymbol(String name) throws SemanticError{
		FieldSymbol fs = fieldsSymbols.get(name);
		if (fs == null) {
			if (is_extends){
				fs = ((ClassSymbolTable) parent).getFieldSymbol(name);
			} else {
				throw new SemanticError("name cannot be resolved:" +name);
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
