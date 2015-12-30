package lir;

import semantic.SemanticError;
import semantic.TypeEvaluator;
import slp.*;
import symbolTableHandler.*;
import types.TypeVoid;

/**
 * Class traverses the global symbol table,
 * and translates the program into LIR code.
 * 
 * each visit is passed the LIR program Environment,
 * that contains the information regarding the LIR code and registers.
 * 
 * setting the program variable labels is done in VarLabelVisitor before is run LirVisitor.
 */
public class LirVisitor implements PropagatingVisitor<Environment,LirReturnInfo>{

	private GlobalSymbolTable globalSymTable = null;
	private Environment environment = null;

	public LirVisitor(GlobalSymbolTable globalSymTable){
		this.globalSymTable = globalSymTable;
	}

	public String getLirCode(Program program){
		environment = new Environment();
		environment.setCurrentStringBuilder(environment.getLirStringBuilder());
		visit(program,environment);

		//add main string builder at the end - keren
		environment.addToLirStringBuilder(environment.getMainStringBuilder().toString());

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
				ClassSymbolTable classSymT = globalSymTable.getClassSymbolTable(c.getName());			
				if(c.getSuperClassName() != null){
					d.addVirtualTable(c.getName(), c.getSuperClassName(), classSymT.getAllVirtualMethods());
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

	/**
	 * Common handler for static and virtual methods
	 */
	private void methodVisitor(Method method, Environment d, boolean isMain) {

		//get current updated lir code
		StringBuilder strb;

		//get class name for comment and label
		String class_name = ((ClassSymbolTable)method.getScope()).getSymbol().getName();

		//add Relevant Label
		if(isMain/*-keren; method.getName().equals("main")*/){
			//if name == main then it must be the static main function by IC specification	
			environment.setCurrentStringBuilder(environment.getMainStringBuilder());

			strb = d.getCurrentStringBuilder();

			d.addToMainStringBuilder("\n####main in "+class_name+"####\n");

			//add label
			d.addToMainStringBuilder("_ic_main:\n");			
		} else {
			//not main function
			strb = d.getCurrentStringBuilder();
			//add comment with method and class name
			strb.append("\n####"+class_name+"."+method.getName()+"####\n");

			//add label
			strb.append("_"+class_name+"_"+method.getName()+":\n");					
		}

		//visit all statements
		method.getStatementList().accept(this, d);

		//return Rdummy if function return type is void
		if(method.getType().getFullName().equals("void") && !isMain){
			//check if method already doesnt have an emty return
			if(strb.lastIndexOf("Return Rdummy\n") != strb.length() - "Return Rdummy\n".length()){
				strb.append("Return Rdummy\n");				
			}
		}
		environment.setCurrentStringBuilder(environment.getLirStringBuilder());

	}


	/**
	 * Translate Static Method into LIR code
	 */
	public LirReturnInfo visit(StaticMethod staticMethod, Environment d) {
		//is staticMethod is main method - keren
		boolean isMain = staticMethod.getType().getName().equals("void")&&
				staticMethod.getName().equals("main") &&
				staticMethod.getFormals().size() == 1 &&
				staticMethod.getFormals().get(0).getType().getFullName().equals("string[]");
		methodVisitor(staticMethod,d,isMain);
		return null; //nothing to be returned
	}


	/**
	 * Translate Class (virtual) Method into LIR code
	 */
	public LirReturnInfo visit(ClassMethod classMethod, Environment d) {
		methodVisitor(classMethod,d,false);
		return null; //nothing to be returned
	}


	/**
	 * PrimitiveType translation to LIR  code
	 * no addition of LIR code required
	 */	
	public LirReturnInfo visit(PrimitiveType primitiveType, Environment d) {
		return null;
	}


	/**
	 * user define ClassType translation to LIR  code
	 * no addition of LIR code required
	 */
	public LirReturnInfo visit(ClassType classType, Environment d) {
		return null;
	}


	/**
	 * Formal translation to LIR  code
	 */
	public LirReturnInfo visit(Formal formal, Environment d) {
		// get LIR code of the formal's type
		return formal.getType().accept(this, d);
	}

	/***
	 * Checks if the given input string represents a variable in memory
	 * @param var
	 * @return if var is in Memory
	 */
	private boolean isMemoryVar(String var){
		// local variable
		if(var.startsWith("v_")){
			return true;
		}
		// method parameter
		if(var.startsWith("p_")){
			return true;
		}
		// object's this
		if(var.startsWith("this")){
			return true;
		}

		return false;
	}

	/***
	 * Function to handle the possible assignments (moves) in LIR
	 * @param location - register, memory, field or array-location
	 * @param value - immediate, register or memory
	 * @param lineNum - line number in translated code
	 * @param d - the current LIR environment
	 */
	private void lirAssignHandler(LirReturnInfo locationInfo, LirReturnInfo valueInfo, Environment d){
		//different storage situations:
		
		//1)store in array or field:
		if(locationInfo.getMoveCommand() != MoveEnum.MOVE){		
			String rightValueLoc = valueInfo.getRegisterLocation();
			//check if right value is a register. if not transfer it to
			//a register and make a MOVE_ARRAY command
			if(valueInfo.getMoveCommand() !=MoveEnum.MOVE ||
					valueInfo.getRegisterLocation().charAt(0) !='R'){
				String newLeftValueReg = "R" + d.getCurrentRegister();
				d.incrementRegister();
				d.addInstructionToBuilder(valueInfo.getMoveCommand(),
						valueInfo.getRegisterLocation(), newLeftValueReg);
				
				rightValueLoc = newLeftValueReg;
			}
			d.addInstructionToBuilder(locationInfo.getMoveCommand(),
					rightValueLoc,locationInfo.getRegisterLocation());			
		}
		//2) store array or field in memory:
		else if(locationInfo.getRegisterLocation().charAt(0) != 'R' &&
				valueInfo.getMoveCommand() !=MoveEnum.MOVE){
			
			//store field or array loc in new reg
			String storeTmpReg = "R"+d.getCurrentRegister();
			d.incrementRegister();
			d.addInstructionToBuilder(valueInfo.getMoveCommand(), valueInfo.getRegisterLocation(),
					storeTmpReg);
			
			//move reg to memory
			d.addInstructionToBuilder(locationInfo.getMoveCommand(), storeTmpReg,
					locationInfo.getRegisterLocation());			
		}
		//3)store field/memory/immidiate in memory
		else{
			d.addInstructionToBuilder(locationInfo.getMoveCommand(),valueInfo.getRegisterLocation(),
					locationInfo.getRegisterLocation());
		}
	}
	

	/**
	 * Translate Assignment statement into LIR code
	 */
	public LirReturnInfo visit(AssignStmt stmt, Environment d) {
		
		

		// get the assignment right-hand-side info first
		LirReturnInfo valueInfo = stmt.getRhs().accept(this, d);
		

		// get the label of the assignment location
		LirReturnInfo locationInfo = stmt.getLocation().accept(this, d);
		
		lirAssignHandler(locationInfo,valueInfo,d);
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
			
			//transfer to a new register if its field or array
			if(returnInfo.getMoveCommand() != MoveEnum.MOVE){
				String newReturnReg = "R"+d.getCurrentRegister();
				d.incrementRegister();
				d.addInstructionToBuilder(returnInfo.getMoveCommand(), currentReg, newReturnReg);
				currentReg = newReturnReg;
			}
			
			
		}
		// a return void statement, we return dummy
		else{
			currentReg = "Rdummy";
		}
		d.addInstructionToBuilder("Return", currentReg, returnStmt.getLineNum());
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
		d.addInstructionToBuilder("Compare", "0", condResRegister, ifStmt.getLineNum());
		// if condition is false jump directly to else/end label
		if(ifStmt.hasElse()){
			// jump to else label
			d.addInstructionToBuilder("JumpTrue", else_label, ifStmt.getLineNum()); 
		}else{
			// no else - jump to end label
			d.addInstructionToBuilder("JumpTrue", end_if_label, ifStmt.getLineNum());
		}

		// get if body LIR code
		ifStmt.getBody().accept(this, d);
		d.addInstructionToBuilder("Jump", end_if_label, ifStmt.getLineNum());// then jump to end-if label

		// get else body, if exists
		if(ifStmt.hasElse()){
			d.addInstructionToBuilder(else_label+":", "", ifStmt.getLineNum()); // else_label: 
			ifStmt.getElseStmt().accept(this, d);
		}

		// add if statement end_label
		d.addInstructionToBuilder(end_if_label+":", "", ifStmt.getLineNum());  	
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
		d.addInstructionToBuilder(testLabel+":", "", whileStmt.getLineNum()); // while_test_label:
		LirReturnInfo condInfo = whileStmt.getCond().accept(this, d);
		String condResRegister = condInfo.getRegisterLocation();

		// check if the condition holds
		d.addInstructionToBuilder("Compare", "0", condResRegister, whileStmt.getLineNum());
		// if condition does not hold, end the while loop
		d.addInstructionToBuilder("JumpTrue", endLabel, whileStmt.getLineNum());
		// id condition holds: execute the while body, check the condition when done
		whileStmt.getBody().accept(this, d);
		d.addInstructionToBuilder("Jump", testLabel, whileStmt.getLineNum());

		// add while statement end_label
		d.addInstructionToBuilder(endLabel+":", "", whileStmt.getLineNum()); 
		return null;
	}


	/**
	 * Translate Break statement into LIR code
	 */
	public LirReturnInfo visit(BreakStmt breakStmt, Environment d) {
		// get the enclosing loop end_label to jump to when we break
		String loopEndLabel = d.getLoopEndLabel();
		d.addInstructionToBuilder("Jump", loopEndLabel, breakStmt.getLineNum());
		return null;
	}


	/**
	 * Translate Continue statement into LIR code
	 */
	public LirReturnInfo visit(ContinueStmt continueStmt, Environment d) {
		// get the enclosing loop test_label to jump to when we continue loop
		String loopTestLabel = d.getLoopTestLabel();
		d.addInstructionToBuilder("Jump", loopTestLabel, continueStmt.getLineNum());
		return null;
	}

	/**
	 * Translate a Definition of local variable into LIR code
	 */
	public LirReturnInfo visit(IDStmt idStmt, Environment d) {
		// if the variable statement contained an initial value,
		// we translate the value, and move it to the variable's memory location
		if(idStmt.hasValue()){

			// get the register where the value is stored
			LirReturnInfo initialValueInfo = idStmt.getValue().accept(this, d);
			String reg = initialValueInfo.getRegisterLocation();

			// get the label of the local variable
			BlockSymbolTable bst = (BlockSymbolTable) idStmt.getScope();
			VariableSymbol localVarSym;
			try {
				localVarSym = bst.getVarSymbolLocal(idStmt.getName());
				String varLabel = localVarSym.getLabel();
				lirAssignHandler(new LirReturnInfo(MoveEnum.MOVE, varLabel), initialValueInfo, d);
			} catch (SemanticError e) {
				// in case method symbol table does not contain parameter
				// should never get here, already checked in Semantic part
				e.printStackTrace();
			}			

		}
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

	/**
	 * Translate a local variable into LIR code
	 */
	public LirReturnInfo visit(VarLocation var_loc, Environment d) {
		String loc ="R"+ d.getCurrentRegister();

		if (var_loc.getLocation() != null){
			//visit location
			LirReturnInfo location_expr = var_loc.getLocation().accept(this, d);

			//move to register only if location_expr not in a register
			if(location_expr.getRegisterLocation().charAt(0)!= 'R' || location_expr.getMoveCommand() != MoveEnum.MOVE){
				
				//runtime check
				//keren - for array fields
				String loc_str = location_expr.getRegisterLocation();
				if(loc_str.contains("."))
				{
					loc_str = loc_str.substring(0, loc_str.indexOf("."));
				}else if(loc_str.contains("[")){
					loc_str = loc_str.substring(0, loc_str.indexOf("["));
				}
				d.addInstructionToBuilder("StaticCall", "__checkNullRef(a="+loc_str+")", "Rdummy");
				
				d.addInstructionToBuilder(location_expr.getMoveCommand().toString(), location_expr.getRegisterLocation(),loc);
				d.incrementRegister();
			}else{
				loc = location_expr.getRegisterLocation();
			}
			


			//field offset
			types.Type class_type = (types.Type)(var_loc.getLocation().accept(new TypeEvaluator(globalSymTable), null));
			String class_name = new String();
			if(class_type instanceof types.TypeClass)
				class_name = class_type.getName();
			else
				System.err.println("Error VarLocation visit\n" );

			FieldSymbol f = globalSymTable.getClassSymbolTable(class_name).getFieldSymbol(var_loc.getName());
			int offset = f.getOffset()+1;

			return new LirReturnInfo(MoveEnum.MOVE_FIELD,loc+"."+offset);
		}
		else{
			//has no external location. location is enclosing class

			//field
			if (((BlockSymbolTable)var_loc.getScope()).isField(var_loc.getName())){
				String class_name = ((BlockSymbolTable)var_loc.getScope())
						.getEnclosingClassSymbolTable().getSymbol().getName();
				//field offset
				FieldSymbol f = globalSymTable.getClassSymbolTable(class_name).getFieldSymbol(var_loc.getName());
				int offset = f.getOffset()+1;

				loc = ((BlockSymbolTable)var_loc.getScope()).getThisregister(d);
				
				String register = loc+"."+offset;

				return new LirReturnInfo(MoveEnum.MOVE_FIELD,register);

			} 
			//local variable
			else {
				BlockSymbolTable var_block = (BlockSymbolTable)var_loc.getScope();
				String label="";
				try {
					label = var_block.getVarSymbol(var_loc.getName()).getLabel();
				} 
				catch (SemanticError e) {
					System.out.println("==BUG==");
					System.out.println("Local Variable in VarLocation Error");
					System.exit(-1);
				}
				return new LirReturnInfo(MoveEnum.MOVE,label);
			}
		}
	}


	/**
	 * Translate array location access into LIR code
	 */
	public LirReturnInfo visit(ArrLocation arr_loc, Environment d) {

		//array location
		LirReturnInfo location_expr = arr_loc.getArrLocation().accept(this, d);
		String arrayLoc = "R" + d.getCurrentRegister();

		//we need to transfer the location only if it is a Memory||reg[reg] || reg.reg as
		//Move array instruction supports only registers
		if(location_expr.getRegisterLocation().charAt(0)!='R' || location_expr.getMoveCommand() != MoveEnum.MOVE){
			
			//runtime check
			//keren - for array fields
			//another fix by lior: location_expr is of type R1.R2 check that R2 != 0
			String loc_str = location_expr.getRegisterLocation();
			if(loc_str.contains("."))
			{
				loc_str = loc_str.substring(0, loc_str.indexOf("."));
			}else if(loc_str.contains("[")){
				loc_str = loc_str.substring(0, loc_str.indexOf("["));
			}
			d.addInstructionToBuilder("StaticCall", "__checkNullRef(a="+loc_str+")","Rdummy");
			
			d.addInstructionToBuilder(location_expr.getMoveCommand().toString(), location_expr.getRegisterLocation(),arrayLoc);
			d.incrementRegister();
		}else{
			arrayLoc = location_expr.getRegisterLocation();
		}
		
		

		//index
		//edited by lior: removed decrement optimization currently - lets make it work first
		//without optimizations
		LirReturnInfo index = arr_loc.getIndex().accept(this, d);
		String indexLoc = "";

		//again we only need to make a move instruction if index isnt allready a register
		
		if(index.getRegisterLocation().charAt(0) != 'R' || index.getMoveCommand() != MoveEnum.MOVE){
			indexLoc = "R" + d.getCurrentRegister();
			d.incrementRegister();
			d.addInstructionToBuilder(index.getMoveCommand().toString(), index.getRegisterLocation(),indexLoc);		
		} else{
			indexLoc = index.getRegisterLocation();
		}

		//runtime check
		d.addInstructionToBuilder("StaticCall", "__checkArrayAccess(a="+arrayLoc+",i="+indexLoc+")","Rdummy");


		return new LirReturnInfo(MoveEnum.MOVE_ARRAY,arrayLoc+"["+indexLoc+"]");
	}

	//Edited by lior:
	public LirReturnInfo visit(StaticCall static_call, Environment d) {

		String code;

		String class_name = static_call.getClassName();
		String method_name = static_call.getMethodName();
		if (static_call.getClassName().equals("Library"))
			code = "__"+method_name+"(";
		else
			code = "_"+class_name+"_"+method_name+"(";

		int i = 0;
		int arg_num = static_call.getArguments().size();
		for (Expr arg: static_call.getArguments()){
			LirReturnInfo arg_expr = arg.accept(this, d);
			String argLoc = "R" + d.getCurrentRegister();
			//check if arg_expr isnt a field
			if(arg_expr.getMoveCommand() != MoveEnum.MOVE){
				//transfer it to a new register
				d.addInstructionToBuilder(arg_expr.getMoveCommand(), arg_expr.getRegisterLocation(), argLoc);
				d.incrementRegister();
			}else{
				argLoc = arg_expr.getRegisterLocation();
			}

			if (!static_call.getClassName().equals("Library")){
				//code += globalSymTable.getClassSymbolTable(class_name)
				//		.getMethodSymbol(method_name).getFormalNames().get(i);

				/*Tomer added**/
				code += "p_"+i;
				code+="=";
			}

			code+=argLoc;
			if(i != arg_num-1){
				code+=",";
			}
			i++;
		}
		code += ")";


		//check if return is void
		types.Type returnType= ((types.TypeMethod)globalSymTable.getClassSymbolTable(class_name)
				.getMethodSymbol(method_name).getType()).getReturnType();

		//Line changed by lior:
		String resReg = "R"+d.getCurrentRegister();

		if(returnType instanceof TypeVoid){
			resReg = "Rdummy";
		}else{
			d.incrementRegister();
		}

		//library method call
		if (static_call.getClassName().equals("Library")){
			d.addInstructionToBuilder("Library", code, resReg);
		}
		else{
			d.addInstructionToBuilder("StaticCall", code, resReg);
		}
		
		return new LirReturnInfo(MoveEnum.MOVE,resReg);
	}

	//method edited by lior
	public LirReturnInfo visit(VirtualCall virtual_call, Environment d) {
		d.addToCurrentStringBuilder("#virtual call to "+virtual_call.getMethodName()+"'s location:\n");
		Expr obj_ref = virtual_call.getObjectReference();
		String objLoc = ""; //added by lior

		if (obj_ref != null){
			LirReturnInfo location = obj_ref.accept(this, d);

			//lior: no need for that
			//move it only if it not register already
			if(location.getRegisterLocation().charAt(0)!= 'R' || location.getMoveCommand() != MoveEnum.MOVE){
				
				//runtime check
				//keren - for array fields
				String loc_str = location.getRegisterLocation();
				if(loc_str.contains("."))
				{
					loc_str = loc_str.substring(0, loc_str.indexOf("."));
					d.addInstructionToBuilder("StaticCall","__checkNullRef(a="+loc_str+")","Rdummy");
				}				
				
				objLoc = "R" + d.getCurrentRegister();
				d.incrementRegister();
				d.addInstructionToBuilder(location.getMoveCommand().toString(),location.getRegisterLocation(),objLoc);

			}else{
				objLoc = location.getRegisterLocation();
			}

		} 
		else {
			objLoc = ((BlockSymbolTable)virtual_call.getScope()).getThisregister(d);
		}

		//get class name
		String class_name;
		if(obj_ref == null)
			class_name = ((symbolTableHandler.BlockSymbolTable)virtual_call.getScope())
			.getEnclosingClassSymbolTable().getSymbol().getName();
		else
			class_name = ((types.TypeClass)obj_ref.accept(new TypeEvaluator(globalSymTable), null)).getName();

		//get method name
		String method_name = virtual_call.getMethodName();

		//calculate method offset in virtul table
		int offset = d.getMethodOffset(class_name, method_name);

		//add beginning of instruction
		String code = "VirtualCall "+objLoc+"."+offset;

		//virtual call's arguments
		//int i = reg+1;

		code += "(";
		int arg_num = virtual_call.getArguments().size();;
		int i=0;
		for (Expr arg: virtual_call.getArguments()){
			//d.incrementRegister();
			LirReturnInfo arg_expr = arg.accept(this, d);
			String argLoc = "R" + d.getCurrentRegister();
			//check if arg_expr is a field or array
			if(arg_expr.getMoveCommand() != MoveEnum.MOVE){
				//transfer it to a new register
				d.addInstructionToBuilder(arg_expr.getMoveCommand(), arg_expr.getRegisterLocation(), argLoc);
				d.incrementRegister();
			}else{
				argLoc = arg_expr.getRegisterLocation();
			}
			//code += globalSymTable.getClassSymbolTable(class_name)
			//		.getMethodSymbol(method_name).getFormalNames().get(i)+"="+argLoc;
			/*Tomer added**/
			code += "p_"+i+"="+argLoc;

			if(i != arg_num-1)
				code+=",";
			//d.addLirInstruction(arg_expr.getMoveCommand().toString(),arg_expr.getRegisterLocation(),"R"+i);
			i++;
		}
		//for(int j=0; j<(i-reg-1); j++)
		//	d.decrementRegister();

		//check if return is void
		types.Type returnType= ((types.TypeMethod)globalSymTable.getClassSymbolTable(class_name)
				.getMethodSymbol(method_name).getType()).getReturnType();

		String newReg = "R" + d.getCurrentRegister();

		if(returnType instanceof TypeVoid){
			newReg ="Rdummy";
		}else{			
			//ask for new register
			d.incrementRegister();
		}

		code += "),"+newReg+"\n";

		d.addToCurrentStringBuilder(code);


		return new LirReturnInfo(MoveEnum.MOVE,newReg);
	}

	public LirReturnInfo visit(This t, Environment d) {
		String thisReg =((BlockSymbolTable)t.getScope()).getThisregister(d);

		return new LirReturnInfo(MoveEnum.MOVE,thisReg);
	}

	public LirReturnInfo visit(NewObject new_obj, Environment d) {
		String reg = "R" + d.getCurrentRegister();
		d.incrementRegister();

		String class_name = new_obj.getClassName();
		int size = 4 * globalSymTable.getClassSymbolTable(class_name).getCurrentClassFieldOffset() + 4;
		d.addInstructionToBuilder("Library","__allocateObject("+size + ")",reg);
		d.addInstructionToBuilder(MoveEnum.MOVE_FIELD,"_DV_" + class_name,reg+".0");

		return new LirReturnInfo(MoveEnum.MOVE,reg);
	}
	//edited by lior:
	public LirReturnInfo visit(NewArray new_arr, Environment d) {

		LirReturnInfo array_len_expr = new_arr.getArrayLength().accept(this, d);
		
		String loc ="R"+ d.getCurrentRegister();
		d.incrementRegister();

		d.addInstructionToBuilder(array_len_expr.getMoveCommand().toString(),
				array_len_expr.getRegisterLocation(),loc);

		//runtime check, check size of array
		d.addInstructionToBuilder("StaticCall","__checkSize(n="+loc+")","Rdummy");

		// save register with original array size, for array init loop
		String arrLenReg = "R" + d.getCurrentRegister();
		d.incrementRegister();
		d.addInstructionToBuilder(MoveEnum.MOVE, loc, arrLenReg);

		//get the actual length in bytes (multiply register by 4, in place)
		d.addInstructionToBuilder("Mul","4",arrLenReg);

		String resLoc = "R" + d.getCurrentRegister();
		d.incrementRegister();

		//library function - allocate memory for a new array
		d.addInstructionToBuilder("Library","__allocateArray("+arrLenReg+")",resLoc);

		// make labels for array initialization loop
		String initStartLabel = d.addLabel("array_init_start");
		String initEndLabel = d.addLabel("array_init_end");
		// initialize the array elements, written in IC specification:
		// object fields and array elements are initialized with default values,
		// 0 for integers, false for booleans, and null for references
		d.addInstructionToBuilder(initStartLabel+":", "", new_arr.getLineNum());
		d.addInstructionToBuilder("Dec",loc);
		// if finished initializing, jump to the end-label
		d.addInstructionToBuilder("Compare","0",loc);
		d.addInstructionToBuilder("JumpL",initEndLabel);
		// else, initialize the current array cell, and jump back to loop
		// 0 represents : zero (integers), false (booleans), null (objects)
		d.addInstructionToBuilder(MoveEnum.MOVE_ARRAY, "0", resLoc+"["+loc+"]");
		d.addInstructionToBuilder("JumpL",initStartLabel);
		// end-label we jump to when done initializing array cells
		d.addInstructionToBuilder(initEndLabel+":", "", new_arr.getLineNum());

		return new LirReturnInfo(MoveEnum.MOVE,resLoc);
	}

	public LirReturnInfo visit(Length length, Environment d) {
		LirReturnInfo array_expr = length.getExpression().accept(this, d);

		//runtime check
		//keren - for array fields
		String loc_str = array_expr.getRegisterLocation();
		if(loc_str.contains("."))
		{
			loc_str = loc_str.substring(0, loc_str.indexOf("."));
		}else if(loc_str.contains("[")){
			loc_str = loc_str.substring(0, loc_str.indexOf("["));
		}
		d.addInstructionToBuilder("StaticCall","__checkNullRef(a="+loc_str+")","Rdummy");
		
		
		d.addInstructionToBuilder("ArrayLength",loc_str,"R"+d.getCurrentRegister());	
		String res = "R"+d.getCurrentRegister();
		d.incrementRegister();

		return new LirReturnInfo(MoveEnum.MOVE,res);
	}

	public LirReturnInfo visit(Literal literal, Environment d) {
		String code = "";

		switch (literal.getType()){
		case TRUE:
			code = "1";
		case FALSE:
			code = "0";
			break;
		case NULL:
			code = "0";
			break;
		case STRING:
			String str_value = ((String) literal.getValue()).replaceAll("\n", "\\\\n");
			if (!d.containedInStringToLabelMap(str_value))
				d.addStringLabel(str_value);
			code = d.getStringLabel(str_value);
			break;
		case INTEGER:
			code = literal.getValue().toString();
			break;
		}
		
		
		//WHY????? Double Work
		// store literal value in register
		//String reg = "R"+d.getCurrentRegister();
		//d.incrementRegister();
		//d.addInstructionToBuilder(MoveEnum.MOVE, code, reg);

		return new LirReturnInfo(MoveEnum.MOVE, code);
	}

	//abstract class Expr, will never get here
	public LirReturnInfo visit(Expr expr, Environment d) {
		throw new RuntimeException("visiting Expr");
	}

	public LirReturnInfo visit(BlockExpr expr, Environment d) {
		return expr.accept(this, d);
	}

	public LirReturnInfo visit(UnaryOpExpr expr, Environment d) {
		LirReturnInfo operand = expr.getOperand().accept(this, d);

		String operandReg = operand.getRegisterLocation();
		
		// If one of the operand is Reg[Reg]
		if(operandReg.contains("[")){
			String fieldReg = "R" + d.getCurrentRegister();
			d.incrementRegister();
			d.addInstructionToBuilder(MoveEnum.MOVE_ARRAY, operandReg, fieldReg);
			operandReg = fieldReg;
		}

		// If one of the operand is Reg.Reg
		if(operandReg.contains(".")){
			String fieldReg = "R" + d.getCurrentRegister();
			d.incrementRegister();
			d.addInstructionToBuilder(MoveEnum.MOVE_FIELD, operandReg, fieldReg);
			operandReg = fieldReg;
		}


		if(expr.hasMathematicalOp()){
			return visitMathUnaryExpr(expr, d, operandReg);
		}
		return visitLogicalUnaryExpr(expr, d, operandReg);
	}

	public LirReturnInfo visitMathUnaryExpr(UnaryOpExpr expr, Environment d, String operand_reg) {
		d.addInstructionToBuilder("Neg",operand_reg);
		return new LirReturnInfo(MoveEnum.MOVE,operand_reg);
	}

	public LirReturnInfo visitLogicalUnaryExpr(UnaryOpExpr expr, Environment d, String operand_reg) {
		String true_label = "_true_label"+d.getLabelIndex();
		String end_label = "_end_label"+d.getLabelIndex();
		d.incrementLabelIndex();

		d.addInstructionToBuilder("Compare","0",operand_reg);
		d.addInstructionToBuilder("JumpTrue",true_label);
		d.addInstructionToBuilder("Move","0",operand_reg);
		d.addInstructionToBuilder("Jump",end_label);
		d.addToCurrentStringBuilder(true_label+":\n");
		d.addInstructionToBuilder("Move","1",operand_reg);
		d.addToCurrentStringBuilder(end_label+":\n");

		return new LirReturnInfo(MoveEnum.MOVE, operand_reg);
	}

	public LirReturnInfo visit(BinaryOpExpr expr, Environment d) {
		// according to LIR spec:
		// OP(a,b) means b:= b OP a
		// for example: x+y --> b=x, a=y
		LirReturnInfo operandB = expr.getLeftOperand().accept(this, d);
		LirReturnInfo operandA = expr.getRightOperand().accept(this, d);
		
		//Moving operandB anyway for doing calculations if not a register
		//even if its a memory and we make a string concatatntion
		String OperandBLoc = "R"+d.getCurrentRegister();
		d.incrementRegister();
		d.addInstructionToBuilder(operandB.getMoveCommand(), operandB.getRegisterLocation(),OperandBLoc);			
		
		
		//handle operand A:
		//move only if field or array
		String OperandALoc = operandA.getRegisterLocation();
		if(operandA.getMoveCommand() != MoveEnum.MOVE ){
			String res = "R"+d.getCurrentRegister();
			d.incrementRegister();
			d.addInstructionToBuilder(operandA.getMoveCommand(), OperandALoc,res);
			OperandALoc = res;			
		}
		

		//check if it is string concatenation
		types.Type lhs_type = (types.Type) expr.getLeftOperand().accept(new TypeEvaluator(globalSymTable), null);
		if (lhs_type.toString().compareTo("int") != 0){
			d.addInstructionToBuilder("Library","__stringCat("+operandB.getRegisterLocation()+","+operandA.getRegisterLocation()
					+")","R"+d.getCurrentRegister());
			
			String res = "R"+d.getCurrentRegister();
			d.incrementRegister();
			return new LirReturnInfo(MoveEnum.MOVE, res);
		}

		//create new register the operation will be kept in
		String resOp = "R" + d.getCurrentRegister();
		d.incrementRegister();
		



		if(expr.hasMathematicalOp()){
			return visitMathBinaryExpr(expr, d,OperandBLoc, OperandALoc);
		}
		return visitLogicalBinaryExpr(expr, d,OperandBLoc, OperandALoc);
	}

	public LirReturnInfo visitMathBinaryExpr(BinaryOpExpr expr, Environment d, String operandB_reg, String operandA_reg ){		
		String res = operandB_reg;

		switch (expr.getOp()){
		case PLUS:
			d.addInstructionToBuilder("Add",operandA_reg,operandB_reg);			
			break;
		case MINUS:
			d.addInstructionToBuilder("Sub",operandA_reg,operandB_reg);
			break;
		case MULTIPLY:
			d.addInstructionToBuilder("Mul",operandA_reg,operandB_reg);
			break;
		case DIVIDE:
			//runtime check
			d.addInstructionToBuilder("StaticCall","__checkZero(b="+operandA_reg+")","Rdummy");
			d.addInstructionToBuilder("Div",operandA_reg,operandB_reg);
			break;
		case MOD:
			d.addInstructionToBuilder("Mod",operandA_reg,operandB_reg);
			break;
		default:
			System.err.println("error");//will not get here
		}

		return new LirReturnInfo(MoveEnum.MOVE,res);
	}

	public LirReturnInfo visitLogicalBinaryExpr(BinaryOpExpr expr, Environment d, String operandB_reg, String operandA_reg){
		String true_label = "_true_label"+d.getLabelIndex();
		String false_label = "_false_label"+d.getLabelIndex();
		String end_label = "_end_label"+d.getLabelIndex();
		d.incrementLabelIndex();
		//for relational operators
		if (expr.getOp() != BinOperator.LAND && expr.getOp() != BinOperator.LOR){
			d.addInstructionToBuilder("Compare",operandA_reg,operandB_reg);
		}

		switch (expr.getOp()){
		case LAND:
			d.addInstructionToBuilder("Compare","0",operandB_reg);
			d.addInstructionToBuilder("JumpTrue",false_label);
			d.addInstructionToBuilder("Compare","0",operandA_reg);
			d.addInstructionToBuilder("JumpTrue",false_label);
			d.addInstructionToBuilder("Jump",true_label);
			d.addToCurrentStringBuilder(false_label+":\n");
			break;
		case LOR:
			d.addInstructionToBuilder("Compare","0",operandB_reg);
			d.addInstructionToBuilder("JumpFalse",true_label);
			d.addInstructionToBuilder("Compare","0","R"+operandA_reg);
			d.addInstructionToBuilder("JumpFalse",true_label);
			break;
		case LT:
			d.addInstructionToBuilder("JumpL",true_label);
			break;
		case GT:
			d.addInstructionToBuilder("JumpG",true_label);
			break;
		case LTE:
			d.addInstructionToBuilder("JumpLE",true_label);
			break;
		case GTE:
			d.addInstructionToBuilder("JumpGE",true_label);
			break;
		case EQUAL:
			d.addInstructionToBuilder("JumpTrue",true_label);
			break;
		case NEQUAL:
			d.addInstructionToBuilder("JumpFalse",true_label);
			break;
		default:
			System.err.println("error");//will not get here	
		}

		//false label
		d.addInstructionToBuilder("Move","0",operandB_reg);	
		d.addInstructionToBuilder("Jump",end_label);

		d.addToCurrentStringBuilder(true_label+":\n");
		d.addInstructionToBuilder("Move","1",operandB_reg);
		d.addToCurrentStringBuilder(end_label+":\n");

		return new LirReturnInfo(MoveEnum.MOVE,operandB_reg);
	}
}
