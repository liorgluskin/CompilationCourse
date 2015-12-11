package symbolTableHandler;

import semantic.SemanticError;

/**
 * Symbol of type 'returned method variable'. 
 *
 */

public class ReturnedVarSymbol extends VariableSymbol{
	
	public ReturnedVarSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name, type_name);
	}
	
}
