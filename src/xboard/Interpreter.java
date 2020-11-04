package xboard;

import datasystem.ChessBoard;
import datasystem.Move;
import utilities.Printer;
import intelligence.Minimax;

import java.util.Scanner;

/**
 * Clasa care "traduce" comenzile de la xboard si executa operatii necesare
 */
public class Interpreter {
	/**
	 * Cheama metoda necesara pentru comanda primita
	 *
	 * @param s sir de caractere primit de la xboard
	 */
	static void handleCommand(String s) {
		Scanner scanner = new Scanner(s);
		if (Printer.debugMode)
			Printer.listen(s);
		// in caz daca se trimite "usermove a1a2" in loc de "a1a2"
		if(!scanner.hasNext())
			return;
		String firstArg = scanner.next();
		String secondArg = "";
		if(scanner.hasNext())
			secondArg = scanner.next();
		// daca e comanda pentru a muta piesa. Ex. a1a2
		if (firstArg.length() > 3 && Character.isDigit(firstArg.charAt(1)) &&
				Character.isDigit(firstArg.charAt(3))) {
			receiveMove(firstArg);
			return;
		}
		switch (firstArg) {
			case "time":
				getTime(secondArg);
			case "xboard":
				//"xboard" doesn't expect any answer
				return;
			case "protover":
				protover();
				break;
			case "new":
				commandNew();
				break;
			case "quit":
				Main.quit();
			case "force":
				force();
				break;
			case "go":
				go();
				break;
			case "white":
				white();
				break;
			case "black":
				black();
				break;
			case "computer":
				computer();
				break;
			case "usermove":
				receiveMove(secondArg);
				break;
			//comanzi folosite doar de noi. Printeaza tabla cand se ruleaza programul din terminal
			case "show":
				Printer.changeShowMode();
			case "p":
				Printer.printBoard();
			default:
				return;
		}
	}

	/**
	 * Comanda "computer
	 */
	private static void computer() {
		ChessEngine.vsComputer = true;
	}

	/**
	 * Comanda "protover"
	 */
	private static void protover() {
		ChessEngine.tellXboard("feature sigint=0");
		ChessEngine.tellXboard("feature san=0");
		ChessEngine.tellXboard("feature done=1");
	}

	/**
	 * Comanda "new"
	 */
	private static void commandNew() {
		ChessEngine.reset();
	}

	private static void getTime(String s) {
		ChessEngine.setTime(Integer.parseInt(s));
	}

	/**
	 * Comanda "force"
	 */
	static void force() {
		ChessEngine.setForceMode(true);
		//TODO stop Clock
	}

	/**
	 * Comanda "go"
	 */
	static void go() {
		ChessEngine.started = true;
		ChessEngine.setForceMode(false);
		ChessEngine.setColor(ChessEngine.getWhoseMove());
		//TODO start clock
		engineMove();
	}

	/**
	 * Se executa mutarea piesei
	 */
	static void handleMove(Move move) {
		ChessBoard.moveOnBoard(move);
		ChessEngine.toggleWhoseMove();
	}

	/**
	 * Metoda primeste mutarea de la oponent si o trateaza.
	 * Dupa engine face mutare
	 *
	 * @param s String cu mutare
	 */
	static void receiveMove(String s) {
		Move move;
		if (s.length() < 5) {
			move = new Move(s.charAt(0) - 'a', s.charAt(1) - '0' - 1,
					s.charAt(2) - 'a', s.charAt(3) - '0' - 1);
		} else { // daca e promotitie
			move = new Move(s.charAt(0) - 'a', s.charAt(1) - '0' - 1,
					s.charAt(2) - 'a', s.charAt(3) - '0' - 1, 0, s.charAt(4));
		}

		handleMove(move);

		// pentru regimul "show"
		if (Printer.showMode)
			System.out.println("# Opponent's move: " + s);
		if (Printer.showMode)
			Printer.printBoard();
		// face mutare, si  trateaza cazul special in care engine-un
		// joaca inpotrva altui engige cand engine joaca cu alt engine
		//-------
		if (ChessEngine.forceMode)
			return;
		if (!ChessEngine.vsComputer || ChessEngine.started)
			engineMove();
		else
			return;
		if (Printer.showMode)
			Printer.printBoard();
	}

	/**
	 * Engine se gandeste si trimite mutare la xboard
	 */
	static void engineMove() {
		if (ChessBoard.checkmated(ChessEngine.color)) {
			ChessEngine.tellXboard("resign");
			return;
		}
		if (ChessBoard.stalemated(ChessEngine.color))
			return;
		Move move = Minimax.getMove(ChessEngine.color);
		handleMove(move);
		ChessEngine.tellXboard("move " + intToCharacter(move.x0) + (move.y0 + 1) +
				intToCharacter(move.x1) + (move.y1 + 1));
	}

	/**
	 * Comanda "white"
	 */
	static void white() {
		ChessEngine.executeColor(1);
	}

	/**
	 * Comanda "black"
	 */
	static void black() {
		ChessEngine.executeColor(-1);
	}

	static public String intToCharacter(int i) {
		return String.valueOf((char) (i + 'a'));
	}

}
