package slp;

import java.util.List;

/** Abstract base class for method AST nodes.
 */
public abstract class Method extends FieldOrMethod{
	protected boolean is_static;
	protected Type type;
	protected String name;
	protected List<Formal> formal_list;
	protected StmtList stmt_list;
	
	public Method(Type type, String name, List<Formal> formal_list, StmtList stmt_list){
		super(type.getLineNum());
		this.type = type;
		this.name = name;
		this.formal_list = formal_list;
		this.stmt_list = stmt_list;
	}
	
	public Type getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public List<Formal> getFormals() {
		return formal_list;
	}

	public StmtList getStatements() {
		return stmt_list;
	}
}
