package symbolTableHandler;

import semantic.SemanticError;

/**
 * Symbol of type 'local variable'. 
 *
 */

public class FieldSymbol extends VariableSymbol{
	
	public FieldSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name, type_name);
	}
	
}
