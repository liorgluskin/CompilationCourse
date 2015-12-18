package semantic;

import java.util.List;
import java.util.function.BinaryOperator;

import slp.ArrLocation;
import slp.AssignStmt;
import slp.BinOperator;
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
import slp.Operator;
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
import slp.VarExpr;
import slp.VarLocation;
import slp.VirtualCall;
import slp.Visitor;
import slp.WhileStmt;
import symbolTableHandler.BlockSymbolTable;
import symbolTableHandler.ClassSymbolTable;
import symbolTableHandler.GlobalSymbolTable;
import symbolTableHandler.SymbolTable;
import types.Type;
import types.TypeArray;
import types.TypeBoolean;
import types.TypeInt;
import types.TypeString;
import types.TypeTable;

/**
 * Class creates an evaluator that traverses the program,
 * Validates that there are no semantic errors of data type assignments,
 * 
 * implements PropagatingVisitor for recursive type passage
 * */
public class TypeEvaluator implements PropagatingVisitor<Object, Object>{

	private GlobalSymbolTable globaSymlTable;
	int inLoopScope = 0;
	private boolean inStaticMethod = false;


	/** Constructor for evaluator of all type checks in program
	 * @param symbol-table of the program global scope
	 */
	public TypeEvaluator(GlobalSymbolTable globaSymlTable){
		this.globaSymlTable = globaSymlTable;
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
		inStaticMethod = false;

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
		inStaticMethod = true;
		return visitMethod(method, o);	
	}

	/** Type Evaluator for Primitive Type
	 * @return true
	 *  */
	public Object visit(PrimitiveType primitiveType, Object o) {
		return true; // no type comparisons for primitives
	}

	/** Type Evaluator for Class Type 
	 * @return true
	 * */
	public Object visit(ClassType classType, Object o) {
		return true; // no type comparisons for user defined data-type
	}

	/** Type Evaluator for Field 
	 * @return true
	 * */
	public Object visit(Field field, Object o) {
		return true; // no type comparisons
	}

	/** Type Evaluator for Formal 
	 * @return true
	 * */
	public Object visit(Formal formal, Object o) {
		return true; // no type comparisons
	}


	/** Type Evaluator for a list of statements
	 * @return true if successful
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
	 * @return true if successful
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
	 * @return call return type
	 */
	public Object visit(CallStmt stmt, Object o) {
		if(stmt.getCall().accept(this, o) == null){
			return null;
		}
		return true;
	}


