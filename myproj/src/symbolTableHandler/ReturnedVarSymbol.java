package symbolTableHandler;

import semantic.SemanticError;

/**
 * 
 * Symbol for returned variable of a method. 
 *
 */

public class ReturnedVarSymbol extends VariableSymbol{
	
	public ReturnedVarSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name, type_name);
	}
	
}
