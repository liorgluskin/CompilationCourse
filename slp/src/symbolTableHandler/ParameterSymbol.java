package symbolTableHandler;

import semantic.SemanticError;

/**
 * Symbol of type 'method parameter'. 
 *
 */

public class ParameterSymbol extends VariableSymbol{
	
	public ParameterSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name,type_name);
	}
	
}
