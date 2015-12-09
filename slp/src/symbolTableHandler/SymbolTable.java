package symbolTableHandler;

/**
 * Abstract class for all symbol tables
 *
 */

public abstract class SymbolTable {
protected SymbolTable parent = null;
	
	/**
	 * Constructs a SymbolTable - global scope.
	 */
	public SymbolTable(){}
	
	/**
	 * Constructs a SymbolTable. 
	 * @param parent
	 * 				parent scope of the current symbol table
	 */
	public SymbolTable(SymbolTable parent){
		this.parent = parent;
	}
	
}
