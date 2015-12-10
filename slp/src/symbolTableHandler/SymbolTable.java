package symbolTableHandler;

public class SymbolTable {
	protected SymbolTable parent;
	
	/**
	 * constructor for generic SymbolTable 
	 * @param parent
	 */
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
	}
	
}
