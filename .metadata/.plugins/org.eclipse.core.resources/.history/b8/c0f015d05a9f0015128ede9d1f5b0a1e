package semantic;

import slp.*;
import symbolTableHandler.BlockSymbolTable;
import symbolTableHandler.ClassSymbolTable;
import symbolTableHandler.MethodSymbol;
import symbolTableHandler.MethodSymbolTable;
import types.Type;
import types.TypeMethod;
import types.TypeTable;

public class SemanticEvaluator implements slp.Visitor{
	private boolean hasMain = false;
	
	/**
	 * Checks whether the given method is the main method.
	 */
	private static boolean isMain(MethodSymbol method_sym){
		//main must be static
		if (!method_sym.isStatic()) return false;						
		if (method_sym.getName().compareTo("main") != 0) return false;
		
		TypeMethod type_method = (TypeMethod)method_sym.getType();
		try{
			//return type must be void
			if (type_method.getReturnType() != (TypeTable.getType("void"))) return false; 
			
			//no parameters or too many
			if (type_method.getParamTypes().size() != 1) return false; 
			
			//parameter is not of type string[]
			Type param_type = type_method.getParamTypes().get(0);
			if (param_type != TypeTable.typeArray(TypeTable.getType("string"))) return false;
		}
		catch(SemanticError se){}
		return true;
	}
	
	@Override
	public void visit(Program program) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassDecl class_decl) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Creates a symbol table for a method scope (all types).
	 * 
	 */
	private void methodVisit(Method method){
		//create symbol table for method scope
		MethodSymbolTable method_scope = new MethodSymbolTable(method.getName(),(ClassSymbolTable)method.getScope());
		
		method.getType().setScope(method_scope);
		
		//method's parameters (formals)
		for (Formal f: method.getFormals()){
			f.setScope(method_scope);
			try{
				method_scope.getVarParamSymbol(f.getName());
				SemanticError error = new SemanticError("parameter is previously defined in method " + method.getName());
				error.toString();
				System.exit(-1);
			} catch (SemanticError not_defined){
				try{
					method_scope.addParamSymbol(f.getName(), f.getType().getFullName());
				} 
				catch (SemanticError e){
					//error adding the parameter symbol
					e.toString();
					System.exit(-1);
				}
			}
			f.accept(this);
		}
		
		//method statements
		for (Stmt s: method.getStatementList().getStatements()){
			s.setScope(method_scope);
			s.accept(this); 
		}
		
		//return variable
		try{
			method_scope.setReturnVarSymbol(method.getType().getFullName());
		} 
		//error setting the return variable
		catch (SemanticError error){
			error.toString();
			System.exit(-1);
		}
	}
	
	public void visit(ClassMethod method) {
		methodVisit(method);	
	}

	public void visit(StaticMethod method) {
		methodVisit(method);
	}

	@Override
	public void visit(PrimitiveType primitiveType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ClassType classType) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the scope for the type of a field and visits it.
	 *
	 */
	public void visit(Field field) {
		field.getType().setScope(field.getScope());
		field.getType().accept(this);
	}

	/**
	 * Sets the scope for the type of a formal and visits it.
	 *
	 */
	public void visit(Formal formal) {
		formal.getType().setScope(formal.getScope());
		formal.getType().accept(this);
		
	}

	@Override
	public void visit(StmtList stmts) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Stmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(AssignStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(CallStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ReturnStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IfStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(WhileStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BreakStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(ContinueStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BlockStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(IDStmt stmt) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the scope of variable's location and visits its node.
	 *
	 */
	public void visit(VarLocation var_loc) {
		if (var_loc.getLocation() != null){
			var_loc.getLocation().setScope(var_loc.getScope());
			var_loc.getLocation().accept(this);
		}
		else 
			try{
				//Check if variable defined
				((BlockSymbolTable)var_loc.getScope()).getVarSymbol(var_loc.getName());
			} 
			catch (SemanticError e){
				e.setLineNum(var_loc.getLineNum());
				//error
			}
	}

	/**
	 * Sets the scope of array's index and location, and visits their AST nodes.
	 *
	 */
	public void visit(ArrLocation arr_loc) {
		arr_loc.getArrLocation().setScope(arr_loc.getScope());
		arr_loc.getArrLocation().accept(this);
		
		arr_loc.getIndex().setScope(arr_loc.getScope());
		arr_loc.getIndex().accept(this);
	}

	/**
	 * Sets the scope for the method arguments, and visits arguments' AST nodes.
	 *
	 */
	public void visit(StaticCall static_call) {
		for (Expr e: static_call.getArguments()){
			e.setScope(static_call.getScope());
			e.accept(this);
		}
	}

	/**
	 * Sets the call's reference scope according to the call's scope if needed & 
	 * Sets the scope for the method arguments, and visits arguments' AST nodes.
	 * 
	 */
	public void visit(VirtualCall virtual_call) {
		if (virtual_call.getObjectReference() != null) {
			virtual_call.getObjectReference().setScope(virtual_call.getScope());
			virtual_call.getObjectReference().accept(this);
		}
		for (Expr e: virtual_call.getArguments()){
			e.setScope(virtual_call.getScope());
			e.accept(this);
		}
	}

	/**
	 * Literal visitor - does nothing.
	 */
	public void visit(Literal literal) {	
	}

	@Override
	public void visit(This t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewObject new_obj) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Sets the scope of a new array's type and length, and visits their AST nodes.
	 *
	 */
	public void visit(NewArray new_arr) {
		new_arr.getType().setScope(new_arr.getScope());
		new_arr.getType().accept(this);
		
		new_arr.getArrayLength().setScope(new_arr.getScope());
		new_arr.getArrayLength().accept(this);

	}

	/**
	 * Sets array's scope, according to its length scope.
	 * 
	 */
	public void visit(Length length) {
		length.getExpression().setScope(length.getScope());
		length.getExpression().accept(this);
	}

	@Override
	public void visit(Expr expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BlockExpr expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(UnaryOpExpr expr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(BinaryOpExpr expr) {
		// TODO Auto-generated method stub
		
	}

}
