package slp;

/** Pretty-prints an SLP AST.
 */
public class PrettyPrinter implements Visitor {
	protected final ASTNode root;
	private String fileName;
	private int depth = 0;


	/** Constructs a printing visitor from an AST.
	 * 
	 * @param root The root of the AST.
	 */
	public PrettyPrinter(ASTNode root, String fileName) {
		this.root = root;
		this.fileName = fileName;
	}

	/** Prints the required lineIndentation and line num for the current AST node
	 * @param AST node 
	 */
	private void lineIndent(ASTNode node) {
		System.out.println();
		for (int i = 0; i < depth; i++){
			System.out.print(" ");
		}
		if (node != null){
			System.out.print(node.getLineNum() + ": ");
		}
	}

	/** Prints the AST with the given root.
	 */
	public void print() {
		root.accept(this);
	}


	public void visit(StmtList stmts) {
		for (Stmt s : stmts.getStatements()) {
			s.accept(this);
			System.out.println();
		}
	}

	public void visit(Stmt stmt) {
		throw new UnsupportedOperationException("Unexpected visit of Stmt abstract class");
	}

	public void visit(AssignStmt assignStmt) {
		lineIndent(assignStmt);
		System.out.print("Assignment statement");
		depth+=2; // increase indentation for child nodes
		assignStmt.getLocation().accept(this); // print assignment variable
		assignStmt.getRhs().accept(this); // print assignment value
		depth-=2; // return to current node indentation
	}

	public void visit(CallStmt callStmt) {
		lineIndent(callStmt);
		System.out.print("Method call statement");
		depth++;
		callStmt.getCall().accept(this);
		depth--;
	}

	public void visit(ReturnStmt returnStmt) {
		lineIndent(returnStmt);
		System.out.print("Return statement");
		if(returnStmt.hasExpr()){
			System.out.print(", with return value");
			depth++;
			returnStmt.getExpr().accept(this);
			depth--;
		}
	}

	public void visit(IfStmt ifStmt) { 
		lineIndent(ifStmt);
		System.out.print("If statement");
		if(ifStmt.hasElse()){
			System.out.print(", with Else");
		}
		depth+=2;
		ifStmt.getCond().accept(this);;
		ifStmt.getBody().accept(this);
		if(ifStmt.hasElse()){
			ifStmt.getElseStmt().accept(this);
		}
		depth-=2;
	}

	public void visit(WhileStmt whileStmt) {
		lineIndent(whileStmt);
		System.out.print("While statement");
		depth += 2;
		whileStmt.getCond().accept(this);
		whileStmt.getBody().accept(this);
		depth -= 2;
	}

	public void visit(BreakStmt breakStmt) { 
		lineIndent(breakStmt);
		System.out.print("Break statement");
	}

	public void visit(ContinueStmt continueStmt) { 
		lineIndent(continueStmt);
		System.out.print("Continue statement");
	}

	public void visit(BlockStmt blockStmt) { 
		lineIndent(blockStmt);
		System.out.print("Block of statements");
		depth+=2;
		for(Stmt s : blockStmt.getStatementList().getStatements()){
			s.accept(this);
		}
		depth-=2;
	}

	public void visit(IDStmt idStmt) { 
		lineIndent(idStmt);
		System.out.print("Declaration of local variable: "+idStmt.getName());
		if(idStmt.hasValue()){
			System.out.print(", with initial value");
			depth++;
		}
		depth++;
		idStmt.getType().accept(this);
		if(idStmt.hasValue()){
			idStmt.getValue().accept(this);
			depth--;
		}
		depth--;
	}


	public void visit(Expr expr) {
		throw new UnsupportedOperationException("Unexpected visit of Expr abstract class");
	}	

	
	public void visit(UnaryOpExpr expr) {
		lineIndent(expr);
		if(expr.hasMathematicalOp()){
			System.out.print("Mathematical unary operation: ");
		}
		else{
			System.out.print("Logical unary operation: ");
		}
		System.out.print(expr.getOp().toString());
		depth++;
		expr.getOperand().accept(this);
		depth--;
	}

	public void visit(BinaryOpExpr expr) {
		lineIndent(expr);
		if(expr.hasMathematicalOp()){
			System.out.print("Mathematical binary operation: ");
		}
		else{
			System.out.print("Logical binary operation: ");
		}
		System.out.print(expr.getOp().toString());
		depth+=2;
		expr.getLeftOperand().accept(this);
		expr.getRightOperand().accept(this);
		depth-=2;
	}

