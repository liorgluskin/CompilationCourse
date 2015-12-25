package lir;

public enum MoveCommandEnum {
	MOVE("Move"),
	MOVE_FIELD("MoveField"),
	MOVE_ARRAY("MoveArray"),
	DUMMY("dummy");
	
	private final String name;
	
	MoveCommandEnum(String name){
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
}