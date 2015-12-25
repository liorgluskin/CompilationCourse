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

	public LirReturnInfo visit(ClassDecl class_decl, Environment d) {

		//Dispatcher table is already done so we continue visit the methods		
		for(FieldOrMethod fom : class_decl.getFieldsOrMethods()){
			fom.accept(this, d);
		}

		//nothing to be returned				
		return null;
	}

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

	public LirReturnInfo visit(StaticMethod staticMethod, Environment d) {
		methodVisitor(staticMethod,d);
		// nothing to be returned
		return null;
	}


	public LirReturnInfo visit(ClassMethod classMethod, Environment d) {
		methodVisitor(classMethod,d);
		// nothing to be returned
		return null;
	}

	public LirReturnInfo visit(PrimitiveType primitiveType, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * user define ClassType translation to LIR  code
	 * no addition of LIR code required
	 */
	public LirReturnInfo visit(ClassType classType, Environment d) {
		return null;
	}

	public LirReturnInfo visit(Formal formal, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(AssignStmt stmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	/**
	 * Translate Call statement into LIR code
	 */
	public LirReturnInfo visit(CallStmt callStmt, Environment d) {
		callStmt.getCall().accept(this, d);
		return null;
	}

	
	/**
	 * Translate Return statement into LIR code
	 */
	public LirReturnInfo visit(ReturnStmt returnStmt, Environment d) {
		String currentReg = null;
		
		// returns non-void
		if(returnStmt.hasExpr()){
			// get register the return expression is in
			LirReturnInfo returnInfo = returnStmt.getExpr().accept(this, d);
			currentReg = returnInfo.getRegisterLocation();
		}
		// a return void statement, we return dummy
		else{
			currentReg = "Rdummy";
		}
		d.addLirInstruction("Return", currentReg, returnStmt.getLineNum());
		return null;
	}

	
	/**
	 * Translates If statements to LIR code
	 */
	public LirReturnInfo visit(IfStmt ifStmt, Environment d) {
		String else_label = d.addLabel("else_label"); //label to jump to case of else 
		String end_if_label = d.addLabel("end_if_label");//label to jump to when if stmt ends

		// go over if condition and the register where the result is
		LirReturnInfo condLirInfo = ifStmt.getCond().accept(this, d);
		String condResRegister = condLirInfo.getRegisterLocation();

		// check if the condition holds
		d.addLirInstruction("Compare", "0", condResRegister, ifStmt.getLineNum());
		// if condition is false jump directly to else/end label
		if(ifStmt.hasElse()){
			// jump to else label
			d.addLirInstruction("JumpTrue", else_label, ifStmt.getLineNum()); 
		}else{
			// no else - jump to end label
			d.addLirInstruction("JumpTrue", end_if_label, ifStmt.getLineNum());
		}

		// get if body LIR code
		ifStmt.getBody().accept(this, d);
		d.addLirInstruction("Jump", end_if_label, ifStmt.getLineNum());// then jump to end-if label

		// get else body, if exists
		if(ifStmt.hasElse()){
			d.addLirInstruction(else_label+":", "", ifStmt.getLineNum()); // else_label: 
			ifStmt.getElseStmt().accept(this, d);
		}

		// add if statement end_label
		d.addLirInstruction(end_if_label+":", "", ifStmt.getLineNum());  	
		return null; // does not return value
	}


	/**
	 * Translate While statement into LIR code
	 */
	public LirReturnInfo visit(WhileStmt whileStmt, Environment d) {

		String testLabel = d.addLabel("while_test_label"); //label for while condition test
		String endLabel = d.addLabel("while_end_label"); //label to jump to when while loop over
		//for break, continue statements:
		d.setLoopTestLabel(testLabel);
		d.setLoopEndLabel(endLabel);

		// go over while condition LIR code
		d.addLirInstruction(testLabel+":", "", whileStmt.getLineNum()); // while_test_label:
		LirReturnInfo condInfo = whileStmt.getCond().accept(this, d);
		String condResRegister = condInfo.getRegisterLocation();

		// check if the condition holds
		d.addLirInstruction("Compare", "0", condResRegister, whileStmt.getLineNum());
		// if condition does not hold, end the while loop
		d.addLirInstruction("JumpTrue", endLabel, whileStmt.getLineNum());
		// id condition holds: execute the while body, check the condition when done
		whileStmt.getBody().accept(this, d);
		d.addLirInstruction("Jump", testLabel, whileStmt.getLineNum());

		// add while statement end_label
		d.addLirInstruction(endLabel+":", "", whileStmt.getLineNum()); 
		return null;
	}

	
	/**
	 * Translate Break statement into LIR code
	 */
	public LirReturnInfo visit(BreakStmt breakStmt, Environment d) {
		// get the enclosing loop end_label to jump to when we break
		String loopEndLabel = d.getLoopEndLabel();
		d.addLirInstruction("Jump", loopEndLabel, breakStmt.getLineNum());
		return null;
	}

	
	/**
	 * Translate Continue statement into LIR code
	 */
	public LirReturnInfo visit(ContinueStmt continueStmt, Environment d) {
		// get the enclosing loop test_label to jump to when we continue loop
		String loopTestLabel = d.getLoopTestLabel();
		d.addLirInstruction("Jump", loopTestLabel, continueStmt.getLineNum());
		return null;
	}

	public LirReturnInfo visit(IDStmt idStmt, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Translate Block statement list into LIR code
	 */
	public LirReturnInfo visit(BlockStmt blockStmt, Environment d) {
		// iterate over the statements inside the {} block
		return blockStmt.getStatementList().accept(this,d);
	}

	/**
	 * Translate Statements list into LIR code
	 */
	public LirReturnInfo visit(StmtList stmtLst, Environment d) {
		// iterate over the statements
		for(Stmt stmt : stmtLst.getStatements()){
			stmt.accept(this, d);
		}
		return null;
	}

	public LirReturnInfo visit(VarLocation var_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(ArrLocation arr_loc, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(StaticCall static_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(VirtualCall virtual_call, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(This t, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(NewObject new_obj, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(NewArray new_arr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(Length length, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(Literal literal, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(Expr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(BlockExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(UnaryOpExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

	public LirReturnInfo visit(BinaryOpExpr expr, Environment d) {
		// TODO Auto-generated method stub
		return null;
	}

}
