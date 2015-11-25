package slp;

/** An enumeration containing all the literal types.
 */

public enum LiteralType {
	INTEGER(0), 
	STRING(null),
	TRUE(true),
	FALSE(false),
	NULL(null);
	
	private Object value;
	
	private LiteralType(Object value) {
		this.value = value;
	}
	
	public Object getValue() {
		return value;
	}
	
	public String valueToString(Object value) {
		return String.valueOf(value);
	}
}
