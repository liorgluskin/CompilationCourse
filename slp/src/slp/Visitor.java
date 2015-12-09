package slp;

/** An interface for AST visitors.
 */
public interface Visitor {

	public void visit(Program program);
	public void visit(ClassDecl class_decl);
	public void visit(ClassMethod method);
	public void visit(StaticMethod method);
	public void visit(PrimitiveType primitiveType);
	public void visit(ClassType classType);
	public void visit(Field field);
	public void visit(Formal formal);
	
	public void visit(StmtList stmts);
	public void visit(Stmt stmt);
	public void visit(AssignStmt stmt);
	public void visit(CallStmt stmt);
	public void visit(ReturnStmt stmt);
	public void visit(IfStmt stmt);
	public void visit(WhileStmt stmt);
	public void visit(BreakStmt stmt);
	public void visit(ContinueStmt stmt);
	public void visit(BlockStmt stmt);
	public void visit(IDStmt stmt);

<<<<<<< HEAD
=======

>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	public void visit(VarLocation var_loc);
	public void visit(ArrLocation arr_loc);
	public void visit(StaticCall static_call);
	public void visit(VirtualCall virtual_call);
	public void visit(Literal literal);
	public void visit(This t);
	public void visit(NewObject new_obj);
	public void visit(NewArray new_arr);
	public void visit(Length length);
	
	public void visit(Expr expr);
<<<<<<< HEAD
	public void visit(BlockExpr expr);
=======
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	public void visit(UnaryOpExpr expr);
	public void visit(BinaryOpExpr expr);
}