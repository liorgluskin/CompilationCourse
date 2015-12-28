package lir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Environment {

	//represents the index of str literal
	private int currentStringLiteralIndex = 0;

	//represents the index of lir labels
	private int currentLabelIndex = 0;

	//represents the next available string to use.
	protected int currentRegister = 1;

	//represents the map from string literals to the actual string
	protected Map<String,String> stringLiteralsMap = new HashMap<String,String>();

	//represents the map from string to label name
	protected Set<String> labels = new HashSet<String>();

	//represents the core Lir code
	protected StringBuilder lirCode = new StringBuilder();

	//string builder for main method code - keren
	protected StringBuilder mainMethodString = new StringBuilder();

	//keren
	protected StringBuilder currentBuilder = new StringBuilder();

	//represents the virtual table map
	//Note: It uses ArrayList because of the importance of the order
	//as we will be using its index as offset in Lir Code generation
	protected Map<String,LinkedList<String>> dispatchTables = new HashMap<String,LinkedList<String>>();

	//this array is responsible for keeping the order of classes seen till
	//current moment, to be able to print later the dispatch table in the right order
	protected ArrayList<String> classOrderKeeper = new ArrayList<String>();

	protected int incrementStringLiteralIndex(){ return ++currentStringLiteralIndex;}

	public int getLabelIndex(){
		return this.currentLabelIndex;
	}

	protected int incrementLabelIndex(){ return currentLabelIndex++;}

	public void incrementRegister(){ ++currentRegister;}

	public void decrementRegister(){ --currentRegister;}

	public boolean containedInStringToLabelMap(String str_key){
		return this.stringLiteralsMap.containsKey(str_key);
	}

	public void addStringLabel(String str){
		stringLiteralsMap.put(str, "str"+incrementStringLiteralIndex());
	}

	public String getStringLabel(String str_key){
		return stringLiteralsMap.get(str_key);
	}

	public int getCurrentRegister() {
		return currentRegister;
	}

	public String makeNewRegister() {
		incrementRegister();
		return "R"+getCurrentRegister();
	}

	// for while, break and continue statements, 
	// we must save the enclosing while loop label (test, end) for continue and break
	private String loopTestLabel = null;
	private String loopEndLabel = null;

	public String getLoopTestLabel() {
		return loopTestLabel;
	}
	public void setLoopTestLabel(String newLabel) {
		this.loopTestLabel = newLabel;
	}

	public String getLoopEndLabel() {
		return loopEndLabel;
	}
	public void setLoopEndLabel(String newLabel) {
		this.loopEndLabel = newLabel;
	}



	/**
	 * Adds a new string literal to the string literals map
	 * */
	public void addStringLiteral(String str){
		stringLiteralsMap.put(str, "str"+incrementStringLiteralIndex());
	}


	/**
	 * Adds a new label represenatation to the label map
	 * @return: the LIR label representation
	 * */
	public String addLabel(String labelName){
		String labelRep = "_"+labelName+currentLabelIndex;
		incrementLabelIndex();
		labels.add(labelRep);
		return labelRep;
	}


	/**
	 * adding virtual table for class without a super class. 
	 */
	public void addVirtualTable(String className, List<String> rawMethodsNames){

		//order keeper
		classOrderKeeper.add(className);

		LinkedList<String> methods = new LinkedList<String>();
		for(String rawMethod : rawMethodsNames){
			String method = "_"+className +"_"+ rawMethod;
			methods.add(method);
		}

		dispatchTables.put(className, methods);
	}

	//Note You can send to this functions also only the new defined functions
	//in className class. this function uses the functions which are defined
	//in superClassName class and adds the new function to it.
	//It uses ArrayList because Order has a meaning
	//@Parameters rawMethods means the name of function and only it without any addons.
	public void addVirtualTable(String className, String superClassName,List<String> rawMethodsNames){

		//order keeper
		classOrderKeeper.add(className);

		//get parent virtual table
		LinkedList<String> superClassVirtualTable = dispatchTables.get(superClassName);

		//should never enter this if statement
		if(superClassVirtualTable == null){
			System.out.println("==BUG==");
			System.out.println("Environment :dispatchTables dont have the supper class.");
			System.exit(-1);
		}

		//Init current class virtual table by adding to it all parents functions
		LinkedList<String> classVirtualTable = new LinkedList<String>();
		classVirtualTable.addAll(superClassVirtualTable);

		//looping over all rawMethods
		for(String rawMethod : rawMethodsNames){
			boolean isOverride = false;
			//setting function name
			String fullMethodName = "_"+className +"_"+ rawMethod;

			//checking if super class has a function with the same name
			for(String superMethodName :superClassVirtualTable ){

				//if the original function has is the overriden function then
				//rawMethod is its name and substring will return its name
				String originalMethodName = superMethodName
						.substring(superMethodName.length() - rawMethod.length());


				if(originalMethodName.equals(rawMethod)){
					isOverride = true;
					//overriding
					int index = superClassVirtualTable.indexOf(superMethodName);
					classVirtualTable.set(index, fullMethodName);
					break;

				} 

			}
			if(isOverride)
				continue;
			else{
				//new method
				classVirtualTable.add(fullMethodName);
			}


		}
		dispatchTables.put(className, classVirtualTable);
	}


	/**
	 * used to update current lir code
	 * @return lircode string-builder
	 */
	public StringBuilder getLirStringBuilder(){ return lirCode;};

	public void addToLirStringBuilder(String code){ this.lirCode.append(code);};
	public void addToCurrentStringBuilder(String code){ currentBuilder.append(code);};

	/**
	 * generates the code from all info in class including run time checks and strings
	 */
	public String generateLirCode(){

		StringBuilder codeGeneration = new StringBuilder();

		codeGeneration.append("\n############################################\n");
		codeGeneration.append("# String literals\n");
		//generate all string errors
		codeGeneration.append("str_null_ref: \"Runtime Error: Null pointer dereference!\"\n");
		codeGeneration.append("str_array_access: \"Runtime Error: Array index out of bounds!\"\n");
		codeGeneration.append("str_size: \"Runtime Error: Array allocation with negative array size!\"\n");
		codeGeneration.append("str_zero: \"Runtime Error: Division by zero!\"\n");

		//generate all string literals
		for(String str :stringLiteralsMap.keySet() ){
			codeGeneration.append(stringLiteralsMap.get(str)+": " + str +"\n");
		}
		codeGeneration.append("############################################\n");


		//generate dispatch table
		codeGeneration.append("\n############################################\n");
		codeGeneration.append("# Dispatch vectors\n");
		for(String className: classOrderKeeper){
			codeGeneration.append("_DV_"+className+": [");
			int commaCounter = 0; // for printing ','
			for(String method:dispatchTables.get(className) ){
				// if we are in final method, don't print comma
				if(commaCounter == dispatchTables.get(className).size()-1){
					codeGeneration.append(method);
				}else{
					codeGeneration.append(method+",");
				}
				commaCounter++;
			}
			codeGeneration.append("]");
			codeGeneration.append("\n");
		}
		codeGeneration.append("############################################\n");


		//add run time check functions implementation
		codeGeneration.append("\n############################################\n");
		codeGeneration.append("# Runtime Checks\n");
		codeGeneration.append("__checkNullRef:\n");
		codeGeneration.append("__checkArrayAccess:\n");
		codeGeneration.append("__checkSize:\n");
		codeGeneration.append("__checkZero:\n");

		codeGeneration.append("############################################\n");


		//add lirCode
		codeGeneration.append("\n\n");
		codeGeneration.append(lirCode);

		return codeGeneration.toString();
	}

	public int getMethodOffset(String className,String methodName){

		LinkedList<String> methods = dispatchTables.get(className);

		if(methods == null){
			System.out.println("==BUG==");
			System.out.println(methodName + " is not in "+className+ " dispatch table.");
			System.exit(-1);
		}

		for(String method : methods ){
			String originalMethodName = method.substring(method.length() - methodName.length());

			if(methodName.equals(originalMethodName)){
				System.out.println("methodName = "+methodName);////////////
				System.out.println("methods.indexOf(method) = "+methods.indexOf(method));//////////////
				return methods.indexOf(method);
			}
		}

		//should not come here
		System.out.println("==BUG==");
		System.out.println(methodName + " is not in "+className+ " dispatch table.");
		System.exit(-1);
		return -1;
	}

	/**
	 * Appends a new LIR instruction to the LIR code
	 * of LIR format: '#lineNum\n Instruction opB,opA'
	 */
	/*
	public void addLirInstruction(String instruction, String opA, String opB, int lineNum){
		if(lineNum != -1){
			lirCode.append("# line number: "+lineNum+"\n");
		}
		lirCode.append(instruction+" "+opA);
		if(opB != null){
			lirCode.append(","+opB);
		}
		lirCode.append("\n");
	}
	public void addLirInstruction(String instruction, String opA, String opB){
		addLirInstruction(instruction, opA, opB, -1);
	}
	public void addLirInstruction(String instruction, String op, int lineNum){
		addLirInstruction(instruction, op, null, -1);
	}
	public void addLirInstruction(String instruction, String op){
		addLirInstruction(instruction, op, -1);
	}

	public void addLirInstruction(MoveEnum move, String opA, String opB, int lineNum) {
		addLirInstruction(move.toString(), opA, opB, lineNum);	
	}
	public void addLirInstruction(MoveEnum move, String opA, String opB) {
		addLirInstruction(move.toString(), opA, opB, -1);	
	}
	 */
	//functions for main string bulider - keren
	public StringBuilder getMainStringBuilder(){
		return mainMethodString;
	}

	public void addToMainStringBuilder(String code){
		mainMethodString.append(code);
	}

	public StringBuilder getCurrentStringBuilder(){
		return currentBuilder;
	}

	public void setCurrentStringBuilder(StringBuilder st){
		currentBuilder = st;
	}

	public void addInstructionToBuilder(String instruction, String opA, String opB, int lineNum){
		if(lineNum != -1){
			currentBuilder.append("# line number: "+lineNum+"\n");
		}
		currentBuilder.append(instruction+" "+opA);
		if(opB != null){
			currentBuilder.append(","+opB);
		}
		currentBuilder.append("\n");
	}

	public void addInstructionToBuilder(String instruction, String opA, String opB){
		addInstructionToBuilder(instruction, opA, opB, -1);
	}
	public void addInstructionToBuilder(String instruction, String op, int lineNum){
		addInstructionToBuilder(instruction, op, null, -1);
	}
	public void addInstructionToBuilder(String instruction, String op){
		addInstructionToBuilder(instruction, op, -1);
	}

	public void addInstructionToBuilder(MoveEnum move, String opA, String opB, int lineNum) {
		addInstructionToBuilder(move.toString(), opA, opB, lineNum);	
	}
	public void addInstructionToBuilder(MoveEnum move, String opA, String opB) {
		addInstructionToBuilder(move.toString(), opA, opB, -1);	
	}

}
