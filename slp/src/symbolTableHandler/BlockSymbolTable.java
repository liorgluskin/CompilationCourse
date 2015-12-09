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
	public VariableSymbol getVarSymbol(String name) throws SemanticError{
		VariableSymbol var_symbol = var_symbols.get(name);
		if (var_symbol == null){
			if(parent instanceof BlockSymbolTable) 
				((BlockSymbolTable) parent).getVarSymbol(name);
			else
				throw new SemanticError("Variable "+ name + " hasn't been defined in one of "
						+ "the enclosing block or method scopes.");
		}
		return var_symbol;
	}
		
	/**
	 * 
	 * @param name
	 * 				variable name.
	 * @return true iff the variable is a class field.
	 */
	public boolean isVarField (String name){
		if (var_symbols.containsKey(name)) return false;
		if(parent instanceof BlockSymbolTable) 
			return ((BlockSymbolTable)parent).isVarField(name);
		return true;
	}
	
	/**
	 * Add a local variable symbol to the symbol table. 
	 * @param varName
	 * 				variable name.
	 * @param typeName
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
}
