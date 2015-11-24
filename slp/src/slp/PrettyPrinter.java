package slp;

/** Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected int depth = 0;
	protected final ASTNode root;
	
	private void indent(StringBuffer str_output, ASTNode node) {
		str_output.append("\n");
		for (int i = 0; i < depth; i++)
			str_output.append(" ");
		if (node != null)
			str_output.append(node.getLine() + ": ");
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
	
	public void visit(ReadIExpr expr) {
		System.out.print("readi()");
	}	
	
	public void visit(VarExpr expr) {
		System.out.print(expr.name);
	}
	
	public void visit(NumberExpr expr) {
		System.out.print(expr.value);
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

	@Override
	public void visit(VarLocation var_loc) {
		StringBuffer output_str = new StringBuffer();

		indent(output_str, var_loc);
		output_str.append("Reference to variable: " + var_loc.getName());
		if (var_loc.getLocation() != null){
			output_str.append(", in external scope");
			++depth;
			output_str.append(var_loc.getLocation().accept(this));
			--depth;
		}
		return output_str.toString();
	}

	@Override
	public void visit(ArrLocation arr_loc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visit(StaticCall static_call) {
		// TODO Auto-generated method stub
		
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