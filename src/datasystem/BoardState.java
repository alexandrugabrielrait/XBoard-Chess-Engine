package datasystem;

import intelligence.Minimax;

/**
 * Descrie o stare a tablii de sah
 */
public class BoardState implements Cloneable {
	public Piece[][] board;
	public Team whiteTeam;
	public Team blackTeam;

	public BoardState(Piece[][] board, Team whiteTeam, Team blackTeam) {
		this.board = board;
		this.whiteTeam = whiteTeam;
		this.blackTeam = blackTeam;
	}

	public BoardState() {

	}

	/**
	 * Calculeaza euristica din punctul de vedere al unui jucator
	 */
	public float evaluate(int player) {
		if (blackTeam.isKingDead()) {
			return player * Minimax.CHECK_MATE;
		}
		if (whiteTeam.isKingDead()) {
			return -player * Minimax.CHECK_MATE;
		}
		float value = 0;
		for (Piece p : whiteTeam) {
				value += p.value;
			}
		for (Piece p : blackTeam) {
				value -= p.value;
			}
		return player * value;
	}

	public BoardState clone() {
		Piece[][] copy = new Piece[8][8];
		Team whiteClone = whiteTeam.clone();
		Team blackClone = blackTeam.clone();
		for (Piece p : whiteClone) {
			copy[p.y][p.x] = p;
		}
		for (Piece p : blackClone) {
			copy[p.y][p.x] = p;
		}
		return new BoardState(copy, whiteClone, blackClone);
	}

}
