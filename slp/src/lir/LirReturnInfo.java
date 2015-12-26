package lir;

public class LirReturnInfo {

	private MoveEnum moveCommand = MoveEnum.DUMMY;
	
	private String registerLocation = "";
	
	public LirReturnInfo(MoveEnum cmd, String loc){
		moveCommand = cmd;
		registerLocation = loc;
		
	}
	
	public String getRegisterLocation(){
		return registerLocation;
	}
	
	public MoveEnum getMoveCommand(){
		return moveCommand;
	}
}
