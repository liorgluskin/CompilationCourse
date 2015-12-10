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
import symbolTableHandler.SymbolTable;
import types.Type;
import types.TypeArray;

/**
 * Class creates an evaluator that traverses the program,
 * Validates that there are no semantic errors of data type assignments,
 * 
 * implements PropagatingVisitor for recursive type passage
 * */
public class TypeEvaluator implements PropagatingVisitor<Object, Object>{

	private SymbolTable globalTable;
	private boolean inLoopScope = false;


	/** Constructor for evaluator of all type checks in program
	 * @param symbol-table of the program global scope
	 */
	public TypeEvaluator(SymbolTable globalTable){
		this.globalTable = globalTable;
	}


	/** Type Evaluator for Program
	 *  @return true
	 */
	public Object visit(Program program, Object o) {
		// iterate over classes in program
		for (ClassDecl c : program.getClasses()){
			c.accept(this, o);
		}
		return true;
	}

	/** Type Evaluator for Class
	 *  @return true
	 */
	public Object visit(ClassDecl class_decl, Object o) {
		for(FieldOrMethod fom :class_decl.getFieldsOrMethods()){
			fom.accept(this, o);
		}
		return true;
	}

	/** Method visit, created to avoid code duplication 
	 *  @return true 
	 */
	public Object visitMethod(Method method, Object o){
		// iterate method formals (parameters)
		for(Formal f : method.getFormals()){
			f.accept(this, o);
		}	
		// iterate method statements
		for(Stmt s : method.getStatementList().getStatements()){
			s.accept(this, o);
		}
		return true;
	}

	/** Type Evaluator for Class (virtual) Methods
	 */
	public Object visit(ClassMethod method, Object o) {
		return visitMethod(method, o);
	}

	/** Type Evaluator for Static Methods
	 */
	public Object visit(StaticMethod method, Object o) {
		return visitMethod(method, o);	
	}

	/** Type Evaluator for Primitive Type */
	public Object visit(PrimitiveType primitiveType, Object o) {
		return true; // no type comparisons for primitives
	}

	/** Type Evaluator for Class Type */
	public Object visit(ClassType classType, Object o) {
		return true; // no type comparisons for user defined data-type
	}

	/** Type Evaluator for Field */
	public Object visit(Field field, Object o) {
		return true; // no type comparisons
	}

	/** Type Evaluator for Formal */
	public Object visit(Formal formal, Object o) {
		return true; // no type comparisons
	}


	/** Type Evaluator for a list of statements
	 * @return true
	 */
	public Object visit(StmtList stmts, Object o) {
		// iterate over all statements in the list
		for(Stmt s : stmts.getStatements()){
			s.accept(this, o);
		}
		return true;
	}


