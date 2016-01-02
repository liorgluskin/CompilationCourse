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
import slp.LiteralType;
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
		return null;
	}

	public Object visit(ClassDecl class_decl, Object o) {
		//we continue visit the methods		
		for(FieldOrMethod fom : class_decl.getFieldsOrMethods()){
			fom.accept(this,o);
		}
		return null;
	}

	/**
	 * Checks if method contains a return statement
	 */
	private Object methodVisit(Method method, Object o){
		// if method is not void - must have 'return'
		if(!method.getType().getFullName().equals("void")){
			for(Stmt s : method.getStatementList().getStatements()){
				// method contains a 'return' statement
				if(s.accept(this,o) != null){
					System.out.println("BBBBBB");//////
					return null;
				}
			}
			// did not find 'return' statement
			SemanticError error = new SemanticError("Invalid method, method with type '"
					+method.getType().getFullName()+"' must have 'return' statement on each program path", method.getLineNum());
			System.out.println(error);
			System.exit(-1);
		}
		return null;
	}


	public Object visit(ClassMethod method, Object o) {
		methodVisit(method, o);
		return null;
	}

	public Object visit(StaticMethod method, Object o) {
		methodVisit(method, o);
		return null;
	}

	public Object visit(PrimitiveType primitiveType, Object o) {return null;}

	public Object visit(ClassType classType, Object o) {return null;}

	public Object visit(Field field, Object o) {
		field.getType().accept(this, o);
		return null;
	}

	public Object visit(Formal formal, Object o) {
		formal.getType().accept(this, o);
		return null;
	}

	public Object visit(StmtList stmts, Object o) {
		// determines if statement block contains 'return'
		Object lastStmtReturn = null; 
		for (Stmt s : stmts.getStatements()) {
			Object hasReturn = s.accept(this, o);
			// found return statement
			if(lastStmtReturn == null){
				lastStmtReturn = hasReturn;
			}
		}
		// if statement in block was return
		// then lastStmtReturn is not null
		return lastStmtReturn;
	}

	public Object visit(Stmt stmt, Object o) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}

	public Object visit(AssignStmt stmt, Object o) {
		stmt.getLocation().accept(this, o); // iterate assignment variable
		stmt.getRhs().accept(this, o); // iterate assignment value
		return null;
	}

	public Object visit(CallStmt stmt, Object o) {
		stmt.getCall().accept(this, o);
		return null;
	}

	public Object visit(ReturnStmt stmt, Object o) {
		if(stmt.hasExpr()){
			stmt.getExpr().accept(this, o);
		}
		return "Return";
	}

	/**
	 * Helper method to determine if the condition of an 'if',
	 * or 'while' statement is set to 'true' or 'false'.
	 * For example 'while(true)', or 'if(false)'
	 * */
	private int getStagnantCondition(Expr expr){
		//check if condition expression if 'true' or 'false'
		if(expr instanceof Literal){
			Literal condition = (Literal)expr;
			if(condition.getType().equals(LiteralType.FALSE)){
				return 0;
			}
			else if(condition.getType().equals(LiteralType.TRUE)){
				return 1;
			}
		}
		// condition is not stagnant
		return -1;
	}

	public Object visit(IfStmt stmt, Object o) {
		Expr cond = stmt.getCond();
		Object ifRes = stmt.getBody().accept(this, o);
		Object elseRes = null;
		if(stmt.hasElse()){
			elseRes = stmt.getElseStmt().accept(this, o);
		}
		// check whether if condition is constant (true/false)
		int ifCondVal = getStagnantCondition(cond);
		// case 'if(true)'
		if(ifCondVal == 1){
			return ifRes;
		}
		// case 'if(false)' --> else
		else if(ifCondVal == 0){
			return elseRes;
		}

		// If condition is not constant

		// 'if' body does not contain 'return'
		if(ifRes == null){
			return ifRes;
		}
		// 'if' body contains 'return'
		// then 'else' branch must also return
		else{
			return elseRes;
		}		
	}


	public Object visit(WhileStmt stmt, Object o) {
		Expr cond = stmt.getCond();
		Object body = stmt.getBody().accept(this, o);

		// a case of 'while(true)'
		if(getStagnantCondition(cond) == 1){
			// the body must contains 'return' statement
			return body;
		}
		return null;
	}


	public Object visit(BreakStmt stmt, Object o) {
		return null;
	}

	public Object visit(ContinueStmt stmt, Object o) {
		return null;
	}

	public Object visit(BlockStmt stmt, Object o) {
		Object lastStmtReturn = null;
		for(Stmt s : stmt.getStatementList().getStatements()){
			Object hasReturn = s.accept(this, o);
			// found return statement
			if(lastStmtReturn == null){
				lastStmtReturn = hasReturn;
			}
		}
		// if statement in block was return
		// then lastStmtReturn is not null
		return lastStmtReturn;
	}

	public Object visit(IDStmt idStmt, Object o) { 
		idStmt.getType().accept(this, o);
		if(idStmt.hasValue()){
			idStmt.getValue().accept(this, o);
		}
		return null;
	}

	public Object visit(VarLocation var_loc, Object o) {
		if(var_loc.hasExternalLocation()){
			var_loc.getLocation().accept(this, o);
		}
		return null;
	}

	public Object visit(ArrLocation arr_loc, Object o) {
		arr_loc.getArrLocation().accept(this, o);
		arr_loc.getIndex().accept(this, o);
		return null;
	}

	public Object visit(StaticCall static_call, Object o) {
		for(Expr e: static_call.getArguments()){
			e.accept(this, o);
		}
		return null;
	}

	public Object visit(VirtualCall virtual_call, Object o) {
		if (virtual_call.getObjectReference() != null){
			virtual_call.getObjectReference().accept(this, o);
		}
		for(Expr e: virtual_call.getArguments()){
			e.accept(this, o);
		}
		return null;
	}

	public Object visit(Literal literal, Object o) {
		return null;
	}

	public Object visit(This t, Object o) {
		return null;
	}

	public Object visit(NewObject new_obj, Object o) {
		return null;
	}

	public Object visit(NewArray new_arr, Object o) {
		new_arr.getType().accept(this, o);
		new_arr.getArrayLength().accept(this, o);
		return null;
	}

	public Object visit(Length length, Object o) {
		return length.getExpression().accept(this, o);
	}

	public Object visit(Expr expr, Object o) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}

	public Object visit(BlockExpr expr, Object o) {
		return expr.getExpression().accept(this, o);
	}

	public Object visit(UnaryOpExpr expr, Object o) {
		return expr.getOperand().accept(this, o);
	}

	public Object visit(BinaryOpExpr expr, Object o) {
		expr.getLeftOperand().accept(this, o);
		expr.getRightOperand().accept(this, o);
		return null;		
	}
}
