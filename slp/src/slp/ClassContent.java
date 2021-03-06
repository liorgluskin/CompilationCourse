package slp;

import java.util.List;
import java.util.ArrayList;

public class ClassContent {
	
	private List<Method> method_lst;
	private List<Field> field_lst;
	
	public ClassContent(Method m){
		method_lst = new ArrayList<Method>();
		method_lst.add(m);
		field_lst = new ArrayList<Field>();
	}
	
	public ClassContent(List<Field> fields){
		field_lst = new ArrayList<Field>();
		 for (Field field: fields){
			 field_lst.add(field);
		 }
		 method_lst = new ArrayList<Method>();
	}
	
	/**
	 * Returns the list of methods
	 */
	public List<Method> getMethodList(){
		return this.method_lst;
	}
	
	/**
	 * Returns the list of fields
	 */
	public List<Field> getFieldList(){
		return this.field_lst;
	}
	
	public void addMethod(Method method){
		this.method_lst.add(method);
	}
	
	public void addField(List<Field> fields){
		for (Field f: fields){
			this.field_lst.add(f);
		}
	}
}
