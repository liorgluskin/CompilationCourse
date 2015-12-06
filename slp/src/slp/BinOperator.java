package slp;

/** An enumeration containing all the binary operation types in the language.
 */
<<<<<<< HEAD:slp/src/slp/Operator.java
public enum Operator {
	MINUS, PLUS, MULT, DIV, LT, GT, LE, GE, LAND, LOR;
=======
public enum BinOperator {
	PLUS, MINUS, MULTIPLY, DIVIDE, MOD, LAND, LOR, LT, GT, LTE, GTE, EQUAL, NEQUAL;
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763:slp/src/slp/BinOperator.java
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case PLUS: return "+";
<<<<<<< HEAD:slp/src/slp/Operator.java
		case MULT: return "*";
		case DIV: return "/";
		case LT: return "<";
		case GT: return ">";
		case LE: return "<=";
		case GE: return ">=";
		case LAND: return "&&";
		case LOR: return "||";
=======
		case MINUS: return "-";
		case MULTIPLY: return "*";
		case DIVIDE: return "/";
		case MOD: return "%";
		case LAND: return "&&";
		case LOR: return "||";
		case LT: return "<";
		case GT: return ">";
		case LTE: return "<=";
		case GTE: return ">=";
		case EQUAL: return "==";
		case NEQUAL: return "!=";
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763:slp/src/slp/BinOperator.java
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}