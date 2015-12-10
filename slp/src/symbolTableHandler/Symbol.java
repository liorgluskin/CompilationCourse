package symbolTableHandler;
import types.*;

public abstract class Symbol {
	protected String name;
	protected Type type;
	protected int line;
	
	public Symbol(String name,int line){
		this.name = name;
		this.line = line;
	}
	
	/**
	 * getter for the Symbol name
	 */
	public String getName(){
		return this.name;
	}
	
	

}
