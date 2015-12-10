package semantic;


import slp.*;
import symbolTableHandler.*;
import types.TypeTable;

public class SemanticEvaluator implements Visitor{
	private Boolean hasMain = false;
	private GlobalSymbolTable global;

	
	public SemanticEvaluator(){
		TypeTable.initTypeTable();
	}
	
	public GlobalSymbolTable getSymbolTable(Program program){
		program.accept(this);
		return global;
	}


	@Override
	public Object visit(Program program){
		global = new GlobalSymbolTable();
		// add classes to global and updates the type table
		for (ClassDecl c: program.getClasses()){
			try{
				global.addClass(c);
			} catch (SemanticError se){
				// class is previously defined or super class is not defined
				se.setLineNum(c.getLineNum());
				System.err.println(se);
				System.exit(-1);
			}
		}
		
		// recursive class symbol tables build
		for (ClassDecl c: program.getClasses()){
			// set enclosing scope
			c.setEnclosingScope(global);
			c.accept(this);
		}
		
		// check if has main method
		if (!hasMain){
			System.err.println(new SemanticError("Program has no main method",0));
			System.exit(-1);
		}
	}


	@Override
	public Object visit(ClassDecl class_decl) {
		
		//create symbol table for class 
		ClassSymbolTable cst;		
		if (class_decl.getSuperClassName() != null) {
			ClassSymbolTable  scst = global.getClassSymbolTable(class_decl.getSuperClassName());
			cst = new ClassSymbolTable( scst, global.getClass(class_decl.getName()) );
			scst.addClassSymbolTable(cst);
		} else { // no superclass
			cst = new ClassSymbolTable(global,global.getClass(class_decl.getName()));
			global.addClassSymbolTable(cst);;
		}
		
		
		
		//create symbol table for methods 
		for( FieldOrMethod fom: class_decl.getFieldsOrMethods()){
			fom.setEnclosingScope(cst);
			
		}
		
		
		
		
		//fields and methods check
	}


	@Override
	public Object visit(ClassMethod method) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(StaticMethod method) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(PrimitiveType primitiveType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(ClassType classType) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Field field) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Formal formal) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(StmtList stmts) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Stmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(AssignStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(CallStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(ReturnStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(IfStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(WhileStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(BreakStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(ContinueStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(BlockStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(IDStmt stmt) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(VarLocation var_loc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(ArrLocation arr_loc) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(StaticCall static_call) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(VirtualCall virtual_call) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Literal literal) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(This t) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(NewObject new_obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(NewArray new_arr) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Length length) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(Expr expr) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(BlockExpr expr) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(UnaryOpExpr expr) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public Object visit(BinaryOpExpr expr) {
		// TODO Auto-generated method stub
		
	}
}