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

	public Integer visit(Expr expr, Environment env) {
		throw new UnsupportedOperationException("Unexpected visit of Expr!");
	}

	public Integer visit(UnaryOpExpr expr, Environment env) {
		UnOperator op = expr.op;
		//if (op != Operator.MINUS)
		//	throw new RuntimeException("Encountered unexpected operator " + op);
		Integer value = expr.operand.accept(this, env);
		return new Integer(- value.intValue());
	}

	public Integer visit(BinaryOpExpr expr, Environment env) {
		Integer lhsValue = expr.lhs.accept(this, env);
		int lhsInt = lhsValue.intValue();
		Integer rhsValue = expr.rhs.accept(this, env);
		int rhsInt = rhsValue.intValue();
		int result;
		switch (expr.op) {
		case DIVIDE:
			if (rhsInt == 0)
				throw new RuntimeException("Attempt to divide by zero: " + expr);
			result = lhsInt / rhsInt;
			break;
		case MINUS:
			result = lhsInt - rhsInt;
			break;
		case MULTIPLY:
			result = lhsInt * rhsInt;
			break;
		case PLUS:
			result = lhsInt + rhsInt;
			break;
		case LT:
			result = lhsInt < rhsInt ? 1 : 0;
			break;
		case GT:
			result = lhsInt > rhsInt ? 1 : 0;
			break;
		case LTE:
			result = lhsInt <= rhsInt ? 1 : 0;
			break;
		case GTE:
			result = lhsInt >= rhsInt ? 1 : 0;
			break;
		case LAND:
			result = (lhsInt!=0 && rhsInt!=0) ? 1 : 0;
			break;
		case LOR:
			result = (lhsInt!=0 || rhsInt!=0) ? 1 : 0;
			break;
		default:
			throw new RuntimeException("Encountered unexpected operator type: " + expr.op);
		}
		return new Integer(result);
	}

	@Override
	public Integer visit(VarLocation var_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(ArrLocation arr_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(StaticCall static_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(VirtualCall virtual_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(This t, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(NewObject new_obj, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(NewArray new_arr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Length length, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(Literal literal, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer visit(VarExpr vexpr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}
}