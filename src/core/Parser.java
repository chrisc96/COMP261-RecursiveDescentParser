package core;

import nodes.interfaces.RobotProgramNode;
import nodes.non_terminals.VarNode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.*;
import javax.swing.JFileChooser;

/**
 * The parser and interpreter. The top level parse function, a main method for
 * testing, and several utility methods are provided. You need to implement
 * parseProgram and all the rest of the parser.
 */
public class Parser {

	/**
	 * Top level parse method, called by the core.World
	 */
	static RobotProgramNode parseFile(File code) {
		Scanner scan = null;
		try {
			scan = new Scanner(code);

			// the only time tokens can be next to each other is
			// when one of them is one of (){},;
			scan.useDelimiter("\\s+|(?=[{}(),;])|(?<=[{}(),;])");

			RobotProgramNode n = ParserCore.parseProgram(scan);

			scan.close();
			return n;
		} catch (FileNotFoundException e) {
			System.out.println("core.Robot program source file not found");
		} catch (ParserFailureException e) {
			System.out.println("core.Parser error:");
			System.out.println(e.getMessage());
			scan.close();
		}
		return null;
	}

	/** For testing the parser without requiring the world */

	public static void main(String[] args) {
		if (args.length > 0) {
			for (String arg : args) {
				File f = new File(arg);
				if (f.exists()) {
					System.out.println("Parsing '" + f + "'");
					RobotProgramNode prog = parseFile(f);
					System.out.println("Parsing completed ");
					if (prog != null) {
						System.out.println("================\nProgram:");
						System.out.println(prog);
					}
					System.out.println("=================");
				} else {
					System.out.println("Can't find file '" + f + "'");
				}
			}
		} else {
			while (true) {
				JFileChooser chooser = new JFileChooser(".");// System.getProperty("user.dir"));
				int res = chooser.showOpenDialog(null);
				if (res != JFileChooser.APPROVE_OPTION) {
					break;
				}
				RobotProgramNode prog = parseFile(chooser.getSelectedFile());
				System.out.println("Parsing completed");
				if (prog != null) {
					System.out.println("Program: \n" + prog);
				}
				System.out.println("=================");
			}
		}
		System.out.println("Done");
	}

	// Useful Patterns
	static Pattern NUM = Pattern.compile("-?[1-9][0-9]*|0");
	static Pattern VAR = Pattern.compile("\\$[A-Za-z][A-Za-z0-9]*");
	static Pattern OPENPAREN = Pattern.compile("\\(");
	static Pattern CLOSEPAREN = Pattern.compile("\\)");
	static Pattern OPENBRACE = Pattern.compile("\\{");
	static Pattern CLOSEBRACE = Pattern.compile("\\}");
	static Pattern SEMICOLON = Pattern.compile(";");
	static Pattern COMMA = Pattern.compile(",");

	// Statements
	static Pattern LOOP = Pattern.compile("loop");
	static Pattern IF = Pattern.compile("if");
	static Pattern ELSE = Pattern.compile("else");
	static Pattern ELIF = Pattern.compile("elif");
	static Pattern WHILE = Pattern.compile("while");

	// Actions
	static Pattern ACTIONS = Pattern.compile("move|takeFuel|turnL|turnR|wait|turnAround|shieldOn|shieldOff");
	static Pattern MOVE = Pattern.compile("move");
	static Pattern TAKEFUEL = Pattern.compile("takeFuel");
	static Pattern TURNLEFT = Pattern.compile("turnL");
	static Pattern TURNRIGHT = Pattern.compile("turnR");
	static Pattern WAIT = Pattern.compile("wait");
	static Pattern TURNAROUND = Pattern.compile("turnAround");
	static Pattern SHIELDON = Pattern.compile("shieldOn");
	static Pattern SHIELDOFF = Pattern.compile("shieldOff");

	// Sensors
	static Pattern SENSORS = Pattern.compile("fuelLeft|oppLR|oppFB|numBarrels|barrelLR|barrelFB|wallDist");
	static Pattern FUELLEFT = Pattern.compile("fuelLeft");
	static Pattern OPPLR = Pattern.compile("oppLR");
	static Pattern OPPFB = Pattern.compile("oppFB");
	static Pattern NUMBARRELS = Pattern.compile("numBarrels");
	static Pattern BARRELLR = Pattern.compile("barrelLR");
	static Pattern BARRELFB = Pattern.compile("barrelFB");
	static Pattern WALLDIST = Pattern.compile("wallDist");

	// Conditionals
	static Pattern CONDITIONALS = Pattern.compile("lt|gt|eq|and|or|not");
	static Pattern LESSTHAN = Pattern.compile("lt");
	static Pattern GREATERTHAN = Pattern.compile("gt");
	static Pattern EQUAL = Pattern.compile("eq");
	static Pattern AND = Pattern.compile("and");
	static Pattern OR = Pattern.compile("or");
	static Pattern NOT = Pattern.compile("not");

	// Operations
	static Pattern OPERATIONS = Pattern.compile("add|sub|mul|div");
	static Pattern ADD = Pattern.compile("add");
	static Pattern SUB = Pattern.compile("sub");
	static Pattern MULTIPLY = Pattern.compile("mul");
	static Pattern DIVIDE = Pattern.compile("div");

	static Map<String, VarNode> variables = new HashMap<>();

	// utility methods for the parser
	/**
	 * Report a failure in the parser.
	 */
	static void fail(String message, Scanner s) {
		String msg = message + "\n   @ ...";
		for (int i = 0; i < 5 && s.hasNext(); i++) {
			msg += " " + s.next();
		}
		throw new ParserFailureException(msg + "...");
	}

	/**
	 * Requires that the next token matches a pattern if it matches, it consumes
	 * and returns the token, if not, it throws an exception with an error
	 * message
	 */
	static String require(String p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	static String require(Pattern p, String message, Scanner s) {
		if (s.hasNext(p)) {
			return s.next();
		}
		fail(message, s);
		return null;
	}

	/**
	 * Requires that the next token matches a pattern (which should only match a
	 * number) if it matches, it consumes and returns the token as an integer if
	 * not, it throws an exception with an error message
	 */
	static int requireInt(String p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	static int requireInt(Pattern p, String message, Scanner s) {
		if (s.hasNext(p) && s.hasNextInt()) {
			return s.nextInt();
		}
		fail(message, s);
		return -1;
	}

	/**
	 * Checks whether the next token in the scanner matches the specified
	 * pattern, if so, consumes the token and return true. Otherwise returns
	 * false without consuming anything.
	 */
	static boolean checkFor(String p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}

	static boolean checkFor(Pattern p, Scanner s) {
		if (s.hasNext(p)) {
			s.next();
			return true;
		} else {
			return false;
		}
	}
}