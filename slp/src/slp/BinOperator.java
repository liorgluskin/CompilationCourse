package slp;

/** An enumeration containing all the binary operation types in the language.
 */
public enum BinOperator {
	PLUS, MINUS, MULTIPLY, DIVIDE, MOD, LAND, LOR, LT, GT, LTE, GTE, EQUAL, NEQUAL;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case PLUS: return "+";
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
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}