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

	// only one instance exists
	private static TypeTable typeTable = null;

	// Types of IC literals
	private TypeInt intType;
	private TypeString stringType;
	private TypeBoolean booleanType;
	private TypeVoid voidType;
	private TypeNull nullType;

	// hash map that contains for each class in program, its type and type-name
	private Map<String, TypeClass> programClassTypes;
	// hash map that contains for each method in program, its type and type-name
	private Map<String, TypeMethod> programMethodTypes;
	// hash map that contains for each array in program, its element-types and
	// type-name
	private Map<Type, TypeArray> programArrayTypes;

	/**
	 * Type Table constructor initialize the literal types initialize the type
	 * hash maps
	 */
	private TypeTable() {
		this.intType = new TypeInt();
		this.stringType = new TypeString();
		this.booleanType = new TypeBoolean();
		this.voidType = new TypeVoid();
		this.nullType = new TypeNull();
		this.programClassTypes = new HashMap<String, TypeClass>();
		this.programMethodTypes = new HashMap<String, TypeMethod>();
		this.programArrayTypes = new HashMap<Type, TypeArray>();
	}

	// Singelton
	public static TypeTable getTypeTable() {
		if (typeTable == null) {
			typeTable = new TypeTable();
		}
		return typeTable;
	}

	/**
	 * Gets string representation of IC type
	 * 
	 * @return the Type object
	 * @throws SemanticError
	 *             in case type is undefined
	 */
	public Type getType(String typeStr) throws SemanticError {

		// check if the type is a literal
		if (typeStr.equals(slp.DataTypes.INT.getDescription())) {
			return intType;
		} else if (typeStr.equals(slp.DataTypes.STRING.getDescription())) {
			return stringType;
		} else if (typeStr.equals(slp.DataTypes.BOOLEAN.getDescription())) {
			return booleanType;
		} else if (typeStr.equals(slp.DataTypes.VOID.getDescription())) {
			return voidType;
		} else if (typeStr == "null") {
			return nullType;
		}

		// check if type-string belongs to an array type
		if (typeStr.endsWith("[]")) {
			// make sure array's elements type is valid
			Type elementsType = getType(typeStr.substring(0, typeStr.length() - 2));
			// add array-type to types map
			TypeArray arrType = new TypeArray(elementsType);

			// check if we have array type already
			if (getArrayType(arrType.getElementType()) == null) {
				// if not add new array type
				addArrayType(arrType);
			}
			return (getArrayType(arrType.getElementType()));
		}

		// The type is a user-defined type
		return getClassType(typeStr);
	}

	/**
	 * Gets the type of the array elements
	 * 
	 * @return the array type of these elements
	 * @throws semantic error if element type does not exist
	 */
	public Type getArrayType(Type arrayElementType) {
		if(programArrayTypes.containsKey(arrayElementType)){
			return programArrayTypes.get(arrayElementType);
		}
		return null;
	}

	public void addArrayType(TypeArray arrType) {
		// add new array type
		programArrayTypes.put(arrType.getElementType(), arrType);
	}

	/**
	 * Gets a method's return-type and formals-types Adds method-type to table,
	 * if not defined
	 * 
	 * @return the method's type
	 * @throws semantic error if class does not exist
	 */
	public TypeMethod adddMethodType(Type returnType, List<Type> formalsTypes) {
		TypeMethod methodType = new TypeMethod(returnType, formalsTypes);
		TypeMethod definedMethodType = programMethodTypes.get(methodType.toString());

		// method was not previously defined
		if (definedMethodType == null) {
			// add method-type to program types
			programMethodTypes.put(methodType.toString(), methodType);
			return methodType;
		}
		// return the existing method type
		return definedMethodType;
	}

	/**
	 * Gets a string representation of a class type
	 * 
	 * @return type of the class
	 * @throws semantic error if class does not exist
	 */
	public Type getClassType(String typeStr) throws SemanticError {
		TypeClass classType = this.programClassTypes.get(typeStr);

		// class type was not defined
		if (classType == null) {
			throw new SemanticError("Undefined class, " + typeStr);
		}
		return classType;
	}

	/**
	 * Gets a class AST node adds the class type to the type table
	 * 
	 * @throws SemanticError
	 * @throws semantic error if class already defined in program
	 */
	public void addClassType(ClassDecl classDecl) throws SemanticError {
		if (programClassTypes.containsKey(classDecl.getName())) {
			throw new SemanticError("Invalid class declaration, class already declared", classDecl.getLineNum());
		}
		// in case class has super-class
		String superClassName = classDecl.getSuperClassName();
		if (superClassName != null) {
			// check if super class is defined
			if (!programClassTypes.containsKey(superClassName)) {
				throw new SemanticError("Invalid class declaration, super class in undefined: '" + superClassName + "'",
						classDecl.getLineNum());
			}
		}

		TypeClass newClassType = new TypeClass(classDecl);
		programClassTypes.put(classDecl.getName(), newClassType);

	}

}