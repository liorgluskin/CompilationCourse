package slp;

import java.io.IOException;

/** Evaluates straight line programs.
 */
public class SLPEvaluator implements PropagatingVisitor<Environment, Integer> {
	protected ASTNode root;

	/** Constructs an SLP interpreter for the given AST.
	 * 
	 * @param root An SLP AST node.
	 */
	public SLPEvaluator(ASTNode root) {
		this.root = root;
	}

	/** Interprets the AST passed to the constructor.
	 */
	public void evaluate() {
		Environment env = new Environment();
		root.accept(this, env);
	}


	public Integer visit(AssignStmt stmt, Environment env) {
		Expr rhs = stmt.rhs;
		Integer expressionValue = rhs.accept(this, env);

		return null;
	}


	public Integer visit(Expr expr, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Expr!");
	}

<<<<<<< HEAD
	public Integer visit(BlockExpr expr, Environment env) {
		return null;
	}

	public Integer visit(UnaryOpExpr expr, Environment env) {
		return null;
	}

	public Integer visit(BinaryOpExpr expr, Environment env) {
		return null;
	}

	public Integer visit(Program program, Environment env) {
		// TODO Auto-generated method stub
		return null;
=======
	public Integer visit(UnaryOpExpr expr, Environment env) {
		UnOperator op = expr.op;
		//if (op != Operator.MINUS)
		//	throw new RuntimeException("Encountered unexpected operator " + op);
		Integer value = expr.operand.accept(this, env);
		return new Integer(- value.intValue());
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	}

	public Integer visit(ClassDecl class_decl, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(Field field, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(StaticMethod staticMethod, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(ClassMethod classMethod, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(Formal formal, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(VarLocation var_loc, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(ArrLocation arr_loc, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(StaticCall static_call, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(VirtualCall virtual_call, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(This t, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(NewObject new_obj, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(NewArray new_arr, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(Length length, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(Literal literal, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(PrimitiveType primitiveType, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(ClassType classType, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(CallStmt callstmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(ReturnStmt returnStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(IfStmt ifStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(WhileStmt whileStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(BreakStmt breakStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(ContinueStmt contineStmtm, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(IDStmt idStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(BlockStmt stmtStmt, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(StmtList stmtLst, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	public Integer visit(VarExpr vexpr, Environment env) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(PrimitiveType primitiveType, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(ClassType classType, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(CallStmt callstmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(ReturnStmt returnStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(IfStmt ifStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(WhileStmt whileStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(BreakStmt breakStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(ContinueStmt contineStmtm, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(IDStmt idStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(StatementsStmt stmtStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(StmtList stmtLst, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(VarExpr vexpr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}
}