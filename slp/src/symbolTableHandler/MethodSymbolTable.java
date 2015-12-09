package symbolTableHandler;

import semantic.SemanticError;

/**
 * A symbol table for the Method scope.
 *
 */

public class MethodSymbolTable extends BlockSymbolTable{
	private String method_name;
	private boolean is_static;
		
	public MethodSymbolTable(String method_name, ClassSymbolTable parent){
		super(parent);
		this.method_name = method_name;
		try{
			this.is_static = parent.getMethodSymbol(method_name).isStatic();
		} 
		catch(SemanticError se){}
	}
		
	/**
	 * Get method name.
	 */
	public String getName(){
		return this.method_name;
	}
		
	/**
	 * 
	 * @param name
	 * 				variable's or parameter's name.
	 * @return method's variable or parameter symbol
	 */
	public VariableSymbol getVarParamSymbol(String name) throws SemanticError{
		VariableSymbol var_param = var_symbols.get(name);
		if (var_param == null) throw new SemanticError(this.getName() + " doesn't consist variable or parameter '"
					+ name + "' ");
		return var_param;
	}
		
	/**
	 * Add parameter symbol to table.
	 * @param name
	 * 				parameter name.
	 * @param typeName
	 * 				type name.
	 * @throws SemanticError
	 */
	public void addParamSymbol(String name, String type_name) throws SemanticError{
		this.var_symbols.put(name, new ParameterSymbol(name, type_name));
	}
	
	/**
	 * 
	 * @return returned variable symbol.
	 */
	public ReturnedVarSymbol getReturnVarSymbol()throws SemanticError{
		return (ReturnedVarSymbol)this.getVarParamSymbol("returned");	
	}
		
	/**
	 * 
	 * @param name
	 * 				variable's name.
	 * @param type_name
	 * 				variable's type.
	 * @throws SemanticError
	 */
	public void setReturnVarSymbol(String type_name) throws SemanticError{
		this.var_symbols.put("returned", new ReturnedVarSymbol("returned", type_name));
	}
}