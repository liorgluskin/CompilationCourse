package symbolTableHandler;

import semantic.SemanticError;

/**
 * Field symbol. 
 *
 */

public class FieldSymbol extends VariableSymbol{
	private int offset;
	
	public FieldSymbol(String sym_name, String type_name,int offset) throws SemanticError{
		super(sym_name, type_name);
		this.offset = offset;
	}
	
	public int getOffset(){
		return offset;
	}
	
}
