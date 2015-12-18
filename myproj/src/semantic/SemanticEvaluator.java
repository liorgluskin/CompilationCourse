package semantic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import slp.*;
import symbolTableHandler.*;
import types.*;

public class SemanticEvaluator implements Visitor{
	private Boolean hasMain = false;
	private Boolean isLibraryClassVisiting = false;
	private types.TypeTable programTypeTable;
	private GlobalSymbolTable globaSymlTable = null;
	
	/**
	 * Construct new Semantic Evaluator
	 * initialize the program type table
	 */
	public SemanticEvaluator(){	
		this.programTypeTable = TypeTable.getTypeTable();
	}
	
	public GlobalSymbolTable getSymbolTable(Program program){
		program.accept(this);
		return globaSymlTable;
	}
	
	/**
	 * Checks whether the given method is the main method.
	 */
	private static boolean isMain(Method method){
		
		//main must be static
		if (!(method instanceof StaticMethod)) return false;
		if (method.getName().compareTo("main") != 0) return false;
		
		//return type must be void
		if (!(method.getType() instanceof PrimitiveType) ||
				method.getType().getName().compareTo("void") != 0) return false;
		
		//no parameters or too many
		if (method.getFormals().size() != 1) return false; 
		
		//parameter is not of type string[]
		slp.Type param_type = method.getFormals().get(0).getType();
		if (param_type.getFullName().compareTo("string[]") != 0) return false;
		
		//paramer name has to be 'args'
		if(!method.getFormals().get(0).getName().equals("args")) return false;
		
		return true;
	}
	
	/**
	 * Should be done before starting visit.
	 * Adds Library class to the program.
	 */
	private void addStaticLibraryClass(Program program){
		//creating all methods
		List<FieldOrMethod> methodsLst = new ArrayList<FieldOrMethod>();
		
		//println added
		List<Formal> printlnFormals = new ArrayList<Formal>(); 
		printlnFormals.add(new Formal(new PrimitiveType(-1, DataTypes.STRING), "s"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.VOID),"println",printlnFormals,new StmtList()));
		
