package slp;

/** An enumeration containing all the unary operation types in the language.
 */

public enum UnOperator {
<<<<<<< HEAD
	UMINUS, LNEG;
=======
	LNEG;
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	
	/** Prints the operator in the same way it appears in the program.
	 */
	public String toString() {
		switch (this) {
<<<<<<< HEAD
		case UMINUS: return "unary minus";
		case LNEG: return "logical negation";
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}
=======
		case LNEG: return "!";
		default: throw new RuntimeException("Unexpted value: " + this.name());
		}
	}
}
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
