package lir;

public class LirReturnInfo {

	private MoveCommandEnum moveCommand = MoveCommandEnum.DUMMY;
	
	private String registerLocation = "";
	
	public LirReturnInfo(MoveCommandEnum cmd, String loc){
		moveCommand = cmd;
		registerLocation = loc;
		
	}
	
	public String getRegisterLocation(){
		return registerLocation;
	}
	
	public MoveCommandEnum getMoveCommand(){
		return moveCommand;
	}
}