		//print added
		List<Formal> printFormals = new ArrayList<Formal>(); 
		printFormals.add(new Formal(new PrimitiveType(-1, DataTypes.STRING), "s"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.VOID),"print",printFormals,new StmtList()));
		
		//printi added
		List<Formal> printiFormals = new ArrayList<Formal>(); 
		printiFormals.add(new Formal(new PrimitiveType(-1, DataTypes.INT), "i"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.VOID),"printi",printiFormals,new StmtList()));
		
		//readi
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.INT),"readi",new ArrayList<Formal>(),new StmtList()));
		
		//readln
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.STRING),"readln",new ArrayList<Formal>(),new StmtList()));
		
		//eof
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.BOOLEAN),"eof",new ArrayList<Formal>(),new StmtList()));
		
		//stoi
		List<Formal> stoi = new ArrayList<Formal>(); 
		stoi.add(new Formal(new PrimitiveType(-1, DataTypes.STRING), "s"));
		stoi.add(new Formal(new PrimitiveType(-1, DataTypes.INT), "n"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.INT),"stoi",stoi,new StmtList()));
		
		//itos
		List<Formal> itos = new ArrayList<Formal>(); 
		itos.add(new Formal(new PrimitiveType(-1, DataTypes.INT), "i"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.STRING),"itos",itos,new StmtList()));
		
		
		//stoa
		List<Formal> stoa = new ArrayList<Formal>(); 
		itos.add(new Formal(new PrimitiveType(-1, DataTypes.STRING), "s"));
		PrimitiveType t = new PrimitiveType(-1, DataTypes.INT);
		t.incrementDimension();
		methodsLst.add(new StaticMethod(t,"stoa",stoa,new StmtList()));
		
		//atos
		List<Formal> atos = new ArrayList<Formal>();
		PrimitiveType t_atos = new PrimitiveType(-1, DataTypes.INT);
		t_atos.incrementDimension();
		atos.add(new Formal(t_atos, "a"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.STRING),"atos",atos,new StmtList()));
		
		//random
		List<Formal> random = new ArrayList<Formal>();
		random.add(new Formal(new PrimitiveType(-1, DataTypes.INT), "i"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.INT),"random",random,new StmtList()));
		
		//random
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.VOID),"time",new ArrayList<Formal>(),new StmtList()));
		
		//exit
		List<Formal> exit = new ArrayList<Formal>();
		exit.add(new Formal(new PrimitiveType(-1, DataTypes.INT), "i"));
		methodsLst.add(new StaticMethod(new PrimitiveType(-1, DataTypes.INT),"exit",exit,new StmtList()));
		
		//declaring class
		ClassDecl libraryClass = new ClassDecl(-2, "Library",methodsLst);
		libraryClass.setScope(globaSymlTable);
		List<ClassDecl> classes = program.getClasses();
		Collections.reverse(classes);
		classes.add(libraryClass);
		Collections.reverse(classes);
	}

	public void visit(Program program){
		globaSymlTable = new GlobalSymbolTable();
		addStaticLibraryClass(program);
		int i = 0; //to get the first class which is Library
		for (ClassDecl c: program.getClasses()){
			
			if(c.getName().equals("Library") && i != 0){
				System.out.println(new SemanticError("Invalid class declaration, can't declare 'Library' class", c.getLineNum()));
				System.exit(-1);
			}
			
			try{
				globaSymlTable.addClass(c);
			} 
			catch (SemanticError se){
				System.out.println(new SemanticError(se.getMessage(),c.getLineNum()));
				System.exit(-1);
			}
			i++;
		}
		
		for (ClassDecl c: program.getClasses()){
			c.setScope(globaSymlTable);
			c.accept(this);
		}

		if (!hasMain){
			System.out.println("Symantic error: Program has no main method");
			System.exit(-1);
		}
	}

	public void visit(ClassDecl class_decl) {
		if(class_decl.getName() == "Library"){
			isLibraryClassVisiting = true;
		}
		

		ClassSymbolTable cst;		
		if (class_decl.getSuperClassName() != null) {
			//there are superClass
			if(class_decl.getSuperClassName().equals("Library")){
				System.out.println(new SemanticError("Invalid class declaretion, can't extend 'Library' class", class_decl.getLineNum()));
				System.exit(-1);
			}

			
			ClassSymbolTable scst = globaSymlTable.getClassSymbolTable(class_decl.getSuperClassName());
			if(scst == null){
				//double check should be here
				System.out.println(new SemanticError("Invalid class declaration, superclass was not previously defined '"+
			class_decl.getSuperClassName()+"'",
						class_decl.getLineNum()));
			}
			cst = new ClassSymbolTable( scst, globaSymlTable.getClass(class_decl.getName()) );
			scst.addClassSymbolTable(cst);
			
		} 
		else { // no superclass
			cst = new ClassSymbolTable(globaSymlTable,globaSymlTable.getClass(class_decl.getName()));
			globaSymlTable.addClassSymbolTable(cst);
		}

		//create symbol table for methods 
		for( FieldOrMethod fom: class_decl.getFieldsOrMethods()){
			fom.setScope(cst);
			if(fom instanceof Field){
				Field f = (Field) fom;
				
				//check if type is already defined
				if(cst.getFieldSymbol(f.getName()) != null){
					System.out.println(new SemanticError(f.getName() +" field is already defined.",f.getLineNum()));
					System.exit(-1);
				}
				
				//check if there a method with the same name
				if(cst.getMethodSymbol(f.getName()) != null){
					System.out.println(new SemanticError(f.getName() +" field is already defined as method", f.getLineNum()));
					System.exit(-1);
				}
				
				try{
					cst.addFieldSymbol(f.getName(), f.getType().getFullName());						
				}
				catch(SemanticError ex){
					//Class type Error -> if Field class type is not defined
					System.out.println(new SemanticError(ex.getMessage(),f.getLineNum()));
					System.exit(-1);
				}									
			} 
			else{
				//method instatnce
				//check if type is already defined
				Method m = (Method) fom;
				
				//check if there isn't field with the same name declared before.
				if(cst.getFieldSymbol(m.getName()) != null){
					System.out.println(new SemanticError(m.getName() +" method is already defined as field.",m.getLineNum()));
					System.exit(-1);
				}
				if(cst.getCurrentMethodSymbol(m.getName()) != null){
					System.out.println(new SemanticError(m.getName() +" method is already defined in class.",m.getLineNum()));
					System.exit(-1);
				}
				
				//check if method is main
				if(isMain(m)){
					if(hasMain){
						System.out.println(new SemanticError(m.getName() + " is already defined.", m.getLineNum()));
						System.exit(-1);						
					}
					else
						hasMain = true;
				} else if(m.getName().equals("main") ){
					System.out.println(new SemanticError(m.getName() + " wrong definition of main method, must be: 'static void main(string[] args).", m.getLineNum()));
					System.exit(-1);
				}
				
				
				//check override or overload
				MethodSymbol upperMethodSymbol = cst.getMethodSymbol(m.getName());
				MethodSymbol currentMethodSymbol = null;
				try{
					currentMethodSymbol = new MethodSymbol(m);
				}
				catch(SemanticError se){
					//this checked within type evaluator
					//should never get here at this point
					System.out.println(new SemanticError("'"+m.getName() + "' method type is undefined",m.getLineNum()));
					System.exit(-1);
				}
				
				
				
				//a method with the same name exists
				//now we want to check if it is an override 
				if(upperMethodSymbol != null){
					if(!currentMethodSymbol.getType().extendsType(upperMethodSymbol.getType())){
						//method is not override- its overload and its not legal
						System.out.println(new SemanticError(m.getName() + " method is already defined.",m.getLineNum()));
						System.exit(-1);
					}
				}
				
				//method is legal here
				cst.addMethodSymbol(currentMethodSymbol);
				
			}
			//method or field passed semantic check so we can continue here
			fom.accept(this);			
		}
		
		//reset LibraryClassVisit
		isLibraryClassVisiting = false;
	}

	/**
	 * Creates a symbol table for a method scope (all types).
	 * 
	 */
	private void methodVisit(Method method){
		//create symbol table for method scope
		
		MethodSymbolTable method_scope = new MethodSymbolTable(method.getName(),(ClassSymbolTable)method.getScope());
		((ClassSymbolTable)method.getScope()).addMethodSymbolTable(method_scope);

		method.getType().setScope(method_scope);
		if(!isLibraryClassVisiting){
			//method's parameters (formals)
			for (Formal f: method.getFormals()){
				f.setScope(method_scope);
				try{
					method_scope.getVarParamSymbol(f.getName());
					SemanticError error = new SemanticError("parameter is previously defined in method " + method.getName(),method.getLineNum());
					System.out.println(error);
					System.exit(-1);
				} catch (SemanticError not_defined){
					try{
						method_scope.addParamSymbol(f.getName(), f.getType().getFullName());
					} 
					catch (SemanticError e){
						//error adding the parameter symbol
						e.setLineNum(method.getLineNum());
						System.out.println(e);
						System.exit(-1);
					}
				}
				f.accept(this);
			}
			
			//method statements
			method.getStatementList().setScope(method_scope);
			method.getStatementList().accept(this);
			
			//return variable
			try{
				method_scope.setReturnVarSymbol(method.getType().getFullName());
			} 
			//error setting the return variable
			catch (SemanticError error){
				error.setLineNum(method.getLineNum());
				System.out.println(error);
				System.exit(-1);
			}
		}
		
		// if method is not void make sure it has a return statement
		if(!method.getType().getFullName().equals("void") && !isLibraryClassVisiting){
			boolean hasReturnStatement = false;
			for(Stmt s : method.getStatementList().getStatements()){
				if(s instanceof ReturnStmt){
					hasReturnStatement = true;
				}
			}
			// check if seen return stmt
			if(!hasReturnStatement){
				SemanticError error = new SemanticError("Invalid method, method with type '"
						+method.getType().getFullName()+"' must have 'return' statement", method.getLineNum());
				System.out.println(error);
				System.exit(-1);
			}
		}
	}
	
	public void visit(ClassMethod method) {
		methodVisit(method);	
	}

	public void visit(StaticMethod method) {
		methodVisit(method);
	}

	/**
	 * PrimitiveType visitor - does nothing.
	 */
	public void visit(PrimitiveType primitiveType) {}

	/**
	 * ClassType visitor - does nothing.
	 */
	public void visit(ClassType classType) {}

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

	/**
	 * Visits each statement in the statement list
	 *
	 */
	public void visit(StmtList stmts) {
		for(Stmt s: stmts.getStatements()){
			s.setScope(stmts.getScope());
			s.accept(this);
		}
	}

	/**
	 * Abstract class Stmt, will never get here.
	 * @throws RuntimeException
	 */
	public void visit(Stmt stmt) {
		throw new  RuntimeException("visiting Stmt");
	}

	/**
	 * Sets the scope of the location and expression of an assignment.
	 *
	 */
	public void visit(AssignStmt stmt) {
		stmt.getLocation().setScope(stmt.getScope());
		stmt.getLocation().accept(this);
		
		stmt.getRhs().setScope(stmt.getScope());
		stmt.getRhs().accept(this);
	}

	/**
	 * Sets the scope of a statement call.
	 *
	 */
	public void visit(CallStmt stmt) {
		stmt.getCall().setScope(stmt.getScope());
		stmt.getCall().accept(this);
	}

	/**
	 * Sets the scope of the return statement.
	 *
	 */
	public void visit(ReturnStmt stmt) {
		if (stmt.hasExpr()){
			stmt.getExpr().setScope(stmt.getScope());
			stmt.getExpr().accept(this);
		}
	}

	/**
	 * Sets the scope of the if statement.
	 *
	 */
	public void visit(IfStmt stmt) {
		stmt.getCond().setScope(stmt.getScope());
		stmt.getCond().accept(this);
		
		Stmt if_stmt = stmt.getBody();
		if (if_stmt instanceof IDStmt){
			BlockSymbolTable block_st = new BlockSymbolTable(stmt.getScope());
			((BlockSymbolTable)stmt.getScope()).addStack(block_st);
			if_stmt.setScope(block_st);	
		} 
		else if_stmt.setScope(stmt.getScope());
		if_stmt.accept(this);
		
		if (stmt.hasElse()){
			Stmt else_stmt = stmt.getElseStmt();
			if (else_stmt instanceof IDStmt){
				BlockSymbolTable block_st = new BlockSymbolTable(stmt.getScope());
				((BlockSymbolTable)stmt.getScope()).addStack(block_st);
				else_stmt.setScope(block_st);	
			} 
			else else_stmt.setScope(stmt.getScope());
			else_stmt.accept(this);
		}	
	}

	/**
	 * Sets the scope of the while statement.
	 *
	 */
	public void visit(WhileStmt stmt) {
		stmt.getCond().setScope(stmt.getScope());
		stmt.getCond().accept(this);
		
		Stmt body = stmt.getBody();
		if (body instanceof IDStmt){
			BlockSymbolTable block_st = new BlockSymbolTable(stmt.getScope());
			((BlockSymbolTable)stmt.getScope()).addStack(block_st);
			body.setScope(block_st);	
		} 
		else body.setScope(stmt.getScope());
		body.accept(this); 
	}

	/**
	 * Break statement visitor - does nothing.
	 */
	public void visit(BreakStmt stmt) {}

	/**
	 * Continue statement visitor - does nothing.
	 */
	public void visit(ContinueStmt stmt) {}

	/**
	 * Visits all components of the current block statement
	 */
	public void visit(BlockStmt stmt) {
		BlockSymbolTable block_st = new BlockSymbolTable(stmt.getScope());
		
		stmt.getStatementList().setScope(block_st);
		stmt.getStatementList().accept(this);
		
		BlockSymbolTable block_parent = (BlockSymbolTable) stmt.getScope();
		block_parent.addStack(block_st);
	}

	/**
	 * Sets the scope of ID statement.
	 *
	 */
	public void visit(IDStmt stmt) {
		stmt.getType().setScope(stmt.getScope());
		stmt.getType().accept(this);
		
		BlockSymbolTable block_st = (BlockSymbolTable)stmt.getScope();
		try{
			block_st.getVarSymbolLocal(stmt.getName());
			SemanticError error = new SemanticError("variable '"+ stmt.getName() +"' is previously defined",stmt.getLineNum());
			System.out.println(error);
			System.exit(-1);
		} 
		catch (SemanticError e1){
			try{
				block_st.addVarSymbol(stmt.getName(), stmt.getType().getFullName());
			} 
			catch (SemanticError e2){
				e2.setLineNum(stmt.getLineNum());
				System.out.println(e2);
				System.exit(-1);
			}
		}
		
		if (stmt.hasValue()){
			stmt.getValue().setScope(stmt.getScope());
			stmt.getValue().accept(this);
		}
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
				System.out.println(e);
				System.exit(-1);
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

	public void visit(StaticCall static_call) {
		for (Expr e: static_call.getArguments()){
			e.setScope(static_call.getScope());
			e.accept(this);
		}
	}

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
	public void visit(Literal literal) {}

	/**
	 * This visitor - does nothing.
	 */
	public void visit(This t) {}
	
	/**
	 * New class visitor - does nothing.
	 */
	public void visit(NewObject new_obj) {		
		if(new_obj.getClassName() == "Library"){
			System.out.println(new SemanticError("Can't create an instance of 'Library' class",new_obj.getLineNum()));
			System.exit(-1);
		}
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

	/**
	 * Abstract class Expr, will never get here.
	 * @throw RuntimeException
	 */
	public void visit(Expr expr) {
		throw new  RuntimeException("visiting Expr");
	}

	/**
	 * Visits all components of the current block expression.
	 */
	public void visit(BlockExpr expr) {
		expr.getExpression().setScope(expr.getScope());
		expr.getExpression().accept(this);
	}
	
	/**
	 * Sets the scope of the expression's operand, and visits its AST nodes.
	 *
	 */
	public void visit(UnaryOpExpr expr) {
		expr.getOperand().setScope(expr.getScope());
		expr.getOperand().accept(this);
	}
	
	/**
	 * Sets the scope of both of the expression's operands, and visits their AST nodes.
	 *
	 */
	public void visit(BinaryOpExpr expr) {
		expr.getLeftOperand().setScope(expr.getScope());
		expr.getLeftOperand().accept(this);
		
		expr.getRightOperand().setScope(expr.getScope());
		expr.getRightOperand().accept(this);
	}

}