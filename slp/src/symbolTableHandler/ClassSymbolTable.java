package symbolTableHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.SemanticError;
import types.*;

public class ClassSymbolTable extends SymbolTable{

	
	private Map<String,FieldSymbol> fieldsSymbols = new HashMap<String,FieldSymbol>();
	private Map<String,MethodSymbol> methodsSymbols = new HashMap<String,MethodSymbol>();
	private Map<String,MethodSymbolTable> methodSymbolTables = new HashMap<String,MethodSymbolTable>();
	private Map<String,ClassSymbolTable> kidsClassSymbolTables = new HashMap<String,ClassSymbolTable>();

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
	
	
	public FieldSymbol getFieldSymbol(String name){
		FieldSymbol fieldSym = fieldsSymbols.get(name);
		if(fieldSym == null){
			if(is_extends){
				fieldSym = ((ClassSymbolTable)parent).getFieldSymbol(name);
			}
			else{
				return null;
			}
		}
		return fieldSym;
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

	/**Get the symbol of the current class*/
	public ClassSymbol getSymbol(){
		return this.symbol;
	}
	
	public MethodSymbolTable getMethodSymbolTable(String methodName){
		return methodSymbolTables.get(methodName);
	}
	
	public ClassSymbolTable getClassSymbolTable(String className){
		ClassSymbolTable classSymTable = kidsClassSymbolTables.get(className);
		if (classSymTable != null){
			return classSymTable;
		}
		else {
			for (ClassSymbolTable csm_l: kidsClassSymbolTables.values()){
				classSymTable = csm_l.getClassSymbolTable(className);
				if (classSymTable != null){
					return classSymTable;
				}
			}
		}
		return null;
	}
	
	public void addFieldSymbol(String fieldName, String fieldTypeName) throws SemanticError{
		this.fieldsSymbols.put(fieldName,new FieldSymbol(fieldName, fieldTypeName));
	}
	
	public void addMethodSymbol(MethodSymbol methodSym){
		this.methodsSymbols.put(methodSym.getName(), methodSym);
	}
	
	public void addMethodSymbolTable(MethodSymbolTable methodSymTable){
		methodSymbolTables.put(methodSymTable.getName(), methodSymTable);
	}
	
	public void addClassSymbolTable(ClassSymbolTable classSymTable){
		kidsClassSymbolTables.put(classSymTable.getSymbol().getName(), classSymTable);
	}

}
