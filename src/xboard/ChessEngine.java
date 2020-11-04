package xboard;

import datasystem.ChessBoard;
import utilities.Printer;

import java.util.Scanner;

/**
 * Clasa care reprezinta engine
 */
public class ChessEngine {


	static Scanner sc = new Scanner(System.in);
	static boolean forceMode = false;
	/**
	 * 1 = alb, -1 = negru
	 * implicit este alb
	 */
	static int whoseMove = 1;
	static boolean vsComputer = false;
	/**
	 * Se foloseste cand jucam contra altui engine
	 */
	static boolean started = false;
	//black = -1 ; white = 1
	/**
	 * 1 = alb, -1 = negru
	 */
	static int color = -1; //by default is black

	static int time;

	public static void setColor(int color) {
		ChessEngine.color = color;
	}

	/**
	 * Trimite comanda la xboard
	 */
	static public void tellXboard(String s) {
		System.out.println(s);
		if (Printer.debugMode) {
			System.out.print("# _____ENGINE SAYS : ");
			Printer.printRed(s);
		}

	}

	/**
	 * Reseteaza engine la setari impicite si reseteaza tabla
	 */
	public static void reset() {
		executeColor(1);
		vsComputer = false;
		started = false;
		forceMode = false;
		whoseMove = 1;
		color = -1;
		//TODO clock
		ChessBoard.resetBoard();
	}

	/**
	 * executa comenzile "white" si "black" in functie de parametru
	 */
	static void executeColor(int color) {
		setWhoseMove(color);
		setColor(-color);
		//TODO stop clocks
	}

	public static void setWhoseMove(int whoseMove) {
		ChessEngine.whoseMove = whoseMove;
	}

	public static void toggleWhoseMove() {
		whoseMove *= -1;
	}

	public static void setForceMode(boolean forceMode) {
		ChessEngine.forceMode = forceMode;
	}

	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		ChessEngine.time = time;
	}

	public static int getWhoseMove() {
		return whoseMove;
	}

}
