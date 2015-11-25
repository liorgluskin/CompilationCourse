package slp;

import java.util.List;
import java.util.ArrayList;

public class ClassContent {
	
	private List<FieldOrMethod> fieldOrMethodLst = new ArrayList<FieldOrMethod>();

	
	public ClassContent(Method m){
		fieldOrMethodLst = new ArrayList<FieldOrMethod>();
		fieldOrMethodLst.add(m);
	}
	
	public ClassContent(List<Field> fields){
		 for (Field field: fields){
			 fieldOrMethodLst.add(field);
		 }
	}
	
	/**
	 * Returns the list of methods
	 */
	public List<FieldOrMethod> getFieldsAndMethods(){
		return this.fieldOrMethodLst;
	}
	
	public void  addFieldOrMethod(Method m){
		fieldOrMethodLst.add(m);
	}
	
	public void addFieldOrMethod(List<Field> fields){
		for(Field f : fields){
			fieldOrMethodLst.add(f);
		}
	}
	
}