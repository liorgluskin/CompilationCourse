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
	private boolean isExtends;
	
	
	
	public ClassSymbolTable(ClassSymbolTable parent, ClassSymbol symbol) {
		super(parent);
		this.symbol = symbol;
		isExtends = true;		
	}
	
	public ClassSymbolTable(GlobalSymbolTable parent,ClassSymbol symbol){
		super(parent);
		this.symbol = symbol;
		isExtends = false;
	}
	
	public MethodSymbol getMethodSymbol(String name) throws SemanticError{
		MethodSymbol ms = methodsSymbols.get(name);
		if (ms == null){
			if(isExtends){
				ms =((ClassSymbolTable) parent).getMethodSymbol(name);				
			}else throw new SemanticError("method does not exist in "+this.symbol.getName()+": name");			
		}
		return ms;		
	}
	
	public void addMethodSymbol(String name, Type returnType, List<Type> paramTypes, boolean isStatic){
		this.methodsSymbols.put(name, new MethodSymbol(name,returnType,paramTypes, isStatic));
	}
	
	public void addMethodSymbol(String name, MethodSymbol ms){
		this.methodsSymbols.put(name, ms);
	}
	
	public FieldSymbol getFieldSymbol(String name) throws SemanticError{
		FieldSymbol fs = fieldsSymbols.get(name);
		if (fs == null) {
			if (isExtends){
				fs = ((ClassSymbolTable) parent).getFieldSymbol(name);
			} else {
				throw new SemanticError("name cannot be resolved:" +name);
			}
		}
		return fs;
	}
	
	public void addFieldSymbol(String name, String typeName) throws SemanticError{
		this.fieldsSymbols.put(name,new FieldSymbol(name,typeName));
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