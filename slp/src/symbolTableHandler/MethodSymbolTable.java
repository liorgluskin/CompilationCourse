package symbolTableHandler;

import lir.Environment;
import semantic.SemanticError;

/**
 * A symbol table for the Method scope.
 *
 */

public class MethodSymbolTable extends BlockSymbolTable{
	private String method_name;
	private boolean is_static;
	private String thisReg = null;
		
	public MethodSymbolTable(String method_name, ClassSymbolTable parent){
		super(parent);
		this.method_name = method_name;
		
		//null cannot be returned here
		this.is_static = parent.getMethodSymbol(method_name).isStatic();
	}
		
	/**
	 * Get method name.
	 */
	public String getName(){
		return this.method_name;
	}
		
	/**
	 * 
	 * @param name
	 * 				variable's or parameter's name.
	 * @return method's variable or parameter symbol
	 */
	public VariableSymbol getVarParamSymbol(String name) throws SemanticError{
		VariableSymbol var_param = var_symbols.get(name);
		if (var_param == null) throw new SemanticError(this.getName() + " doesn't consist variable or parameter '"
					+ name + "' ");
		return var_param;
	}
	
	/**
	 * Get variable symbol by its name.
	 * @param name
	 * 				variable name
	 * @return
	 * 				variable symbol
	 * @throws SemanticError
	 */
	public VariableSymbol getVarSymbol(String name) throws SemanticError{
		VariableSymbol var_symbol = var_symbols.get(name);
		if (var_symbol == null){
			var_symbol = ((ClassSymbolTable) parent).getFieldSymbol(name);
			if(var_symbol == null){
				throw new SemanticError("variable '"+ name +"' cannot be resolved");
			}
			else if (this.is_static){
				throw new SemanticError("cannot access variable '"+ name +"' in a static method");
			} 
		}
		return var_symbol;
	}
	
	/**
	 * Add parameter symbol to table.
	 * @param name
	 * 				parameter name.
	 * @param type_name
	 * 				type name.
	 * @throws SemanticError
	 */
	public void addParamSymbol(String name, String type_name) throws SemanticError{
		this.var_symbols.put(name, new ParameterSymbol(name, type_name));
	}
	
	public ReturnedVarSymbol getReturnVarSymbol()throws SemanticError{
		return (ReturnedVarSymbol)this.getVarParamSymbol("returned");	
	}
		
	public void setReturnVarSymbol(String type_name) throws SemanticError{
		this.var_symbols.put("returned", new ReturnedVarSymbol("returned", type_name));
	}
	
	public boolean isField (String var){
		if (var_symbols.containsKey(var)) 
			return false;
		return true;
	}
	
	public String getThisregister(Environment d){
		if(thisReg != null){
			return thisReg;
		}else{
			thisReg = "R" + d.getCurrentRegister();
			d.addLirInstruction("Move", "this", thisReg);
			d.incrementRegister();
			return thisReg;
		}
	}
}