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
	protected Map<String,ArrayList<String>> dispacherTables = new HashMap<>();
	
	protected int incrementStringLabelIndex(){ return ++currentStringLabelIndex;};
	
	public void incrementRegistr(){ ++currentRegister;};
	
	public void decrementRegistr(){ --currentRegister;};
	
	public void addStringLabel(String str){
		stringToLabelMap.put(str, "str"+incrementStringLabelIndex());
	}
	
	public void addVirtualTable(String className, List<String> rawMethodsNames){
		
		ArrayList<String> methods = new ArrayList<>();
		for(String rawMethod : rawMethodsNames){
			String method = "_"+className +"_"+ rawMethod;
			methods.add(method);
		}
		
		dispacherTables.put(className, methods);
	}
	public void addVirtualTable(String className, String superClassName,List<String> rawMethodsNames){
		
		//get parent virtual table
		ArrayList<String> superClassVirtualTable = dispacherTables.get(superClassName);
		
		//Init current class virtual table by adding to it all parents functions
		ArrayList<String> classVirtualTable = new ArrayList<>();
		classVirtualTable.addAll(superClassVirtualTable);
		
		
		for(String rawMethod : rawMethodsNames){
			for(String superMethodName :superClassVirtualTable ){
							
				String originalMethodName = superMethodName
						.substring(superMethodName.length() - rawMethod.length());
				
				String fullMethodName = "_"+className +"_"+ rawMethod;
						
				if(originalMethodName.equals(rawMethod)){
					//overriding here
					int index = superClassVirtualTable.indexOf(superMethodName);
					classVirtualTable.set(index, fullMethodName);
					
				} else{
					//new methods
					classVirtualTable.add(fullMethodName);
				}
			}
		}
	}
	
	public void addVirtualMethod(String className, String method){
		if(dispacherTables.containsKey(className)){
			dispacherTables.get(className).add(method);
		} else{
			dispacherTables.put(className,new ArrayList<String>(){{add(method);}} );
		}
	}
	
	public StringBuilder getLirCode(){ return lirCode;};	
	

}
