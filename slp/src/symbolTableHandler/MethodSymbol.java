package symbolTableHandler;

import java.util.ArrayList;
import java.util.List;

import semantic.SemanticError;
import slp.*;

import types.Type;
import types.TypeTable;

/**
 * Symbol of type 'method'. 
 *
 */
public class MethodSymbol extends Symbol{
private boolean is_static;
	


	public MethodSymbol(Method method) throws SemanticError{
		super(method.getName(),method.getLineNum());
		List<Type> param_types = new ArrayList<Type>();
		for (Formal formal: method.getFormals()){
			param_types.add(TypeTable.getType(formal.getType().getFullName())); 
		}
		Type return_type = TypeTable.getType(method.getType().getFullName());
		
		this.type = TypeTable.typeMethod(return_type, param_types);
		this.is_static = method instanceof StaticMethod;
	}
	
	/**
	 * @return true iff the method is static.
	 */
	public boolean isStatic(){
		return this.is_static;
	}
}
