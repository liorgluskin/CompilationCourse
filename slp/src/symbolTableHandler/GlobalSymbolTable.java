package symbolTableHandler;

import java.util.Map;

import semantic.SemanticError;
import slp.ClassDecl;

import java.util.HashMap;

public class GlobalSymbolTable extends SymbolTable {
	// map contains class-symbol-tables of all the classes of the program 
	private Map<String,ClassSymbolTable> childrenSymTables = new HashMap<String,ClassSymbolTable>(); 
	// map contains all of the class symbols in program
	private Map<String,ClassSymbol> tableEntries = new HashMap<String,ClassSymbol>();

	public GlobalSymbolTable() {
		super(null);
	}


	public ClassSymbol getClass(String className){
		return tableEntries.get(className);
	}

	public ClassSymbolTable getClassSymbolTable(String name){
		ClassSymbolTable classSymTable = childrenSymTables.get(name);
		if(classSymTable != null){
			return classSymTable;
		}
		else{
			for (ClassSymbolTable childSymTable: childrenSymTables.values()){
				classSymTable = childSymTable.getClassSymbolTable(name);
				if (classSymTable != null){
					return classSymTable;
				}
			}
		}
		return null;
	}

	public void addClassSymbolTable(ClassSymbolTable classSymTable){
		childrenSymTables.put(classSymTable.getSymbol().getName(), classSymTable);
	}

	public void addClass(ClassDecl classDecl) throws SemanticError{
		tableEntries.put(classDecl.getName(), new ClassSymbol(classDecl));
	}



}
