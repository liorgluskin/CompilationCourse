package symbolTableHandler;

import semantic.SemanticError;
import types.TypeTable;

/**
 * All types of variable symbols. 
 *
 */

public class VariableSymbol extends Symbol{
	//initialized to -1 for internal use
	//contains the value of variable
	private int register_num=-1;
	
	public VariableSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name,-1);
		this.type = TypeTable.getTypeTable().getType(type_name);
	}
	
	public int getRegisterNum(){
		return register_num;
	}
	
	public void setRegisterNum(int register){
		register_num = register;
	}
	
}