	/**
	 * Type checks for Return statement:
	 * validate the return expr type is a subtype of enclosing method type
	 * @return the return statement type
	 */
	public Object visit(ReturnStmt stmt, Object o) {
		types.Type returnType = null;
		types.Type enclosingMethodType = null;

		// get type of return statement, if followed by expression
		if(stmt.hasExpr()){
			returnType = (types.Type) stmt.getExpr().accept(this, o);
		}
		// return type is void
		else{

			returnType = new types.TypeVoid();
		}

		// get enclosing method type

		try {
			enclosingMethodType = ( (symbolTableHandler.BlockSymbolTable) stmt.getScope() ).getVarSymbol("returned").getType();
		} catch (SemanticError e) {
			// got here if 'method symbol table' doe not contain 'returned'
			e.printStackTrace();
		}


		// check if subtype of method type
		if(!returnType.extendsType(enclosingMethodType)){
			SemanticError error = new SemanticError("Invalid return statement, inconsistent with enclosing method type", 
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		return returnType;
	}


	/**
	 * Type checks for Return statement:
	 * validate the if condition
	 * validate the if expression is of type boolean
	 * validate the body, and else statements
	 * @return true if successful
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
	 * @return true if successful
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
		inLoopScope++;
		stmt.getBody().accept(this, o);
		inLoopScope--; //finished validating while

		return true;
	}


	/**
	 * Type checks for Break statement:
	 * validate break is used only within a loop scope
	 * @return true if successful
	 */
	public Object visit(BreakStmt stmt, Object o) {
		if(inLoopScope<=0){
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
	 * @return true if successful
	 */
	public Object visit(ContinueStmt stmt, Object o) {
		if(inLoopScope<=0){
			SemanticError error = new SemanticError("Continue statement must occur inside an enclosing loop",
					stmt.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return true;
	}


	/**
	 * Type checks for a Block statement:
	 * validate every statement in the block statement list
	 * @return true if successful
	 */
	public Object visit(BlockStmt stmt, Object o) {
		for(Stmt s : stmt.getStatementList().getStatements()){
			s.accept(this, o);
		}
		return true;
	}


	/**
	 * Type checks for a Variable Definition statement:
	 * If variable has been assigned a value, 
	 * validate assigned value is a subtype of the variable type
	 * @return true if successful
	 */
	public Object visit(IDStmt stmt, Object o) {

		// variable has been assigned with value
		if(stmt.hasValue()){
			// get variable's type
			types.Type varType=null;
			try {

				varType = ((symbolTableHandler.BlockSymbolTable) stmt.getScope()).getVarSymbol(stmt.getName()).getType();
			} catch (SemanticError e) {
				// variable type undefined, print error and exit
				System.out.println(new SemanticError(e.getMessage(),stmt.getLineNum()));
				System.exit(-1);
			}
			// get value's type
			types.Type valueType = (types.Type) stmt.getValue().accept(this, o);

			// check value type extends or is equal to variable type
			if(!valueType.extendsType(varType)){
				SemanticError error  = new SemanticError("Variable defined with incompatible value",
						stmt.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
		}
		return true;
	}

	/**
	 * Type checks for a Variable Location expression:
	 * validate the location itself
	 * @return the variable-location type
	 */
	public Object visit(VarLocation var_loc, Object o) {

		// check if variable is a class field
		if(var_loc.hasExternalLocation()){
			// get the type of the field's class
			types.Type locationType = (types.Type) var_loc.getLocation().accept(this, o);

			// Verify the class contains a field with var_loc's name
			// get the class symbol table - if class exists 
			////// handle when class not found in sym table!
			symbolTableHandler.ClassSymbolTable classSymTable = globaSymlTable.getClassSymbolTable(locationType.getName());
			// get the class field - if exists
			////// handle when field not found in class table!
			symbolTableHandler.FieldSymbol fieldSymbol = classSymTable.getFieldSymbol(var_loc.getName());

			// field symbol undefined
			if(fieldSymbol == null){
				SemanticError error  = new SemanticError("Class field is undefined",
						var_loc.getLineNum());
				System.out.println(error);
				System.exit(1);
			}

			return fieldSymbol.getType(); // return the field's type
		}
		// local variable location
		else{
			// get the local variable's type from the scope type-table
			types.Type varType = null;
			try {
				varType = ((symbolTableHandler.BlockSymbolTable) var_loc.getScope()).getVarSymbol(var_loc.getName()).getType();
			} catch (SemanticError e) {
				// variable type undefined, print error and exit
				System.out.println(new SemanticError(e.getMessage(),var_loc.getLineNum()));
				System.exit(-1);
			}
			return varType;
		}
	}


	/**
	 * Type checks for an Array Location expression:
	 * validate the array type exists
	 * validate the index type is int
	 * @return the array type
	 */
	public Object visit(ArrLocation arr_loc, Object o) {
		// get the type of the array elements
		// validates type is defined
		types.Type arrType = (types.Type) arr_loc.getArrLocation().accept(this, o);

		// get the type of the array location index
		types.Type indexType = (types.Type) arr_loc.getIndex().accept(this, o);
		// validate index type is int

		if(!indexType.extendsType(new TypeInt())){
			SemanticError error = new SemanticError("Array location invalid, index not of type int",
					arr_loc.getLineNum());
			System.out.println(error);
			System.exit(1);
		}


		return ((TypeArray)arrType).getElementType();
	}


	/**
	 * Type checks for an Static Call expression:
	 * validate static method's enclosing class exists
	 * validate called method is defined in enclosing class, as static method
	 * validate call parameters are subtypes of the method's formals types
	 * 
	 * @return the called method return-type
	 */
	public Object visit(StaticCall static_call, Object o) {

		// validate static method's enclosing class exists
		symbolTableHandler.ClassSymbolTable classSymTable = null;
		classSymTable = globaSymlTable.getClassSymbolTable(static_call.getClassName());

		// validate called method is defined in enclosing class, as static method
		symbolTableHandler.MethodSymbol methodSym = classSymTable.getMethodSymbol(static_call.getMethodName());
		if (!methodSym.isStatic()){
			SemanticError error = new SemanticError("Invalid static method call, method is not static",
					static_call.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// validate call parameters are subtypes of the method's formals types
		// get call parameters
		List<Expr> callParameters = static_call.getArguments();
		// get call method formals
		List<Type> methodFormalsTypes = ( (types.TypeMethod) methodSym.getType()).getParamTypes();

		// wrong number of arguments in call
		if(callParameters.size() != methodFormalsTypes.size()){
			SemanticError error = new SemanticError("Invalid static method call, method called with wrong number of arguments",
					static_call.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// if correct num of parameters passed to call
		// validate each parameter type corresponds to formal type in method declaration
		types.Type callParamType = null;
		for(int i = 0 ; i < callParameters.size(); i++){
			// get current call parameter type
			callParamType = (types.Type) (callParameters.get(i)).accept(this, o); 
			// validate parameter type is not subtype of method formal
			if( !callParamType.extendsType(methodFormalsTypes.get(i)) ){
				SemanticError error = new SemanticError("Invalid static method call, passed wrong argument type",
						static_call.getLineNum());
				System.out.println(error);
				System.exit(1);
			}	
		}

		// the call returns the method return-type
		return ((types.TypeMethod) methodSym.getType()).getReturnType();
	}


	/**
	 * Type checks for an Virtual Call expression:
	 * validate virtual method's enclosing class exists
	 * validate called method is defined in enclosing class, as class method
	 * validate virtual method is not called from a static method
	 * validate call parameters are subtypes of the method's formals types
	 * 
	 * @return the called method return-type
	 */
	public Object visit(VirtualCall virtual_call, Object o) {

		symbolTableHandler.ClassSymbolTable classSymTable = null;

		// virtual call of an instance: obj.call()
		if(virtual_call.getObjectReference() != null){
			// get instance type
			types.Type objType = (types.Type) virtual_call.getObjectReference().accept(this, o);
			symbolTableHandler.ClassSymbolTable objClassTable = ((GlobalSymbolTable) globaSymlTable).getClassSymbolTable(objType.getName());
			// instance class undefined
			if(objClassTable == null){
				SemanticError error = new SemanticError("Invalid virutal method call, instance class undefined",
						virtual_call.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
		}
		// virtual call without object instance
		else{		
			classSymTable = ((BlockSymbolTable)virtual_call.getScope()).getEnclosingClassSymbolTable();

			// validate call is not from static method body
			if(inStaticMethod){
				SemanticError error = new SemanticError("Invalid virutal method call, non-refernce call from a static method",
						virtual_call.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
		}
		classSymTable = ((BlockSymbolTable)virtual_call.getScope()).getEnclosingClassSymbolTable();

		// validate method is defined as virtual in enclosing class
		symbolTableHandler.MethodSymbol methodSym = classSymTable.getMethodSymbol(virtual_call.getMethodName());
		if(methodSym == null){
			SemanticError error = new SemanticError("Invalid method call, method is undefined",
					virtual_call.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		else if (methodSym.isStatic()){
			SemanticError error = new SemanticError("Invalid virtual method call, method '"+virtual_call.getMethodName()+"' is not virtual",
					virtual_call.getLineNum());
			System.out.println(error);
			System.exit(1);
		}


		// validate call parameters are subtypes of the method's formals types
		// get call parameters
		List<Expr> callParameters = virtual_call.getArguments();
		// get call method formals
		List<Type> methodFormalsTypes = ( (types.TypeMethod) methodSym.getType()).getParamTypes();

		// wrong number of arguments in call
		if(callParameters.size() != methodFormalsTypes.size()){
			SemanticError error = new SemanticError("Invalid virtual method call, method called with wrong number of arguments",
					virtual_call.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// if correct num of parameters passed to call
		// validate each parameter type corresponds to formal type in method declaration
		types.Type callParamType = null;
		for(int i = 0 ; i < callParameters.size(); i++){
			// get current call parameter type
			callParamType = (types.Type) (callParameters.get(i)).accept(this, o); 
			// validate paramter type is not subtype of method formal
			if( !callParamType.extendsType(methodFormalsTypes.get(i)) ){
				SemanticError error = new SemanticError("Invalid virtual method call, passed wrong argument type",
						virtual_call.getLineNum());
				System.out.println(error);
				System.exit(1);
			}	
		}

		// the call returns the method return-type
		return ((types.TypeMethod) methodSym.getType()).getReturnType();
	}


	/**
	 * Type checks for Literal:
	 * @returns the literal type
	 */
	public Object visit(Literal literal, Object o) {
		LiteralType literalType = literal.getType();
		try {
			switch(literalType){
			case INTEGER: return types.TypeTable.getTypeTable().getType("int");
			case STRING: return types.TypeTable.getTypeTable().getType("string");
			case TRUE: return types.TypeTable.getTypeTable().getType("boolean");
			case FALSE: return types.TypeTable.getTypeTable().getType("boolean");
			case NULL: return types.TypeTable.getTypeTable().getType("null");
			} 
		}catch (SemanticError e) {
			// if the type-table does not contain the above types
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * Type checks for 'This' expression:
	 * validate 'this' not referenced in a static method 
	 * 
	 * @return type 'this' refers to
	 */
	public Object visit(This t, Object o) {
		if(inStaticMethod){
			SemanticError error = new SemanticError("Invalid expression, 'this' inside static method", 
					t.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		types.Type thisType = null;
		if(t.getScope() instanceof ClassSymbolTable)
			thisType = ((ClassSymbolTable)t.getScope()).getSymbol().getType();
		else
			thisType = ((BlockSymbolTable)t.getScope()).getEnclosingClassSymbolTable().getSymbol().getType();

		return thisType;
	}


	/**
	 * Type checks for a New Object:
	 * validate the object type exists
	 * 
	 * @return the object type
	 */
	public Object visit(NewObject new_obj, Object o) {
		types.Type objectType = null;
		try {
			objectType = types.TypeTable.getTypeTable().getClassType(new_obj.getClassName());
		} catch (SemanticError e) {
			// if class object already declared, print error and exit
			SemanticError error = new SemanticError(e.getMessage(), new_obj.getLineNum());
			System.out.println(error);
			System.exit(-1);
		}
		if(objectType == null){
			SemanticError error = new SemanticError("Invalid new Object(), object type undefined", 
					new_obj.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return objectType;
	}

	/**
	 * Type checks for a New Array expression:
	 * validate array type is not void
	 * validate array type exists
	 * validate array length expression is of type in
	 * 
	 * @return the array type
	 */
	public Object visit(NewArray new_arr, Object o) {

		// get array type - if exists
		types.Type arrType = null;
		try {
			arrType = TypeTable.getTypeTable().getType(new_arr.getType().getFullName()+"[]");
		} catch (SemanticError e) {
			SemanticError error = new SemanticError(e.getMessage(), new_arr.getLineNum());
			System.out.println(error);
			System.exit(-1);
		}

		if(arrType == null){
			SemanticError error = new SemanticError("Invalid new Array, array type undefined or void", 
					new_arr.getLineNum());
			System.out.println(error);
			System.exit(1);
		}

		// get type of array length
		types.Type lenType = (types.Type) new_arr.getArrayLength().accept(this, 0);
		if(!lenType.extendsType(new types.TypeInt())){
			SemanticError error = new SemanticError("Array definiton invalid, length not of type int",
					new_arr.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		return arrType;
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
			SemanticError error = new SemanticError("The expression length was applied to, is not an array", 
					length.getLineNum());
			System.out.println(error);
			System.exit(1);
		}
		try {
			return types.TypeTable.getTypeTable().getType("int");
		} catch (SemanticError e) {
			e.printStackTrace(); //if type table does not contain value "int"
		}
		return null;
	}


	/** Type Evaluator for abstract class Expr
	 *  @return true
	 */
	public Object visit(Expr expr, Object o) {
		return true;
	}


	/** Type Evaluator for a block of Expressions
	 *  @return the expressions evaluation result 
	 */
	public Object visit(BlockExpr expr, Object o) {
		// check the block's expressions
		return expr.getExpression().accept(this, o);
	}


	/**
	 * Type checks for Unary Operation expression:
	 * validate operator parameter is of the correct type
	 * 
	 * @return int - if mathematical op
	 * @return boolean - if logical op
	 */
	public Object visit(UnaryOpExpr expr, Object o) {
		String expectedParamType = null;

		// get expression's parameter type
		types.Type paramType = (types.Type) expr.getOperand().accept(this, o);

		// Mathematical unary operation
		if(expr.hasMathematicalOp()){
			// parameter type must be int
			expectedParamType = "int";
		}
		// Logical unary operation
		else{
			// parameter type must be boolean
			expectedParamType = "boolean";
		}
		// validate parameter is of the correct type
		try {
			if(!paramType.extendsType(types.TypeTable.getTypeTable().getType(expectedParamType))){
				SemanticError error = new SemanticError("Invalid unary operation, operand not of type "+expectedParamType, 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
		} catch (SemanticError e) {
			// if type-table does not contain "int" or "boolean"
			e.printStackTrace();
		}
		return paramType;
	}


	/**
	 * Type checks for Binary Operation expression:
	 * arithmetic operators, operands must be integers
	 * relational comparison operators, operands must be integers
	 * equality comparisons operators, operands must have the same type
	 * conditional operators, operands must be booleans
	 * 
	 * @return int - if mathematical op
	 * @return boolean - if logical op
	 */
	public Object visit(BinaryOpExpr expr, Object o) {

		// get expression's parameters types
		types.Type leftParamType = (types.Type) expr.getLeftOperand().accept(this, o);
		types.Type rightParamType = (types.Type) expr.getRightOperand().accept(this, o);

		BinOperator operator = expr.getOp();

		// Binary operation of string concatenation
		boolean leftParamIsStr = (leftParamType instanceof TypeString);
		boolean rightParamIsStr = (rightParamType instanceof TypeString);
		// if both concatenation operands are strings
		if(expr.getOp() == BinOperator.PLUS && leftParamIsStr && rightParamIsStr){
			return leftParamType; // return string type
		}

		// equality comparisons - operands must have the same type
		if(expr.getOp() == BinOperator.EQUAL || expr.getOp() == BinOperator.NEQUAL){
			boolean validExprTypes = false;

			// check if left operand type is subtype of right
			validExprTypes = validExprTypes || leftParamType.extendsType(rightParamType);
			// check if right operand type is subtype of left
			validExprTypes = validExprTypes || rightParamType.extendsType(leftParamType);

			// if operand types are unrelated, expression is invalid
			if(!validExprTypes){
				SemanticError error = new SemanticError("Invalid binary operation, operands are of different types", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
			// if successful return boolean type
			return new TypeBoolean();
		}

		// conditional operators - operands must be booleans
		else if(expr.getOp() == BinOperator.LAND || expr.getOp() == BinOperator.LOR){
			// check if left operand of type boolean
			if(!leftParamType.extendsType(new TypeBoolean())){
				SemanticError error = new SemanticError("Invalid binary operation, left operand not of type boolean", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
			// check if right operand of type int
			if(!rightParamType.extendsType(new TypeBoolean())){
				SemanticError error = new SemanticError("Invalid binary operation, right operand not of type boolean", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}
		}

		// arithmetic or relational operators - operands must be integers
		else if(expr.getOp() == BinOperator.PLUS || 
				expr.getOp() == BinOperator.MINUS ||
				expr.getOp() == BinOperator.MULTIPLY ||
				expr.getOp() == BinOperator.DIVIDE ||
				expr.getOp() == BinOperator.MOD){

			// check if left operand of type int
			if(!leftParamType.extendsType(new TypeInt())){
				SemanticError error = new SemanticError("Invalid binary operation, left operand not of type int", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}

			// check if right operand of type int
			if(!rightParamType.extendsType(new TypeInt())){
				SemanticError error = new SemanticError("Invalid binary operation, right operand not of type int", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}

			// all checks are valid return the higher type of the operands
			if(leftParamType.extendsType(rightParamType)){
				return rightParamType;
			}
		}

		else if(expr.getOp() == BinOperator.LT ||
				expr.getOp() == BinOperator.GT ||
				expr.getOp() == BinOperator.LTE ||
				expr.getOp() == BinOperator.GTE	){

			// check if left operand of type int
			if(!leftParamType.extendsType(new TypeInt())){
				SemanticError error = new SemanticError("Invalid binary operation, left operand not of type int", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}

			// check if right operand of type int
			if(!rightParamType.extendsType(new TypeInt())){
				SemanticError error = new SemanticError("Invalid binary operation, right operand not of type int", 
						expr.getLineNum());
				System.out.println(error);
				System.exit(1);
			}

			// all checks are valid return type boolean
			if(leftParamType.extendsType(rightParamType)){
				return new TypeBoolean();
			}
		}


		return leftParamType;
	}


}