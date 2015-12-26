package lir;

import semantic.SemanticError;
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
import slp.NewArray;
import slp.NewObject;
import slp.PrimitiveType;
import slp.Program;
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
import symbolTableHandler.MethodSymbolTable;
import symbolTableHandler.VariableSymbol;


/**
 * Visitor class meant to traverse program and set the labels for 
 * all program var-symbols: local-variables, fields and method parameters
 * 
 * Variable labels are of following format:
 *		v_ID_symname - for local variable
 *		p_ID_symname - for method parameters
 *		f_ID_symname - for class fields
 */
public class VarLabelVisitor implements Visitor{
	private int uniqueID = 0; // unique id for var labels

	public void visit(Program program) {
		//visit all classes apart from Library
		for(ClassDecl c: program.getClasses()){
			if (!c.getName().equals("Library"))
				c.accept(this);
		}
	}

	public void visit(ClassDecl class_decl) {
		//we continue visit the methods		
		for(FieldOrMethod fom : class_decl.getFieldsOrMethods()){
			fom.accept(this);
		}
	}

	public void visit(ClassMethod method) {
		// visit method type, formals and statements
		method.getType().accept(this);
		for(Formal f : method.getFormals()){
			f.accept(this);
		}
		for(Stmt s : method.getStatementList().getStatements()){
			s.accept(this);
		}
	}

	public void visit(StaticMethod method) {
		// visit method type, formals and statements
		method.getType().accept(this);
		for(Formal f : method.getFormals()){
			f.accept(this);
		}
		for(Stmt s : method.getStatementList().getStatements()){
			s.accept(this);
		}
	}

	public void visit(PrimitiveType primitiveType) {}

	public void visit(ClassType classType) {}

	/**
	 * Sets Field variable symbol's label
	 */
	public void visit(Field field) {
		// get the field symbol
		ClassSymbolTable cst = (ClassSymbolTable) field.getScope();
		VariableSymbol varSym = cst.getFieldSymbol(field.getName());
		// set field label accordingly
		varSym.setLabel("f_"+ (uniqueID++) + field.getName());			
		field.getType().accept(this);
	}

	/**
	 * Sets method Parameter variable symbol's label
	 */
	public void visit(Formal formal) {
		try {
			// get the formal symbol
			MethodSymbolTable mst = (MethodSymbolTable) formal.getScope();
			VariableSymbol varSym = mst.getVarParamSymbol(formal.getName());
			// set parameter label accordingly
			varSym.setLabel("p_"+ (uniqueID++) + formal.getName());	
		} catch (SemanticError e) {
			// in case method symbol table does not contain parameter
			// should never get here, already checked in Semantic part
			e.printStackTrace();
		}	
	}

	public void visit(StmtList stmts) {
		for (Stmt s : stmts.getStatements()) {
			s.accept(this);
		}
	}

	public void visit(Stmt stmt) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}

	public void visit(AssignStmt stmt) {
		stmt.getLocation().accept(this); // iterate assignment variable
		stmt.getRhs().accept(this); // iterate assignment value
	}

	public void visit(CallStmt stmt) {
		stmt.getCall().accept(this);
	}

	public void visit(ReturnStmt stmt) {
		if(stmt.hasExpr()){
			stmt.getExpr().accept(this);
		}
	}

	public void visit(IfStmt stmt) {
		stmt.getCond().accept(this);;
		stmt.getBody().accept(this);
		if(stmt.hasElse()){
			stmt.getElseStmt().accept(this);
		}
	}

	public void visit(WhileStmt stmt) {
		stmt.getCond().accept(this);
		stmt.getBody().accept(this);
	}

	public void visit(BreakStmt stmt) {}

	public void visit(ContinueStmt stmt) {}

	public void visit(BlockStmt stmt) {
		for(Stmt s : stmt.getStatementList().getStatements()){
			s.accept(this);
		}
	}

	/**
	 * Sets local Variable symbol's label
	 */
	public void visit(IDStmt stmt) {
		// get the local variable symbol from its method symbol table
		BlockSymbolTable bst = (BlockSymbolTable) stmt.getScope();
		VariableSymbol varSym;
		try {
			varSym = bst.getVarSymbolLocal(stmt.getName());
			// set field label accordingly
			varSym.setLabel("v_"+ (uniqueID++) + stmt.getName());
		} catch (SemanticError e) {
			// in case block symbol table does not contain parameter
			// should never get here, already checked in Semantic part
			e.printStackTrace();
		}

		// visit type recursively
		stmt.getType().accept(this);
		// visit initial value recursively
		if(stmt.hasValue()){
			stmt.getValue().accept(this);
		}
	}

	public void visit(VarLocation var_loc) {
		if(var_loc.hasExternalLocation()){
			var_loc.getLocation().accept(this);
		}
	}

	public void visit(ArrLocation arr_loc) {
		arr_loc.getArrLocation().accept(this);
		arr_loc.getIndex().accept(this);
	}

	public void visit(StaticCall static_call) {
		for(Expr e: static_call.getArguments()){
			e.accept(this);
		}
	}

	public void visit(VirtualCall virtual_call) {
		if (virtual_call.getObjectReference() != null){
			virtual_call.getObjectReference().accept(this);
		}
		for(Expr e: virtual_call.getArguments()){
			e.accept(this);
		}
	}

	public void visit(Literal literal) {}

	public void visit(This t) {}

	public void visit(NewObject new_obj) {}

	public void visit(NewArray new_arr) {
		new_arr.getType().accept(this);
		new_arr.getArrayLength().accept(this);
	}

	public void visit(Length length) {
		length.getExpression().accept(this);
	}

	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}

	public void visit(BlockExpr expr) {
		expr.getExpression().accept(this);
	}

	public void visit(UnaryOpExpr expr) {
		expr.getOperand().accept(this);
	}

	public void visit(BinaryOpExpr expr) {
		expr.getLeftOperand().accept(this);
		expr.getRightOperand().accept(this);
	}

}
