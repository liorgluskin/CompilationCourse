package lir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Environment {

	//represents the index of str literal
	private int currentStringLiteralIndex = 0;

	//represents the index of lir labels
	private int currentLabelIndex = 0;

	//represents the next available string to use.
	protected int currentRegister = 0;

	//represents the map from string literals to the actual string
	protected Map<String,String> stringLiteralsMap = new HashMap<String,String>();

	//represents the map from string to label name
	protected Set<String> labels = new HashSet<String>();

	//represents the core Lir code
	protected StringBuilder lirCode = new StringBuilder();

	//represents the virtual table map
	//Note: It uses ArrayList because of the importance of the order
	//as we will be using its index as offset in Lir Code generation
	protected Map<String,ArrayList<String>> dispatchTables = new HashMap<String,ArrayList<String>>();

	//this array is responsible for keeping the order of classes seen till
	//current moment, to be able to print later the dispatch table in the right order
	protected ArrayList<String> classOrderKeeper = new ArrayList<String>();

	protected int incrementStringLiteralIndex(){ return ++currentStringLiteralIndex;}

	protected int incrementLabelIndex(){ return currentLabelIndex++;}

	public void incrementRegistr(){ ++currentRegister;}

	public void decrementRegistr(){ --currentRegister;}

	////////////////////////////////////////////////////////
	public int getCurrentRegistr() {return currentRegister;}

	public int makeNewRegistr() {
		incrementRegistr();
		return getCurrentRegistr();
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


	//////////////////////////////////////////////////


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
		String labelRep = "_"+labelName+incrementLabelIndex();
		labels.add(labelRep);
		return labelRep;
	}


	/**
	 * adding virtual table for class without a super class. 
	 */
	public void addVirtualTable(String className, List<String> rawMethodsNames){

		//order keeper
		classOrderKeeper.add(className);

		ArrayList<String> methods = new ArrayList<String>();
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
		ArrayList<String> superClassVirtualTable = dispatchTables.get(superClassName);

		//should never enter this if statement
		if(superClassVirtualTable == null){
			System.out.println("==BUG==");
			System.out.println("Environment :dispatchTables dont have the supper class.");
			System.exit(-1);
		}

		//Init current class virtual table by adding to it all parents functions
		ArrayList<String> classVirtualTable = new ArrayList<String>();
		classVirtualTable.addAll(superClassVirtualTable);

		//looping over all rawMethods
		for(String rawMethod : rawMethodsNames){
			//checking if super class has a function with the same name
			for(String superMethodName :superClassVirtualTable ){

				//if the original function has is the overriden function then
				//rawMethod is its name and substring will return its name
				String originalMethodName = superMethodName
						.substring(superMethodName.length() - rawMethod.length());

				//setting function name
				String fullMethodName = "_"+className +"_"+ rawMethod;

				if(originalMethodName.equals(rawMethod)){
					//overriding
					int index = superClassVirtualTable.indexOf(superMethodName);
					classVirtualTable.set(index, fullMethodName);

				} else{
					//new method
					classVirtualTable.add(fullMethodName);
				}
			}
		}
	}


	/**
	 * used to update current lir code
	 * @return lircode string-builder
	 */
	public StringBuilder getLirStringBuilder(){ return lirCode;};


	/**
	 * generates the code from all info in class including run time checks and strings
	 */
	public String generateLirCode(){

		StringBuilder codeGeneration = new StringBuilder();

		//generate all string errors
		codeGeneration.append("str_null_ref: \"Runtime Error: Null pointer dereference!\"\n");
		codeGeneration.append("str_array_access: \"Runtime Error: Array index out of bounds!\"\n");
		codeGeneration.append("str_size: \"Runtime Error: Array allocation with negative array size!\"\n");
		codeGeneration.append("str_zero: \"Runtime Error: Division by zero!\"\n");

		//generate all string literals
		for(String str :stringLiteralsMap.keySet() ){
			codeGeneration.append(stringLiteralsMap.get(str)+": \"" + str +"\"\n");
		}

		//generate dispatch table
		for(String className: classOrderKeeper){
			codeGeneration.append("_DV_"+className+": [");
			for(String method:dispatchTables.get(className) ){
				codeGeneration.append(method+",");
			}
			codeGeneration.setCharAt(codeGeneration.length()-1, ']');
			codeGeneration.append("\n");
		}

		//add runTimeCheckFunctions
		//TO DO: run time checks

		//add lirCode
		codeGeneration.append("\n\n");
		codeGeneration.append(lirCode);

		return codeGeneration.toString();
	}

	public int getMethodOffset(String className,String methodName){

		ArrayList<String> methods = dispatchTables.get(className);
		if(methods == null){
			System.out.println("==BUG==");
			System.out.println(methodName + " is not in "+className+ " dispatch table.");
			System.exit(-1);
		}
		return methods.indexOf(methodName);

	}

	/**
	 * Appends a new LIR instruction to the LIR code
	 * of LIR format: '#lineNum\n Instruction opB,opA'
	 */
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


}