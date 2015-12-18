package slp;

import semantic.SemanticError;

/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {

	
	public UpType visit(Program program, DownType d);
	public UpType visit(ClassDecl class_decl, DownType d);
	public UpType visit(Field field, DownType d);
	public UpType visit(StaticMethod staticMethod, DownType d);
	public UpType visit(ClassMethod classMethod,DownType d);
	public UpType visit(PrimitiveType primitiveType,DownType d);
	public UpType visit(ClassType classType, DownType d);
	public UpType visit(Formal formal, DownType d);
	
	public UpType visit(AssignStmt stmt, DownType d);
	public UpType visit(CallStmt callstmt, DownType d);
	public UpType visit(ReturnStmt returnStmt, DownType d);
	public UpType visit(IfStmt ifStmt, DownType d);
	public UpType visit(WhileStmt whileStmt, DownType d);
	public UpType visit(BreakStmt breakStmt, DownType d);
	public UpType visit(ContinueStmt contineStmtm, DownType d);
	public UpType visit(IDStmt idStmt, DownType d);
	public UpType visit(BlockStmt blockStmt, DownType d);
	public UpType visit(StmtList stmtLst , DownType d);
	
		
	public UpType visit(VarLocation var_loc, DownType d);
	public UpType visit(ArrLocation arr_loc, DownType d);
	public UpType visit(StaticCall static_call, DownType d);
	public UpType visit(VirtualCall virtual_call, DownType d);
	public UpType visit(This t, DownType d);
	public UpType visit(NewObject new_obj, DownType d);
	public UpType visit(NewArray new_arr, DownType d);
	public UpType visit(Length length, DownType d);
	public UpType visit(Literal literal, DownType d);


	public UpType visit(Expr expr, DownType d);
	public UpType visit(BlockExpr expr, DownType d);
	public UpType visit(UnaryOpExpr expr, DownType d);
	public UpType visit(BinaryOpExpr expr, DownType d);
}