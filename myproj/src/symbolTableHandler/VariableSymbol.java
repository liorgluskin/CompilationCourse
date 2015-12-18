package symbolTableHandler;

import semantic.SemanticError;
import types.TypeTable;

/**
 * All types of variable symbols. 
 *
 */

public class VariableSymbol extends Symbol{
	
	public VariableSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name,-1);
		this.type = TypeTable.getTypeTable().getType(type_name);
	}
	
}