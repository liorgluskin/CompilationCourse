package slp;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

import java_cup.runtime.*;

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
			System.out.println("Parsed " + args[0] + " successfully!");
			Program root = (Program) parseSymbol.value;
<<<<<<< HEAD

=======
			
>>>>>>> 3d37c2531b055cf777b4254699a6233211364fe5
			//get file name of file
			Path p = Paths.get(args[0]);
			String fileName = p.getFileName().toString();

			// Pretty-print the program to System.out
			PrettyPrinter printer = new PrettyPrinter(root,fileName);
			printer.print();

			
			////TO DECIDE!!!
			// Handling IC Library class

			///////////////////////////////
			/// Semantic Analysis Part: ///
			///////////////////////////////

			// Create the Symbol Table
			
			
			// Interpret the program
			SLPEvaluator evaluator = new SLPEvaluator(root);
			evaluator.evaluate();
		} catch (Exception e) {
			System.out.print(e);
		}
	}

	/** Prints usage information about this application to System.out
	 */
	public static void printUsage() {
		System.out.println("Usage: slp file [-printtokens]");
	}
}