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

	// Types of IC literals
	private TypeInt intType;
	private TypeString stringType;
	private TypeBoolean booleanType;
	private TypeVoid voidType;
	private TypeNull nullType;

	// hash map that contains for each class in program, its type and type-name
	private Map<String,TypeClass> programClassTypes;
	// hash map that contains for each method in program, its type and type-name
	private Map<String,TypeMethod> programMethodTypes;
	// hash map that contains for each array in program, its element-types and type-name
	private Map<Type,TypeArray> programArrayTypes;


	/**
	 * Type Table constructor
	 * initialize the literal types
	 * initialize the type hash maps
	 */
	public TypeTable(){
		this.intType = new TypeInt();
		this.stringType = new TypeString();
		this.booleanType = new TypeBoolean();
		this.voidType = new TypeVoid();
		this.nullType = new TypeNull();
		this.programClassTypes = new HashMap<String,TypeClass>();
		this.programMethodTypes = new HashMap<String,TypeMethod>();
		this.programArrayTypes = new HashMap<Type,TypeArray>();
	}



	/**
	 * Gets string representation of IC type 
	 * @return the Type object
	 * @throws SemanticError in case type is undefined
	 */
	public Type getType(String typeStr) throws SemanticError{

		// check if the type is a literal
		if(typeStr == slp.DataTypes.INT.getDescription()){
			return intType;
		}else if(typeStr == slp.DataTypes.STRING.getDescription()){
			return stringType;
		}else if(typeStr == slp.DataTypes.BOOLEAN.getDescription()){
			return booleanType;
		}else if(typeStr == slp.DataTypes.VOID.getDescription()){
			return voidType;
		}else if(typeStr == "null"){
			return nullType;
		}

		// check if type-string belongs to an array type
		if(typeStr.endsWith("[]")){
			// make sure array's elements type is valid
			Type elementsType = getArrayElemType(typeStr.substring(0, typeStr.length()-2));
			// add array-type to types map
			TypeArray arrType = new TypeArray(elementsType);
			addArrayType(arrType);
			return arrType; 
		}

		// The type is a user-defined type
		return getClassType(typeStr);
	}


	/**
	 * Gets the type of the array elements
	 * @return the array type of these elements
	 * @throws semantic error if element type does not exist
	 * */
	public Type getArrayype(Type arrayElementType) {
		Type elementType;

		// Check if array element-type is literal:

		// check if element type was defined as int
		if(elemTypeStr == slp.DataTypes.INT.getDescription()){
			return intType;
		}
		// check if element type was defined as string
		if(elemTypeStr == slp.DataTypes.STRING.getDescription()){
			return stringType;
		}
		// check if element type was defined as boolean
		if(elemTypeStr == slp.DataTypes.BOOLEAN.getDescription()){
			return booleanType;
		}
		// check if element type was defined as void
		if(elemTypeStr == slp.DataTypes.VOID.getDescription()){
			return null; // no arrays of type 'void' allowed
		}

		// check if element type was defined as array-type
		elementType = this.programArrayTypes.get(elemTypeStr);
		if(elementType != null){
			return elementType;
		}

		// check if element type was defined as class-type
		elementType = this.programClassTypes.get(elemTypeStr);
		if(elementType != null){
			return elementType;
		}

		// array's element type is undefined
		return null;
	}

	
	public void addArrayType(TypeArray arrType) {
		// check if array type already exists
		
		// if not - add new array type
	}

	/**
	 * Gets a string representation of a class type
	 * @return type of the class
	 * @throws semantic error if class does not exist
	 * */
	public Type getClassType(String typeStr) throws SemanticError{
		TypeClass classType = this.programClassTypes.get(typeStr);

		// class type was not defined
		if(classType == null){
			throw new SemanticError("Undefined class, "+typeStr);
		}
		return classType;
	}

	public void addClassType(ClassDecl classDecl) {
		// TODO Auto-generated method stub

	}

}
