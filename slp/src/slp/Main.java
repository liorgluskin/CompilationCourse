package slp;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java_cup.runtime.*;
import semantic.SemanticEvaluator;
import semantic.TypeEvaluator;
import symbolTableHandler.GlobalSymbolTable;
import lir.*;

/** The entry point of the SLP (Straight Line Program) application.
 *
 */
public class Main {
	private static boolean printtokens = false;

	/** Reads an SLP and pretty-prints it.
	 * 
	 * @param args Should be the name of the file containing an SLP.
	 */
	public static void main(String[] args) {
		try {
			if (args.length == 0) {
				System.out.println("Error: Missing input file argument!");
				printUsage();
				System.exit(-1);
			}
			if (args.length == 2) {
				if (args[1].equals("-printtokens")) {
					printtokens = true;
				}
				else {
					printUsage();
					System.exit(-1);
				}
			}



			///////////////////////////////
			/// Lexical and Syntax Part: ///
			///////////////////////////////

			// Parse the input file
			FileReader txtFile = new FileReader(args[0]);
			Lexer scanner = new Lexer(txtFile);
			Parser parser = new Parser(scanner);

			Symbol parseSymbol = parser.parse();
			//System.out.println("Parsed " + args[0] + " successfully!");
			Program root = (Program) parseSymbol.value;

			//get file name of file
			Path p = Paths.get(args[0]);
			String fileName = p.getFileName().toString();

			// Pretty-print the program to System.out
			//PrettyPrinter printer = new PrettyPrinter(root,fileName);
			//printer.print();



			///////////////////////////////
			/// Semantic Analysis Part: ///
			///////////////////////////////	

			// Interpret the program:
			System.out.println("Semantic Evaluation of input file: '"+fileName+"'");
			System.out.println("---------------------------------------------------");

			// create symbol table and initial semantic evaluation
			// IC Library classes added to program symbol table automatically
			SemanticEvaluator evaluator = new SemanticEvaluator();
			GlobalSymbolTable global_st = evaluator.getSymbolTable(root);
			// validate semantics of program types
			TypeEvaluator typeEvalutor = new TypeEvaluator(global_st);
			typeEvalutor.visit(root, null);



			///////////////////////////////
			/// LIR Translation Part: /////
			///////////////////////////////	
			lir.VarLabelVisitor labelVisit = new lir.VarLabelVisitor();
			labelVisit.visit(root);
			
			lir.LirVisitor lirVisitor = new lir.LirVisitor(global_st);
			String lirCode = lirVisitor.getLirCode(root);
			System.out.println(lirCode);



		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** Prints usage information about this application to System.out
	 */
	public static void printUsage() {
		System.out.println("Usage: slp file [-printtokens]");
	}
}