	public Object visit(Stmt stmt, Object o) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}

	/**
	 * Type checks for Assignment statement:
	 * validate location and rhs are compatible
	 */
	public Object visit(AssignStmt stmt, Object o){
		SemanticError error;

		// get type of assignment location
		types.Type locationType = (types.Type) stmt.getLocation().accept(this, o);
		// get type of right-hand-side expression
		types.Type rhsType = (types.Type) stmt.getRhs().accept(this, o);

		// assignment location has invalid type
		if(locationType == null){
			error = new SemanticError("Assignment location of invalid type", stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		// assignment expression has invalid type
		else if(rhsType == null){
			error = new SemanticError("Assignment expression of invalid type", stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// if both types are valid, 
		// check type of rhs extends or is equal to location type
		if(!rhsType.extendsType(locationType)){
			error = new SemanticError("Assignment expression not subtype of location", stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return true;
	}


	/**
	 * Type checks for Call statement:
	 * type-checks validity of the call
	 */
	public Object visit(CallStmt stmt, Object o) {
		return stmt.accept(this, o);
	}


	/**
	 * Type checks for Return statement:
	 * validate the return expr type is a subtype of the method's return type
	 */
	public Object visit(ReturnStmt stmt, Object o) {

		////////////////////////////////////////////////////////////////
	}


	/**
	 * Type checks for Return statement:
	 * validate the if condition
	 * validate the if expression is of type boolean
	 * validate the body, and else statements
	 */
	public Object visit(IfStmt stmt, Object o) {
		// validate condition and get its type
		types.Type ifCondType = (types.Type) stmt.getCond().accept(this, o);

		if(ifCondType instanceof types.TypeBoolean){
			// if condition is boolean as required
		}else{
			SemanticError error = new SemanticError("If statement condition is not a boolean type",
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		// validate the statements of the if-body
		stmt.getBody().accept(this, o);

		// check whether if has else clause
		// if has else, check the else statements
		if(stmt.hasElse()){
			return stmt.getElseStmt().accept(this, o);
		}

		return true;
	}


	/**
	 * Type checks for While statement:
	 * validate the while condition
	 * validate the while expression is of type boolean
	 * validate the body of the while loop
	 */
	public Object visit(WhileStmt stmt, Object o) {
		// validate condition and get its type
		types.Type whileCondType = (types.Type) stmt.getCond().accept(this, o);

		if(whileCondType instanceof types.TypeBoolean){
			// while condition is boolean as required
		}else{
			SemanticError error = new SemanticError("While statement condition is not a boolean type",
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// validate while statement body
		inLoopScope = true;
		stmt.getBody().accept(this, o);
		inLoopScope = false; //finished validating while

		return true;
	}


	/**
	 * Type checks for Break statement:
	 * validate break is used only within a loop scope
	 */
	public Object visit(BreakStmt stmt, Object o) {
		if(!inLoopScope){
			SemanticError error = new SemanticError("Break statement must occur inside an enclosing loop",
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return true;
	}


	/**
	 * Type checks for Continue statement:
	 * validate break is used only within a loop scope
	 */
	public Object visit(ContinueStmt stmt, Object o) {
		if(!inLoopScope){
			SemanticError error = new SemanticError("Continue statement must occur inside an enclosing loop",
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return true;
	}


	public Object visit(BlockStmt stmt, Object o) {
		// TODO Auto-generated method stub
	}


	public Object visit(IDStmt stmt, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(VarLocation var_loc, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(ArrLocation arr_loc, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(StaticCall static_call, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(VirtualCall virtual_call, Object o) {
		// TODO Auto-generated method stub
	}


	/**
	 * Type checks for Literal:
	 * returns the literal's type
	 */
	public Object visit(Literal literal, Object o) {
		LiteralType literalType = literal.getType();
		try {
			switch(literalType){
			case INTEGER: return types.TypeTable.getType("int");
			case STRING: return types.TypeTable.getType("string");
			case TRUE: return types.TypeTable.getType("boolean");
			case FALSE: return types.TypeTable.getType("boolean");
			case NULL: return types.TypeTable.getType("null");
			} 
		}catch (SemanticError e) {
			// if the type-table does not contain the above types
			e.printStackTrace();
		}
		return null;
	}


	public Object visit(This t, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(NewObject new_obj, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(NewArray new_arr, Object o) {
		// TODO Auto-generated method stub

	}


	/**
	 * Type checks for Array Length:
	 * validate '.length' is applied to an expression of type array
	 * 
	 * @return the type of length - int
	 */
	public Object visit(Length length, Object o) {
		types.Type exprType = (types.Type) length.getExpression().accept(this,o);

		if(!(exprType instanceof types.TypeArray)){
			SemanticError error = new SemanticError("Expression length was applied to is not an array", 
					length.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		try {
			return types.TypeTable.getType("int");
		} catch (SemanticError e) {
			e.printStackTrace(); //if type table does not contain value "int"
		}
		return null;
	}


	public Object visit(Expr expr, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(BlockExpr expr, Object o) {
		// check the block's expressions
		return expr.getExpression().accept(this, o);
	}


	public Object visit(UnaryOpExpr expr, Object o) {
		// TODO Auto-generated method stub

	}


	public Object visit(BinaryOpExpr expr, Object o) {
		// TODO Auto-generated method stub

	}

}
