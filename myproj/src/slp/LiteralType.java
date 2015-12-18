package slp;

/** An enumeration containing all the literal types.
 */

public enum LiteralType {
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
	}
	
	public Object getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public String valueToString(Object value) {
		return String.valueOf(value);
	}
}
