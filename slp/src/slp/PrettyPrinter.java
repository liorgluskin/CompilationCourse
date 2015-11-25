package slp;

/** Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected int depth = 0;
	protected final ASTNode root;
	
	private void indent(ASTNode node) {
		System.out.print("\n");
		for (int i = 0; i < depth; i++)
			System.out.print(" ");
		if (node != null)
			System.out.print(node.getLine() + ": ");
	}
	
	/** Constructs a printin visitor from an AST.
	 * 
	 * @param root The root of the AST.
	 */
	public PrettyPrinter(ASTNode root) {
		this.root = root;
	}

	/** Prints the AST with the given root.
	 */
	public void print() {
		root.accept(this);
	}
	
	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}	
	
	public void visit(VarExpr expr) {
		System.out.print(expr.name);
	}
	
	public void visit(UnaryOpExpr expr) {
		System.out.print(expr.op);
		expr.operand.accept(this);
	}
	
	public void visit(BinaryOpExpr expr) {
		expr.lhs.accept(this);
		System.out.print(expr.op);
		expr.rhs.accept(this);
	}
//////////////////
	@Override
	public void visit(VarLocation var_loc) {
		indent(var_loc);
		System.out.print("Reference to variable: " + var_loc.getName());
		if (var_loc.getLocation() != null){
			System.out.print(", in external scope");
			++depth;
			var_loc.getLocation().accept(this);
			--depth;
		}
	}

	@Override
	public void visit(ArrLocation arr_loc) {
		indent(arr_loc);
		System.out.print("Reference to array");
		depth+=2;
		arr_loc.getArrLocation().accept(this);
		arr_loc.getIndex().accept(this);
		depth-=2;
	}

	@Override
	public void visit(StaticCall static_call) {
		indent(static_call);
		System.out.print("Call to static method: " +static_call.getMethodName()+ ", in class "+ static_call.getClassName());
		--depth;
		
		++depth;
	}

	@Override
	public void visit(VirtualCall virtual_call) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Literal literal) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(This t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewObject new_obj) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(NewArray new_arr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(Length length) {
		// TODO Auto-generated method stub
		
	}

}