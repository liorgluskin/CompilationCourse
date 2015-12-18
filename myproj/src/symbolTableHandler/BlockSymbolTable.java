package symbolTableHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import semantic.SemanticError;

/**
 * A symbol table for blocks.
 *
 */

public class BlockSymbolTable extends SymbolTable{
	//Symbol table
	protected Map<String,VariableSymbol> var_symbols = new HashMap<String,VariableSymbol>();
	
	//Scope info
	protected List<BlockSymbolTable> scope_stacks = new ArrayList<BlockSymbolTable>();
	
	/**
	 * Constructor 
	 */
	public BlockSymbolTable(SymbolTable parent){
		super(parent);
	}
	
	/**
	 * Get a local variable symbol by its name.
	 * @param name
	 * 				variable name
	 * @return
	 * 				variable symbol
	 * @throws SemanticError
	 */
	public VariableSymbol getVarSymbolLocal(String name) throws SemanticError{
		VariableSymbol var_symbol = var_symbols.get(name);
		if (var_symbol == null){ 
			throw new SemanticError("Variable "+ name + " hasn't been defined in one of "
					+ "the enclosing block or method scopes.");
		}
		return var_symbol;
	}
	
	/**
	 * Get variable symbol by its name - general.
	 * @param name
	 * 				variable name
	 * @return
	 * 				variable symbol
	 * @throws SemanticError
	 */
	public VariableSymbol getVarSymbol(String name) throws SemanticError{
		VariableSymbol var_symbol = var_symbols.get(name);
		if (var_symbol == null){ 
			var_symbol = ((BlockSymbolTable) parent).getVarSymbol(name);
		}
		return var_symbol;
	}
	
	/**
	 * Add a variable symbol to the symbol table. 
	 * @param var_name
	 * 				variable name.
	 * @param type_name
	 * 				type name.
	 * @throws SemanticError
	 */
	public void addVarSymbol(String var_name, String type_name) throws SemanticError{
		this.var_symbols.put(var_name, new VariableSymbol(var_name, type_name));
	}
	
	public void addStack(BlockSymbolTable stack){
		scope_stacks.add(stack);
	}
		
	public List<BlockSymbolTable> getScopeStacks(){
		return scope_stacks;	
	}
	
	public ClassSymbolTable getEnclosingClassSymbolTable(){
		if( parent instanceof ClassSymbolTable)
			//closest parent is the enclosing class
			return (ClassSymbolTable) parent;
		else
			//if parent is not ClassSymbolTable then it has to be blockSymbolTable
			return ((BlockSymbolTable)parent).getEnclosingClassSymbolTable();
		
	}
}