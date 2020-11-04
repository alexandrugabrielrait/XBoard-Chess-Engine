package utilities;

import datasystem.*;
import xboard.ChessEngine;

import java.io.PrintStream;

public class Printer {
	public static boolean showMode = false; // <---- for Printer
	public static boolean debugMode = false; // <---- for debug

	public static void printBoard() {
		printBoard(System.out);
	}

	public static void printBoard(PrintStream out) {//printeaza tabla de sah
		int i, j;

		out.println("#____a__b__c__d__e__f__g__h__");
		for (i = 7; i >= 0; i--) {
			out.print("# " + (i + 1) + "│");
			for (j = 0; j < 8; j++) {
				if (ChessBoard.state.board[i][j] == null) {
					out.print(" . ");
					continue;
				}
				if (ChessBoard.state.board[i][j].color == -1) {
					out.print(Printer.ANSI_YELLOW);
				}
				if (ChessBoard.state.board[i][j] instanceof King) {
					out.print(" K ");
				} else if (ChessBoard.state.board[i][j] instanceof Bishop) {
					out.print(" B ");
				} else if (ChessBoard.state.board[i][j] instanceof Queen) {
					out.print(" Q ");
				} else if (ChessBoard.state.board[i][j] instanceof Pawn) {
					out.print(" P ");
				} else if (ChessBoard.state.board[i][j] instanceof Knight) {
					out.print(" N ");
				} else if (ChessBoard.state.board[i][j] instanceof Rook) {
					out.print(" R ");
				}
				if (ChessBoard.state.board[i][j].color == -1) {
					out.print(Printer.ANSI_RESET);
				}

			}
			out.println("│" + (i + 1) + " ");
		}
		out.println("#‾‾‾‾a‾‾b‾‾c‾‾d‾‾e‾‾f‾‾g‾‾h‾‾");
	}

	public static void changeShowMode() {
		showMode = !showMode;
		ChessEngine.tellXboard("Show mode: " + showMode);
		System.out.println("Current board:");
		printBoard();
	}

	public static void listen(String s) {
		System.out.print("# _____XBOARD SAYS : ");
		printYellow(s);
	}

	public static void printYellow(String s) {
		System.out.print(Printer.ANSI_YELLOW);
		System.out.print(s);
		System.out.println(Printer.ANSI_RESET);
	}

	public static void printRed(String s) {
		System.out.print(Printer.ANSI_RED);
		System.out.print(s);
		System.out.println(Printer.ANSI_RESET);
	}

	public static void printBlue(String s) {
		System.out.print(Printer.ANSI_BLUE);
		System.out.print(s);
		System.out.println(Printer.ANSI_RESET);
	}

	public static void printPurple(String s) {
		System.out.print(Printer.ANSI_PURPLE);
		System.out.print(s);
		System.out.println(Printer.ANSI_RESET);
	}

	public static void printGreen(String s) {
		System.out.print(Printer.ANSI_GREEN);
		System.out.print(s);
		System.out.println(Printer.ANSI_RESET);
	}

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_GREEN = "\u001B[42m";
	public static final String ANSI_PURPLE = "\u001B[45m";

}
