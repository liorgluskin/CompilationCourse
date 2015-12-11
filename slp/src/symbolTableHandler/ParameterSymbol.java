package symbolTableHandler;

import semantic.SemanticError;

/**
 * Parameter symbol. 
 *
 */

public class ParameterSymbol extends VariableSymbol{
	
	public ParameterSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name, type_name);
	}
	
}
