package slp;

/** An enumeration containing all the literal types.
 */

public enum LiteralType {
<<<<<<< HEAD
	INTEGER(0, "Integer"), 
	STRING(null, "String"),
	TRUE(true, "Boolean"),
	FALSE(false, "Boolean"),
	NULL(null, "Null");
	
	private Object value;
	private String desc;
	
	private LiteralType(Object value, String desc) {
		this.value = value;
		this.desc = desc;
=======
	INTEGER(0), 
	STRING(null),
	TRUE(true),
	FALSE(false),
	NULL(null);
	
	private Object value;
	
	private LiteralType(Object value) {
		this.value = value;
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	}
	
	public Object getValue() {
		return value;
	}
	
<<<<<<< HEAD
	public String getDesc() {
		return desc;
	}
	
=======
>>>>>>> efe3ea8bd6df4e0ec5842b895e3593fa3bf62763
	public String valueToString(Object value) {
		return String.valueOf(value);
	}
}
