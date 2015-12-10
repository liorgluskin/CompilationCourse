package slp;

/** An enumeration containing all the binary operation types in the language.
 */
public enum BinOperator {
	PLUS, MINUS, MULTIPLY, DIVIDE, MOD, LAND, LOR, LT, GT, LTE, GTE, EQUAL, NEQUAL;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case PLUS: return "addition";
		case MINUS: return "subtraction";
		case MULTIPLY: return "multiplication";
		case DIVIDE: return "division";
		case MOD: return "modulo";
		case LAND: return "logical and";
		case LOR: return "logical or";
		case LT: return "less than";
		case GT: return "greater than";
		case LTE: return "less than or equal to";
		case GTE: return "greater than or equal to";
		case EQUAL: return "equality";
		case NEQUAL: return "inequality";
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}