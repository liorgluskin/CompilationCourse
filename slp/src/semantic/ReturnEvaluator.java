package semantic;

import slp.ArrLocation;
import slp.AssignStmt;
import slp.BinaryOpExpr;
import slp.BlockExpr;
import slp.BlockStmt;
import slp.BreakStmt;
import slp.CallStmt;
import slp.ClassDecl;
import slp.ClassMethod;
import slp.ClassType;
import slp.ContinueStmt;
import slp.Expr;
import slp.Field;
import slp.FieldOrMethod;
import slp.Formal;
import slp.IDStmt;
import slp.IfStmt;
import slp.Length;
import slp.Literal;
import slp.Method;
import slp.NewArray;
import slp.NewObject;
import slp.PrimitiveType;
import slp.Program;
import slp.PropagatingVisitor;
import slp.ReturnStmt;
import slp.StaticCall;
import slp.StaticMethod;
import slp.Stmt;
import slp.StmtList;
import slp.This;
import slp.UnaryOpExpr;
import slp.VarLocation;
import slp.VirtualCall;
import slp.Visitor;
import slp.WhileStmt;
import symbolTableHandler.BlockSymbolTable;
import symbolTableHandler.ClassSymbolTable;
import symbolTableHandler.GlobalSymbolTable;
import symbolTableHandler.MethodSymbolTable;
import symbolTableHandler.VariableSymbol;

/**This visitor validate that each method in the program, 
 * contains a valid return statement.
 * 
 * It deals with the case where a non-void method contains an if-else clause, 
 * where both if and else contain return statements.
 * */
public class ReturnEvaluator implements PropagatingVisitor<Object, Object>{

	private GlobalSymbolTable globaSymlTable;

	/** Constructor for all return statements checks in program
	 * @param symbol-table of the program global scope
	 */
	public ReturnEvaluator(GlobalSymbolTable globaSymlTable){
		this.globaSymlTable = globaSymlTable;
	}
	public Object visit(Program program, Object o) {
		//visit all classes apart from Library
		for(ClassDecl c: program.getClasses()){
			if (!c.getName().equals("Library"))
				c.accept(this,o);
		}
		return true;
	}

	public Object visit(ClassDecl class_decl, Object o) {
		//we continue visit the methods		
		for(FieldOrMethod fom : class_decl.getFieldsOrMethods()){
			fom.accept(this,o);
		}
		return true;
	}
	
	/**
	 * Checks if method contains a return statement
	 */
	private Object methodVisit(Method method){
		// if method is not void - must have 'return'
		if(!method.getType().getFullName().equals("void")){
			for(Stmt s : method.getStatementList().getStatements()){
				s.accept(this);
			}
		}
		if(!hasReturn){
			SemanticError error = new SemanticError("Invalid method, method with type '"
					+method.getType().getFullName()+"' must have 'return' statement", method.getLineNum());
			System.out.println(error);
			System.exit(-1);
		}
		return true;
	}
	

	public Object visit(ClassMethod method, Object o) {
		methodVisit(method);
		return true;
	}

	public Object visit(StaticMethod method, Object o) {
		methodVisit(method);
		return true;
	}

	public Object visit(PrimitiveType primitiveType, Object o) {return true;}

	public Object visit(ClassType classType, Object o) {return true;}

	public Object visit(Field field, Object o) {
		field.getType().accept(this);
		return true;
	}

	public Object visit(Formal formal, Object o) {
		formal.getType().accept(this);
		return true;
	}

	public Object visit(StmtList stmts, Object o) {
		for (Stmt s : stmts.getStatements()) {
			s.accept(this);
		}
		return true;
	}

	public Object visit(Stmt stmt, Object o) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
		return true;
	}

	public Object visit(AssignStmt stmt, Object o) {
		stmt.getLocation().accept(this); // iterate assignment variable
		stmt.getRhs().accept(this); // iterate assignment value
	}

	public Object visit(CallStmt stmt, Object o) {
		stmt.getCall().accept(this);
	}

	public Object visit(ReturnStmt stmt, Object o) {
		if(stmt.hasExpr()){
			stmt.getExpr().accept(this);
		}
		this.hasReturn = true;
	}

	public Object visit(IfStmt stmt, Object o) {
		stmt.getCond().accept(this);;
		stmt.getBody().accept(this);
		if(stmt.hasElse()){
			stmt.getElseStmt().accept(this);
		}
	}

	public Object visit(WhileStmt stmt, Object o) {
		stmt.getCond().accept(this);
		stmt.getBody().accept(this);
	}

	public Object visit(BreakStmt stmt, Object o) {}

	public Object visit(ContinueStmt stmt, Object o) {}

	public Object visit(BlockStmt stmt, Object o) {
		for(Stmt s : stmt.getStatementList().getStatements()){
			s.accept(this);
		}
	}

	public Object visit(IDStmt idStmt, Object o) { 
		idStmt.getType().accept(this);
		if(idStmt.hasValue()){
			idStmt.getValue().accept(this);
		}
	}

	public Object visit(VarLocation var_loc, Object o) {
		if(var_loc.hasExternalLocation()){
			var_loc.getLocation().accept(this);
		}
	}

	public Object visit(ArrLocation arr_loc, Object o) {
		arr_loc.getArrLocation().accept(this);
		arr_loc.getIndex().accept(this);
	}

	public Object visit(StaticCall static_call, Object o) {
		for(Expr e: static_call.getArguments()){
			e.accept(this);
		}
	}

	public Object visit(VirtualCall virtual_call, Object o) {
		if (virtual_call.getObjectReference() != null){
			virtual_call.getObjectReference().accept(this);
		}
		for(Expr e: virtual_call.getArguments()){
			e.accept(this);
		}
	}

	public Object visit(Literal literal, Object o) {}

	public Object visit(This t, Object o) {}

	public Object visit(NewObject new_obj, Object o) {}

	public Object visit(NewArray new_arr, Object o) {
		new_arr.getType().accept(this);
		new_arr.getArrayLength().accept(this);
	}

	public Object visit(Length length, Object o) {
		length.getExpression().accept(this);
	}

	public Object visit(Expr expr, Object o) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}

	public Object visit(BlockExpr expr, Object o) {
		expr.getExpression().accept(this);
	}

	public Object visit(UnaryOpExpr expr, Object o) {
		expr.getOperand().accept(this);
	}

	public Object visit(BinaryOpExpr expr, Object o) {
		expr.getLeftOperand().accept(this);
		expr.getRightOperand().accept(this);
	}
}