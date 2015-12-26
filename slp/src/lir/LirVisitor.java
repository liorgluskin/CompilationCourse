package lir;

import semantic.SemanticError;
import semantic.TypeEvaluator;
import slp.*;
import symbolTableHandler.*;

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

	/**
	 * Common handler for static and virtual methods
	 */
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


	/**
	 * Translate Static Method into LIR code
	 */
	public LirReturnInfo visit(StaticMethod staticMethod, Environment d) {
		methodVisitor(staticMethod,d);
		return null; //nothing to be returned
	}


	/**
	 * Translate Class (virtual) Method into LIR code
	 */
	public LirReturnInfo visit(ClassMethod classMethod, Environment d) {
		methodVisitor(classMethod,d);
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
	private void lirAssignHandler(String location, String value, int lineNum, Environment d){

		// handle a move to a register
		if(location.startsWith("R")){
			d.addLirInstruction(MoveEnum.MOVE, value, location, lineNum);
		}

		// handle a move to memory
		else if(isMemoryVar(location)){
			String register = value;
			// attempting to move from memory to memory, illegal in LIR
			// must first move value to register, then move register value to location
			if(isMemoryVar(value)){
				register = d.makeNewRegister();
				// move value to register
				d.addLirInstruction(MoveEnum.MOVE, value, register, lineNum);				
			}
			// move from register to location
			d.addLirInstruction(MoveEnum.MOVE, register, location, lineNum);
		}

		// handle a move to a field
		// field is of format 'Reg.Reg' or 'Reg.Immediate'
		else if(location.contains(".")){
			d.addLirInstruction(MoveEnum.MOVE_FIELD, value, location, lineNum);
		}

		// handle a move to an array-location
		// only array ends with ']'
		else if(location.endsWith("]")){ 
			d.addLirInstruction(MoveEnum.MOVE_ARRAY, value, location, lineNum);
		}
	}

	/**
	 * Translate Assignment statement into LIR code
	 */
	public LirReturnInfo visit(AssignStmt stmt, Environment d) {
		
		// get the assignment right-hand-side info first
		LirReturnInfo valueInfo = stmt.getRhs().accept(this, d);
		String valueReg = valueInfo.getRegisterLocation(); // register where value is stored
		
		// get the label of the assignment location
		LirReturnInfo locationInfo = stmt.getLocation().accept(this, d);
		String locationReg = locationInfo.getRegisterLocation(); // register where location is stored
		
		// handle the assignment
		lirAssignHandler(locationReg, valueReg, stmt.getLineNum(), d);
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
			MethodSymbolTable mst = (MethodSymbolTable) idStmt.getScope();
			VariableSymbol localVarSym;
			try {
				localVarSym = mst.getVarSymbolLocal(idStmt.getName());
				String varLabel = localVarSym.getLabel();
			} catch (SemanticError e) {
				// in case method symbol table does not contain parameter
				// should never get here, already checked in Semantic part
				e.printStackTrace();
			}			
			lirAssignHandler(idStmt.getName(), reg, idStmt.getLineNum(), d);
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

	public LirReturnInfo visit(VarLocation var_loc, Environment d) {
		int reg = d.getCurrentRegister();
		
		if (var_loc.getLocation() != null){
			LirReturnInfo location_expr = var_loc.getLocation().accept(this, d);
			d.addLirInstruction(location_expr.getMoveCommand().toString(), location_expr.getRegisterLocation(), "R"+reg);
			
			//runtime check
			d.addLirInstruction("StaticCall", "__checkNullRef(a=R"+reg+")", "Rdummy");
			
			//field offset
			types.Type class_type = (types.Type)(var_loc.getLocation().accept(new TypeEvaluator(globalSymTable), null));
			String class_name = new String();
			if(class_type instanceof types.TypeClass)
				class_name = class_type.getName();
			else
				System.err.println("Error in line 310\n");
			
			FieldSymbol f = globalSymTable.getClassSymbolTable(class_name).getFieldSymbol(var_loc.getName());
			int offset = f.getOffset()+1;
			
			return new LirReturnInfo(MoveEnum.MOVE_FIELD,"R"+reg+"."+offset);
		}
		else{
			//field
			if (((BlockSymbolTable)var_loc.getScope()).isField(var_loc.getName())){
				String class_name = ((BlockSymbolTable)var_loc.getScope())
						.getEnclosingClassSymbolTable().getSymbol().getName();
				//field offset
				FieldSymbol f = globalSymTable.getClassSymbolTable(class_name).getFieldSymbol(var_loc.getName());
				int offset = f.getOffset()+1;
				String register = "R"+reg+"."+offset;
				
				d.addLirInstruction("Move", "this", "R"+reg);
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
					//will never get here
				}
				return new LirReturnInfo(MoveEnum.MOVE,label);
			}
		}
	}

	public LirReturnInfo visit(ArrLocation arr_loc, Environment d) {
		int reg = d.getCurrentRegister();
		
		//array location
		LirReturnInfo location_expr = arr_loc.getArrLocation().accept(this, d);
		d.addLirInstruction(location_expr.getMoveCommand().toString(), location_expr.getRegisterLocation(),"R"+reg);
		
		//runtime check
		d.addLirInstruction("StaticCall", "__checkNullRef(a=R"+reg+")","Rdummy");
		
		//index
		d.incrementRegister();
		LirReturnInfo index = arr_loc.getIndex().accept(this, d);
		d.decrementRegister();
		d.addLirInstruction(index.getMoveCommand().toString(), index.getRegisterLocation(),"R"+(reg+1));

		//runtime check
		d.addLirInstruction("StaticCall", "__checkArrayAccess(a=R"+reg+",i=R"+(reg+1)+")","Rdummy");
		
		return new LirReturnInfo(MoveEnum.MOVE_ARRAY,"R"+reg+"[R"+(reg+1)+"]");
	}

	public LirReturnInfo visit(StaticCall static_call, Environment d) {
		int reg = d.getCurrentRegister();

		int i = reg;
		for (Expr arg: static_call.getArguments()){
			LirReturnInfo arg_expr = arg.accept(this, d);
			d.addToLirStringBuilder("# argument #"+(i-reg)+":\n");
			d.addLirInstruction(arg_expr.getMoveCommand().toString(),arg_expr.getRegisterLocation(),"R"+i);
			i++;
			d.incrementRegister();
		}
		for(int j=0; j<(i-reg); j++)
			d.decrementRegister();

		//library method call
		if (static_call.getClassName().equals("Library"))
			return libraryVisit(static_call, d, reg);

		String class_name = static_call.getClassName();
		String method_name = static_call.getMethodName();
		String code = "_"+class_name+"_"+method_name+"(";

		//parameters
		int arg_num = static_call.getArguments().size();
		for(i = 0; i < arg_num; i++){
			code += globalSymTable.getClassSymbolTable(class_name)
					.getMethodSymbol(method_name).getFormalNames().get(i)+"=R"+(reg+i);
			if(i != arg_num-1)
				code+=",";
		}
		code += ")";

		d.addLirInstruction("StaticCall", code, "R"+reg);
		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo libraryVisit(StaticCall static_call, Environment d, int reg){
		String code = "__"+static_call.getMethodName()+"(";

		int arg_num = static_call.getArguments().size();
		for(int i = 0; i < arg_num; i++){
			code += "R"+(i+reg)+",";
			if(i != arg_num-1)
				code+=",";
		}
		code += ")";

		d.addLirInstruction("Library", code, "R"+reg);
		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(VirtualCall virtual_call, Environment d) {
		int reg = d.getCurrentRegister();
		d.addToLirStringBuilder("#virtual call to "+virtual_call.getMethodName()+"'s location:\n");
		Expr obj_ref = virtual_call.getObjectReference();

		if (obj_ref != null){
			LirReturnInfo location = obj_ref.accept(this, d);
			d.addLirInstruction(location.getMoveCommand().toString(),location.getRegisterLocation(),"R"+reg);

			//runtime check
			d.addLirInstruction("StaticCall","__checkNullRef(a=R"+reg+")","Rdummy");
		} 
		else {
			d.addLirInstruction("Move","this","R"+reg);
		}

		//virtual call's arguments
		int i = reg+1;
		for (Expr arg: virtual_call.getArguments()){
			d.incrementRegister();
			LirReturnInfo arg_expr = arg.accept(this, d);
			d.addToLirStringBuilder("#argument #"+(i-reg-1)+":\n");
			d.addLirInstruction(arg_expr.getMoveCommand().toString(),arg_expr.getRegisterLocation(),"R"+i);
			i++;
		}
		for(int j=0; j<(i-reg-1); j++)
			d.decrementRegister();

		d.addToLirStringBuilder("VirtualCall R"+reg+".");

		String class_name;
		if(obj_ref == null)
			class_name = ((symbolTableHandler.BlockSymbolTable)virtual_call.getScope())
			.getEnclosingClassSymbolTable().getSymbol().getName();
		else
			class_name = ((types.TypeClass)obj_ref.accept(new TypeEvaluator(globalSymTable), null)).getName();

		String method_name = virtual_call.getMethodName();
		int offset = d.getMethodOffset(class_name, method_name);

		//parameters
		String code = offset+"(";
		int arg_num = virtual_call.getArguments().size();
		for(i = 0; i < arg_num; i++){
			code += globalSymTable.getClassSymbolTable(class_name)
					.getMethodSymbol(method_name).getFormalNames().get(i)+"=R"+(reg+i+1);
			if(i != arg_num-1)
				code+=",";
		}
		code += "),R"+reg+"\n";

		d.addToLirStringBuilder(code);
		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(This t, Environment d) {
		int reg = d.getCurrentRegister();
		d.addLirInstruction("Move","this","R"+reg);

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(NewObject new_obj, Environment d) {
		int reg = d.getCurrentRegister();

		String class_name = new_obj.getClassName();
		int size = 4 * globalSymTable.getClassSymbolTable(class_name).getCurrentClassFieldOffset() + 4;
		d.addLirInstruction("Library","__allocateObject("+size + ")","R"+reg);
		d.addLirInstruction(MoveEnum.MOVE_FIELD,"_DV_" + class_name,"R"+reg+".0");

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(NewArray new_arr, Environment d) {
		int reg = d.getCurrentRegister();

		LirReturnInfo array_len_expr = new_arr.getArrayLength().accept(this, d);
		d.addLirInstruction(array_len_expr.getMoveCommand().toString(),
				array_len_expr.getRegisterLocation(),"R"+reg);
		//get the actual length in bytes (multiply register by 4, in place)
		d.addLirInstruction("Mul","4","R"+reg);

		//runtime check
		d.addLirInstruction("StaticCall","__checkSize(n=R"+reg+")","Rdummy");

		//library function - allocate memory for a new array
		d.addLirInstruction("Library","__allocateArray(R"+reg+")","R"+reg);

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(Length length, Environment d) {
		int reg = d.getCurrentRegister();

		LirReturnInfo array_expr = length.getExpression().accept(this, d);
		d.addLirInstruction(array_expr.getMoveCommand().toString(),array_expr.getRegisterLocation(),"R"+reg);

		//runtime check
		d.addLirInstruction("StaticCall","__checkNullRef(a=R"+reg+")","Rdummy");

		//we don't need the array's address anymore, reuse the register
		d.addLirInstruction("ArrayLength","R"+reg,"R"+reg);		

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
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

		d.addToLirStringBuilder(code);
		return new LirReturnInfo(MoveEnum.MOVE, "");
	}

	//abstract class Expr, will never get here
	public LirReturnInfo visit(Expr expr, Environment d) {
		throw new RuntimeException("visiting Expr");
	}

	public LirReturnInfo visit(BlockExpr expr, Environment d) {
		return expr.accept(this, d);
	}

	public LirReturnInfo visit(UnaryOpExpr expr, Environment d) {
		int reg = d.getCurrentRegister();
		LirReturnInfo operand = expr.getOperand().accept(this, d);
		d.addLirInstruction(operand.getMoveCommand().toString(),operand.getRegisterLocation(),"R"+reg);

		if(expr.hasMathematicalOp())
			return visitMathUnaryExpr(expr, d, reg);
		return visitLogicalUnaryExpr(expr, d, reg);
	}

	public LirReturnInfo visitMathUnaryExpr(UnaryOpExpr expr, Environment d, int reg) {
		d.addLirInstruction("Neg","R"+reg);
		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visitLogicalUnaryExpr(UnaryOpExpr expr, Environment d, int reg) {
		String true_label = "_true_label"+d.getLabelIndex();
		String end_label = "_end_label"+d.getLabelIndex();
		d.incrementLabelIndex();

		d.addLirInstruction("Compare","0","R"+reg);
		d.addLirInstruction("JumpTrue",true_label);
		d.addLirInstruction("Move","0","R"+reg);
		d.addLirInstruction("Jump",end_label);
		d.addToLirStringBuilder(true_label+":\n");
		d.addLirInstruction("Move","1","R"+reg);
		d.addToLirStringBuilder(end_label+":\n");

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visit(BinaryOpExpr expr, Environment d) {
		int reg = d.getCurrentRegister();

		LirReturnInfo operand1 = expr.getLeftOperand().accept(this, d);
		d.addLirInstruction(operand1.getMoveCommand().toString(),operand1.getRegisterLocation(),"R"+reg);

		d.incrementRegister();
		LirReturnInfo operand2 = expr.getRightOperand().accept(this, d);
		d.decrementRegister();
		d.addLirInstruction(operand2.getMoveCommand().toString(),operand2.getRegisterLocation(),"R"+(reg+1));

		if(expr.hasMathematicalOp())
			return visitMathBinaryExpr(expr, d, reg);
		return visitLogicalBinaryExpr(expr, d, reg);
	}

	public LirReturnInfo visitMathBinaryExpr(BinaryOpExpr expr, Environment d, int reg){		
		switch (expr.getOp()){
		case PLUS:
			types.Type lhs_type = (types.Type) expr.getLeftOperand().accept(new TypeEvaluator(globalSymTable), null);
			//mathematical addition
			if (lhs_type.toString().compareTo("int") == 0){
				d.addLirInstruction("Add","R"+(reg+1),"R"+reg);
			}
			//string concatenation
			else {
				d.addLirInstruction("Library","__stringCat(R"+reg+",R"+(reg+1)+")","R"+reg);
			}
			break;
		case MINUS:
			d.addLirInstruction("Sub","R"+(reg+1),"R"+reg);
			break;
		case MULTIPLY:
			d.addLirInstruction("Mul","R"+(reg+1),"R"+reg);
			break;
		case DIVIDE:
			//runtime check
			d.addLirInstruction("StaticCall","__checkZero(b=R"+(reg+1)+")","Rdummy");
			d.addLirInstruction("Div","R"+(reg+1),"R"+reg);
			break;
		case MOD:
			d.addLirInstruction("Mod","R"+(reg+1),"R"+reg);
			break;
		default:
			System.err.println("error");//will not get here
		}

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}

	public LirReturnInfo visitLogicalBinaryExpr(BinaryOpExpr expr, Environment d, int reg){
		String true_label = "_true_label"+d.getLabelIndex();
		String false_label = "_false_label"+d.getLabelIndex();
		String end_label = "_end_label"+d.getLabelIndex();
		d.incrementLabelIndex();

		//for relational operators
		if (expr.getOp() != BinOperator.LAND && expr.getOp() != BinOperator.LOR){
			d.addLirInstruction("Compare","R"+(reg+1),"R"+reg);
		}

		switch (expr.getOp()){
		case LAND:
			d.addLirInstruction("Compare","0","R"+reg);
			d.addLirInstruction("JumpTrue",false_label);
			d.addLirInstruction("Compare","0","R"+(reg+1));
			d.addLirInstruction("JumpTrue",false_label);
			d.addLirInstruction("Jump",true_label);
			d.addToLirStringBuilder(false_label+":\n");
			break;
		case LOR:
			d.addLirInstruction("Compare","0","R"+reg);
			d.addLirInstruction("JumpFalse",true_label);
			d.addLirInstruction("Compare","0","R"+(reg+1));
			d.addLirInstruction("JumpFalse",true_label);
			break;
		case LT:
			d.addLirInstruction("JumpL",true_label);
			break;
		case GT:
			d.addLirInstruction("JumpG",true_label);
			break;
		case LTE:
			d.addLirInstruction("JumpLE",true_label);
			break;
		case GTE:
			d.addLirInstruction("JumpGE",true_label);
			break;
		case EQUAL:
			d.addLirInstruction("JumpTrue",true_label);
			break;
		case NEQUAL:
			d.addLirInstruction("JumpFalse",true_label);
			break;
		default:
			System.err.println("error");//will not get here	
		}

		//false label
		d.addLirInstruction("Move","0","R"+reg);	
		d.addLirInstruction("Jump",end_label);

		d.addToLirStringBuilder(true_label+":\n");
		d.addLirInstruction("Move","1","R"+reg);
		d.addToLirStringBuilder(end_label+":\n");

		return new LirReturnInfo(MoveEnum.MOVE,"R"+reg);
	}
}