	public void visit(Program program) {
		System.out.println("\nAbstract Syntax Tree: "+fileName);
		for(ClassDecl cls : program.getClasses()){
			cls.accept(this);
		}

	}

	public void visit(ClassDecl class_decl) {
		lineIndent(class_decl);
		System.out.print("Declaration of class: " + class_decl.getName());
		if (class_decl.getSuperClassName() != null){
			System.out.print(", subclass of " + class_decl.getSuperClassName());
		}
		depth+=2;
		for (FieldOrMethod fieldOrMethod : class_decl.getFieldsOrMethods()){
			fieldOrMethod.accept(this);
		}
		depth-=2;
	}

	public void visit(ClassMethod method) {
		lineIndent(method);
		System.out.print("Declaration of virtual method: " + method.getName());
		depth+=2;
		method.getType().accept(this);
		for(Formal f : method.getFormals()){
			f.accept(this);
		}
		for(Stmt s : method.getStatementList().getStatements()){
			s.accept(this);
		}
		depth-=2;
	}

	public void visit(StaticMethod method) {
		lineIndent(method);
		System.out.print("Declaration of static method: " + method.getName());
		depth+=2;
		method.getType().accept(this);
		for(Formal f : method.getFormals()){
			f.accept(this);
		}
		for(Stmt s : method.getStatementList().getStatements()){
			s.accept(this);
		}
		depth-=2;
	}

	public void visit(PrimitiveType primitiveType) {
		lineIndent(primitiveType);
		System.out.print("Primitive data type: ");
		if (primitiveType.getDimension() > 0){
			System.out.print(primitiveType.getDimension() + "-dimensional array of ");
		}
		System.out.print(primitiveType.getName());
	}

	public void visit(ClassType classType) {
		lineIndent(classType);
		System.out.print("User-defined data type: ");
		if (classType.getDimension() > 0){
			System.out.print(classType.getDimension() + "-dimensional array of ");
		}
		System.out.print(classType.getName());
	}

	public void visit(Field field) {
		lineIndent(field);
		System.out.print("Declaration of field: " + field.getName());
		depth++;
		field.getType().accept(this);
		depth--;
	}

	public void visit(Formal formal) {
		lineIndent(formal);
		System.out.print("Parameter: " + formal.getName());
		depth++;
		formal.getType().accept(this);
		depth--;
	}

	public void visit(VarLocation var_loc) {
		lineIndent(var_loc);
		System.out.print("Reference to variable: " + var_loc.getName());
		if(var_loc.hasExternalLocation()){
			System.out.print(", in external scope");
			depth++;
			var_loc.getLocation().accept(this);
			depth--;
		}
	}

	public void visit(ArrLocation arr_loc) {
		lineIndent(arr_loc);
		System.out.print("Reference to array");
		depth+=2;
		arr_loc.getArrLocation().accept(this);
		arr_loc.getIndex().accept(this);
		depth-=2;
	}

	public void visit(StaticCall static_call) {
		lineIndent(static_call);
		System.out.print("Call to static method: "+static_call.getMethodName()+", in class "+static_call.getClassName());
		depth+=2;
		for(Expr e: static_call.getArguments()){
			e.accept(this);
		}
		depth-=2;
	}

	public void visit(VirtualCall virtual_call) {
		lineIndent(virtual_call);
		System.out.print("Call to virtual method: "+virtual_call.getMethodName());
		depth+=2;
		if (virtual_call.getObjectReference() != null){
			System.out.print(", in external scope");
			virtual_call.getObjectReference().accept(this);
		}
		for(Expr e: virtual_call.getArguments()){
			e.accept(this);
		}
		depth-=2;
	}

	public void visit(Literal literal) {
		lineIndent(literal);
		System.out.print(literal.getType().getDesc()+" literal: "+literal.getValue());
	}

	public void visit(This t) {
		lineIndent(t);
		System.out.print("Reference to 'this' instance");	
	}

	public void visit(NewObject new_obj) {
		lineIndent(new_obj);
		System.out.print("Instantiation of class: "+new_obj.getClassName());	
	}

	public void visit(NewArray new_arr) {
		lineIndent(new_arr);
		System.out.print("Array allocation");
		depth+=2;
		new_arr.getType().accept(this);
		new_arr.getArrayLength().accept(this);
		depth-=2;
	}

	public void visit(Length length) {
		lineIndent(length);
		System.out.print("Reference to array length");
		depth++;
		length.getExpression().accept(this);
		depth--;
	}

}