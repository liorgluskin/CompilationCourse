package symbolTableHandler;

import semantic.SemanticError;
import types.TypeTable;

/**
 * All types of variable symbols. 
 *
 */

public class VariableSymbol extends Symbol{
	private String varLabel = null;
	
	public VariableSymbol(String sym_name, String type_name) throws SemanticError{
		super(sym_name,-1);
		this.type = TypeTable.getTypeTable().getType(type_name);
	}
	
	
	// Methods for LIR translation - VarLabelVisitor
	// for each program variable symbol we keep the variable's 'memory address',
	// which is a label of the variable of format:
	//	 v_ID_symname - for local variable
	//	 p_ID_symname - for method parameters
	// 	 f_ID_symname - for class fields
	public String getLabel(){
		return varLabel;
	}
	public void setLabel(String varLabel){
		this.varLabel = varLabel;
	}
	
	
}
