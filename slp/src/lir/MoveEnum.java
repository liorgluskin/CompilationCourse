package lir;

public enum MoveEnum {
	MOVE("Move"),
	MOVE_FIELD("MoveField"),
	MOVE_ARRAY("MoveArray"),
	DUMMY("dummy");
	
	private final String name;
	
	MoveEnum(String name){
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}