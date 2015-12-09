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
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	}
	
	public Object getValue() {
		return value;
	}
	
<<<<<<< HEAD
	public String getDesc() {
		return desc;
	}
	
=======
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
	public String valueToString(Object value) {
		return String.valueOf(value);
	}
}
