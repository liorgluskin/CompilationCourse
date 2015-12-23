package lir;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Environment {
		
	//represents the index of str label
	private int currentStringLabelIndex = 0;
	
	//represents the next available string to use.
	protected int currentRegister = 0;
	
	//represents the map from string labels to the actual string
	protected Map<String,String> stringToLabelMap = new HashMap<>();
	
	//represents the core Lir code
	protected StringBuilder lirCode = new StringBuilder();
	
	//represents the virtual table map
	//Note: It uses ArrayList because of the importance of the order
	//as we will be using its index as offset in Lir Code generation
	protected Map<String,ArrayList<String>> dispacherTables = new HashMap<>();
		
	//this array is responsible for keeping the order of classes seen till
	//current moment, to be able to print later the dispatcher table in the right order
	protected ArrayList<String> classOrderKeeper = new ArrayList<>();
	
	protected int incrementStringLabelIndex(){ return ++currentStringLabelIndex;};
	
	public void incrementRegistr(){ ++currentRegister;};
	
	public void decrementRegistr(){ --currentRegister;};
	
	public void addStringLabel(String str){
		stringToLabelMap.put(str, "str"+incrementStringLabelIndex());
	}
	
	//adding virtual table for class that doent have a super class.
	public void addVirtualTable(String className, List<String> rawMethodsNames){
		
		//order keeper
		classOrderKeeper.add(className);
		
		ArrayList<String> methods = new ArrayList<>();
		for(String rawMethod : rawMethodsNames){
			String method = "_"+className +"_"+ rawMethod;
			methods.add(method);
		}
		
		dispacherTables.put(className, methods);
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
		ArrayList<String> superClassVirtualTable = dispacherTables.get(superClassName);
		
		//should never enter this if statement
		if(superClassVirtualTable == null){
			System.out.println("==BUG==");
			System.out.println("Environment, line 57:dispacherTables dont have the supper class.");
			System.exit(-1);
		}
		
		//Init current class virtual table by adding to it all parents functions
		ArrayList<String> classVirtualTable = new ArrayList<>();
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
		
	
	//used to update current lir code
	public StringBuilder getLirStringBuilder(){ return lirCode;};
	
	//generates the code from all info in class including
	//run time checks and strings
	public String generateLirCode(){
		
		StringBuilder codeGeneration = new StringBuilder();

		//generate all string errors
		codeGeneration.append("str_null_ref: \"Runtime Error: Null pointer dereference!\"\n");
		codeGeneration.append("str_array_access: \"Runtime Error: Array index out of bounds!\"\n");
		codeGeneration.append("str_size: \"Runtime Error: Array allocation with negative array size!\"\n");
		codeGeneration.append("str_zero: \"Runtime Error: Division by zero!\"\n");
		
		//generate all string literals
		for(String str :stringToLabelMap.keySet() ){
			codeGeneration.append(stringToLabelMap.get(str)+": \"" + str +"\"\n");
		}
		
		//generate dispatcher table
		for(String className: classOrderKeeper){
			codeGeneration.append("_DV_"+className+": [");
			for(String method:dispacherTables.get(className) ){
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
		
		ArrayList<String> methods = dispacherTables.get(className);
		if(methods == null){
			System.out.println("==BUG==");
			System.out.println(methodName + " is not in "+className+ " dispatcher table.");
			System.exit(-1);
		}
		return methods.indexOf(methodName);
		
	}

}
