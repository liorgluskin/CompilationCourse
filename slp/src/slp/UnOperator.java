package slp;

/** An enumeration containing all the unary operation types in the language.
 */

public enum UnOperator {
	UMINUS, LNEG;
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
		case UMINUS: return "unary minus";
		case LNEG: return "logical negation";
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
<<<<<<< HEAD
}
=======
}
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
