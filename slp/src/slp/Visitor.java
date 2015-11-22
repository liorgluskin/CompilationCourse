package slp;

/** An interface for AST visitors.
 */
public interface Visitor {
	public void visit(Program program);
	public void visit(ClassDecl class_decl);
	public void visit(Field field);
	public void visit(Formal formal);
	public void visit(VarLocation var_loc);
	public void visit(ArrLocation arr_loc);
	public void visit(StaticCall static_call);
	public void visit(VirtualCall virtual_call);
	public void visit(Literal literal);
	public void visit(This t);
	public void visit(NewObject new_obj);
	public void visit(NewArray new_arr);
	public void visit(Length length);
	public void visit(StmtList stmts);
	public void visit(Stmt stmt);
	public void visit(PrintStmt stmt);
	public void visit(AssignStmt stmt);
	public void visit(Expr expr);
	public void visit(ReadIExpr expr);
	public void visit(VarExpr expr);
	public void visit(NumberExpr expr);
	public void visit(UnaryOpExpr expr);
	public void visit(BinaryOpExpr expr);
}