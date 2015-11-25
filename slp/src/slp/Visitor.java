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

	
	public void visit(Expr expr);
	public void visit(ReadIExpr expr);
	public void visit(VarExpr expr);
	public void visit(NumberExpr expr);
	public void visit(UnaryOpExpr expr);
	public void visit(BinaryOpExpr expr);
}