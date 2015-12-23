package lir;

import slp.*;
import symbolTableHandler.*;

public class LirVisitor implements PropagatingVisitor<Environment,LirReturnInfo>{
	
	private GlobalSymbolTable globalSymTable = null;
	private Environment environment = null;
	
	public LirVisitor(GlobalSymbolTable globalSymT){
		globalSymTable = globalSymT;
	}
	
	String getLirCode(Program program){
		environment = new Environment();
		visit(program,environment);
		return environment.generateLirCode();
	}

	@Override
	public LirReturnInfo visit(Program program, Environment d) {
		
		//all checks are done in Environment instance
		//therefore, nothing to be done in current visit
		
		//create Dispatcher table
		//traverse all classes before visiting all methods 
		//as class methods can be called before class declaration
		for(ClassDecl c: program.getClasses()){
			if (!c.getName().equals("Library")){
				//create dispatcher table
				ClassSymbolTable classSymT = globalSymTable
						.getClassSymbolTable(c.getName());			
				
				if(c.getSuperClassName() != null){
					d.addVirtualTable(c.getName(), c.getSuperClassName(),
							classSymT.getAllVirtualMethods());
				}else{
					d.addVirtualTable(c.getName(), classSymT.getAllVirtualMethods());
				}
			}
		}
		
		//visit all classes apart from Library
		for(ClassDecl c: program.getClasses()){
			if (!c.getName().equals("Library"))
				c.accept(this, d);
		}
		
		//nothing to be returned
		return null;
	}

	@Override
	public LirReturnInfo visit(ClassDecl class_decl, Environment d) {
		
		//Dispatcher table is already done so we continue visit the methods		
		for(FieldOrMethod fom : class_decl.getFieldsOrMethods()){
			fom.accept(this, d);
		}
			
		//nothing to be returned				
		return null;
	}

	@Override
	public LirReturnInfo visit(Field field, Environment d) {
		// nothing to be done here
		//offset id already handled by symbolTable 
		return null;
	}
	
	//handling static and virtual methods are similar
	private void methodVisitor(Method method, Environment d) {
		
		//get current updated lir code
		StringBuilder strb = d.getLirStringBuilder();
		
		//add new line
		strb.append("\n");
		
		//get class name for comment and label
		String class_name = ((ClassSymbolTable)method.getScope()).getSymbol().getName();
		
		//add Relevant Label
		if(method.getName().equals("main")){
			//if name == main then it must be the static main function by IC specification	
			
			//add comment with method and class name
			strb.append("####main in "+class_name+"####\n");
			
			//add label
			strb.append("_ic_main:\n");			
		} else {
			//not main function
			
			//add comment with method and class name
			strb.append("####"+class_name+"."+method.getName()+"####\n");
			
			//add label
			strb.append("_"+class_name+"_"+method.getName()+":\n");					
		}
		
		//visit all statements
		method.getStatementList().accept(this, d);
		
		//return 9999 if function return type is void
		if(method.getType().getFullName().equals("void")){
			strb.append("Return 9999\n");
		}
		
	}

	@Override
	public LirReturnInfo visit(StaticMethod staticMethod, Environment d) {
		methodVisitor(staticMethod,d);
		// nothing to be returned
		return null;
	}


	@Override
	public LirReturnInfo visit(ClassMethod classMethod, Environment d) {
		methodVisitor(classMethod,d);
		// nothing to be returned
		return null;
	}

	@Override
	public LirReturnInfo visit(PrimitiveType primitiveType, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(ClassType classType, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(Formal formal, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(AssignStmt stmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(CallStmt callstmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(ReturnStmt returnStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(IfStmt ifStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(WhileStmt whileStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(BreakStmt breakStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(ContinueStmt contineStmtm, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(IDStmt idStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(BlockStmt blockStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(StmtList stmtLst, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(VarLocation var_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(ArrLocation arr_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(StaticCall static_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(VirtualCall virtual_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(This t, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(NewObject new_obj, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(NewArray new_arr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(Length length, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(Literal literal, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(Expr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(BlockExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(UnaryOpExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LirReturnInfo visit(BinaryOpExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

}
