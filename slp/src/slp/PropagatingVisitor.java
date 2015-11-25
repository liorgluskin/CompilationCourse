package slp;

/** An interface for a propagating AST visitor.
 * The visitor passes down objects of type <code>DownType</code>
 * and propagates up objects of type <code>UpType</code>.
 */
public interface PropagatingVisitor<DownType,UpType> {
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
	public UpType visit(VarExpr expr, DownType d);
	public UpType visit(UnaryOpExpr expr, DownType d);
	public UpType visit(BinaryOpExpr expr, DownType d);
}