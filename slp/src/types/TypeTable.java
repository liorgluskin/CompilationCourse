package types;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import semantic.SemanticError;
import slp.ClassDecl;

/**
 * Main class to hold the type table for the input program
 *
 */
public class TypeTable {
    private static Map<String,TypeClass> programClasses = new HashMap<String,TypeClass>();
    private static Map<Type,TypeArray> programArrays = new HashMap<Type,TypeArray>();
    private static Map<String,TypeMethod> programMethods = new HashMap<String,TypeMethod>();
    private static Map<String,Type> programPrimitives = new HashMap<String,Type>();
    protected static int idCounter = 0;
    
    public static Map<String, Type> getProgramPrimitives() {
		return programPrimitives;
	}

    /**
     * initialize the type table
     */
    public static void initTypeTable(){
    	programPrimitives.put("int", new TypeInt());
    	programPrimitives.put("boolean", new TypeBoolean());
    	programPrimitives.put("null", new TypeNull());
    	programPrimitives.put("string", new TypeString());
    	programPrimitives.put("void", new TypeVoid());
    }
    

    /**
     *  Returns unique array type object
     * 
     */
    public static TypeArray typeArray(Type elemType) {
       if (programArrays.containsKey(elemType)) {
          // array type object already created � return it
          return programArrays.get(elemType);
       }
       else {
          // object doesn't exist � create and return it
          TypeArray arrt = new TypeArray(elemType);
          programArrays.put(elemType,arrt);
          return arrt;
       }
    }
    
    /**
     * Adds a new TypeClass entry to TypeTable. If the class is already defined
     * or extends a class that was not previously defined, throws SemanticError. 
     * @param c
     * @throws SemanticError
     */
    public static void addClass(ClassDecl c) throws SemanticError{
    	if (programClasses.containsKey(c.getName())){ 
    		throw new SemanticError("class already defined: "+c.getName(), c.getLineNum());
    	}
    	if (c.getSuperClassName() != null) {
    		if (!programClasses.containsKey(c.getSuperClassName()))
    			throw new SemanticError("super-class is undefined: " +c.getSuperClassName(),c.getLineNum());
    	}
    	
    	TypeClass ct = new TypeClass(c);
    	programClasses.put(c.getName(),ct);
    }
    
    /** 
     * Returns unique class type object
     */
    public static TypeClass getClass(String name) throws SemanticError{
    	TypeClass ct = programClasses.get(name);
    	if (ct == null) throw new SemanticError("class is undefined: "+ name);
    	else return ct;
    }
    
    public static TypeMethod typeMethod(Type returnType, List<Type> paramTypes){
    	TypeMethod mt = new TypeMethod(returnType,paramTypes);
    	String key = mt.toString();
    	
    	TypeMethod mt2 = programMethods.get(key);
    	if (mt2 == null) {
    		programMethods.put(key, mt);
    		return mt;
    	} else return mt2;
    	
    }
    
    /**
     * A getter that gets a String and returns the type
     */
    public static Type getType(String typeName) throws SemanticError{
    	Type t;
    	
    	// case primitive type
    	t = programPrimitives.get(typeName);
    	if (t != null) return t;
    	// case array type
    	if (typeName.endsWith("[]")) return typeArray(getType(typeName.substring(0, typeName.length()-2)));
    	// case class type
    	else return getClass(typeName);
    }
    
    /**
     * returns string representation for the TypeTable fitting the "-dump-symtab" IC.Compiler flag
     * @return
     */
    public static String staticToString(){
    	String str = "";
    	
    	// construct string representation for primitive types
    	Iterator<Type> programPrimitivesIter = programPrimitives.values().iterator();
    	String primitiveTypesStr = "";
    	while (programPrimitivesIter.hasNext()){
    		Type t = programPrimitivesIter.next();
    		primitiveTypesStr += "\t"+t.getTypeID()+": Primitive type: "+t.getName()+"\n";
    	}
    	
    	// construct string representation for class types
    	Iterator<TypeClass> programClassesIter = programClasses.values().iterator();
    	String TypeClasssStr = "";
    	while (programClassesIter.hasNext()){
    		TypeClass ct = programClassesIter.next();
    		TypeClasssStr += "\t"+ct.getTypeID()+": Class: "+ct.toString()+"\n";
    	}
    	
    	// construct string representation for array types
    	Iterator<TypeArray> programArraysIter = programArrays.values().iterator();
    	String TypeArraysStr = "";
    	while (programArraysIter.hasNext()){
    		TypeArray at = programArraysIter.next();
    		TypeArraysStr += "\t"+at.getTypeID()+": Array type: "+at.toString()+"\n";
    	}
    	
    	// construct string representation for method types
    	String TypeMethodsStr = "";
    	for (TypeMethod mt: programMethods.values()){
    		TypeMethodsStr += "\t"+mt.getTypeID()+": Method type: "+mt.toString()+"\n";
    	}
    	
    	str += primitiveTypesStr+TypeClasssStr+TypeArraysStr+TypeMethodsStr;
    	return str;
    }
    
    /**
     * Checks whether the name is of a primitive type (except for null or string).
     * @param name - type name.
     * @return true if type is primitive, false otherwise.
     */
    public static boolean isPrimitive(String name){
    	return ((name == "int") || (name == "boolean") || (name == "void"));
    }
    
}
