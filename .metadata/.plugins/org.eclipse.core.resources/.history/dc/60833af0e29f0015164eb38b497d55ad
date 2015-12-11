package symbolTableHandler;

import java.util.ArrayList;
import java.util.List;

import semantic.SemanticError;
import slp.Formal;
import slp.Method;
import types.Type;
import types.TypeTable;

/**
 * Symbol of type 'method'. 
 *
 */
public class MethodSymbol extends Symbol{
private boolean is_static;
	
	public MethodSymbol(String sym_name, Type return_type, List<Type> param_types, boolean is_static){
		super(sym_name,0);
		this.type = TypeTable.typeMethod(sym_name,return_type, param_types);
		this.is_static = is_static;
	}

	public MethodSymbol(Method method) throws SemanticError{
		super(method.getName(),0);
		List<Type> param_types = new ArrayList<Type>();
		for (Formal formal: method.getFormals()){
			param_types.add(TypeTable.getType(formal.getType().getFullName())); 
		}
		Type return_type = TypeTable.getType(method.getType().getFullName());
		
		this.type = TypeTable.typeMethod(method.getName(),return_type, param_types);
		this.is_static = method.isStatic();
	}
	
	/**
	 * @return true iff the method is static.
	 */
	public boolean isStatic(){
		return this.is_static;
	}
}
