package symbolTableHandler;

import semantic.SemanticError;
import types.TypeTable;

/**
 * Symbol of type 'local variable'. 
 *
 */

public class VariableSymbol extends Symbol{
	
	public VariableSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name,-1);
		this.type = TypeTable.getType(type_name);
	}
	
}
