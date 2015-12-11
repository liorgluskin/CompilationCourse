package symbolTableHandler;
import types.*;

/**
 * Abstract class for a symbol table entry.
 *
 */

public abstract class Symbol {
	protected String sym_name;
	protected Type type;
	protected int line;
	
	/**
	 * A Symbol constructor.
	 * 
	 */	
	public Symbol(String sym_name, int line){
		this.sym_name = sym_name;
		this.line = line;
	}
	
	public String getName(){
		return this.sym_name;
	}
	
	public Type getType(){
		return this.type;
	}
	
	

}

