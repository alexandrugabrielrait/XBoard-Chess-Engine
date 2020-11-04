package datasystem;

import xboard.Interpreter;

import java.util.*;

public class MoveList extends ArrayList<Move> {

	public MoveList() {
	}

	public MoveList(Team team) {
			if (team.king == null) {
				return;
			}
			for (Piece piece : team) {
				if (piece != null)
					piece.addValidMoves(this);
			}
	}

	public void create(Piece piece, int x, int y) {
		add(new Move(piece.x, piece.y, x, y));
	}

	public void create(Piece piece, int x, int y, float value) {
		add(new Move(piece.x, piece.y, x, y, value));
	}

	public void create(Piece piece, int x, int y, float value, char promotion) {
		add(new Move(piece.x, piece.y, x, y, value, promotion));
	}

	/**
	 * Alege o mutare cu valoare maxima din lista
	 */
	public static Move pick(MoveList list) {
		Collections.sort(list);
		Move m = list.get(list.size() - 1);
		return m;
	}

	/**
	 * Alege o mutare la intamplare din lista
	 */
	public static Move pickRandom(MoveList list) {
		Move m = list.get(new Random().nextInt(list.size()));
		return m;
	}


	/**
	 * Scoate toate mutarile cu valoare mai mica de cea maxima si alege o mutare la intamplare
	 */
	public static Move pickRandomFromBest(MoveList list) {
		Collections.sort(list);
		float value = list.get(list.size() - 1).value;
		Iterator<Move> moveIterator = list.iterator();
		while (moveIterator.hasNext()) {
			if (moveIterator.next().value < value)
				moveIterator.remove();
		}
		return pickRandom(list);
	}
}
