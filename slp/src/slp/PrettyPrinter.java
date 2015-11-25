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
			System.out.print(node.getLineNum() + ": ");
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
////////////////	
	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
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
		++depth;
		arr_loc.getArrLocation().accept(this);
		arr_loc.getIndex().accept(this);
		--depth;
	}

	@Override
	public void visit(StaticCall static_call) {
		indent(static_call);
		System.out.print("Call to static method: " +static_call.getMethodName()+ ", in class "+ static_call.getClassName());
		++depth;
		for(Expr e: static_call.getArguments())
			e.accept(this);
		--depth;
	}

	@Override
	public void visit(VirtualCall virtual_call) {
		indent(virtual_call);
		System.out.print("Call to virtual method: " +virtual_call.getMethodName());
		++depth;
		if (virtual_call.getObjectReference() != null){
			System.out.print(", in external scope");
			virtual_call.getObjectReference().accept(this);
		}
		for(Expr e: virtual_call.getArguments())
			e.accept(this);
		--depth;
	}

	@Override
	public void visit(Literal literal) {
		indent(literal);
		System.out.print(literal.getType()+" literal: "+literal.getValue());
	}

	@Override
	public void visit(This t) {
		indent(t);
		System.out.print("Reference to 'this' instance");	
	}

	@Override
	public void visit(NewObject new_obj) {
		indent(new_obj);
		System.out.print("Instantiation of class: "+new_obj.getClassName());	
	}

	@Override
	public void visit(NewArray new_arr) {
		indent(new_arr);
		System.out.print("Array allocation");
		++depth;
		new_arr.getType().accept(this);
		new_arr.getArrayLength().accept(this);
		--depth;
	}

	@Override
	public void visit(Length length) {
		indent(length);
		System.out.print("Reference to array length");
		++depth;
		length.getExpression().accept(this);
		--depth;
		
	}

}